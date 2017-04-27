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
package org.xwiki.url;

import org.junit.Rule;
import org.junit.Test;
import org.xwiki.context.ExecutionContext;
import org.xwiki.test.mockito.MockitoComponentMockingRule;
import org.xwiki.url.internal.URLExecutionContextInitializer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link URLExecutionContextInitializer}.
 *
 * @version $Id: 647653145c63644ab483ed1207ee0aa988a6c568 $
 * @since 7.2M1
 */
public class URLExecutionContextInitializerTest
{
    @Rule
    public MockitoComponentMockingRule<URLExecutionContextInitializer> mocker =
        new MockitoComponentMockingRule<>(URLExecutionContextInitializer.class);

    @Test
    public void initializeWhenFormatIdNotInContext() throws Exception
    {
        ExecutionContext ec = new ExecutionContext();
        URLConfiguration configuration = this.mocker.getInstance(URLConfiguration.class);
        when(configuration.getURLFormatId()).thenReturn("test");

        this.mocker.getComponentUnderTest().initialize(ec);

        assertEquals("test", ec.getProperty("urlscheme"));
    }

    @Test
    public void initializeWhenFormatIdAlreadyInContext() throws Exception
    {
        ExecutionContext ec = new ExecutionContext();
        ec.setProperty("urlscheme", "existing");
        this.mocker.getComponentUnderTest().initialize(ec);

        assertEquals("existing", ec.getProperty("urlscheme"));
    }
}
