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

/**
 * Sync patterns used by DMR
 */
public enum DMRSyncPattern
{
    BASE_STATION_SOURCED_VOICE(0x755FD7DF75F7l, "BASE DATA"),
    BASE_STATION_SOURCED_DATA(0xDFF57D75DF5Dl, "BASE VOICE"),
    MOBILE_STATION_SOURCED_VOICE(0x7F7D5DD57DFDl, "MOBILE DATA"),
    MOBILE_STATION_SOURCED_DATA(0xD5D7F77FD757l, "MOBILE VOICE"),
    MOBILE_STATION_REVERSE_CHANNEL(0x77D55F7DFD77l, "MOBILE REVERSE"),
    DIRECT_MODE_VOICE_TIMESLOT_1(0x5D577F7757FFl, "DIRECT MODE VOICE TS1"),
    DIRECT_MODE_DATA_TIMESLOT_1(0xF7FDD5DDFD55l, "DIRECT MODE VOICE TS1"),
    DIRECT_MODE_VOICE_TIMESLOT_2(0x7DFFD5F55D5Fl, "DIRECT MODE VOICE TS2"),
    DIRECT_MODE_DATA_TIMESLOT_2(0xD7557F5FF7F5l, "DIRECT MODE VOICE TS2"),
    RESERVED(0xDD7FF5D757DDl, "RESERVED PATTERN"),
    UNKNOWN(-1, "UNKNOWN");

    private long mPattern;
    private String mLabel;

    /**
     * DMR Sync Patterns.  See TS 102-361-1, paragraph 9.1.1
     */
    DMRSyncPattern(long pattern, String label)
    {
        mPattern = pattern;
        mLabel = label;
    }

    /**
     * Pattern that represents the enum entry
     */
    public long getPattern()
    {
        return mPattern;
    }

    public String toString()
    {
        return mLabel;
    }

    /**
     * Lookup the DMR Sync Pattern from the transmitted value.
     * @param value to match to a pattern
     * @return the matching enum entry or UNKNOWN
     */
    public static DMRSyncPattern fromValue(long value)
    {
        for(DMRSyncPattern pattern: DMRSyncPattern.values())
        {
            if(pattern.getPattern() == value)
            {
                return pattern;
            }
        }

        return UNKNOWN;
    }
}
