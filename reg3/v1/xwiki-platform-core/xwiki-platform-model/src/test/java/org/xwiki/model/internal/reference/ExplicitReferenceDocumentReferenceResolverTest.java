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
package org.xwiki.model.internal.reference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.xwiki.component.util.ReflectionUtils;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.DocumentReferenceResolver;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.test.annotation.ComponentList;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

/**
 * Unit tests for {@link org.xwiki.model.internal.reference.ExplicitReferenceDocumentReferenceResolver}.
 *
 * @version $Id: 9c17480650f2a82e1faa0f3ed886e48c67ac3b23 $
 * @since 2.2.3
 */
@ComponentList({
    DefaultSymbolScheme.class
})
public class ExplicitReferenceDocumentReferenceResolverTest
{
    @Rule
    public MockitoComponentMockingRule<DefaultStringEntityReferenceResolver> mocker =
        new MockitoComponentMockingRule<>(DefaultStringEntityReferenceResolver.class);

    private DocumentReferenceResolver<EntityReference> resolver;

    @Before
    public void setUp() throws Exception
    {
        this.resolver = new ExplicitReferenceDocumentReferenceResolver();
        ReflectionUtils.setFieldValue(this.resolver, "entityReferenceResolver", this.mocker.getComponentUnderTest());
    }

    @Test
    public void resolveWithExplicitDocumentReference()
    {
        DocumentReference reference = this.resolver.resolve(null, new DocumentReference("wiki", "space", "page"));

        Assert.assertEquals("page", reference.getName());
        Assert.assertEquals("space", reference.getLastSpaceReference().getName());
        Assert.assertEquals("wiki", reference.getWikiReference().getName());
    }
}
