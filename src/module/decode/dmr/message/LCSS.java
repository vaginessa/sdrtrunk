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

public enum LCSS
{
    SINGLE_FRAGMENT("[FL]"),
    FIRST_FRAGMENT("[F-]"),
    LAST_FRAGMENT("[-L]"),
    CONTINUATION_FRAGMENT("[--]"),
    UNKNOWN("[??]");

    private String mLabel;

    /**
     * Link Control(LC) Start/Stop indicator.  Used to identify the starting, continuation and last frames in a frame
     * sequence
     */
    LCSS(String label)
    {
        mLabel = label;
    }

    /**
     * Lookup the LCSS entry from the value
     * @param value (0 - 3)
     */
    public static LCSS fromValue(int value)
    {
        if(0 <= value && value <= 4)
        {
            return LCSS.values()[value];
        }

        return UNKNOWN;
    }

    /**
     * String representation of the entry
     */
    public String toString()
    {
        return mLabel;
    }
}
