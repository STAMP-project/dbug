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
package org.xwiki.template.event;

import org.xwiki.observation.event.Event;
import org.xwiki.stability.Unstable;

/**
 * An event related to a template.
 *
 * @version $Id: d9eaedb664a798b291d83f4f125d7b21076c0af8 $
 * @since 7.0M1
 */
@Unstable
public interface TemplateEvent extends Event
{
    /**
     * @return the id of the template
     */
    String getId();
}
