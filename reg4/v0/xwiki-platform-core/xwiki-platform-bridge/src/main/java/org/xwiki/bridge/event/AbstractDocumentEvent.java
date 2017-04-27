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
package org.xwiki.bridge.event;

import org.xwiki.model.internal.reference.DefaultStringEntityReferenceSerializer;
import org.xwiki.model.internal.reference.DefaultSymbolScheme;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.observation.event.AbstractCancelableEvent;
import org.xwiki.observation.event.filter.EventFilter;

/**
 * Base class for all document related {@link org.xwiki.observation.event.Event}.
 * 
 * @version $Id: bd0d16eb181e1e62f44d28082f1ecb90ee4bd4c3 $
 * @since 2.7RC1
 */
public abstract class AbstractDocumentEvent extends AbstractCancelableEvent
{
    /**
     * The version identifier for this Serializable class. Increment only if the <i>serialized</i> form of the class
     * changes.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Used to serialize document name.
     */
    private static final DefaultStringEntityReferenceSerializer SERIALIZER =
        new DefaultStringEntityReferenceSerializer(new DefaultSymbolScheme());

    /**
     * This event will match any other document event of the same type.
     */
    public AbstractDocumentEvent()
    {
        super();
    }

    /**
     * This event will match only events of the same type affecting the same document.
     * 
     * @param documentReference the reference of the document relater to this event
     */
    public AbstractDocumentEvent(DocumentReference documentReference)
    {
        super(SERIALIZER.serialize(documentReference));
    }

    /**
     * Constructor using a custom {@link EventFilter}.
     * 
     * @param eventFilter the filter to use for matching events
     */
    public AbstractDocumentEvent(EventFilter eventFilter)
    {
        super(eventFilter);
    }
}
