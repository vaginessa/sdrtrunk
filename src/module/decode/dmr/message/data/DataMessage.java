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
package module.decode.dmr.message.data;

import alias.Alias;
import bits.CorrectedBinaryMessage;
import module.decode.dmr.message.DMRMessage;
import module.decode.dmr.message.DMRSyncPattern;

public class DataMessage extends DMRMessage
{
    private SlotType mSlotType;

    /**
     * DMR Data Message.
     *
     * @param syncPattern either BASE_STATION_DATA or MOBILE_STATION_DATA
     * @param message containing 288-bit DMR message with preliminary bit corrections indicated.
     */
    public DataMessage(DMRSyncPattern syncPattern, CorrectedBinaryMessage message)
    {
        super(syncPattern, message);
    }

    /**
     * Slot Type identifies the color code and data type for this data message
     */
    public SlotType getSlotType()
    {
        if(mSlotType == null)
        {
            mSlotType = new SlotType(getTransmittedMessage());
        }

        return mSlotType;
    }

    @Override
    public String toString()
    {
        return null;
    }

    @Override
    public boolean isValid()
    {
        return getTransmittedMessage().isCorrected();
    }

    @Override
    public String getErrorStatus()
    {
        return null;
    }

    @Override
    public String getMessage()
    {
        return null;
    }

    @Override
    public String getBinaryMessage()
    {
        return getTransmittedMessage().toHexString();
    }

    @Override
    public String getEventType()
    {
        return null;
    }

    @Override
    public String getFromID()
    {
        return null;
    }

    @Override
    public Alias getFromIDAlias()
    {
        return null;
    }

    @Override
    public String getToID()
    {
        return null;
    }

    @Override
    public Alias getToIDAlias()
    {
        return null;
    }
}
