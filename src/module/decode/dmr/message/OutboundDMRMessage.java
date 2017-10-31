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

public abstract class OutboundDMRMessage extends DMRMessage
{
    private CACH mCACH;

    /**
     * Outbound DMR message frame transmitted by a repeater.  This message is comprised of a 24-bit Common Announcement
     * Channel and a 264-bit message frame.
     *
     * @param syncPattern
     * @param message containing 288-bit DMR message with preliminary bit corrections indicated.
     */
    public OutboundDMRMessage(DMRSyncPattern syncPattern, CorrectedBinaryMessage message)
    {
        super(syncPattern, message);
    }

    public CACH getCACH()
    {
        if(mCACH == null)
        {
            mCACH = new CACH(getOriginalMessage());
        }

        return mCACH;
    }
}
