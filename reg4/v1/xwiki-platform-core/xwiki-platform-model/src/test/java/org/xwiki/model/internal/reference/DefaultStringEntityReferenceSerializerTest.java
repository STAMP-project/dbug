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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xwiki.component.util.ReflectionUtils;
import org.xwiki.model.EntityType;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.model.reference.EntityReferenceProvider;
import org.xwiki.model.reference.EntityReferenceResolver;
import org.xwiki.model.reference.EntityReferenceSerializer;
import org.xwiki.model.reference.test.TestConstants;

/**
 * Unit tests for {@link DefaultStringEntityReferenceSerializer}.
 * 
 * @version $Id: d4ed8e7d740bfc2d9564c4cedfb59eadd754bcc5 $
 * @since 2.2M1
 */
public class DefaultStringEntityReferenceSerializerTest implements TestConstants
{
    private EntityReferenceSerializer<String> serializer;

    private EntityReferenceResolver<String> resolver;

    private Mockery mockery = new Mockery();

    @Before
    public void setUp()
    {
        this.serializer = new DefaultStringEntityReferenceSerializer();

        this.resolver = new DefaultStringEntityReferenceResolver();
        final EntityReferenceProvider mockReferenceProvider = this.mockery.mock(EntityReferenceProvider.class);
        ReflectionUtils.setFieldValue(this.resolver, "provider", mockReferenceProvider);

        this.mockery.checking(new Expectations()
        {
            {
                allowing(mockReferenceProvider).getDefaultReference(EntityType.WIKI);
                will(returnValue(DEFAULT_WIKI_REFERENCE));
                allowing(mockReferenceProvider).getDefaultReference(EntityType.SPACE);
                will(returnValue(DEFAULT_SPACE_REFERENCE));
                allowing(mockReferenceProvider).getDefaultReference(EntityType.DOCUMENT);
                will(returnValue(DEFAULT_PAGE_REFERENCE));
                allowing(mockReferenceProvider).getDefaultReference(EntityType.ATTACHMENT);
                will(returnValue(DEFAULT_ATTACHMENT_REFERENCE));
                allowing(mockReferenceProvider).getDefaultReference(EntityType.OBJECT);
                will(returnValue(DEFAULT_OBJECT_REFERENCE));
                allowing(mockReferenceProvider).getDefaultReference(EntityType.OBJECT_PROPERTY);
                will(returnValue(DEFAULT_OBJECT_PROPERTY_REFERENCE));
                allowing(mockReferenceProvider).getDefaultReference(EntityType.CLASS_PROPERTY);
                will(returnValue(DEFAULT_CLASS_PROPERTY_REFERENCE));
            }
        });
    }

    @Test
    public void testSerializeDocumentReference() throws Exception
    {
        EntityReference reference = resolver.resolve("wiki:space.page", EntityType.DOCUMENT);
        Assert.assertEquals("wiki:space.page", serializer.serialize(reference));

        reference = resolver.resolve("wiki:space.", EntityType.DOCUMENT);
        Assert.assertEquals("wiki:space.defpage", serializer.serialize(reference));

        reference = resolver.resolve("space.", EntityType.DOCUMENT);
        Assert.assertEquals("defwiki:space.defpage", serializer.serialize(reference));

        reference = resolver.resolve("page", EntityType.DOCUMENT);
        Assert.assertEquals("defwiki:defspace.page", serializer.serialize(reference));

        reference = resolver.resolve(".", EntityType.DOCUMENT);
        Assert.assertEquals("defwiki:defspace.defpage", serializer.serialize(reference));

        reference = resolver.resolve(null, EntityType.DOCUMENT);
        Assert.assertEquals("defwiki:defspace.defpage", serializer.serialize(reference));

        reference = resolver.resolve("", EntityType.DOCUMENT);
        Assert.assertEquals("defwiki:defspace.defpage", serializer.serialize(reference));

        reference = resolver.resolve("wiki1.wiki2:wiki3:some.space.page", EntityType.DOCUMENT);
        Assert.assertEquals("wiki1.wiki2:wiki3:some.space.page", serializer.serialize(reference));

        reference = resolver.resolve("some.space.page", EntityType.DOCUMENT);
        Assert.assertEquals("defwiki:some.space.page", serializer.serialize(reference));

        reference = resolver.resolve("wiki:page", EntityType.DOCUMENT);
        Assert.assertEquals("defwiki:defspace.wiki:page", serializer.serialize(reference));

        // Verify that passing null doesn't throw a NPE
        Assert.assertNull(serializer.serialize(null));

        // Test escapes

        reference = resolver.resolve("\\.:@\\.", EntityType.DOCUMENT);
        Assert.assertEquals("defwiki:defspace.\\.:@\\.", serializer.serialize(reference));

        reference = resolver.resolve("\\\\:\\\\.\\\\", EntityType.DOCUMENT);
        Assert.assertEquals("\\\\:\\\\.\\\\", serializer.serialize(reference));

        // The escaping here is not necessary but we want to test that it works
        reference = resolver.resolve("\\wiki:\\space.\\page", EntityType.DOCUMENT);
        Assert.assertEquals("wiki:space.page", serializer.serialize(reference));
    }

    @Test
    public void testSerializeSpaceReference() throws Exception
    {
        EntityReference reference = resolver.resolve("wiki:space1.space2", EntityType.SPACE);
        Assert.assertEquals("wiki:space1.space2", serializer.serialize(reference));
    }

    @Test
    public void testSerializeAttachmentReference() throws Exception
    {
        EntityReference reference = resolver.resolve("wiki:space.page@filename", EntityType.ATTACHMENT);
        Assert.assertEquals("wiki:space.page@filename", serializer.serialize(reference));

        reference = resolver.resolve("", EntityType.ATTACHMENT);
        Assert.assertEquals("defwiki:defspace.defpage@deffilename", serializer.serialize(reference));

        reference = resolver.resolve("wiki:space.page@my.png", EntityType.ATTACHMENT);
        Assert.assertEquals("wiki:space.page@my.png", serializer.serialize(reference));

        reference = resolver.resolve("some:file.name", EntityType.ATTACHMENT);
        Assert.assertEquals("defwiki:defspace.defpage@some:file.name", serializer.serialize(reference));

        // Test escapes

        reference = resolver.resolve(":.\\@", EntityType.ATTACHMENT);
        Assert.assertEquals("defwiki:defspace.defpage@:.\\@", serializer.serialize(reference));
    }

    @Test
    public void testSerializeReferenceWithChild()
    {
        EntityReference reference = resolver.resolve("wiki:Space.Page", EntityType.DOCUMENT);
        Assert.assertEquals("wiki:Space", serializer.serialize(reference.getParent()));

        Assert.assertEquals("wiki", serializer.serialize(reference.getParent().getParent()));
    }

    /**
     * Tests resolving and re-serializing an object reference.
     */
    @Test
    public void testSerializeObjectReference()
    {
        EntityReference reference = resolver.resolve("wiki:space.page^Object", EntityType.OBJECT);
        Assert.assertEquals("wiki:space.page^Object", serializer.serialize(reference));

        // default values
        reference = resolver.resolve("", EntityType.OBJECT);
        Assert.assertEquals("defwiki:defspace.defpage^defobject", serializer.serialize(reference));

        // property reference with no object
        reference = resolver.resolve("wiki:space.page.property", EntityType.OBJECT);
        Assert.assertEquals("defwiki:defspace.defpage^wiki:space.page.property", serializer.serialize(reference));

        // test escaping character
        reference = resolver.resolve("wiki:space.page^Obje\\^ct", EntityType.OBJECT);
        Assert.assertEquals("wiki:space.page^Obje\\^ct", serializer.serialize(reference));

        reference = resolver.resolve("wiki:spa^ce.page^Obje\\^ct", EntityType.OBJECT);
        Assert.assertEquals("wiki:spa^ce.page^Obje\\^ct", serializer.serialize(reference));

        reference = resolver.resolve(":.\\^@", EntityType.OBJECT);
        Assert.assertEquals("defwiki:defspace.defpage^:.\\^@", serializer.serialize(reference));
    }

    /**
     * Tests resolving and re-serializing an object reference.
     */
    @Test
    public void testSerializeObjectPropertyReference()
    {
        EntityReference reference = resolver.resolve("wiki:space.page^xwiki.class[0].prop", EntityType.OBJECT_PROPERTY);
        Assert.assertEquals("wiki:space.page^xwiki.class[0].prop", serializer.serialize(reference));

        // default values
        reference = resolver.resolve("", EntityType.OBJECT_PROPERTY);
        Assert.assertEquals("defwiki:defspace.defpage^defobject.defobjproperty", serializer.serialize(reference));

        // using separators
        reference = resolver.resolve("space^page@attachment", EntityType.OBJECT_PROPERTY);
        Assert
            .assertEquals("defwiki:defspace.defpage^defobject.space^page@attachment", serializer.serialize(reference));

        reference = resolver.resolve("wiki:space^object", EntityType.OBJECT_PROPERTY);
        Assert.assertEquals("defwiki:defspace.defpage^defobject.wiki:space^object", serializer.serialize(reference));

        // test escaping character
        reference = resolver.resolve("wiki:space.page^xwiki.class[0].prop\\.erty", EntityType.OBJECT_PROPERTY);
        Assert.assertEquals("wiki:space.page^xwiki.class[0].prop\\.erty", serializer.serialize(reference));

        reference = resolver.resolve(":\\.^@", EntityType.OBJECT_PROPERTY);
        Assert.assertEquals("defwiki:defspace.defpage^defobject.:\\.^@", serializer.serialize(reference));
    }

    /**
     * Tests resolving and re-serializing an object reference.
     */
    @Test
    public void testSerializeClassPropertyReference()
    {
        EntityReference reference = resolver.resolve("wiki:space.page^ClassProperty", EntityType.CLASS_PROPERTY);
        Assert.assertEquals("wiki:space.page^ClassProperty", serializer.serialize(reference));

        // default values
        reference = resolver.resolve("", EntityType.CLASS_PROPERTY);
        Assert.assertEquals("defwiki:defspace.defpage^defclassproperty", serializer.serialize(reference));

        // property reference with no object
        reference = resolver.resolve("wiki:space.page.property", EntityType.CLASS_PROPERTY);
        Assert.assertEquals("defwiki:defspace.defpage^wiki:space\\.page\\.property", serializer.serialize(reference));

        // test escaping character
        reference = resolver.resolve("wiki:space.page^Obje\\^ct", EntityType.CLASS_PROPERTY);
        Assert.assertEquals("wiki:space.page^Obje\\^ct", serializer.serialize(reference));

        reference = resolver.resolve("wiki:spa^ce.page^Obje\\^ct", EntityType.CLASS_PROPERTY);
        Assert.assertEquals("wiki:spa^ce.page^Obje\\^ct", serializer.serialize(reference));

        reference = resolver.resolve(":.\\^@", EntityType.CLASS_PROPERTY);
        Assert.assertEquals("defwiki:defspace.defpage^:\\.\\^@", serializer.serialize(reference));
    }

    @Test
    public void testSerializeRelativeReference()
    {
        EntityReference reference = new EntityReference("page", EntityType.DOCUMENT);
        Assert.assertEquals("page", serializer.serialize(reference));

        reference = new EntityReference("page", EntityType.DOCUMENT, new EntityReference("space", EntityType.SPACE));
        Assert.assertEquals("space.page", serializer.serialize(reference));

        // Ensure that the page part is not displayed in the serialized result
        Assert.assertEquals("space", serializer.serialize(reference.extractReference(EntityType.SPACE)));
    }
}
