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
package com.xpn.xwiki.internal.mandatory;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;

import com.xpn.xwiki.doc.XWikiDocument;

/**
 * Update XWiki.XWikiGlobalRights document with all required informations.
 *
 * @version $Id: ed2420fee01e109527fbafc3cd3edb54e07fd457 $
 * @since 4.3M1
 */
@Component
@Named("XWiki.XWikiGlobalRights")
@Singleton
public class XWikiGlobalRightsDocumentInitializer extends AbstractRightsDocumentInitializer
{
    /**
     * Default constructor.
     */
    public XWikiGlobalRightsDocumentInitializer()
    {
        super("XWikiGlobalRights");
    }

    @Override
    public boolean updateDocument(XWikiDocument document)
    {
        boolean needsUpdate = super.updateDocument(document);

        needsUpdate |= setClassDocumentFields(document, "XWiki Global Rights Class");

        return needsUpdate;
    }
}
