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
package org.xwiki.officeimporter.builder;

import java.io.InputStream;

import org.xwiki.component.annotation.Role;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.officeimporter.OfficeImporterException;
import org.xwiki.officeimporter.document.XHTMLOfficeDocument;

/**
 * Component responsible for building {@link XHTMLOfficeDocument} objects from binary office files.
 * 
 * @version $Id: c83a21ba84584dc8e23041949161abb7d6933567 $
 * @since 2.1M1
 */
@Role
public interface XHTMLOfficeDocumentBuilder
{
    /**
     * Builds a {@link XHTMLOfficeDocument} corresponding to the specified office document.
     * 
     * @param officeFileStream {@link InputStream} corresponding to the office document
     * @param officeFileName name of the office document (used to determine input document format)
     * @param reference reference document w.r.t which HTML cleaning is performed. If the office file contains images or
     *            other binary artifacts, HTML cleaning will be performed assuming that those artifacts are present as
     *            attachments to the reference document
     * @param filterStyles whether to filter CSS styles present in the HTML content produced by office server
     * @return an {@link XHTMLOfficeDocument} corresponding to the office document
     * @throws OfficeImporterException if an error occurs while performing the import operation
     * @since 2.2M1
     */
    XHTMLOfficeDocument build(InputStream officeFileStream, String officeFileName, DocumentReference reference,
        boolean filterStyles) throws OfficeImporterException;
}
