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
package org.xwiki.rendering.internal.wiki;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.xwiki.bridge.DocumentAccessBridge;
import org.xwiki.bridge.SkinAccessBridge;
import org.xwiki.model.EntityType;
import org.xwiki.model.reference.AttachmentReference;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReferenceResolver;
import org.xwiki.model.reference.EntityReferenceSerializer;
import org.xwiki.rendering.internal.configuration.XWikiRenderingConfiguration;
import org.xwiki.rendering.internal.resolver.DefaultResourceReferenceEntityReferenceResolver;
import org.xwiki.rendering.listener.reference.AttachmentResourceReference;
import org.xwiki.rendering.listener.reference.DocumentResourceReference;
import org.xwiki.rendering.listener.reference.ResourceReference;
import org.xwiki.rendering.listener.reference.ResourceType;
import org.xwiki.rendering.wiki.WikiModel;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link XWikiWikiModel}.
 * 
 * @version $Id: d5c8edb185ca522027bc74a00db51df4a40c84f4 $
 * @since 2.0M1
 */
public class XWikiWikiModelTest
{
    @Rule
    public MockitoComponentMockingRule<WikiModel> mocker = new MockitoComponentMockingRule<WikiModel>(
        XWikiWikiModel.class);

    private EntityReferenceResolver<ResourceReference> referenceResolver;

    private DocumentAccessBridge documentAccessBridge;

    @Before
    public void configure() throws Exception
    {
        this.referenceResolver =
            this.mocker.getInstance(DefaultResourceReferenceEntityReferenceResolver.TYPE_RESOURCEREFERENCE);
        this.documentAccessBridge = this.mocker.getInstance((Type) DocumentAccessBridge.class);
    };

    @Test
    public void testGetDocumentEditURLWhenNoQueryStringSpecified() throws Exception
    {
        DocumentResourceReference reference = new DocumentResourceReference("TargetSpace.TargetPage");
        reference.setAnchor("anchor");

        EntityReferenceSerializer<String> compactEntityReferenceSerializer =
            this.mocker.getInstance(EntityReferenceSerializer.TYPE_STRING, "compactwiki");

        // Note: we use a character that needs to be encoded in the current document's page name to make sure the
        // generate query string is encoded.
        DocumentReference currentDocumentReference = new DocumentReference("Wiki", "Space", "Page\u20AC");
        DocumentReference documentReference = new DocumentReference("TargetWiki", "TargetSpace", "TargetPage");

        when(this.documentAccessBridge.getCurrentDocumentReference()).thenReturn(currentDocumentReference);
        when(compactEntityReferenceSerializer.serialize(currentDocumentReference)).thenReturn("Wiki:Space.Page\u20AC");
        when(this.referenceResolver.resolve(reference, EntityType.DOCUMENT)).thenReturn(documentReference);

        this.mocker.getComponentUnderTest().getDocumentEditURL(reference);

        // Verify that getDocumentURL is called with the query string already encoded since getDocumentURL() doesn't
        // encode it.
        verify(this.documentAccessBridge).getDocumentURL(documentReference, "create",
            "parent=Wiki%3ASpace.Page%E2%82%AC", "anchor");
    }

    /**
     * Tests that the proper image URL is generated when both the width and the height image parameters are specified.
     * 
     * @throws Exception if an exception occurs while running the test
     */
    @Test
    public void testGetImageURLWhenBothWidthAndHeightAttributesAreSpecified() throws Exception
    {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("width", "100px");
        parameters.put("height", "50");

        testImageURL(new AttachmentResourceReference("attachmentReference"), parameters, true, "width=100&height=50");
    }

    /**
     * Tests that the proper image URL is generated when both the width and the height image parameters are specified
     * but including them in the image URL is forbidden from the rendering configuration.
     * 
     * @throws Exception if an exception occurs while running the test
     */
    @Test
    public void testGetImageURLWhenIncludingImageDimensionsIsForbidden() throws Exception
    {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("width", "101px");
        parameters.put("height", "55px");

        testImageURL(new AttachmentResourceReference("attachmentReference"), parameters, false, null);
    }

    /**
     * Tests that the proper image URL is generated when both the width and the height CSS properties are specified.
     * 
     * @throws Exception if an exception occurs while running the test
     */
    @Test
    public void testGetImageURLWhenBothWidthAndHeightCSSPropertiesAreSpecified() throws Exception
    {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("style", "border: 1px; height: 30px; margin-top: 2em; width: 70px");

        testImageURL(new AttachmentResourceReference("attachmentReference"), parameters, true, "width=70&height=30");
    }

    /**
     * Tests that the proper image URL is generated when only the width image parameter is specified.
     * 
     * @throws Exception if an exception occurs while running the test
     */
    @Test
    public void testGetImageURLWhenOnlyWidthAttributeIsSpecified() throws Exception
    {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("width", "150");
        parameters.put("height", "30%");

        testImageURL(new AttachmentResourceReference("attachmentReference"), parameters, true, "width=150");
    }

    /**
     * Tests that the proper image URL is generated when only the height CSS property is specified.
     * 
     * @throws Exception if an exception occurs while running the test
     */
    @Test
    public void testGetImageURLWhenOnlyHeightCSSPropertyIsSpecified() throws Exception
    {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("style", "width: 5cm; height: 80px");

        testImageURL(new AttachmentResourceReference("attachmentReference"), parameters, true, "height=80");
    }

    /**
     * Tests that the proper image URL is generated when both the width and the height are unspecified and image size is
     * limited in the configuration.
     * 
     * @throws Exception if an exception occurs while running the test
     */
    @Test
    public void testGetImageURLWhenBothWidthAndHeightAreUnspecifiedAndImageSizeIsLimited() throws Exception
    {
        XWikiRenderingConfiguration configuration = this.mocker.getInstance((Type) XWikiRenderingConfiguration.class);
        when(configuration.getImageWidthLimit()).thenReturn(200);
        when(configuration.getImageHeightLimit()).thenReturn(170);
        Map<String, String> parameters = Collections.emptyMap();

        testImageURL(new AttachmentResourceReference("attachmentReference"), parameters, true,
            "width=200&height=170&keepAspectRatio=true");
    }

    /**
     * Tests that the proper image URL is generated when both the width and the height are unspecified and only the
     * image width is limited in the configuration.
     * 
     * @throws Exception if an exception occurs while running the test
     */
    @Test
    public void testGetImageURLWhenBothWidthAndHeightAreUnspecifiedAndOnlyImageWidthIsLimited() throws Exception
    {
        final XWikiRenderingConfiguration configuration =
            this.mocker.getInstance((Type) XWikiRenderingConfiguration.class);
        when(configuration.getImageWidthLimit()).thenReturn(25);
        when(configuration.getImageHeightLimit()).thenReturn(-1);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("width", "45%");
        parameters.put("style", "height:10em");

        testImageURL(new AttachmentResourceReference("attachmentReference"), parameters, true, "width=25");
    }

    /**
     * Tests that the proper image URL is generated when both the width and the height are unspecified and the image
     * size is not limited in the configuration.
     * 
     * @throws Exception if an exception occurs while running the test
     */
    @Test
    public void testGetImageURLWhenBothWidthAndHeightAreUnspecifiedAndImageSizeIsNotLimited() throws Exception
    {
        final XWikiRenderingConfiguration configuration =
            this.mocker.getInstance((Type) XWikiRenderingConfiguration.class);
        when(configuration.getImageWidthLimit()).thenReturn(-1);
        when(configuration.getImageHeightLimit()).thenReturn(-1);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("style", "bad CSS declaration");

        testImageURL(new AttachmentResourceReference("attachmentReference"), parameters, true, null);
    }

    /**
     * Tests that the proper image URL is generated when the attachment URL has a query string and a fragment
     * identifier.
     * 
     * @throws Exception if an exception occurs while running the test
     */
    @Test
    public void testGetImageURLWhenImageReferenceHasQueryString() throws Exception
    {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("height", "17");
        parameters.put("width", "23");

        AttachmentResourceReference reference = new AttachmentResourceReference("attachmentReference");
        reference.setParameter(AttachmentResourceReference.QUERY_STRING, "width=41&param=value");
        testImageURL(reference, parameters, true, "width=41&param=value&height=17");
    }

    /**
     * Tests that the proper image URL is generated when both the style and the dimension parameters are specified.
     * 
     * @throws Exception if an exception occurs while running the test
     */
    @Test
    public void testGetImageURLWhenBothStyleAndDimensionParametersAreSpecified() throws Exception
    {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("height", "46");
        parameters.put("width", "101px");
        parameters.put("style", "width: 20%; height:75px");

        // Note that the style parameter take precedence over the dimension parameters and the width is actually 20% but
        // we can't use it for resizing the image on the server side so it's omitted from the query string.
        testImageURL(new AttachmentResourceReference("attachmentReference"), parameters, true, "height=75");
    }

    @Test
    public void testGetImageURLWhenIcon() throws Exception
    {
        ResourceReference reference = new ResourceReference("iconname", ResourceType.ICON);
        SkinAccessBridge skinAccessBridge = this.mocker.getInstance((Type) SkinAccessBridge.class);
        when(skinAccessBridge.getIconURL("iconname")).thenReturn("/path/to/icon");

        assertEquals("/path/to/icon",
            this.mocker.getComponentUnderTest().getImageURL(reference, Collections.<String, String>emptyMap()));
    }

    @Test
    public void testGetDocumentViewURLWhenNoBaseReference() throws Exception
    {
        ResourceReference reference = new ResourceReference("reference", ResourceType.DOCUMENT);
        DocumentReference documentReference = new DocumentReference("wiki", "space", "page");
        when(this.referenceResolver.resolve(reference, EntityType.DOCUMENT)).thenReturn(documentReference);
        when(this.documentAccessBridge.getDocumentURL(documentReference, "view", null, null)).thenReturn("viewurl");

        assertEquals("viewurl", this.mocker.getComponentUnderTest().getDocumentViewURL(reference));
    }

    @Test
    public void testGetDocumentViewURLWhenBaseReferenceSpecified() throws Exception
    {
        ResourceReference reference = new ResourceReference("reference", ResourceType.DOCUMENT);
        reference.addBaseReference("base");
        DocumentReference documentReference = new DocumentReference("wiki", "space", "page");
        when(this.referenceResolver.resolve(reference, EntityType.DOCUMENT)).thenReturn(documentReference);
        when(this.documentAccessBridge.getDocumentURL(documentReference, "view", null, null)).thenReturn("viewurl");

        assertEquals("viewurl", this.mocker.getComponentUnderTest().getDocumentViewURL(reference));
    }

    private void testImageURL(final ResourceReference imageReference, Map<String, String> parameters,
        final boolean expectedIsImageDimensionsIncludedInImageURL, final String expectedQueryString) throws Exception
    {
        AttachmentReference attachmentReference =
            new AttachmentReference("image", new DocumentReference("wiki", "space", "page"));
        XWikiRenderingConfiguration configuration = this.mocker.getInstance((Type) XWikiRenderingConfiguration.class);
        when(configuration.isImageDimensionsIncludedInImageURL()).thenReturn(
            expectedIsImageDimensionsIncludedInImageURL);
        when(this.referenceResolver.resolve(imageReference, EntityType.ATTACHMENT)).thenReturn(attachmentReference);
        when(this.documentAccessBridge.getAttachmentURL(attachmentReference, expectedQueryString, false)).thenReturn(
            "?" + expectedQueryString);

        assertEquals("?" + expectedQueryString,
            this.mocker.getComponentUnderTest().getImageURL(imageReference, parameters));
    }
}
