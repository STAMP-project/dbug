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
package com.xpn.xwiki.internal.observation.remote.converter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.xwiki.context.Execution;
import org.xwiki.context.ExecutionContext;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.observation.remote.converter.AbstractEventConverter;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.util.XWikiStubContextProvider;

/**
 * Provide some serialization tools for old apis like {@link XWikiDocument} and {@link XWikiContext}.
 *
 * @version $Id: 4059a6daf2cdb3633c9e17dd21abf4ebcbb08e7d $
 * @since 2.0M4
 */
public abstract class AbstractXWikiEventConverter extends AbstractEventConverter
{
    protected static final String CONTEXT_WIKI = "contextwiki";

    protected static final String CONTEXT_USER = "contextuser";

    protected static final String DOC_NAME = "docname";

    protected static final String DOC_VERSION = "docversion";

    protected static final String DOC_LANGUAGE = "doclanguage";

    protected static final String ORIGDOC_VERSION = "origdocversion";

    protected static final String ORIGDOC_LANGUAGE = "origdoclanguage";

    /**
     * The logger to log.
     */
    @Inject
    protected Logger logger;

    /**
     * Used to set some proper context informations.
     */
    @Inject
    private Execution execution;

    /**
     * Generate stub XWikiContext.
     */
    @Inject
    private XWikiStubContextProvider stubContextProvider;

    /**
     * @param context the XWiki context to serialize
     * @return the serialized version of the context
     */
    protected Serializable serializeXWikiContext(XWikiContext context)
    {
        HashMap<String, Serializable> remoteDataMap = new HashMap<String, Serializable>();

        remoteDataMap.put(CONTEXT_WIKI, context.getWikiId());
        remoteDataMap.put(CONTEXT_USER, context.getUser());

        return remoteDataMap;
    }

    /**
     * @return a stub XWikiContext, null if none can be generated (XWiki has never been accessed yet)
     */
    private XWikiContext getXWikiStubContext()
    {
        ExecutionContext context = this.execution.getContext();
        XWikiContext xcontext = (XWikiContext) context.getProperty(XWikiContext.EXECUTIONCONTEXT_KEY);

        if (xcontext == null) {
            xcontext = this.stubContextProvider.createStubContext();

            if (xcontext != null) {
                xcontext.declareInExecutionContext(context);
            }
        }

        return xcontext;
    }

    /**
     * @param remoteData the serialized version of the context
     * @return the XWiki context
     */
    protected XWikiContext unserializeXWikiContext(Serializable remoteData)
    {
        XWikiContext xcontext = getXWikiStubContext();

        Map<String, Serializable> remoteDataMap = (Map<String, Serializable>) remoteData;

        if (xcontext != null) {
            xcontext.setWikiId((String) remoteDataMap.get(CONTEXT_WIKI));
            xcontext.setUser((String) remoteDataMap.get(CONTEXT_USER));
        } else {
            this.logger.warn("Can't get a proper XWikiContext."
                + " It generally mean that the wiki has never been fully initialized,"
                + " i.e. has never been accesses at least once");
        }

        return xcontext;
    }

    /**
     * @param document the document to serialize
     * @return the serialized version of the document
     */
    protected Serializable serializeXWikiDocument(XWikiDocument document)
    {
        HashMap<String, Serializable> remoteDataMap = new HashMap<String, Serializable>();

        remoteDataMap.put(DOC_NAME, document.getDocumentReference());

        if (!document.isNew()) {
            remoteDataMap.put(DOC_VERSION, document.getVersion());
            remoteDataMap.put(DOC_LANGUAGE, document.getLanguage());
        }

        XWikiDocument originalDocument = document.getOriginalDocument();

        if (originalDocument != null && !originalDocument.isNew()) {
            remoteDataMap.put(ORIGDOC_VERSION, originalDocument.getVersion());
            remoteDataMap.put(ORIGDOC_LANGUAGE, originalDocument.getLanguage());
        }

        return remoteDataMap;
    }

    protected XWikiDocument getDocument(DocumentReference documentReference, String language, String version)
        throws XWikiException
    {
        XWikiContext xcontext = getXWikiStubContext();

        XWikiDocument document = new XWikiDocument(documentReference);
        document.setLanguage(language);

        // Force bypassing the cache to make extra sure we get the last version of the document.
        XWikiDocument targetDocument = xcontext.getWiki().getNotCacheStore().loadXWikiDoc(document, xcontext);

        if (!targetDocument.getVersion().equals(version)) {
            // It's not the last version of the document, ask versioning store.
            targetDocument = xcontext.getWiki().getVersioningStore().loadXWikiDoc(document, version, xcontext);
        }

        return targetDocument;
    }

    /**
     * @param remoteData the serialized version of the document
     * @return the document
     * @throws XWikiException when failing to unserialize document
     */
    protected XWikiDocument unserializeDocument(Serializable remoteData) throws XWikiException
    {
        Map<String, Serializable> remoteDataMap = (Map<String, Serializable>) remoteData;

        DocumentReference docReference = (DocumentReference) remoteDataMap.get(DOC_NAME);

        XWikiDocument doc;
        if (remoteDataMap.get(DOC_VERSION) == null) {
            doc = new XWikiDocument(docReference);
        } else {
            doc =
                getDocument(docReference, (String) remoteDataMap.get(DOC_LANGUAGE),
                    (String) remoteDataMap.get(DOC_VERSION));
        }

        XWikiDocument origDoc;
        if (remoteDataMap.get(ORIGDOC_VERSION) == null) {
            origDoc = new XWikiDocument(docReference);
        } else {
            origDoc =
                getDocument(docReference, (String) remoteDataMap.get(ORIGDOC_LANGUAGE),
                    (String) remoteDataMap.get(ORIGDOC_VERSION));
        }

        doc.setOriginalDocument(origDoc);

        return doc;
    }
}
