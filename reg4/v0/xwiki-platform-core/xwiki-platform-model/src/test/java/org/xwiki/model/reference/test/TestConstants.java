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
package org.xwiki.model.reference.test;

import org.xwiki.model.EntityType;
import org.xwiki.model.reference.EntityReference;

/**
 * Constants used in many unit tests.
 * 
 * @version $Id: 2c430c6d5c8aee0caa305927f9a3f9a93559080f $
 * @since 7.2M1
 */
public interface TestConstants
{
    String DEFAULT_WIKI = "defwiki";

    String DEFAULT_SPACE = "defspace";

    String DEFAULT_PAGE = "defpage";

    String DEFAULT_ATTACHMENT = "deffilename";

    String DEFAULT_OBJECT = "defobject";

    String DEFAULT_OBJECT_PROPERTY = "defobjproperty";

    String DEFAULT_CLASS_PROPERTY = "defclassproperty";

    EntityReference DEFAULT_WIKI_REFERENCE = new EntityReference(DEFAULT_WIKI, EntityType.WIKI);

    EntityReference DEFAULT_SPACE_REFERENCE = new EntityReference(DEFAULT_SPACE, EntityType.SPACE);

    EntityReference DEFAULT_PAGE_REFERENCE = new EntityReference(DEFAULT_PAGE, EntityType.DOCUMENT);

    EntityReference DEFAULT_ATTACHMENT_REFERENCE = new EntityReference(DEFAULT_ATTACHMENT, EntityType.ATTACHMENT);

    EntityReference DEFAULT_OBJECT_REFERENCE = new EntityReference(DEFAULT_OBJECT, EntityType.OBJECT);

    EntityReference DEFAULT_OBJECT_PROPERTY_REFERENCE = new EntityReference(DEFAULT_OBJECT_PROPERTY,
        EntityType.OBJECT_PROPERTY);

    EntityReference DEFAULT_CLASS_PROPERTY_REFERENCE = new EntityReference(DEFAULT_CLASS_PROPERTY,
        EntityType.CLASS_PROPERTY);

}
