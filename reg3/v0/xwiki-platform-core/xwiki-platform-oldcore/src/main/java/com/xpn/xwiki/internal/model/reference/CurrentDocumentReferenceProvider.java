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
package com.xpn.xwiki.internal.model.reference;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.model.EntityType;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.model.reference.EntityReferenceProvider;
import org.xwiki.model.reference.SpaceReference;

/**
 * Provide complete current document reference.
 * 
 * @version $Id: 1da452232a2c3a0c4438eb54e07abc80f36bd22a $
 * @since 7.2M1
 */
@Component
@Named("current")
@Singleton
public class CurrentDocumentReferenceProvider implements Provider<DocumentReference>
{
    @Inject
    @Named("current")
    private Provider<SpaceReference> spaceReferenceProvider;

    @Inject
    @Named("current")
    private EntityReferenceProvider provider;

    @Override
    public DocumentReference get()
    {
        return new DocumentReference(new EntityReference(this.provider.getDefaultReference(EntityType.DOCUMENT),
            this.spaceReferenceProvider.get()));
    }
}
