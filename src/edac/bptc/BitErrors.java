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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BitErrors
{
    private List<Integer> mCorrectableBitErrorIndexes = new ArrayList<>();
    private int mUncorrectableBitErrorCount;

    /**
     * BitError constructor incorporating an uncorrectable bit error count to use.
     * @param uncorrectableBitErrorCount to set as the initial value
     */
    public BitErrors(int uncorrectableBitErrorCount)
    {
        mUncorrectableBitErrorCount = uncorrectableBitErrorCount;
    }

    /**
     * BitError constructor
     */
    public BitErrors()
    {
        this(0);
    }

    /**
     * Creates a bit error instance containing one correctable bit error index
     * @param index of the correctable bit error
     * @return a new bit error instance with one correctable index
     */
    public static BitErrors createSingleBitError(int index)
    {
        BitErrors bitErrors = new BitErrors();
        bitErrors.addCorrectableIndex(index);
        return bitErrors;
    }

    /**
     * Indicates if there are no correctable or uncorrectable bit errors
     */
    public boolean isEmpty()
    {
        return mCorrectableBitErrorIndexes.isEmpty() && mUncorrectableBitErrorCount == 0;
    }

    /**
     * Indicates if there are any correctable or uncorrectable bit errors
     */
    public boolean hasErrors()
    {
        return !isEmpty();
    }

    /**
     * Total bit error count, both correctable and uncorrectable.
     */
    public int getTotalErrors()
    {
        return mCorrectableBitErrorIndexes.size() + mUncorrectableBitErrorCount;
    }

    /**
     * Total uncorrectable bit error count
     */
    public int getUncorrectableBitErrorCount()
    {
        return mUncorrectableBitErrorCount;
    }

    /**
     * Adds the uncorrectable bit error count to the total uncorrectable bit error count
     */
    public void addUncorrectableError(int count)
    {
        mUncorrectableBitErrorCount += count;
    }

    /**
     * Adds the correctable bit error index to this instance
     */
    public void addCorrectableIndex(int index)
    {
        if(!mCorrectableBitErrorIndexes.contains(index))
        {
            mCorrectableBitErrorIndexes.add(index);
            Collections.sort(mCorrectableBitErrorIndexes);
        }
    }

    /**
     * List of correctable bit error indexes
     */
    public List<Integer> getCorrectableErrorIndexes()
    {
        return mCorrectableBitErrorIndexes;
    }

    /**
     * Merges the bit error argument into this bit error instance
     */
    public void merge(BitErrors bitErrors)
    {
        for(int index: mCorrectableBitErrorIndexes)
        {
            addCorrectableIndex(index);
        }

        addUncorrectableError(bitErrors.getUncorrectableBitErrorCount());
    }

    /**
     * Creates a list of correctable bit error indexes that is common to both this bit error instance and the
     * bit error argument.
     *
     * @param bitErrors to compare with this instance to develop the common index list
     * @return list of bit error indexes
     */
    public List<Integer> getCommonCorrectableBitIndexes(BitErrors bitErrors)
    {
        if(bitErrors.getCorrectableErrorIndexes().size() == 0 || mCorrectableBitErrorIndexes.size() == 0)
        {
            return Collections.EMPTY_LIST;
        }

        List<Integer> commonIndexes = new ArrayList<>();

        for(int index: mCorrectableBitErrorIndexes)
        {
            if(bitErrors.getCorrectableErrorIndexes().contains(index))
            {
                commonIndexes.add(index);
            }
        }

        return commonIndexes;
    }

    /**
     * Override to string to describe this instance
     */
    @Override
    public String toString()
    {
        return "Bit Errors - Uncorrectable:" + getUncorrectableBitErrorCount() + " Correctable:" +
            getCorrectableErrorIndexes();
    }
}
