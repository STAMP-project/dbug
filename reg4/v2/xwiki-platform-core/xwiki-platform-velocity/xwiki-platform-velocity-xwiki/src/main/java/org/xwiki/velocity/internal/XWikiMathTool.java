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
package org.xwiki.velocity.internal;

import java.util.Locale;

import javax.inject.Provider;

import org.apache.velocity.tools.generic.MathTool;

import com.xpn.xwiki.XWikiContext;

/**
 * Extend the Velocity Tool-provided MathTool to use the current context locale.
 *
 * @version $Id: 16cc41aa22e09499bc9ca133d5abb11793a60e52 $
 * @since 8.2RC1
 */
public class XWikiMathTool extends MathTool
{
    private Provider<XWikiContext> contextProvider;

    /**
     * @param contextProvider the provider to get the {@link XWikiContext} dynamically at runtime
     */
    public XWikiMathTool(Provider<XWikiContext> contextProvider)
    {
        this.contextProvider = contextProvider;
    }

    /**
     * @return the current locale from the XWiki context
     */
    @Override
    public Locale getLocale()
    {
        return this.contextProvider.get().getLocale();
    }
}
