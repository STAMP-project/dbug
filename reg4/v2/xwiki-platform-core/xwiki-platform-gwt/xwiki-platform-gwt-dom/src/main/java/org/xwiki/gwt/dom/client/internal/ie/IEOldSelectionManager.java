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
package org.xwiki.gwt.dom.client.internal.ie;

import java.util.HashMap;
import java.util.Map;

import org.xwiki.gwt.dom.client.Document;
import org.xwiki.gwt.dom.client.Selection;
import org.xwiki.gwt.dom.client.SelectionManager;

/**
 * {@link SelectionManager} implementation for older versions of Internet Explorer (6, 7 and 8).
 * 
 * @version $Id: b4a111b263312a9544fef7ecac62ddf02f5e30f1 $
 */
public final class IEOldSelectionManager implements SelectionManager
{
    /**
     * The selection cache.
     */
    private final Map<Document, Selection> cache = new HashMap<Document, Selection>();

    @Override
    public Selection getSelection(Document document)
    {
        Selection selection = cache.get(document);
        if (selection == null) {
            selection = new IEOldSelectionCacheProxy(NativeSelection.getInstance(document));
            cache.put(document, selection);
            invalidateCacheOnUnload(document);
        }
        return selection;
    }

    /**
     * Deletes from the cache the selection object associated with the given document before its window unloads.
     * 
     * @param document a DOM document that has its selection object cached
     */
    private native void invalidateCacheOnUnload(Document document)
    /*-{
        var self = this;
        document.parentWindow.attachEvent('onunload', function() {
            document.parentWindow.detachEvent('onunload', arguments.callee);
            self.@org.xwiki.gwt.dom.client.internal.ie.IEOldSelectionManager::invalidateCache(Lorg/xwiki/gwt/dom/client/Document;)(document);
        });
    }-*/;

    /**
     * Deletes from the cache the selection object associated with the given document.
     * 
     * @param document a DOM document that has its selection object cached
     */
    private void invalidateCache(Document document)
    {
        ((IEOldSelectionCacheProxy) cache.remove(document)).release();
    }
}
