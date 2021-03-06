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
package org.xwiki.watchlist.internal.documents;

import javax.annotation.Priority;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;

/**
 * Document initializer for {@value #DOCUMENT_FULL_NAME}.
 * 
 * @version $Id: 79c67914f1a51cdb8858f9010cf1dedb642aeba5 $
 */
@Component
@Named(DailyWatchListJobDocumentInitializer.DOCUMENT_FULL_NAME)
@Singleton
@Priority(2000)
public class DailyWatchListJobDocumentInitializer extends AbstractWatchListJobDocumentInitializer
{
    /**
     * Name of the document inside the Scheduler space.
     */
    public static final String DOCUMENT_NAME = "WatchListDailyNotifier";

    /**
     * Full name of the document.
     */
    public static final String DOCUMENT_FULL_NAME = SCHEDULER_SPACE + "." + DOCUMENT_NAME;

    /**
     * Default constructor.
     */
    public DailyWatchListJobDocumentInitializer()
    {
        super(SCHEDULER_SPACE, DOCUMENT_NAME);
    }

    @Override
    protected String getCron()
    {
        return "0 0 0 * * ?";
    }

    @Override
    protected String getJobName()
    {
        return "WatchList daily notifier";
    }

    @Override
    protected String getDocumentTitleTranslationKey()
    {
        return "watchlist.job.daily";
    }

    @Override
    protected boolean isMainWikiOnly()
    {
        // Main wiki document.
        return Boolean.TRUE;
    }
}
