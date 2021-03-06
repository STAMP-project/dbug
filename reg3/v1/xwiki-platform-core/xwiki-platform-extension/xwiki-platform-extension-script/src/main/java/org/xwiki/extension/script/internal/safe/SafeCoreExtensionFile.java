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
package org.xwiki.extension.script.internal.safe;

import java.net.URL;

import org.xwiki.extension.CoreExtensionFile;

/**
 * Provide a public script access to a core extension file.
 * 
 * @param <T> the extension type
 * @version $Id: 891f0fc851db4a8efd0a67335a04784221a782fc $
 * @since 4.0M2
 */
public class SafeCoreExtensionFile<T extends CoreExtensionFile> extends SafeExtensionFile<T> implements
    CoreExtensionFile
{
    /**
     * @param file he wrapped file
     */
    public SafeCoreExtensionFile(T file)
    {
        super(file);
    }

    // CoreExtensionFile

    @Override
    public URL getURL()
    {
        return getWrapped().getURL();
    }
}
