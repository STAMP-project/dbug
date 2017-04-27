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
package org.xwiki.url;

import java.util.Arrays;

import org.junit.Test;
import org.xwiki.url.internal.RelativeExtendedURL;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link RelativeExtendedURL}.
 *
 * @version $Id: 1efb8098476fd7e8e0fe943da864d267cbb2560c $
 * @since 7.2M1
 */
public class RelativeExtendedURLTest
{
    @Test
    public void serialize()
    {
        RelativeExtendedURL url = new RelativeExtendedURL(Arrays.asList("a", "b"));
        assertEquals("a/b", url.serialize());
    }
}
