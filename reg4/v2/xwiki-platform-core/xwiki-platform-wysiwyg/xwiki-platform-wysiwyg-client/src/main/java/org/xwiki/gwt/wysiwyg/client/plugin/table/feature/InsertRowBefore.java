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
package org.xwiki.gwt.wysiwyg.client.plugin.table.feature;

import org.xwiki.gwt.user.client.ui.rta.cmd.Command;
import org.xwiki.gwt.wysiwyg.client.Strings;
import org.xwiki.gwt.wysiwyg.client.plugin.table.TablePlugin;
import org.xwiki.gwt.wysiwyg.client.plugin.table.util.TableUtils;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableRowElement;

/**
 * Feature allowing to insert a row above the currently edited row. After the insertion the caret is positioned in the
 * cell above the originally edited cell. It is disabled when the caret is positioned outside of a row. A row is a set
 * of cells aligned horizontally in a table. Represented by the TR tag in HTML.
 * 
 * @version $Id: f4b4912b49be73809eb56a65da88b9cae6b1a6f1 $
 */
public class InsertRowBefore extends AbstractTableFeature
{
    /**
     * Feature name.
     */
    public static final String NAME = "insertrowbefore";

    /**
     * Initialize the feature. Table features needs to be aware of the plug-in (here the ClickListener) since they hold
     * their own PushButton.
     * 
     * @param plugin table plug-in.
     */
    public InsertRowBefore(TablePlugin plugin)
    {
        super(NAME, new Command(NAME), Strings.INSTANCE.insertRowBefore(), plugin);
    }

    @Override
    public boolean execute(String parameter)
    {
        TableCellElement currentCell =
            TableUtils.getInstance().getCell(TableUtils.getInstance().getCaretNode(rta.getDocument()));
        TableUtils.getInstance().insertRow(rta.getDocument(), true);
        TableUtils.getInstance().putCaretInNode(rta, TableUtils.getInstance().getPreviousCellInColumn(currentCell));
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        if (!super.isEnabled()) {
            return false;
        }
        Node node = TableUtils.getInstance().getCaretNode(rta.getDocument());
        TableRowElement row = TableUtils.getInstance().getRow(node);
        return row != null && !TableUtils.getInstance().isHeaderRow(row);
    }
}
