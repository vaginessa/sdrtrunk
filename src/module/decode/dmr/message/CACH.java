/*******************************************************************************
 * sdrtrunk
 * Copyright (C) 2014-2017 Dennis Sheirer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 ******************************************************************************/
package module.decode.dmr.message;

import bits.BinaryMessage;
import bits.CorrectedBinaryMessage;

public class CACH
{
    private static final int AT_INBOUND_TIMESLOT_STATUS = 0;
    private static final int TC_TIMESLOT = 4;
    private static final int[] LINK_CONTROL_START_STOP = new int[]{8,12};
    private static final int[] HAMMING_WORD = new int[]{0,4,8,12,14,18,22};
    private static final int[] HAMMING_CRC = new int[]{14,18,22};
    private static final int[] HAMMING_7_4_3_CRC = new int[]{5,7,6,3,4,2,1};
    private static final int[] PAYLOAD_INDEXES = new int[]{1,2,3,5,6,7,9,10,11,13,15,16,17,19,20,21,23};

    public enum TimeslotStatus {IDLE, BUSY};

    private CorrectedBinaryMessage mMessage;
    private BinaryMessage mPayloadMessage;

    /**
     * Common Announcement Channel is a 24-bit sequence that precedes an outbound DMR frame transmitted by a repeater.
     * @param message containing a complete transmitted DMR frame where the initial 24 bits contain the transmitted
     * CACH frame.  Note: any error correction information will be added to this original corrected message.
     */
    public CACH(CorrectedBinaryMessage message)
    {
        mMessage = message;
        errorCheck();
    }

    /**
     * Performs error detection and correction over the Hamming(7,4,3) protected information bits and crc.
     */
    private void errorCheck()
    {
        int checksum = 0;

        for(int x = 0; x < 4; x++)
        {
            if(mMessage.get(HAMMING_WORD[x]))
            {
                checksum ^= HAMMING_7_4_3_CRC[x];
            }
        }

        int crc = mMessage.getInt(HAMMING_CRC);

        checksum ^= crc;

        if(checksum != 0)
        {
            for(int x = 0; x < HAMMING_7_4_3_CRC.length; x++)
            {
                if(checksum == HAMMING_7_4_3_CRC[x])
                {
                    mMessage.flip(HAMMING_WORD[x]);
                    mMessage.setCorrectedBitCount(mMessage.getCorrectedBitCount() + 1);
                }
            }
        }
    }

    /**
     * Indicates the state of the next inbound frame for this timeslot, IDLE or BUSY
     */
    public TimeslotStatus getInboundTimeslotStatus()
    {
        if(mMessage.get(AT_INBOUND_TIMESLOT_STATUS))
        {
            return TimeslotStatus.BUSY;
        }
        else
        {
            return TimeslotStatus.IDLE;
        }
    }

    /**
     * Indicates if the next inbound frame for this timeslot is busy
     */
    public boolean isBusy()
    {
        return getInboundTimeslotStatus() == TimeslotStatus.BUSY;
    }

    /**
     * Link Control or CSBK Start/Stop fragment indicator
     */
    public LCSS getLCSS()
    {
        return LCSS.fromValue(mMessage.getInt(LINK_CONTROL_START_STOP));
    }

    /**
     * Indicates the timeslot that follows this CACH frame
     */
    public int getTimeslot()
    {
        return mMessage.get(TC_TIMESLOT) ? 1 : 0;
    }

    /**
     * Binary Payload (17-bit) message fragment.  Note: the payload represents 1/4 of a Short Link Control (SLC) frame,
     * therefore four CACH frames must be assembled to form a complete SLC message where the first frame has an LCSS
     * start indicator followed by two LCSS continuation frames and an LCSS final/last frame.
     */
    public BinaryMessage getPayload()
    {
        if(mPayloadMessage == null)
        {
            mPayloadMessage = new BinaryMessage(17);

            for(int x = 0; x < PAYLOAD_INDEXES.length; x++)
            {
                if(mMessage.get(PAYLOAD_INDEXES[x]))
                {
                    mPayloadMessage.set(x);
                }
            }
        }

        return mPayloadMessage;
    }

    /**
     * String representation of the parsed CACH
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("CACH");
        sb.append(" TS:").append(getTimeslot());
        sb.append(" IN:").append(getInboundTimeslotStatus().name());
        sb.append(" ").append(getLCSS()).append(getPayload().toHexString());

        return sb.toString();
    }
}
