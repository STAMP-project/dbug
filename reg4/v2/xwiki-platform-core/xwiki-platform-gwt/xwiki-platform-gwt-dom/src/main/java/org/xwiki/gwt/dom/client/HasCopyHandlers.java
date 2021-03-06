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
package org.xwiki.gwt.dom.client;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * A widget that implements this interface provides registration for {@link CopyHandler} instances.
 * 
 * @version $Id: 715dd685dba4191d85825bb7b73b5f2046e5c302 $
 * @since 5.0M2
 */
public interface HasCopyHandlers extends HasHandlers
{
    /**
     * Adds a {@link CopyEvent} handler.
     * 
     * @param handler the copy handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    HandlerRegistration addCopyHandler(CopyHandler handler);
}
