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
package com.xpn.xwiki.internal.store.hibernate.query;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Validate {@link HqlQueryUtils}.
 * 
 * @version $Id: 1a0ec141e0b3bdd299f4fbd08a1409e3265ff6cf $
 */
public class HqlQueryUtilsTest
{
    @Test
    public void isSafe()
    {
        // allowed

        assertTrue(HqlQueryUtils.isSafe("select name from XWikiDocument"));
        assertTrue(HqlQueryUtils.isSafe("select doc.name, space.name from XWikiDocument doc, XWikiSpace space"));
        assertTrue(HqlQueryUtils
            .isSafe("select doc.name, space.name from XWikiDocument doc, XWikiSpace space, OtherTable as ot"));
        assertTrue(HqlQueryUtils.isSafe("select count(name) from XWikiDocument"));
        assertTrue(HqlQueryUtils.isSafe("select count(doc.name) from XWikiDocument doc"));
        assertTrue(HqlQueryUtils
            .isSafe("select doc.fullName from XWikiDocument as doc, com.xpn.xwiki.objects.StringProperty as str"));

        assertTrue(HqlQueryUtils.isSafe("select count(*) from XWikiSpace"));

        // not allowed

        assertFalse(HqlQueryUtils.isSafe("select name from OtherTable"));
        assertFalse(HqlQueryUtils.isSafe("select doc.* from XWikiDocument doc, XWikiSpace space"));
        assertFalse(HqlQueryUtils.isSafe("select * from XWikiDocument doc"));
        assertFalse(HqlQueryUtils
            .isSafe("select doc.name, ot.field from XWikiDocument doc, XWikiSpace space, OtherTable as ot"));
        assertFalse(HqlQueryUtils.isSafe("select count(*) from OtherTable"));
    }
}
