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
package org.xwiki.model.internal.reference;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.model.EntityType;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.model.reference.EntityReferenceProvider;
import org.xwiki.model.reference.SpaceReference;
import org.xwiki.model.reference.WikiReference;

/**
 * Provide a complete reference based on configurable default wiki, spaces and document.
 * 
 * @version $Id: 157de9017e01b495052fa8442de9fac9a69ed8df $
 * @since 7.2M1
 */
@Component
@Singleton
public class DefaultSpaceReferenceProvider implements Provider<SpaceReference>
{
    @Inject
    private EntityReferenceProvider provider;

    @Inject
    private Provider<WikiReference> wikiReferenceProvider;

    /**
     * We can cache the default document since it's configurable only at xwiki.properties level which require a restart
     * to be modified.
     */
    private SpaceReference cachedReference;

    @Override
    public SpaceReference get()
    {
        if (this.cachedReference == null) {
            EntityReference reference = this.provider.getDefaultReference(EntityType.SPACE);

            // Add wikis
            reference = reference.appendParent(this.wikiReferenceProvider.get());

            this.cachedReference = new SpaceReference(reference);
        }

        return this.cachedReference;
    }
}
