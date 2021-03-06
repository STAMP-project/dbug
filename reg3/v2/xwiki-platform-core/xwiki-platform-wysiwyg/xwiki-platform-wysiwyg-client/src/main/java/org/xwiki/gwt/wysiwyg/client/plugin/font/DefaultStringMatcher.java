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
package org.xwiki.gwt.wysiwyg.client.plugin.font;

/**
 * Matches two {@link String}s if they are both null or equal ignoring case.
 * 
 * @version $Id: e15aa67eafc0a6a4fa1b551d4f2d53f61bc5a6f9 $
 */
public class DefaultStringMatcher implements Matcher<String>
{
    @Override
    public boolean match(String leftValue, String rightValue)
    {
        return leftValue == null ? rightValue == null : leftValue.equalsIgnoreCase(rightValue);
    }
}
