/*******************************************************************************
 *     SDR Trunk 
 *     Copyright (C) 2014-2016 Dennis Sheirer
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 ******************************************************************************/
package source.tuner.rtl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import source.SourceException;
import source.tuner.Tuner;
import source.tuner.TunerClass;
import source.tuner.TunerType;

public class RTL2832Tuner extends Tuner
{
    private final static Logger mLog = LoggerFactory.getLogger(RTL2832Tuner.class);

    private TunerClass mTunerClass;

    public RTL2832Tuner(TunerClass tunerClass, RTL2832TunerController controller) throws SourceException
    {
        super(tunerClass.getVendorDeviceLabel() + "/" + controller.getTunerType().getLabel() + " " +
            controller.getUniqueID(), controller);

        mTunerClass = tunerClass;
    }

    public void dispose()
    {
    }

    public RTL2832TunerController getController()
    {
        return (RTL2832TunerController)getTunerController();
    }

    @Override
    public String getUniqueID()
    {
        return getController().getUniqueID();
    }

    @Override
    public TunerClass getTunerClass()
    {
        return mTunerClass;
    }

    @Override
    public TunerType getTunerType()
    {
        return getController().getTunerType();
    }

    @Override
    public double getSampleSize()
    {
        //Note: although sample size is 8, we set it to 11 to align with the
        //actual noise floor.
        return 11.0;
    }
}
