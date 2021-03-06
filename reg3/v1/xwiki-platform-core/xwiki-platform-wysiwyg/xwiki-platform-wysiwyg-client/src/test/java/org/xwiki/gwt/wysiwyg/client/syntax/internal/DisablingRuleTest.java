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
package org.xwiki.gwt.wysiwyg.client.syntax.internal;

import org.xwiki.gwt.user.client.ui.rta.RichTextArea;
import org.xwiki.gwt.wysiwyg.client.WysiwygTestCase;


/**
 * Unit test for {@link DisablingRule}.
 * 
 * @version $Id: 9d092dafc97fe93d16fee58eeb6efba83bed504a $
 */
public class DisablingRuleTest extends WysiwygTestCase
{
    /**
     * Tests if a {@link DisablingRule} really disables the specified features.
     */
    public void testAlwaysDisabled()
    {
        DisablingRule dr = new DisablingRule(new String[] {"a", "b", "c"});
        assertFalse(dr.areValid(new RichTextArea()));
    }
}
