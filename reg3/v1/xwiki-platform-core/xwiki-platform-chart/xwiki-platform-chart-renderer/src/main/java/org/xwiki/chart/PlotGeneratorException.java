/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.chart;

/**
 * The class used to indicate exceptions specific to the plot generator.
 * 
 * @version $Id: 881ece9057e48bde930b328a5b611d3433861c05 $
 * @since 4.2M1
 */
public class PlotGeneratorException extends ChartGeneratorException
{
    /**
     * Class version.
     */
    private static final long serialVersionUID = 1;

    /**
     * Constructs a new exception with the specified message.
     * 
     * @param message The explanation of the exception.
     */
    public PlotGeneratorException(String message)
    {
        super(message);
    }

}
