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
package org.xwiki.gwt.wysiwyg.client.widget.explorer;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * An object that fires {@link DoubleClickNodeEvent}s.
 * 
 * @version $Id: 3573840bbb4c2470a60fb3ea72dee1f8af81cea5 $
 */
public interface HasDoubleClickNodeHandlers extends HasHandlers
{
    /**
     * Registers the given handles for double click node events.
     * 
     * @param handler the object to be notified when a node is double clicked
     * @return the registration for the event, to be used for removing the handler
     */
    HandlerRegistration addDoubleClickNodeHandler(DoubleClickNodeHandler handler);
}
