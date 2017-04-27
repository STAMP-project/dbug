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

import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * A collection of {@link HandlerRegistration} instances.
 * 
 * @version $Id: 7faf7248a2f18ff84283fead6c81bd2d96be9eec $
 */
public class HandlerRegistrationCollection extends ArrayList<HandlerRegistration>
{
    /**
     * Field required by all {@link java.io.Serializable} classes.
     */
    private static final long serialVersionUID = 9046100072911029017L;

    /**
     * Removes all handlers which have registrations in this collection.
     * 
     * @see HandlerRegistration#removeHandler()
     */
    public void removeHandlers()
    {
        for (HandlerRegistration registration : this) {
            registration.removeHandler();
        }
        clear();
    }
}