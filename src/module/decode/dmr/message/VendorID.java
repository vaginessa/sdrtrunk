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

public enum VendorID
{
    STANDARD(0),
    MOTOROLA_IP_SITE_CONNECT(6),
    MOTOROLA_CAPACITY_PLUS(16),
    UNKNOWN(-1);

    private int mIdentifier;

    /**
     * Vendor Identifier.  Note: this is referred to as the Manufacturer Feature Identifier (MFID) in the DMR
     * documentation.  See TS 102-361-1 Appendix H.1
     *
     * Vendor ID 1-3 and 128-255 are reserved for future implementations.  Identifiers in range 4-127 are valid.
     *
     * @param identifier value that represents the vendor-specific message implementation
     */
    VendorID(int identifier)
    {
        mIdentifier = identifier;
    }

    /**
     * Feature Identifier (FID) for the vendor-specific identifier
     */
    public int getIdentifier()
    {
        return mIdentifier;
    }

    /**
     * Lookup the vendor ID enum entry from the Feature Set identifier
     * @param identifier or FID
     * @return entry or UNKNOWN if the identifier does not match the listed enumerations.
     */
    public static VendorID fromIdentifier(int identifier)
    {
        switch(identifier)
        {
            case 0:
                return STANDARD;
            case 6:
                return MOTOROLA_IP_SITE_CONNECT;
            case 16:
                return MOTOROLA_CAPACITY_PLUS;
            default:
                return UNKNOWN;
        }
    }
}
