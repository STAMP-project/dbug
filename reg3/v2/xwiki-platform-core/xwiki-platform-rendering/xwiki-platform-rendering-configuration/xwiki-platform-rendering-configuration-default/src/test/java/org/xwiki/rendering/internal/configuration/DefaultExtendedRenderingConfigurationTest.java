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
package org.xwiki.rendering.internal.configuration;

import java.util.Arrays;
import java.util.List;

import javax.inject.Provider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.component.util.DefaultParameterizedType;
import org.xwiki.configuration.ConfigurationSource;
import org.xwiki.rendering.parser.Parser;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.syntax.SyntaxFactory;
import org.xwiki.rendering.syntax.SyntaxType;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

import com.xpn.xwiki.CoreConfiguration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link org.xwiki.rendering.internal.configuration.DefaultExtendedRenderingConfiguration}.
 * 
 * @version $Id: 0a1bf38afda4da916b8f2325175d4001b292713d $
 * @since 8.2M1
 */
public class DefaultExtendedRenderingConfigurationTest
{
    @Rule
    public MockitoComponentMockingRule<DefaultExtendedRenderingConfiguration> mocker =
        new MockitoComponentMockingRule<>(DefaultExtendedRenderingConfiguration.class);

    @Before
    public void configure() throws Exception
    {
        Provider<ComponentManager> contextComponentManagerProvider =
            this.mocker.registerMockComponent(
                new DefaultParameterizedType(null, Provider.class, ComponentManager.class), "context");
        when(contextComponentManagerProvider.get()).thenReturn(this.mocker);
    }

    @Test
    public void getImageWidthLimit() throws Exception
    {
        ConfigurationSource source = this.mocker.getInstance(ConfigurationSource.class);
        when(source.getProperty("rendering.imageWidthLimit", -1)).thenReturn(100);

        assertEquals(100, this.mocker.getComponentUnderTest().getImageWidthLimit());
    }

    @Test
    public void getImageHeightLimit() throws Exception
    {
        ConfigurationSource source = this.mocker.getInstance(ConfigurationSource.class);
        when(source.getProperty("rendering.imageHeightLimit", -1)).thenReturn(150);

        assertEquals(150, this.mocker.getComponentUnderTest().getImageHeightLimit());
    }

    @Test
    public void isImageDimensionsIncludedInImageURL() throws Exception
    {
        ConfigurationSource source = this.mocker.getInstance(ConfigurationSource.class);
        when(source.getProperty("rendering.imageDimensionsIncludedInImageURL", true)).thenReturn(false);

        assertFalse(this.mocker.getComponentUnderTest().isImageDimensionsIncludedInImageURL());
    }

    @Test
    public void getConfiguredAndDisabledSyntaxesWhenNoConfigXObjectAndExistingXWikiCfgProperty() throws Exception
    {
        ConfigurationSource renderingSource = this.mocker.getInstance(ConfigurationSource.class, "rendering");
        when(renderingSource.getProperty("disabledSyntaxes")).thenReturn(null);

        ConfigurationSource xwikiCfgSource = this.mocker.getInstance(ConfigurationSource.class, "xwikicfg");
        when(xwikiCfgSource.getProperty("xwiki.rendering.syntaxes", List.class)).thenReturn(
            Arrays.asList("xwiki/2.0", "xwiki/2.1"));

        // Register some Syntaxes for the test

        SyntaxFactory syntaxFactory = this.mocker.getInstance(SyntaxFactory.class);
        Syntax xwikiSyntax20 = new Syntax(new SyntaxType("xwiki", "XWiki"), "2.0");
        when(syntaxFactory.createSyntaxFromIdString("xwiki/2.0")).thenReturn(xwikiSyntax20);
        Syntax xwikiSyntax21 = new Syntax(new SyntaxType("xwiki", "XWiki"), "2.1");
        when(syntaxFactory.createSyntaxFromIdString("xwiki/2.1")).thenReturn(xwikiSyntax21);
        Parser xwikiSyntax20Parser = this.mocker.registerMockComponent(Parser.class, "xwiki/2.0");
        when(xwikiSyntax20Parser.getSyntax()).thenReturn(xwikiSyntax20);
        Parser xwikiSyntax21Parser = this.mocker.registerMockComponent(Parser.class, "xwiki/2.1");
        when(xwikiSyntax21Parser.getSyntax()).thenReturn(xwikiSyntax21);

        Parser syntax1Parser = this.mocker.registerMockComponent(Parser.class, "syntax1/1.0");
        Syntax syntax1 = new Syntax(new SyntaxType("syntax1", "Syntax 1"), "1.0");
        when(syntax1Parser.getSyntax()).thenReturn(syntax1);
        Parser syntax2Parser = this.mocker.registerMockComponent(Parser.class, "syntax2/1.0");
        Syntax syntax2 = new Syntax(new SyntaxType("syntax2", "Syntax 2"), "1.0");
        when(syntax2Parser.getSyntax()).thenReturn(syntax2);

        List<Syntax> disabledSyntaxes = this.mocker.getComponentUnderTest().getDisabledSyntaxes();
        assertEquals(2, disabledSyntaxes.size());
        assertTrue(disabledSyntaxes.contains(syntax1));
        assertTrue(disabledSyntaxes.contains(syntax2));

        List<Syntax> configuredSyntaxes = this.mocker.getComponentUnderTest().getConfiguredSyntaxes();
        assertEquals(2, configuredSyntaxes.size());
        assertTrue(configuredSyntaxes.contains(xwikiSyntax20));
        assertTrue(configuredSyntaxes.contains(xwikiSyntax21));
    }

    @Test
    public void getConfiguredAndDisabledSyntaxesWhenNoConfigXObjectAndNoXWikiCfgProperty() throws Exception
    {
        ConfigurationSource renderingSource = this.mocker.getInstance(ConfigurationSource.class, "rendering");
        when(renderingSource.getProperty("disabledSyntaxes")).thenReturn(null);

        ConfigurationSource xwikiCfgSource = this.mocker.getInstance(ConfigurationSource.class, "xwikicfg");
        when(xwikiCfgSource.getProperty("xwiki.rendering.syntaxes", List.class)).thenReturn(null);

        CoreConfiguration coreConfiguration = this.mocker.getInstance(CoreConfiguration.class);
        Syntax defaultSyntax = new Syntax(new SyntaxType("xwiki", "XWiki"), "2.1");
        when(coreConfiguration.getDefaultDocumentSyntax()).thenReturn(defaultSyntax);

        // Register some Syntaxes for the test

        Parser defaultSyntaxParser = this.mocker.registerMockComponent(Parser.class, "xwiki/2.1");
        when(defaultSyntaxParser.getSyntax()).thenReturn(defaultSyntax);

        Parser syntax1Parser = this.mocker.registerMockComponent(Parser.class, "syntax1/1.0");
        Syntax syntax1 = new Syntax(new SyntaxType("syntax1", "Syntax 1"), "1.0");
        when(syntax1Parser.getSyntax()).thenReturn(syntax1);
        Parser syntax2Parser = this.mocker.registerMockComponent(Parser.class, "syntax2/1.0");
        Syntax syntax2 = new Syntax(new SyntaxType("syntax2", "Syntax 2"), "1.0");
        when(syntax2Parser.getSyntax()).thenReturn(syntax2);

        List<Syntax> disabledSyntaxes = this.mocker.getComponentUnderTest().getDisabledSyntaxes();
        assertEquals(2, disabledSyntaxes.size());
        assertTrue(disabledSyntaxes.contains(syntax1));
        assertTrue(disabledSyntaxes.contains(syntax2));

        List<Syntax> configuredSyntaxes = this.mocker.getComponentUnderTest().getConfiguredSyntaxes();
        assertEquals(1, configuredSyntaxes.size());
        assertTrue(configuredSyntaxes.contains(defaultSyntax));
    }

    @Test
    public void getConfiguredAndDisabledSyntaxesWhenConfigXObjectExists() throws Exception
    {
        ConfigurationSource renderingSource = this.mocker.getInstance(ConfigurationSource.class, "rendering");
        when(renderingSource.getProperty("disabledSyntaxes")).thenReturn(
            Arrays.asList("syntax1/1.0", "syntax2/1.0"));

        // Register some Syntaxes for the test

        Parser syntax1Parser = this.mocker.registerMockComponent(Parser.class, "syntax1/1.0");
        Syntax syntax1 = new Syntax(new SyntaxType("syntax1", "Syntax 1"), "1.0");
        when(syntax1Parser.getSyntax()).thenReturn(syntax1);
        Parser syntax2Parser = this.mocker.registerMockComponent(Parser.class, "syntax2/1.0");
        Syntax syntax2 = new Syntax(new SyntaxType("syntax2", "Syntax 2"), "1.0");
        when(syntax2Parser.getSyntax()).thenReturn(syntax2);

        SyntaxFactory syntaxFactory = this.mocker.getInstance(SyntaxFactory.class);
        when(syntaxFactory.createSyntaxFromIdString("syntax1/1.0")).thenReturn(syntax1);
        when(syntaxFactory.createSyntaxFromIdString("syntax2/1.0")).thenReturn(syntax2);

        Parser xwikiSyntax20Parser = this.mocker.registerMockComponent(Parser.class, "xwiki/2.0");
        Syntax xwikiSyntax20 = new Syntax(new SyntaxType("xwiki", "XWiki"), "2.0");
        when(xwikiSyntax20Parser.getSyntax()).thenReturn(xwikiSyntax20);

        List<Syntax> disabledSyntaxes = this.mocker.getComponentUnderTest().getDisabledSyntaxes();
        assertEquals(2, disabledSyntaxes.size());
        assertTrue(disabledSyntaxes.contains(syntax1));
        assertTrue(disabledSyntaxes.contains(syntax2));

        List<Syntax> configuredSyntaxes = this.mocker.getComponentUnderTest().getConfiguredSyntaxes();
        assertEquals(1, configuredSyntaxes.size());
        assertTrue(configuredSyntaxes.contains(xwikiSyntax20));
    }
}
