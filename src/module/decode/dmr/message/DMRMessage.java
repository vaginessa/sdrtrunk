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

import bits.CorrectedBinaryMessage;
import message.Message;

public abstract class DMRMessage extends Message
{
    private DMRSyncPattern mSyncPattern;
    private CorrectedBinaryMessage mMessage;

    /**
     * DMR message frame.  This message is comprised of a 24-bit prefix and a 264-bit message frame.  Outbound base
     * station frames transmit a Common Announcement Channel (CACH) in the 24-bit prefix, whereas Mobile inbound frames
     * do not use the 24-bit prefix.
     *
     * @param message containing 288-bit DMR message with preliminary bit corrections indicated.
     */
    public DMRMessage(DMRSyncPattern syncPattern, CorrectedBinaryMessage message)
    {
        mSyncPattern = syncPattern;
        mMessage = message;
    }

    @Override
    public String getProtocol()
    {
        return "DMR";
    }

    /**
     * DMR Sync pattern used by this message
     */
    public DMRSyncPattern getSyncPattern()
    {
        return mSyncPattern;
    }

    /**
     * The original message as captured over the wire with initial error detection and correction applied.
     */
    public CorrectedBinaryMessage getOriginalMessage()
    {
        return mMessage;
    }
}
