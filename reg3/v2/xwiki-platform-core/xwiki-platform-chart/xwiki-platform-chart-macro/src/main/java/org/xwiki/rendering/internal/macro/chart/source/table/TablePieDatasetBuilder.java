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
package org.xwiki.rendering.internal.macro.chart.source.table;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.CategoryToPieDataset;
import org.jfree.data.general.Dataset;
import org.jfree.util.TableOrder;

/**
 * A buider of pie dataset ({@see org.jfree.data.pie.PieDataset}) from a table data source.
 *
 * @version $Id: c63abba624129a04f0e88fca6ae5ba6f79d5b234 $
 * @since 4.2M1
 */
public class TablePieDatasetBuilder extends TableCategoryDatasetBuilder
{
    @Override
    @SuppressWarnings("unchecked")
    public Dataset getDataset()
    {
        CategoryDataset dataset = (CategoryDataset) super.getDataset();

        return new CategoryToPieDataset(dataset, TableOrder.BY_ROW, 0);
    }
}
