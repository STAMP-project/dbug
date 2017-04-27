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
package org.xwiki.url;

import org.xwiki.component.annotation.Role;
import org.xwiki.stability.Unstable;

/**
 * Access various URL-related items from the Execution Context; for example access the current URL scheme.
 *
 * @version $Id: f1536daf4c19e2be953d85b9274da680b02cf2d5 $
 * @since 7.2M1
 */
@Role
@Unstable
public interface URLContextManager
{
    /**
     * @return the URL scheme being used; it's retrieved from the Execution Context (e.g. "standard", "reference", etc)
     */
    String getURLFormatId();

    /**
     * @param urlFormatId see {@link #getURLFormatId}
     */
    void setURLFormatId(String urlFormatId);
}
