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
package org.xwiki.chart.internal.plot;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.xwiki.component.annotation.Component;

/**
 * A {@link PlotGenerator} for generating area charts.
 * 
 * @version $Id: cb6be26495331b01c531a690e83c5c5eee3610fe $
 * @since 2.0M1
 */
@Component
@Named("area")
@Singleton
public class AreaPlotGenerator extends AbstractCategoryPlotGenerator
{
    @Override
    protected CategoryItemRenderer getRenderer(Map<String, String> parameters)
    {
        return new AreaRenderer();
    }
}
