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

import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.xwiki.component.annotation.Component;

/**
 * A {@link org.xwiki.chart.internal.plot.PlotGenerator} for generating 3D line charts.
 * 
 * @version $Id: b849bdabc5675b3c1794323bd1f927b2216f6daf $
 * @since 4.1M1
 */
@Component
@Named("line3D")
@Singleton
public class Line3DPlotGenerator extends AbstractCategoryPlotGenerator
{
    @Override
    protected CategoryItemRenderer getRenderer(Map<String, String> parameters)
    {
        return new LineRenderer3D();
    }
}
