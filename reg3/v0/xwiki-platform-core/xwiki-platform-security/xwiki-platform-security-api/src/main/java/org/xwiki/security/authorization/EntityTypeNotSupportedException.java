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
package org.xwiki.security.authorization;

import org.xwiki.model.EntityType;

import java.util.Formatter;

/**
 * Thrown when attempting to load a right for an unsupported entity type.
 * 
 * @version $Id: 55507c80c22a47bcd245f8844c93eaacd6106994 $
 * @since 4.0M2
 */
public class EntityTypeNotSupportedException extends AuthorizationException
{
    /** Serialization identifier. */
    private static final long serialVersionUID = 1L;

    /**
     * @param entityType Type of the unsupported entity.
     * @param reader The loader that does not support the entity type.
     */
    public EntityTypeNotSupportedException(EntityType entityType, SecurityEntryReader reader)
    {
        super(new Formatter().format("Entities of type %s are not supported by security reader of type %s.",
                                     entityType,
                                     reader.getClass().getName()).toString(), null);
    }
}
