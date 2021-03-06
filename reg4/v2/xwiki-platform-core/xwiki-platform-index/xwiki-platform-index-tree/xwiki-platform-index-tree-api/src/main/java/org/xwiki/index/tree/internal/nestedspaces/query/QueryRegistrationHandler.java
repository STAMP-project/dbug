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
package org.xwiki.index.tree.internal.nestedspaces.query;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.observation.event.Event;

/**
 * Registers the named query mapping.
 * 
 * @version $Id: 91e82d756213415806de4f3715cfc8a656845579 $
 * @since 8.3RC1
 * @since 7.4.5
 */
@Component
@Named("queryRegistrationHandler/nestedSpaces")
@Singleton
public class QueryRegistrationHandler extends org.xwiki.index.tree.internal.nestedpages.query.QueryRegistrationHandler
{
    @Override
    public String getName()
    {
        return "queryRegistrationHandler/nestedSpaces";
    }

    @Override
    public void onEvent(Event event, Object source, Object data)
    {
        loadMappingFile("org/xwiki/index/tree/internal/nestedspaces/query/queries.hbm.xml");
    }
}
