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
package com.xpn.xwiki.internal.parentchild;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.configuration.ConfigurationSource;

/**
 * Default (and unique) implementation of {@link ParentChildConfiguration}.
 *
 * @version $Id: 4362dbd6ac25739e0bad3458edb5871b4dc17d9a $
 * @since 9.0RC1
 * @since 8.4.1
 * @since 7.4.6
 */
@Component
@Singleton
public class DefaultParentChildConfiguration implements ParentChildConfiguration
{
    @Inject
    private ConfigurationSource configurationSource;

    @Override
    public boolean isParentChildMechanismEnabled()
    {
        return "parentchild".equals(
                configurationSource.getProperty("core.hierarchyMode", "reference"));
    }
}
