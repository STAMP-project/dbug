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
package com.xpn.xwiki.objects.classes;

import org.junit.Rule;
import org.junit.Test;
import org.xwiki.model.reference.DocumentReference;

import com.xpn.xwiki.test.MockitoOldcoreRule;
import com.xpn.xwiki.test.reference.ReferenceComponentList;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link BaseClass} class.
 *
 * @version $Id: ce110eb0a01f9c6b528b80eec215a24ed1d364dc $
 */
@ReferenceComponentList
public class BaseClassTest
{
    @Rule
    public MockitoOldcoreRule oldcore = new MockitoOldcoreRule();

    @Test
    public void setDocumentReference() throws Exception
    {
        BaseClass baseClass = new BaseClass();

        DocumentReference reference = new DocumentReference("wiki", "space", "page");
        baseClass.setDocumentReference(reference);

        assertEquals(reference, baseClass.getDocumentReference());
    }

    @Test
    public void setNameSetWiki() throws Exception
    {
        String database = this.oldcore.getXWikiContext().getWikiId();
        BaseClass baseClass = new BaseClass();

        baseClass.setName("space.page");

        assertEquals(database, baseClass.getDocumentReference().getWikiReference().getName());
        assertEquals("space", baseClass.getDocumentReference().getLastSpaceReference().getName());
        assertEquals("page", baseClass.getDocumentReference().getName());
    }

    @Test
    public void setNameAloneWithChangingContext() throws Exception
    {
        String database = this.oldcore.getXWikiContext().getWikiId();
        BaseClass baseClass = new BaseClass();

        baseClass.setName("space.page");

        try {
            this.oldcore.getXWikiContext().setWikiId("otherwiki");

            assertEquals(database, baseClass.getDocumentReference().getWikiReference().getName());
            assertEquals("space", baseClass.getDocumentReference().getLastSpaceReference().getName());
            assertEquals("page", baseClass.getDocumentReference().getName());

            baseClass.setName("otherspace.otherpage");
        } finally {
            this.oldcore.getXWikiContext().setWikiId(database);
        }

        assertEquals(database, baseClass.getDocumentReference().getWikiReference().getName());
        assertEquals("otherspace", baseClass.getDocumentReference().getLastSpaceReference().getName());
        assertEquals("otherpage", baseClass.getDocumentReference().getName());

        baseClass = new BaseClass();
        try {
            this.oldcore.getXWikiContext().setWikiId("otherwiki");
            baseClass.setName("space.page");
        } finally {
            this.oldcore.getXWikiContext().setWikiId(database);
        }

        assertEquals("otherwiki", baseClass.getDocumentReference().getWikiReference().getName());
        assertEquals("space", baseClass.getDocumentReference().getLastSpaceReference().getName());
        assertEquals("page", baseClass.getDocumentReference().getName());

        baseClass.setName("otherspace.otherpage");

        assertEquals("otherwiki", baseClass.getDocumentReference().getWikiReference().getName());
        assertEquals("otherspace", baseClass.getDocumentReference().getLastSpaceReference().getName());
        assertEquals("otherpage", baseClass.getDocumentReference().getName());
    }

    @Test
    public void addTextAreaFieldWhenNullContentType() throws Exception
    {
        BaseClass baseClass = new BaseClass();

        TextAreaClass textAreaClass = new TextAreaClass();
        textAreaClass.setName("field");
        textAreaClass.setPrettyName("pretty name");
        textAreaClass.setSize(55);
        textAreaClass.setRows(33);
        baseClass.put("field", textAreaClass);

        assertFalse(baseClass.addTextAreaField("field", "pretty name", 55, 33));
    }
}
