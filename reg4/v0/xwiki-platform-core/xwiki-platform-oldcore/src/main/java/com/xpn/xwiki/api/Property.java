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
package com.xpn.xwiki.api;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.objects.BaseProperty;

/**
 * Property is a single attribute of an XWiki {@link com.xpn.xwiki.api.Object}.
 *
 * @version $Id: d257d9ba675a2ef56c46b43be6b598df15427ae6 $
 */
public class Property extends Element
{
    /**
     * The Constructor.
     *
     * @param property the internal {@link com.xpn.xwiki.objects.BaseProperty} to wrap.
     * @param context the XWikiContext which may be used to get information about the current request.
     */
    public Property(BaseProperty property, XWikiContext context)
    {
        super(property, context);
    }

    /**
     * @return the internal {@link com.xpn.xwiki.objects.BaseProperty} which this Property wraps.
     */
    protected BaseProperty getBaseProperty()
    {
        return (BaseProperty) this.element;
    }

    /**
     * @return the internal {@link com.xpn.xwiki.objects.BaseProperty} which this Property wraps.
     */
    public BaseProperty getProperty()
    {
        if (hasProgrammingRights()) {
            return (BaseProperty) this.element;
        } else {
            return null;
        }
    }

    /**
     * @return the definition of the property
     * @since 8.3M1
     */
    public PropertyClass getPropertyClass()
    {
        BaseProperty baseProperty = getBaseProperty();

        com.xpn.xwiki.objects.classes.PropertyClass propertyClass = baseProperty.getPropertyClass(getXWikiContext());

        if (propertyClass != null) {
            return new PropertyClass(propertyClass, getXWikiContext());
        }

        return null;
    }

    /**
     * @return the actual value of the property, as a String, Number or List.
     */
    public java.lang.Object getValue()
    {
        BaseProperty baseProperty = getBaseProperty();

        com.xpn.xwiki.objects.classes.PropertyClass propertyClass = baseProperty.getPropertyClass(getXWikiContext());

        // Avoid dumping password hashes if the user does not have programming rights. This is done only at the
        // API level, so that java code using core classes will still have access, regardless or rights.
        if (propertyClass != null && "Password".equals(propertyClass.getClassType())) {
            if (!getXWikiContext().getWiki().getRightService().hasProgrammingRights(getXWikiContext())) {
                return null;
            }
        }

        return getBaseProperty().getValue();
    }
}
