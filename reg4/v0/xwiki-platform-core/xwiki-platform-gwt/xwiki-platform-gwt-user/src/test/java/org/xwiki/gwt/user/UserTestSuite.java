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
package org.xwiki.gwt.user;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.xwiki.gwt.user.client.StringUtilsTest;
import org.xwiki.gwt.user.client.TimerTest;
import org.xwiki.gwt.user.client.ui.rta.RichTextAreaTest;
import org.xwiki.gwt.user.client.ui.rta.SelectionPreserverTest;
import org.xwiki.gwt.user.client.ui.rta.cmd.CommandListenerCollectionTest;
import org.xwiki.gwt.user.client.ui.rta.cmd.internal.DefaultCommandManagerTest;
import org.xwiki.gwt.user.client.ui.rta.cmd.internal.DeleteExecutableTest;
import org.xwiki.gwt.user.client.ui.rta.cmd.internal.EnableExecutableTest;
import org.xwiki.gwt.user.client.ui.rta.cmd.internal.InsertHTMLExecutableTest;
import org.xwiki.gwt.user.client.ui.rta.cmd.internal.StyleExecutableTest;

import com.google.gwt.junit.tools.GWTTestSuite;

/**
 * All the User tests to be run. We're using a test suite because it decreases the overall testing time. GWTTestCase
 * derived tests are slow. This is because the JUnitShell has to load the module for each test (create the shell, hook
 * into it, etc). GWTTestSuite mitigates this by grouping all the tests that are for the same module (those that return
 * the same value for getModuleName) together and running them via the same shell instance.
 * <p>
 * As described in GWT issue 2486, GWTTestSuite is not a translatable class and in order to prevent reports of harmless
 * errors we moved our test suite class out of module's source path.
 * <p>
 * See http://code.google.com/p/google-web-toolkit/issues/detail?id=2486.
 * 
 * @version $Id: d27f0ad8e3ee2f11d98d409d62819e97749090eb $
 */
public class UserTestSuite extends GWTTestSuite
{
    /**
     * @return the suite of all the User tests to be run
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite("XWiki GWT-User Unit Tests");

        suite.addTestSuite(TimerTest.class);
        suite.addTestSuite(StringUtilsTest.class);
        suite.addTestSuite(CommandListenerCollectionTest.class);
        suite.addTestSuite(DefaultCommandManagerTest.class);
        suite.addTestSuite(RichTextAreaTest.class);
        suite.addTestSuite(SelectionPreserverTest.class);
        suite.addTestSuite(DeleteExecutableTest.class);
        suite.addTestSuite(InsertHTMLExecutableTest.class);
        suite.addTestSuite(StyleExecutableTest.class);
        suite.addTestSuite(EnableExecutableTest.class);

        return suite;
    }
}
