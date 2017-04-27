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
package com.xpn.xwiki.internal.script;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.configuration.ConfigurationSource;
import org.xwiki.container.Container;
import org.xwiki.context.Execution;
import org.xwiki.context.ExecutionContext;
import org.xwiki.job.event.status.JobProgress;
import org.xwiki.script.service.ScriptService;

import com.xpn.xwiki.web.XWikiAction;

/**
 * Various internal debug tools.
 * 
 * @version $Id: 479422d5b986359569f6835627f03e2c64555225 $
 * @since 7.1M2
 */
@Component
@Singleton
@Named("debug")
public class DebugScriptService implements ScriptService
{
    @Inject
    private Execution execution;

    @Inject
    @Named("xwikiproperties")
    private ConfigurationSource properties;

    @Inject
    private Container container;

    /**
     * @return is debug enabled in the current execution context
     */
    public boolean isEnabled()
    {
        return getActionProgress() != null;
    }

    /**
     * @return the detailed progress of the current action
     */
    public JobProgress getActionProgress()
    {
        ExecutionContext econtext = this.execution.getContext();

        if (econtext != null) {
            return (JobProgress) econtext.getProperty(XWikiAction.ACTION_PROGRESS);
        }

        return null;
    }

    /**
     * @return true if resources should be minified when possible
     * @since 7.1RC1
     */
    public boolean isMinify()
    {
        String minifyString = (String) this.container.getRequest().getProperty("minify");
        if (minifyString != null) {
            return Boolean.valueOf(minifyString);
        }

        return this.properties.getProperty("debug.minify", true);
    }
}
