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
package org.xwiki.search.solr.internal;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.xwiki.model.EntityType;
import org.xwiki.model.reference.ClassPropertyReference;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.model.reference.EntityReferenceProvider;
import org.xwiki.model.reference.EntityReferenceResolver;
import org.xwiki.model.reference.SpaceReference;
import org.xwiki.model.reference.WikiReference;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link SolrFieldStringEntityReferenceResolver}.
 * 
 * @version $Id: 494147eac92aff82058e555e2870598cad36d3a7 $
 * @since 5.3RC1
 */
public class SolrFieldStringEntityReferenceResolverTest
{
    @Rule
    public MockitoComponentMockingRule<EntityReferenceResolver<String>> mocker =
        new MockitoComponentMockingRule<EntityReferenceResolver<String>>(SolrFieldStringEntityReferenceResolver.class);

    @Test
    public void resolve() throws Exception
    {
        EntityReferenceProvider currentEntityReferenceProvider =
            this.mocker.getInstance(EntityReferenceProvider.class, "current");
        when(currentEntityReferenceProvider.getDefaultReference(EntityType.WIKI)).thenReturn(new WikiReference("test"));

        EntityReferenceResolver<String> resolver = mocker.getComponentUnderTest();

        DocumentReference documentReference =
            new DocumentReference("test", Arrays.asList("My App", "Code", "Model"), "A Class");
        assertEquals(new ClassPropertyReference("title", documentReference),
            new ClassPropertyReference(resolver.resolve("My App.Code.Model.A Class.title", EntityType.CLASS_PROPERTY)));

        documentReference = new DocumentReference("test", Arrays.asList("My.App", "Co.de"), "A.Class");
        assertEquals(new ClassPropertyReference("ti.tle", documentReference),
            new ClassPropertyReference(resolver.resolve("My..App.Co..de.A..Class.ti..tle", EntityType.CLASS_PROPERTY)));

        assertEquals(new SpaceReference("0.9", new SpaceReference("a..z", new WikiReference("test"))),
            new SpaceReference(resolver.resolve("a....z.0..9", EntityType.SPACE)));

        // Relative reference resolved based on the given parameters.

        assertEquals(
            new ClassPropertyReference("title", new DocumentReference("foo", Arrays.asList("Code", "Model"), "A Class")),
            new ClassPropertyReference(resolver.resolve("Code.Model.A Class.title", EntityType.CLASS_PROPERTY,
                new SpaceReference("My App", new WikiReference("foo")))));

        // Relative reference resolve based on the current entity.

        when(currentEntityReferenceProvider.getDefaultReference(EntityType.SPACE)).thenReturn(
            new EntityReference("Code", EntityType.SPACE, new EntityReference("My App", EntityType.SPACE, null)));
        assertEquals(
            new ClassPropertyReference("title",
                new DocumentReference("bar", Arrays.asList("My App", "Code"), "A Class")),
            new ClassPropertyReference(resolver.resolve("A Class.title", EntityType.CLASS_PROPERTY, new WikiReference(
                "bar"))));
    }
}
