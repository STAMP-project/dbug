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
package org.xwiki.gwt.user.client;

/**
 * An object that can be selected.
 * 
 * @version $Id: b240eb91aaea88629aab5310f243d2f4834e94eb $
 */
public interface Selectable
{
    /**
     * @return {@code true} if this object is selected, {@code false} otherwise
     */
    boolean isSelected();

    /**
     * Marks this object as selected or not selected.
     * 
     * @param selected {@code true} to select this object, {@code false} to remove the selection
     */
    void setSelected(boolean selected);
}
