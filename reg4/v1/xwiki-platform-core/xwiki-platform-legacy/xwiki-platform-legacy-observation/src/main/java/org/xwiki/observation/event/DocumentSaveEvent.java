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
package org.xwiki.observation.event;

import org.xwiki.observation.event.filter.EventFilter;

/**
 * An event triggered when a document is saved for the first time (a new document is created).
 * 
 * @version $Id: 5b34ef1d811f0f7afd5d5c28e6a8c87b060ec159 $
 * @deprecated since 2.7RC1 use {@link org.xwiki.bridge.event.DocumentCreatedEvent} instead
 */
@Deprecated
public class DocumentSaveEvent extends AbstractDocumentEvent
{
    /**
     * The version identifier for this Serializable class. Increment only if the <i>serialized</i> form of the class
     * changes.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor initializing the event filter with an
     * {@link org.xwiki.observation.event.filter.AlwaysMatchingEventFilter}, meaning that this event will match any
     * other document save event.
     */
    public DocumentSaveEvent()
    {
        super();
    }

    /**
     * Constructor initializing the event filter with a {@link org.xwiki.observation.event.filter.FixedNameEventFilter},
     * meaning that this event will match only save events affecting the same document.
     * 
     * @param documentName the name of the saved document to match
     */
    public DocumentSaveEvent(String documentName)
    {
        super(documentName);
    }

    /**
     * Constructor using a custom {@link EventFilter}.
     * 
     * @param eventFilter the filter to use for matching events
     */
    public DocumentSaveEvent(EventFilter eventFilter)
    {
        super(eventFilter);
    }
}
