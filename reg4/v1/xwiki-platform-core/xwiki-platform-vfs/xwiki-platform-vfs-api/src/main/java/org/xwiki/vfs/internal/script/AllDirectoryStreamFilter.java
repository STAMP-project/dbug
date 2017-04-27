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
package org.xwiki.vfs.internal.script;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;

/**
 * @version $Id: 0de0e7154544d509ed049188e25c83042b3c84b2 $
 * @since 7.4M2
 */
@Component(roles = {DirectoryStream.Filter.class})
@Named("all")
@Singleton
public class AllDirectoryStreamFilter implements DirectoryStream.Filter<Path>
{
    @Override
    public boolean accept(Path entry) throws IOException
    {
        return true;
    }
}
