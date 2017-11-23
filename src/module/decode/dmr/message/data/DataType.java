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

public enum DataType
{
    PRIVACY_INICATOR_HEADER(0),
    VOICE_LINK_CONTROL_HEADER(1),
    TERMINATOR_WITH_LINK_CONTROL(2),
    CSBK(3),
    MULTI_BLOCK_CONTROL_HEADER(4),
    MULTI_BLOCK_CONTROL_CONTINUATION(5),
    DATA_HEADER(6),
    RATE_1_2_DATA(7),
    RATE_3_4_DATA(8),
    IDLE(9),
    RATE_1_DATA(10),
    UNIFIED_SINGLE_BLOCK_DATA(11),
    RESERVED_12(12),
    RESERVED_13(13),
    RESERVED_14(14),
    RESERVED_15(15),
    UNKNOWN(-1);

    private int mValue;

    DataType(int value)
    {
        mValue = value;
    }

    /**
     * Lookup the data type from the value.
     */
    public static DataType fromValue(int value)
    {
        if(0 <= value && value <= 15)
        {
            return DataType.values()[value];
        }

        return UNKNOWN;
    }
}
