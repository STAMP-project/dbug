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
package org.xwiki.url.internal.standard;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.resource.ResourceReference;
import org.xwiki.resource.SerializeResourceReferenceException;
import org.xwiki.resource.UnsupportedResourceReferenceException;
import org.xwiki.url.ExtendedURL;
import org.xwiki.url.internal.AbstractExtendedURLResourceReferenceSerializer;

/**
 * Transforms a XWiki Resource instance into a {@link ExtendedURL} object following the Standard URL format.
 *
 * @version $Id: fefbbbec24d0ab4948a7cb5e87538ea980f140b3 $
 * @since 6.1M2
 */
@Component
@Named("standard")
@Singleton
public class StandardExtendedURLResourceReferenceSerializer extends AbstractExtendedURLResourceReferenceSerializer
{
    @Override
    public ExtendedURL serialize(ResourceReference reference)
        throws SerializeResourceReferenceException, UnsupportedResourceReferenceException
    {
        return serialize(reference, "standard");
    }
}
