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
package org.xwiki.wiki.internal.descriptor.document;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.container.Container;
import org.xwiki.container.Request;
import org.xwiki.container.servlet.ServletRequest;

import com.xpn.xwiki.XWiki;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.internal.mandatory.AbstractMandatoryDocumentInitializer;
import com.xpn.xwiki.objects.BaseObject;
import com.xpn.xwiki.user.api.XWikiRightService;

/**
 * Initialize main wiki descriptor with what come from the first request.
 * 
 * @version $Id: 7681f936f8f24a6991e3aeb1d3ffee689af7c744 $
 * @since 8.0RC1
 */
@Component
@Named("XWiki.XWikiServerXwiki")
@Singleton
public class XWikiServerXwikiDocumentInitializer extends AbstractMandatoryDocumentInitializer
{
    /**
     * The name of the mandatory document.
     */
    public static final String DOCUMENT_NAME = "XWikiServerXwiki";

    /**
     * Used to access the XWiki model.
     */
    @Inject
    private Provider<XWikiContext> contextProvider;

    @Inject
    private Logger logger;

    @Inject
    private Container container;

    /**
     * Default constructor.
     */
    public XWikiServerXwikiDocumentInitializer()
    {
        super(XWiki.SYSTEM_SPACE, DOCUMENT_NAME);
    }

    @Override
    public boolean isMainWikiOnly()
    {
        // Initialize it only for the main wiki.
        return true;
    }

    @Override
    public boolean updateDocument(XWikiDocument document)
    {
        boolean needsUpdate = false;

        // Add a descriptor if none already exist
        if (document.getXObject(XWikiServerClassDocumentInitializer.SERVER_CLASS) == null) {
            XWikiContext xcontext = this.contextProvider.get();

            try {
                BaseObject xobject = document.newXObject(XWikiServerClassDocumentInitializer.SERVER_CLASS, xcontext);

                xobject.setStringValue(XWikiServerClassDocumentInitializer.FIELD_DESCRIPTION, "Main wiki");
                xobject.setStringValue(XWikiServerClassDocumentInitializer.FIELD_HOMEPAGE, "Main.WebHome");
                xobject.setStringValue(XWikiServerClassDocumentInitializer.FIELD_LANGUAGE, "en");
                xobject.setIntValue(XWikiServerClassDocumentInitializer.FIELD_SECURE, 0);
                xobject.setStringValue(XWikiServerClassDocumentInitializer.FIELD_STATE, "active");
                xobject.setStringValue(XWikiServerClassDocumentInitializer.FIELD_VISIBILITY, "public");
                xobject.setStringValue(XWikiServerClassDocumentInitializer.FIELD_OWNER,
                    XWikiRightService.SUPERADMIN_USER_FULLNAME);
                xobject.setStringValue(XWikiServerClassDocumentInitializer.FIELD_WIKIPRETTYNAME, "Home");

                Request request = this.container.getRequest();
                if (request instanceof ServletRequest) {
                    ServletRequest servletRequest = (ServletRequest) request;
                    xobject.setStringValue(XWikiServerClassDocumentInitializer.FIELD_SERVER,
                        servletRequest.getHttpServletRequest().getServerName());
                } else {
                    xobject.setStringValue(XWikiServerClassDocumentInitializer.FIELD_SERVER, "localhost");
                }

                needsUpdate = true;
            } catch (XWikiException e) {
                this.logger.error("Faied to initialize main wiki descriptor", e);
            }
        }

        // Note: We don't set a title since there's a sheet computing a proper title.
        needsUpdate |= setDocumentFields(document, "");

        return needsUpdate;
    }

}
