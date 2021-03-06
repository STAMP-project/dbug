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
package org.xwiki.wiki.rest;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.xwiki.rest.XWikiRestException;
import org.xwiki.rest.model.jaxb.Wiki;

/**
 * REST API to create wikis.
 *
 * @since 5.4RC1
 * @version $Id: a2e2d630329055ef936fc38b5e09e2f42ec5e358 $
 */
@Path("/wikimanager")
public interface WikiManagerREST
{
    /**
     * Create a wiki.
     *
     * @param template the wiki template to be used for initializing the new wiki. Can be null.
     * @param wiki the wiki model object (see {@link org.xwiki.rest.model.jaxb.Wiki})containing information about the
     * wiki to be created.
     * @return a response containing a description of the created wiki (see {@link org.xwiki.rest.model.jaxb.Wiki})
     * @throws XWikiRestException is there is an error while creating the wiki.
     */
    Response createWiki(@QueryParam("template") String template, Wiki wiki) throws XWikiRestException;
}
