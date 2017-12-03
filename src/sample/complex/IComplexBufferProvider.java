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
package sample.complex;

import sample.Listener;

public interface IComplexBufferProvider
{
    /**
     * Adds the listener to receive complex buffer samples
     */
    void addComplexBufferListener(Listener<ComplexBuffer> listener);

    /**
     * Removes the listener from receiving complex buffer samples
     */
    void removeComplexBufferListener(Listener<ComplexBuffer> listener);

    /**
     * Indicates if there are any complex buffer listeners registered
     */
    boolean hasComplexBufferListeners();
}
