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
package org.xwiki.search.solr.internal;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.component.phase.Initializable;
import org.xwiki.component.phase.InitializationException;
import org.xwiki.search.solr.internal.api.SolrConfiguration;
import org.xwiki.search.solr.internal.api.SolrInstance;

/**
 * Provider for {@link SolrInstance} that, based on the current configuration, returns the right component.
 * 
 * @version $Id: 260622a3d6ba6eb25c3914e0157feb17125993c8 $
 * @since 4.3M2
 */
@Component
@Singleton
public class SolrInstanceProvider implements Provider<SolrInstance>, Initializable
{
    /**
     * Solr configuration.
     */
    @Inject
    private SolrConfiguration configuration;

    /**
     * The component manager used to lookup the configured component.
     */
    @Inject
    private ComponentManager componentManager;

    /** The Solr instance configured. Since the configuration is read only once at startup, it can be cached. */
    private SolrInstance configuredInstance;

    @Override
    public void initialize() throws InitializationException
    {
        String type = this.configuration.getServerType();
        try {
            this.configuredInstance = this.componentManager.getInstance(SolrInstance.class, type);
        } catch (ComponentLookupException e) {
            throw new InitializationException(
                String.format("Failed to lookup configured Solr instance type [%s]", type), e);
        }
    }

    @Override
    public SolrInstance get()
    {
        return this.configuredInstance;
    }
}
