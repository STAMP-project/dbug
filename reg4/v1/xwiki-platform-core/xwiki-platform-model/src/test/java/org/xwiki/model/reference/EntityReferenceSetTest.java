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
package org.xwiki.model.reference;

import java.util.Locale;

import org.junit.Test;
import org.xwiki.model.EntityType;
import org.xwiki.model.internal.reference.RelativeStringEntityReferenceResolver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Validate {@link EntityReferenceSet}.
 * 
 * @version $Id: fd0bd775e5fc4ebab2a12bc50a017a9c6b96a5c1 $
 */
public class EntityReferenceSetTest
{
    private RelativeStringEntityReferenceResolver RESOLVER = new RelativeStringEntityReferenceResolver();

    private EntityReferenceSet set = new EntityReferenceSet();

    private void assertMatches(EntityReference reference)
    {
        assertTrue(this.set.matches(reference));
    }

    private void assertNotMatches(EntityReference reference)
    {
        assertFalse(this.set.matches(reference));
    }

    private void assertMatches(String reference, EntityType type)
    {
        assertMatches(RESOLVER.resolve(reference, type));
    }

    private void assertNotMatches(String reference, EntityType type)
    {
        assertNotMatches(RESOLVER.resolve(reference, type));
    }

    private void assertMatchesWiki(String reference)
    {
        assertMatches(reference, EntityType.WIKI);
    }

    private void assertNotMatchesWiki(String reference)
    {
        assertNotMatches(reference, EntityType.WIKI);
    }

    private void assertMatchesSpace(String reference)
    {
        assertMatches(reference, EntityType.SPACE);
    }

    private void assertNotMatchesSpace(String reference)
    {
        assertNotMatches(reference, EntityType.SPACE);
    }

    private void assertMatchesDocument(String reference)
    {
        assertMatches(reference, EntityType.DOCUMENT);
    }

    private void assertNotMatchesDocument(String reference)
    {
        assertNotMatches(reference, EntityType.DOCUMENT);
    }

    // Includes

    private void includes(EntityReference reference)
    {
        this.set.includes(reference);
    }

    private void includes(String reference, EntityType type)
    {
        includes(RESOLVER.resolve(reference, type));
    }

    private void includesWiki(String reference)
    {
        includes(reference, EntityType.WIKI);
    }

    private void includesSpace(String reference)
    {
        includes(reference, EntityType.SPACE);
    }

    private void includesDocument(String reference)
    {
        includes(reference, EntityType.DOCUMENT);
    }

    // Excludes

    private void excludes(EntityReference reference)
    {
        this.set.excludes(reference);
    }

    private void excludes(String reference, EntityType type)
    {
        excludes(RESOLVER.resolve(reference, type));
    }

    private void excludesWiki(String reference)
    {
        excludes(reference, EntityType.WIKI);
    }

    private void excludesSpace(String reference)
    {
        excludes(reference, EntityType.SPACE);
    }

    private void excludesDocument(String reference)
    {
        excludes(reference, EntityType.DOCUMENT);
    }

    // Tests

    @Test
    public void testIncludeWiki()
    {
        includesWiki("wiki");

        assertMatchesWiki("wiki");
        assertNotMatchesWiki("notwiki");

        assertMatchesSpace("wiki:space");
        assertNotMatchesSpace("notwiki:space");

        includesWiki("otherwiki");

        assertMatchesWiki("wiki");

        assertMatchesWiki("otherwiki");

        assertNotMatchesWiki("notwiki");
    }

    @Test
    public void testIncludeSpace()
    {
        includesSpace("wiki:space");

        assertMatchesSpace("wiki:space");

        assertNotMatchesSpace("notwiki:space");
        assertNotMatchesSpace("wiki:notspace");

        includesSpace("wiki:otherspace");

        assertMatchesSpace("wiki:space");

        assertMatchesSpace("wiki:otherspace");

        assertNotMatchesSpace("wiki:notspace");
    }

    @Test
    public void testIncludeNestedSpace()
    {
        includesSpace("wiki:space.nested");

        assertMatchesSpace("wiki:space");

        assertMatchesSpace("wiki:space.nested");
        assertMatchesDocument("wiki:space.nested.document");

        assertNotMatchesSpace("notwiki:space");
        assertNotMatchesSpace("wiki:notspace");
        assertNotMatchesSpace("wiki:space.notnested");
        assertNotMatchesDocument("wiki:space.document");

        includesSpace("wiki:otherspace");

        assertMatchesSpace("wiki:space");

        assertMatchesSpace("wiki:otherspace");

        assertNotMatchesSpace("wiki:notspace");
    }

    @Test
    public void testIncludePartialOnlySpace()
    {
        includesSpace("space");

        assertMatchesSpace("wiki:space");
        assertMatchesSpace("space");

        assertNotMatchesSpace("wiki:notspace");
        assertNotMatchesSpace("notspace");
    }

    @Test
    public void testIncludeDocument()
    {
        includesDocument("wiki:space.document");

        assertMatchesDocument("wiki:space.document");

        assertNotMatchesDocument("notwiki:space.document");
        assertNotMatchesDocument("wiki:notspace.document");
        assertNotMatchesDocument("wiki:space.notdocument");
    }

    @Test
    public void testIncludeLocalDocumentLocale()
    {
        includes(new LocalDocumentReference("space", "document", Locale.ROOT));

        assertMatches(new LocalDocumentReference("space", "document"));
        assertMatches(new LocalDocumentReference("space", "document", Locale.ROOT));

        assertNotMatches(new LocalDocumentReference("space", "document", Locale.FRENCH));

        includes(new LocalDocumentReference("space", "document", Locale.ENGLISH));

        assertMatches(new LocalDocumentReference("space", "document"));
        assertMatches(new LocalDocumentReference("space", "document", Locale.ROOT));
        assertMatches(new LocalDocumentReference("space", "document", Locale.ENGLISH));

        assertNotMatches(new LocalDocumentReference("space", "document", Locale.FRENCH));
    }

    @Test
    public void testIncludeDocumentInNestedSpace()
    {
        includesDocument("wiki:space.nestedspace.document");

        assertMatchesDocument("wiki:space.nestedspace.document");

        assertNotMatchesDocument("wiki:space.othernestedspace.document");
        assertNotMatchesDocument("wiki:space.document");
        assertNotMatchesDocument("notwiki:space.nestedspace.document");
        assertNotMatchesDocument("wiki:notspace.nestedspace.document");
        assertNotMatchesDocument("wiki:space.nestedspace.notdocument");
        assertNotMatchesDocument("wiki:space.nestedspace.othernestedspace.document");
    }

    @Test
    public void testIncludeDocumentsInNestedSpacesWithShortAfterLong()
    {
        includesDocument("wiki:space.nestedspace.document");
        includesDocument("wiki:space.document");

        assertMatchesDocument("wiki:space.document");
        assertMatchesDocument("wiki:space.nestedspace.document");

        assertNotMatchesDocument("wiki:space.othernestedspace.document");
        assertNotMatchesDocument("notwiki:space.nestedspace.document");
        assertNotMatchesDocument("wiki:notspace.nestedspace.document");
        assertNotMatchesDocument("wiki:space.nestedspace.notdocument");
        assertNotMatchesDocument("wiki:space.nestedspace.othernestedspace.document");
    }

    @Test
    public void testIncludeDocumentsInNestedSpacesWithLongAfterShort()
    {
        includesDocument("wiki:space.document");
        includesDocument("wiki:space.nestedspace.document");

        assertMatchesDocument("wiki:space.document");
        assertMatchesDocument("wiki:space.nestedspace.document");

        assertNotMatchesDocument("wiki:space.othernestedspace.document");
        assertNotMatchesDocument("notwiki:space.nestedspace.document");
        assertNotMatchesDocument("wiki:notspace.nestedspace.document");
        assertNotMatchesDocument("wiki:space.nestedspace.notdocument");
        assertNotMatchesDocument("wiki:space.nestedspace.othernestedspace.document");
    }

    @Test
    public void testExcludeWiki()
    {
        excludesWiki("wiki");

        assertNotMatchesWiki("wiki");

        assertMatchesWiki("otherwiki");

        assertMatchesWiki("notwiki");

        set.excludes(new EntityReference("otherwiki", EntityType.WIKI));

        assertNotMatchesWiki("wiki");

        assertNotMatchesWiki("otherwiki");

        assertMatchesWiki("notwiki");
    }

    @Test
    public void testExcludeSpace()
    {
        excludesSpace("wiki:space");

        assertNotMatchesSpace("wiki:space");

        assertMatchesWiki("wiki");
        assertMatchesSpace("otherwiki:space");
        assertMatchesSpace("wiki:otherspace");
    }

    @Test
    public void testExcludeNestedSpace()
    {
        excludesSpace("wiki:space.nested");

        assertNotMatchesSpace("wiki:space.nested");
        assertNotMatchesDocument("wiki:space.nested.page");

        assertMatchesDocument("wiki:space.page");
        assertMatchesSpace("wiki:space");
        assertMatchesWiki("wiki");
        assertMatchesSpace("otherwiki:space");
        assertMatchesSpace("wiki:otherspace");
    }

    @Test
    public void testExcludePartial()
    {
        excludesSpace("space");

        assertNotMatches(new EntityReference("space", EntityType.SPACE, new EntityReference("wiki", EntityType.WIKI)));
        assertNotMatches(new EntityReference("space", EntityType.SPACE));

        assertMatches(new EntityReference("notspace", EntityType.SPACE, new EntityReference("wiki", EntityType.WIKI)));
        assertMatches(new EntityReference("notspace", EntityType.SPACE));
    }

    @Test
    public void testIncludeLocale()
    {
        includes(new DocumentReference("wiki", "space", "document", Locale.ENGLISH));

        assertMatches(new DocumentReference("wiki", "space", "document"));
        assertMatches(new DocumentReference("wiki", "space", "document", Locale.ENGLISH));

        assertNotMatches(new DocumentReference("wiki", "space", "document", Locale.FRENCH));
        assertNotMatches(new DocumentReference("wiki", "space", "document", Locale.ROOT));

        includes(new DocumentReference("wiki", "space", "document", Locale.FRENCH));

        assertMatches(new DocumentReference("wiki", "space", "document"));
        assertMatches(new DocumentReference("wiki", "space", "document", Locale.ENGLISH));
        assertMatches(new DocumentReference("wiki", "space", "document", Locale.FRENCH));

        assertNotMatches(new DocumentReference("wiki", "space", "document", Locale.ROOT));
    }

    @Test
    public void testExcludeLocale()
    {
        excludes(new DocumentReference("wiki", "space", "document", Locale.ENGLISH));

        assertMatches(new DocumentReference("wiki", "space", "document"));

        assertNotMatches(new DocumentReference("wiki", "space", "document", Locale.ENGLISH));

        assertMatches(new DocumentReference("wiki", "space", "document", Locale.FRENCH));
        assertMatches(new DocumentReference("wiki", "space", "document", Locale.ROOT));

        excludes(new DocumentReference("wiki", "space", "document", Locale.FRENCH));

        assertMatches(new DocumentReference("wiki", "space", "document"));

        assertNotMatches(new DocumentReference("wiki", "space", "document", Locale.ENGLISH));
        assertNotMatches(new DocumentReference("wiki", "space", "document", Locale.FRENCH));

        assertMatches(new DocumentReference("wiki", "space", "document", Locale.ROOT));
    }
}
