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
package org.xwiki.officeimporter.internal;

import org.xwiki.bridge.DocumentAccessBridge;
import org.xwiki.model.reference.DocumentReferenceResolver;
import org.xwiki.model.reference.EntityReferenceSerializer;
import org.xwiki.officeimporter.server.OfficeServer;
import org.xwiki.test.jmock.AbstractComponentTestCase;

/**
 * An abstract test case to be used by all office-importer tests.
 * 
 * @version $Id: 9aa172bd1c7ded5e76aa6e9634c05fe1e7acedfd $
 * @since 2.1M1
 */
public abstract class AbstractOfficeImporterTest extends AbstractComponentTestCase
{
    /**
     * Mock document access bridge.
     */
    protected DocumentAccessBridge mockDocumentAccessBridge;

    /**
     * Mock (default) string document reference serializer.
     */
    protected EntityReferenceSerializer<String> mockDefaultStringEntityReferenceSerializer;

    /**
     * Mock (compactwiki) string document reference serializer.
     */
    protected EntityReferenceSerializer<String> mockCompactWikiStringEntityReferenceSerializer;

    /**
     * Mock document name factory.
     */
    protected DocumentReferenceResolver<String> mockDocumentReferenceResolver;

    /**
     * Mock office server.
     */
    protected OfficeServer mockOfficeServer;

    @Override
    protected void registerComponents() throws Exception
    {
        super.registerComponents();

        this.mockDocumentAccessBridge = registerMockComponent(DocumentAccessBridge.class);
        this.mockDefaultStringEntityReferenceSerializer =
            registerMockComponent(EntityReferenceSerializer.TYPE_STRING, "default", "s1");
        this.mockCompactWikiStringEntityReferenceSerializer =
            registerMockComponent(EntityReferenceSerializer.TYPE_STRING, "compactwiki", "s2");
        this.mockDocumentReferenceResolver =
            registerMockComponent(DocumentReferenceResolver.TYPE_STRING, "currentmixed");
        this.mockOfficeServer = registerMockComponent(OfficeServer.class);
    }
}
