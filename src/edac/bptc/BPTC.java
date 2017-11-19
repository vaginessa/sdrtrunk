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
package edac.bptc;

import bits.BinaryMessage;
import bits.CorrectedBinaryMessage;

import java.util.ArrayList;
import java.util.List;

public class BPTC
{
    //Interleave specified in the DMR ICD
    public static int[] INTERLEAVE = new int[]{0,181,166,151,136,121,106,91,76,61,46,31,16,1,182,167,152,137,122,107,92,
        77,62,47,32,17,2,183,168,153,138,123,108,93,78,63,48,33,18,3,184,169,154,139,124,109,94,79,64,49,34,19,4,
        185,170,155,140,125,110,95,80,65,50,35,20,5,186,171,156,141,126,111,96,81,66,51,36,21,6,187,172,157,142,127,
        112,97,82,67,52,37,22,7,188,173,158,143,128,113,98,83,68,53,38,23,8,189,174,159,144,129,114,99,84,69,54,39,
        24,9,190,175,160,145,130,115,100,85,70,55,40,25,10,191,176,161,146,131,116,101,86,71,56,41,26,11,192,177,
        162,147,132,117,102,87,72,57,42,27,12,193,178,163,148,133,118,103,88,73,58,43,28,13,194,179,164,149,134,119,
        104,89,74,59,44,29,14,195,180,165,150,135,120,105,90,75,60,45,30,15};

    //Interleave offsets for a 24-bit CACH + 264-bit DMR frame - calculated using getInterleaveOffsets()
    public static int[] INTERLEAVE_OFFSETS = new int[]{24,273,258,243,228,213,198,115,100,85,70,55,40,25,274,259,244,
        229,214,199,116,101,86,71,56,41,26,275,260,245,230,215,200,117,102,87,72,57,42,27,276,261,246,231,216,201,118,
        103,88,73,58,43,28,277,262,247,232,217,202,119,104,89,74,59,44,29,278,263,248,233,218,203,120,105,90,75,60,45,
        30,279,264,249,234,219,204,121,106,91,76,61,46,31,280,265,250,235,220,205,190,107,92,77,62,47,32,281,266,251,
        236,221,206,191,108,93,78,63,48,33,282,267,252,237,222,207,192,109,94,79,64,49,34,283,268,253,238,223,208,193,
        110,95,80,65,50,35,284,269,254,239,224,209,194,111,96,81,66,51,36,285,270,255,240,225,210,195,112,97,82,67,52,
        37,286,271,256,241,226,211,196,113,98,83,68,53,38,287,272,257,242,227,212,197,114,99,84,69,54,39};

    public static int[][] ROW_INDEXES = new int[][]{{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
        {16,17,18,19,20,21,22,23,24,25,26,27,28,29,30}, {31,32,33,34,35,36,37,38,39,40,41,42,43,44,45},
        {46,47,48,49,50,51,52,53,54,55,56,57,58,59,60}, {61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
        {76,77,78,79,80,81,82,83,84,85,86,87,88,89,90}, {91,92,93,94,95,96,97,98,99,100,101,102,103,104,105},
        {106,107,108,109,110,111,112,113,114,115,116,117,118,119,120},
        {121,122,123,124,125,126,127,128,129,130,131,132,133,134,135},
        {136,137,138,139,140,141,142,143,144,145,146,147,148,149,150},
        {151,152,153,154,155,156,157,158,159,160,161,162,163,164,165},
        {166,167,168,169,170,171,172,173,174,175,176,177,178,179,180},
        {181,182,183,184,185,186,187,188,189,190,191,192,193,194,195}};

    public static int[][] ROW_CRC_INDEXES = new int[][]{{12,13,14,15}, {27,28,29,30}, {42,43,44,45}, {57,58,59,60},
        {72,73,74,75}, {87,88,89,90}, {102,103,104,105}, {117,118,119,120}, {132,133,134,135}, {147,148,149,150},
        {162,163,164,165}, {177,178,179,180}, {192,193,194,195}};

    public static int[][] COLUMN_INDEXES = new int[][]{{1,16,31,46,61,76,91,106,121,136,151,166,181},
        {2,17,32,47,62,77,92,107,122,137,152,167,182}, {3,18,33,48,63,78,93,108,123,138,153,168,183},
        {4,19,34,49,64,79,94,109,124,139,154,169,184}, {5,20,35,50,65,80,95,110,125,140,155,170,185},
        {6,21,36,51,66,81,96,111,126,141,156,171,186}, {7,22,37,52,67,82,97,112,127,142,157,172,187},
        {8,23,38,53,68,83,98,113,128,143,158,173,188}, {9,24,39,54,69,84,99,114,129,144,159,174,189},
        {10,25,40,55,70,85,100,115,130,145,160,175,190}, {11,26,41,56,71,86,101,116,131,146,161,176,191},
        {12,27,42,57,72,87,102,117,132,147,162,177,192}, {13,28,43,58,73,88,103,118,133,148,163,178,193},
        {14,29,44,59,74,89,104,119,134,149,164,179,194}, {15,30,45,60,75,90,105,120,135,150,165,180,195}};

    public static int[][] COLUMN_CRC_INDEXES = new int[][]{{136,151,166,181}, {137,152,167,182}, {138,153,168,183},
        {139,154,169,184}, {140,155,170,185}, {141,156,171,186}, {142,157,172,187}, {143,158,173,188},
        {144,159,174,189}, {145,160,175,190}, {146,161,176,191}, {147,162,177,192}, {148,163,178,193},
        {149,164,179,194}, {150,165,180,195}};

    public static final int BPTC_PAYLOAD_1_START = 24;
    public static final int BPTC_PAYLOAD_2_START = 190;
    public static final int INTERLEAVE_OFFSET = 98;
    public static final int MAX_ERROR_CORRECTION_ITERATIONS = 5;
    public static final int[] HAMMING_13_9_3 = new int[]{0xF,0xE,0x7,0xA,0x5,0xB,0xC,0x6,0x3,0x8,0x4,0x2,0x1};
    public static final int[] HAMMING_15_11_3 = new int[]{0x9,0xD,0xF,0xE,0x7,0xA,0x5,0xB,0xC,0x6,0x3,0x8,0x4,0x2,0x1};
    public static final BitErrors NO_BIT_ERRORS = new BitErrors();
    public static final BitErrors TWO_BIT_ERRORS = new BitErrors(2);

    /**
     * Digital Mobile Radio (DMR) Block Product Turbo Code (BPTC) error detection and correction utility for the DMR
     * 1/2 rate data frame that contains 96 information bits and 100 CRC bits.  The CRC bits protect the message using
     * a grid of rows and columns where the rows are protected by a Hamming(15,11,3) code and the columns are protected
     * by a Hamming(13,9,3) code.  Each of these codes enable detecting up to 2 bit errors and correcting up to 1 bit
     * error in each row and/or column.
     */
    public BPTC()
    {
    }

    /**
     * Performs error detection and correction against the transmitted BPTC-protected DMR data frame.
     *
     * @param transmittedMessage to error detect and correct
     * @return corrected message (if possible) or a deinterleaved message with the isCorrected() flag set to false
     */
    public static CorrectedBinaryMessage errorDetectAndCorrect(BinaryMessage transmittedMessage)
    {
        CorrectedBinaryMessage deinterleaved = deinterleave(transmittedMessage);

        BitErrors rowErrors = getRowBitErrors(deinterleaved);
        BitErrors columnErrors = getColumnBitErrors(deinterleaved);

        if(rowErrors.hasErrors() || columnErrors.hasErrors())
        {
            List<Integer> correctedBitIndexes = new ArrayList<>();

            int iterations = 0;

            while((rowErrors.hasErrors() || columnErrors.hasErrors()) && iterations < MAX_ERROR_CORRECTION_ITERATIONS)
            {
                //Fix whichever correctable index set is larger
                if(rowErrors.getCorrectableErrorIndexes().size() > columnErrors.getCorrectableErrorIndexes().size())
                {
                    repair(deinterleaved, rowErrors.getCorrectableErrorIndexes(), correctedBitIndexes);
                }
                else
                {
                    repair(deinterleaved, columnErrors.getCorrectableErrorIndexes(), correctedBitIndexes);
                }

                rowErrors = getRowBitErrors(deinterleaved);
                columnErrors = getColumnBitErrors(deinterleaved);

                iterations++;
            }

            //Transfer the corrected bit error count to the message
            deinterleaved.setCorrectedBitCount(deinterleaved.getCorrectedBitCount() + correctedBitIndexes.size());
        }

        if(rowErrors.isEmpty() && columnErrors.isEmpty())
        {
            deinterleaved.setCorrected(true);
        }
        else
        {
            //If we couldn't correct the message, replace it with a fresh deinterleaved version and leave the
            //corrected flag set to false
            deinterleaved = deinterleave(transmittedMessage);
        }

        return deinterleaved;
    }

    /**
     * Performs error detection on all of the rows of this message
     *
     * @param message for error detection
     * @return bit errors contained across all rows
     */
    private static BitErrors getRowBitErrors(CorrectedBinaryMessage message)
    {
        BitErrors bitErrors = new BitErrors();

        for(int x = 0; x < 13; x++)
        {
            bitErrors.merge(detectHamming15_11_3(message, x));
        }

        return bitErrors;
    }

    /**
     * Performs error detection on all of the columns of this message
     *
     * @param message for error detection
     * @return bit errors contained across all columns
     */
    private static BitErrors getColumnBitErrors(CorrectedBinaryMessage message)
    {
        BitErrors bitErrors = new BitErrors();

        for(int x = 0; x < 15; x++)
        {
            bitErrors.merge(detectHamming13_9_3(message, x));
        }

        return bitErrors;
    }

    /**
     * Performs Hamming(15,11,3) error detection on the message row and returns a bit errors instance indicating
     * 0 errors, 1 correctable error index, or two uncorrectable bit errors.
     *
     * @param message for error detection
     * @param rowIndex for the message row that needs error detection
     * @return bit errors results
     */
    private static BitErrors detectHamming15_11_3(CorrectedBinaryMessage message, int rowIndex)
    {
        if(rowIndex > 12)
        {
            throw new IllegalArgumentException("Row index must be in range of 0 <> 12");
        }

        int checksum = 0;

        for(int x = 0; x < 11; x++)
        {
            int index = ROW_INDEXES[rowIndex][x];

            if(message.get(index))
            {
                checksum ^= HAMMING_15_11_3[x];
            }
        }

        int crc = message.getInt(ROW_CRC_INDEXES[rowIndex]);

        checksum ^= crc;

        boolean corrected = (checksum == 0);

        if(corrected)
        {
            return NO_BIT_ERRORS;
        }
        else
        {
            //Attempt to identify the index of any single-bit errors
            for(int x = 0; x < HAMMING_15_11_3.length; x++)
            {
                if(checksum == HAMMING_15_11_3[x])
                {
                    return BitErrors.createSingleBitError(ROW_INDEXES[rowIndex][x]);
                }
            }
        }

        //Maximum detectable bit errors
        return TWO_BIT_ERRORS;
    }

    /**
     * Performs Hamming(13,9,3) error detection on the message column and returns a bit errors instance indicating
     * 0 errors, 1 correctable error index, or two uncorrectable bit errors.
     *
     * @param message for error detection
     * @param columnIndex for the message column that needs error detection
     * @return bit errors results
     */
    private static BitErrors detectHamming13_9_3(CorrectedBinaryMessage message, int columnIndex)
    {
        if(columnIndex > 14)
        {
            throw new IllegalArgumentException("Column index must be in range of 0 <> 14");
        }

        int checksum = 0;

        for(int x = 0; x < 9; x++)
        {
            int index = COLUMN_INDEXES[columnIndex][x];

            if(message.get(index))
            {
                checksum ^= HAMMING_13_9_3[x];
            }
        }

        int crc = message.getInt(COLUMN_CRC_INDEXES[columnIndex]);

        checksum ^= crc;

        boolean corrected = (checksum == 0);

        if(corrected)
        {
            return NO_BIT_ERRORS;
        }
        else
        {
            //Attempt to identify the index of any single-bit errors
            for(int x = 0; x < HAMMING_13_9_3.length; x++)
            {
                if(checksum == HAMMING_13_9_3[x])
                {
                    return BitErrors.createSingleBitError(COLUMN_INDEXES[columnIndex][x]);
                }
            }
        }

        //Maximum detectable bit errors
        return TWO_BIT_ERRORS;
    }


    /**
     * Deinterleaves a transmitted 196-bit DMR binary message that is preceeded by a CACH block and returns a
     * deinterleaved version of the DMR data frame message.
     *
     * @param transmitted message
     * @return deinterleaved message
     */
    public static CorrectedBinaryMessage deinterleave(BinaryMessage transmitted)
    {
        CorrectedBinaryMessage deinterleaved = new CorrectedBinaryMessage(INTERLEAVE.length);

        for(int x = 0; x < INTERLEAVE_OFFSETS.length; x++)
        {
            if(transmitted.get(INTERLEAVE_OFFSETS[x]))
            {
                deinterleaved.set(x);
            }
        }

        return deinterleaved;
    }

    /**
     * Utility to calculate the deinterleave offsets for a 288-bit DMR frame
     */
    public static List<Integer> getInterleaveOffsets()
    {
        List<Integer> offsets = new ArrayList<>();

        for(int x = 0; x < INTERLEAVE.length; x++)
        {
            int offset = INTERLEAVE[x];

            if(offset < INTERLEAVE_OFFSET)
            {
                offset += BPTC_PAYLOAD_1_START;
            }
            else
            {
                offset += BPTC_PAYLOAD_2_START;
                offset -= INTERLEAVE_OFFSET;
            }

            offsets.add(offset);
        }

        return offsets;
    }

    /**
     * Repairs the binary message by flipping each bit identified in the bit error indexes list.
     *
     * @param message to be repaired
     * @param bitIndexes identifying which bits to repair by flipping the value
     * @param fixedBitErrors containing the complete set of bit indexes repaired thus far
     */
    public static void repair(CorrectedBinaryMessage message, List<Integer> bitIndexes, List<Integer> fixedBitErrors)
    {
        for(int bitIndex: bitIndexes)
        {
            message.flip(bitIndex);

            //If we've previously fixed this bit, flipping reverts the previous fix - remove it from the fixed error list
            if(fixedBitErrors.contains(bitIndex))
            {
                fixedBitErrors.remove(fixedBitErrors.indexOf(bitIndex));
            }
            else
            {
                fixedBitErrors.add(bitIndex);
            }
        }
    }
}
