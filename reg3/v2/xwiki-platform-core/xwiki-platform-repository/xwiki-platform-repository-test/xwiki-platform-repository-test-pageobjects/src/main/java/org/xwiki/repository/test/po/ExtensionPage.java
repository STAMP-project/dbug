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
package org.xwiki.repository.test.po;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.xwiki.test.ui.po.ViewPage;

/**
 * @version $Id: 8a3c73f82dd191289a82a5d6d6f45a876d7deca7 $
 * @since 4.2M1
 */
public class ExtensionPage extends ViewPage
{
    @FindBy(xpath = "//a[@title='Update extension']")
    private WebElement update;

    /**
     * @since 4.2M1
     */
    public boolean isValidExtension()
    {
        List<WebElement> elements = getDriver().findElementsWithoutWaiting(
            By.xpath("//div[@class='box successmessage']/*[text() = 'Installable with the Extension Manager']"));
        return !elements.isEmpty();
    }

    public ExtensionPage updateExtension()
    {
        this.update.click();

        return new ExtensionPage();
    }
}
