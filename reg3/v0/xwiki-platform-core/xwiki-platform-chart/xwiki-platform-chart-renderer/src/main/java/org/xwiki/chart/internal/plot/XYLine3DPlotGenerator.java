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

import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLine3DRenderer;
import org.xwiki.component.annotation.Component;

/**
 * See {@Link XYLine3DRenderer}.
 * 
 * @version $Id: 1c6902af490cc654cb0b94d0ad29c7fb08664193 $
 * @since 4.2M3
 */
@Component
@Named("xy_line3D")
@Singleton
public class XYLine3DPlotGenerator extends AbstractXYPlotGenerator
{
    @Override
    public XYItemRenderer getRenderer(Map<String, String> parameters)
    {
        return new XYLine3DRenderer();
    }
}
