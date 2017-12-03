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
package source.tuner.fcd;

import source.tuner.Tuner;
import source.tuner.TunerClass;
import source.tuner.TunerType;

public class FCDTuner extends Tuner
{
	public FCDTuner(FCDTunerController controller)
	{
		super( controller.getConfiguration().toString(), controller);
	}
	
	public void dispose()
	{
		getController().dispose();
	}
	
	public FCDTunerController getController()
	{
		return (FCDTunerController)getTunerController();
	}
	
	@Override
    public TunerClass getTunerClass()
    {
	    return getController().getTunerClass();
    }

	@Override
    public TunerType getTunerType()
    {
	    return getController().getTunerType();
    }

	@Override
    public String getUniqueID()
    {
	    return getController().getUSBAddress();
    }

	@Override
	public double getSampleSize()
	{
		return 16.0;
	}
}
