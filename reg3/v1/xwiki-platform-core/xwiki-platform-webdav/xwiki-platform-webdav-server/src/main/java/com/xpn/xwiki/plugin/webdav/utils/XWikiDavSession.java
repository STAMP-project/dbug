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
package com.xpn.xwiki.plugin.webdav.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.jackrabbit.webdav.DavSession;

/**
 * Manages session information associated with a WebDAV request.
 * 
 * @version $Id: ed81ba240320ac025d2f4e02d05001f500c6a87c $
 */
public class XWikiDavSession implements DavSession
{
    /**
     * The lock tokens of this session.
     */
    private final Set<String> lockTokens = new HashSet<String>();

    @Override
    public void addReference(Object reference)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeReference(Object reference)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addLockToken(String token)
    {
        lockTokens.add(token);
    }

    @Override
    public String[] getLockTokens()
    {
        return lockTokens.toArray(new String[lockTokens.size()]);
    }

    @Override
    public void removeLockToken(String token)
    {
        lockTokens.remove(token);
    }
}
