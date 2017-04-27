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
package org.xwiki.chart.axis;

/**
 * Enumeration of supported axis types.
 * 
 * @version $Id: 277562ca9cb5809baf163a356aa28cd82f991bb7 $
 * @since 4.2M1
 */
public enum AxisType
{
    /** Number axis. */
    NUMBER("number"),
    /** Category axis. */
    CATEGORY("category"),
    /** Date axis. */
    DATE("date");
    
    /** The name. */
    private final String name;

    /**
     * @param name the name.
     */
    AxisType(String name)
    {
        this.name = name;
    }

    /** @return the name. */
    public String getName()
    {
        return name;
    }

    /**
     * @param name A plot type.
     * @return the axis type corresponding to the name, or {@code null}.
     */
    public static AxisType forName(String name)
    {
        for (AxisType type : values())
        {
            if (name.equals(type.getName())) {
                return type;
            }
        }

        return null;
    }
}