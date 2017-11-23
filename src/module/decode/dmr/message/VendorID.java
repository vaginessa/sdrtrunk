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
    STANDARD(0x00, "STANDARD"),
    RESERVED_1(0x01, "RESERVED 1"),
    RESERVED_2(0x02, "RESERVED 2"),
    RESERVED_3(0x03, "RESERVED 3"),
    FYLDE_MICRO(0x04, "FYLDE MICRO"),
    PROD_EL_SPA_SELEX_MILAN(0x05, "SELEX ECOS-D"),
    TRIDENT_MICRO_SYSTEMS(6, "MOTOROLA IPSC"),
    RADIO_DATA_GMBH(0x07, "RADIO DATA"),
    HYTERA_SCIENCE_TECH_08(0x08, "HYTERA 08"),
    ASELSAN_ELEKTRONIK(0x09, "ASELSAN"),
    KIRISUN(0x0A, "KIRISUN"),
    DMR_ASSOCIATION(0x0B, "DMR ASSOC"),
    MOTOROLA_CAPACITY_PLUS(0x10, "MOTOROLA CAP+"),
    ELECTRONIC_MARKETING_COMPANY_13(0x13, "EMC 13"),
    ELECTRONIC_MARKETING_COMPANY_1C(0x1C, "EMC 1C"),
    JVC_KENWOOD(0x20, "JVC-KENWOOD"),
    RADIO_ACTIVITY_33(0x33, "RADIO ACTIVITY 33"),
    RADIO_ACTIVITY_3C(0x3C, "RADIO ACTIVITY 3C"),
    TAIT(0x58, "TAIT"),
    HYTERA_SCIENCE_TECH_68(0x68, "HYTERA 68"),
    VERTEX_STANDARD(0x77, "VERTEX STANDARD"),
    UNKNOWN(-1, "UNKNOWN");

    private int mIdentifier;
    private String mLabel;

    /**
     * Vendor Identifier.  Note: this is referred to as the Manufacturer Feature Identifier (MFID) in the DMR
     * documentation.  See TS 102-361-1 Appendix H.1
     *
     * Vendor ID 1-3 and 128-255 are reserved for future implementations.  Identifiers in range 4-127 are valid.
     *
     * @param identifier value that represents the vendor-specific message implementation
     */
    VendorID(int identifier, String label)
    {
        mIdentifier = identifier;
        mLabel = label;
    }

    /**
     * Feature Identifier (FID) for the vendor-specific identifier
     */
    public int getIdentifier()
    {
        return mIdentifier;
    }

    /**
     * Overrides the string value for this entry
     */
    public String toString()
    {
        return mLabel;
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
            case 0x00:
                return STANDARD;
            case 0x01:
                return RESERVED_1;
            case 0x02:
                return RESERVED_2;
            case 0x03:
                return RESERVED_3;
            case 0x04:
                return FYLDE_MICRO;
            case 0x05:
                return PROD_EL_SPA_SELEX_MILAN;
            case 0x06:
                return TRIDENT_MICRO_SYSTEMS;
            case 0x07:
                return RADIO_DATA_GMBH;
            case 0x08:
                return HYTERA_SCIENCE_TECH_08;
            case 0x09:
                return ASELSAN_ELEKTRONIK;
            case 0x0A:
                return KIRISUN;
            case 0x0B:
                return DMR_ASSOCIATION;
            case 0x10:
                return MOTOROLA_CAPACITY_PLUS;
            case 0x13:
                return ELECTRONIC_MARKETING_COMPANY_13;
            case 0x1C:
                return ELECTRONIC_MARKETING_COMPANY_1C;
            case 0x20:
                return JVC_KENWOOD;
            case 0x33:
                return RADIO_ACTIVITY_33;
            case 0x3C:
                return RADIO_ACTIVITY_3C;
            case 0x58:
                return TAIT;
            case 0x68:
                return HYTERA_SCIENCE_TECH_68;
            case 0x77:
                return VERTEX_STANDARD;
            default:
                return UNKNOWN;
        }
    }
}
