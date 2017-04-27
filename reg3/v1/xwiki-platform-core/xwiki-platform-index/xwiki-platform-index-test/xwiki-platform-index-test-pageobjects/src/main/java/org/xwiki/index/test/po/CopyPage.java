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
package org.xwiki.index.test.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.xwiki.test.ui.po.DocumentPicker;

/**
 * Copy Page enhanced with a Document Tree Picker.
 * 
 * @version $Id: c754c3b31842ed8bdf9f4b666f73418ec7c0b5d3 $
 * @since 7.2M3
 */
public class CopyPage extends org.xwiki.test.ui.po.CopyPage
{
    /**
     * The element that contains the document picker used to select the target document.
     */
    @FindBy(className = "location-picker")
    private WebElement documentPickerElement;

    /**
     * @return the document picker used to select the target document
     */
    public DocumentPicker getDocumentPicker()
    {
        return new DocumentPicker(this.documentPickerElement);
    }
}
