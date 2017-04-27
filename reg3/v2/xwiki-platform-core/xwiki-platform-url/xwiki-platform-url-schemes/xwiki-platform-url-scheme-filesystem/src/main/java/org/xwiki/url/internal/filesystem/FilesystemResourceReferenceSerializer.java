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
package org.xwiki.url.internal.filesystem;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.resource.ResourceReference;
import org.xwiki.resource.SerializeResourceReferenceException;
import org.xwiki.resource.UnsupportedResourceReferenceException;
import org.xwiki.url.ExtendedURL;
import org.xwiki.url.internal.AbstractExtendedURLResourceReferenceSerializer;

/**
 * Converts a {@link org.xwiki.resource.ResourceReference} into a {@link ExtendedURL} (with the Context Path added)
 * that points to a absolute URL pointing to a file on the local filesystem. This can be used when exporting to HTML
 * for example.
 *
 * @version $Id: 423081781a1d20e7a1a37853ce4193c92db10772 $
 * @since 7.2M1
 */
@Component
@Named("filesystem")
@Singleton
public class FilesystemResourceReferenceSerializer extends AbstractExtendedURLResourceReferenceSerializer
{
    @Override
    public ExtendedURL serialize(ResourceReference reference)
        throws SerializeResourceReferenceException, UnsupportedResourceReferenceException
    {
        return serialize(reference, "filesystem");
    }
}
