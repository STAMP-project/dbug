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
package org.xwiki.sharepage.test.po;

import org.xwiki.test.ui.po.ViewPage;

/**
 * Adds the API to click on Share Page by Mail to a ViewPage.
 *
 * @version $Id: a31e7b5b52a1351a0ceb50c82047aec372fee6c4 $
 * @since 7.0RC1
 */
public class ShareableViewPage extends ViewPage
{
    /**
     * Click the Share by Email submenu in the More Actions menu.
     */
    public ShareDialog clickShareByEmail()
    {
        clickMoreActionsSubMenuEntry("tmActionShare");
        return new ShareDialog();
    }
}