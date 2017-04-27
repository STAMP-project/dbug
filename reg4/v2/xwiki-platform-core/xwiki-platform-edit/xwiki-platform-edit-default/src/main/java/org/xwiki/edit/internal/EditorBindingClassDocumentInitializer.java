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
package org.xwiki.edit.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.model.reference.LocalDocumentReference;

import com.xpn.xwiki.XWiki;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.internal.mandatory.AbstractMandatoryDocumentInitializer;
import com.xpn.xwiki.objects.classes.BaseClass;

/**
 * Update the {@code XWiki.EditorBindingClass} document with all the required information.
 * 
 * @version $Id: 085c52111a888147eb757b13b20034134a099684 $
 * @since 8.2RC1
 */
@Component
@Named("XWiki.EditorBindingClass")
@Singleton
public class EditorBindingClassDocumentInitializer extends AbstractMandatoryDocumentInitializer
{
    /**
     * The local reference of the editor binding class.
     */
    public static final LocalDocumentReference REFERENCE = new LocalDocumentReference(XWiki.SYSTEM_SPACE,
        "EditorBindingClass");

    /**
     * Default constructor.
     */
    public EditorBindingClassDocumentInitializer()
    {
        super(REFERENCE);
    }

    @Override
    public boolean updateDocument(XWikiDocument document)
    {
        boolean needsUpdate = false;

        BaseClass bclass = document.getXClass();

        needsUpdate |= bclass.addTextField("dataType", "Data Type", 30);
        needsUpdate |= bclass.addTextField("roleHint", "Role Hint", 30);

        needsUpdate |= setClassDocumentFields(document, "Editor Binding Class");

        return needsUpdate;
    }
}
