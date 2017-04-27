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
package org.xwiki.filter.instance.internal;

import java.util.List;

import org.xwiki.component.annotation.Role;
import org.xwiki.filter.FilterException;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReferenceTreeNode;
import org.xwiki.model.reference.SpaceReference;
import org.xwiki.model.reference.WikiReference;

/**
 * @version $Id: d2f6ad3a8a7beb9403dc84c4eab5fb7d920f4dba $
 * @since 6.2M1
 */
@Role
public interface InstanceModel
{
    /**
     * @since 7.2M1
     */
    List<WikiReference> getWikiReferences() throws FilterException;

    /**
     * @since 7.2M1
     */
    EntityReferenceTreeNode getSpaceReferences(WikiReference wiki) throws FilterException;

    /**
     * @since 7.2M1
     */
    List<DocumentReference> getDocumentReferences(SpaceReference spaceReference) throws FilterException;
}
