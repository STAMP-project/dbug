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
package org.xwiki.filter.xar.internal;

/**
 * @version $Id: f5275b88e26cb62ae862ea158cfc5e0a45edd937 $
 * @since 6.2M1
 */
public final class XARFilterUtils
{
    /**
     * @version $Id: f5275b88e26cb62ae862ea158cfc5e0a45edd937 $
     * @since 6.2M1
     */
    public static class EventParameter
    {
        public String name;

        public Class<?> type;

        public EventParameter(String name, Class<?> type)
        {
            this.name = name;
            this.type = type;
        }

        public EventParameter(String name)
        {
            this(name, String.class);
        }
    }

    /**
     * @since 7.2M1
     */
    public static final String ROLEHINT_11 = "xwiki+xar/1.1";

    /**
     * @since 7.2M1
     */
    public static final String ROLEHINT_12 = "xwiki+xar/1.2";

    /**
     * @since 7.2M1
     */
    public static final String ROLEHINT_CURRENT = ROLEHINT_12;

    /**
     * @since 6.2M1
     * @deprecated 7.2M1
     */
    @Deprecated
    public static final String ROLEHINT = ROLEHINT_11;
}
