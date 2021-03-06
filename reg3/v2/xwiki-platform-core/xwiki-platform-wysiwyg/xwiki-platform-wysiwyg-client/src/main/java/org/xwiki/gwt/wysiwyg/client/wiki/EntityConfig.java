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
package org.xwiki.gwt.wysiwyg.client.wiki;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Base class for entity configuration classes.
 * 
 * @version $Id: b998e8e784ec3d09fa204fa8d7e697bcba63a728 $
 */
public class EntityConfig implements IsSerializable
{
    /**
     * The string serialization of the entity reference.
     */
    private String reference;

    /**
     * The entity URL.
     */
    private String url;

    /**
     * @return the string serialization of the entity reference
     */
    public String getReference()
    {
        return reference;
    }

    /**
     * Sets the string serialization of the entity reference.
     * 
     * @param reference the new string serialization of the entity reference
     */
    public void setReference(String reference)
    {
        this.reference = reference;
    }

    /**
     * @return the entity URL
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * Sets the entity URL.
     * 
     * @param url the new entity URL
     */
    public void setUrl(String url)
    {
        this.url = url;
    }
}
