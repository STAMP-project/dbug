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
package org.xwiki.url.internal;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.xwiki.model.internal.reference.DefaultSymbolScheme;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.test.annotation.ComponentList;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link URLStringEntityReferenceSerializer}.
 *
 * @version $Id: b2e603090e95c0b19dadf76a16af03a879f822b1 $
 * @since 8.2M1
 */
@ComponentList({
    DefaultSymbolScheme.class,
    URLSymbolScheme.class
})
public class URLStringEntityReferenceSerializerTest
{
    @Rule
    public MockitoComponentMockingRule<URLStringEntityReferenceSerializer> mocker =
        new MockitoComponentMockingRule<>(URLStringEntityReferenceSerializer.class);

    @Test
    public void serializeReferences() throws Exception
    {
        EntityReference reference = new DocumentReference("wiki!", Arrays.asList("space:.!"), "page.!");
        assertEquals("wiki!!:space!:!.!!.page!.!!", this.mocker.getComponentUnderTest().serialize(reference));
    }
}


