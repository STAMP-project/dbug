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
package org.xwiki.query.internal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.component.phase.Initializable;
import org.xwiki.configuration.ConfigurationSource;

/**
 * Base class for filtering hidden entities.
 *
 * @version $Id: 3a8eb5efe120010ba13f144ce6fec7d3549d828e $
 * @since 7.2M2
 */
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
public abstract class AbstractHiddenFilter extends AbstractWhereQueryFilter implements Initializable
{
    /**
     * Used to retrieve user preference regarding hidden documents.
     */
    @Inject
    @Named("user")
    private ConfigurationSource userPreferencesSource;

    /**
     * @see #initialize()
     */
    private boolean isActive;

    /**
     * Sets the #isActive property, based on the user configuration.
     */
    @Override
    public void initialize()
    {
        Integer preference = userPreferencesSource.getProperty("displayHiddenDocuments", Integer.class);
        isActive = preference == null || preference != 1;
    }

    @Override
    public String filterStatement(String statement, String language)
    {
        String result = statement;
        if (isActive) {
            result = filterHidden(statement, language);
        }
        return result;
    }

    protected abstract String filterHidden(String statement, String language);

    @Override
    public List filterResults(List results)
    {
        return results;
    }
}
