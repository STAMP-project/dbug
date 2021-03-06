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
package com.xpn.xwiki.internal.event;

import org.xwiki.model.reference.EntityReference;
import org.xwiki.observation.event.Event;

/**
 * Represent an event related to a provided entity.
 *
 * @version $Id: 90947d1e63c638385ef7d4a777e6187ae6fe6457 $
 * @since 3.2M1
 */
public interface EntityEvent extends Event
{
    /**
     * @return the reference of the entity related to this event. Can be null when (and only when) the event is actually
     *         an event filter.
     */
    EntityReference getReference();
}
