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
package org.xwiki.test.linkchecker.internal;

import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.internal.transformation.linkchecker.HTTPChecker;

/**
 * Stubbed HTTP Checker implementation for the Link Checker functional UI tests in order for the test to not
 * depend on the environment (Internet Connexion and availability of external sites).
 *
 * @version $Id: 226f405ee8ad45277726b20c1da389b01fc2b220 $
 * @since 3.4M1
 */
@Component
@Singleton
public class StubHTTPChecker implements HTTPChecker
{
    @Override
    public int check(String url)
    {
        if (url.equals("http://doesntexist")) {
            return 404;
        } else {
            return 200;
        }
    }
}
