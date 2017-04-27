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
package org.xwiki.filter.instance.input;

import java.util.Map;

import org.xwiki.component.annotation.Role;
import org.xwiki.filter.FilterException;
import org.xwiki.filter.descriptor.FilterStreamDescriptor;

/**
 * @param <E>
 * @param <P>
 * @version $Id: f38fa9aa67cc382d9b111852a6173f94e839d183 $
 * @since 6.2M1
 */
@Role
public interface EntityEventGenerator<E>
{
    void write(E entity, Object filter, Map<String, Object> properties) throws FilterException;

    /**
     * @return The FilterStreamDescriptor describes a FilterStream and has the list of bean class parameters or properties.
     */
    FilterStreamDescriptor getDescriptor();
}