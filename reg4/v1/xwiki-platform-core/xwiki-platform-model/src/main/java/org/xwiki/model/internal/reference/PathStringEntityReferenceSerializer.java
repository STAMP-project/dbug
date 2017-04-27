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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.model.reference.EntityReference;

/**
 * Generate a path representation of an entity reference (eg "Wiki/Space/Page" for a Document Reference in the
 * "wiki" Wiki, the "space" Space and the "page" Page).
 *
 * @version $Id: cd7eda310b14a539e2defba7319a1fa3081f5c35 $
 * @since 3.0M2
 */
@Component
@Named("path")
@Singleton
public class PathStringEntityReferenceSerializer extends AbstractStringEntityReferenceSerializer
{
    /**
     * {@inheritDoc}
     *
     * <p/>
     * Add a segment to the path. All non-URL compatible characters are escaped in the URL-escape format
     * (%NN). Dot (".") and Star ("*") characters are also encoded. If this is not the last segment in the reference,
     * append a "/" separator between reference element.
     */
    @Override
    protected void serializeEntityReference(EntityReference currentReference, StringBuilder representation,
        boolean isLastReference, Object... parameters)
    {
        if (currentReference.getParent() != null) {
            // Note: Java will convert the file separator to the proper separator for the underlying FileSystem.
            // Note: Using "/" allows us to reuse the serialized result into URLs. Caveat: The % character might need
            // to be escaped as %25 in this case as otherwise the browser will automatically decode % encoding.
            representation.append('/');
        }

        try {
            // Note: We assume the FileSystem is case-sensitive. This is not the case for 16 bit Windows but we
            // consider that we don't support these. If we wanted to support case-insensitive File systems we would
            // need to escape all capital or lower-case letters.

            // Encode special non ASCII characters and handle "." and "*" in a special way since they're ASCII char
            // (and thus not encoded by URLEncoder.encode()) but have some special meanings in some File systems:
            // - On Unix a file starting with dot is a hidden file. On Windows the part after the last dot represents
            //   the file extension and a file cannot end with a dot
            // - On Windows, the star is a wildcard.
            // See https://en.wikipedia.org/wiki/Filename#Reserved_characters_and_words and
            // http://stackoverflow.com/questions/2304221/what-character-sequence-should-i-not-allow-in-a-filename
            representation.append(
                URLEncoder.encode(currentReference.getName(), "UTF-8").replace(".", "%2E").replace("*", "%2A"));
        } catch (UnsupportedEncodingException e) {
            // This will never happen, UTF-8 is always available
            throw new RuntimeException("UTF-8 encoding is not present on the system!", e);
        }
    }
}
