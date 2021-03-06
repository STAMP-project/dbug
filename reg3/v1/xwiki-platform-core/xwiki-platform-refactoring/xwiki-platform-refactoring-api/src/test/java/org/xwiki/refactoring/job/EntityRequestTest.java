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
package org.xwiki.refactoring.job;

import java.util.Collections;

import org.junit.Test;
import org.xwiki.model.reference.DocumentReference;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link EntityRequest}.
 * 
 * @version $Id: abcfbcf39003f608a36aa3d220d01ccc73e368d8 $
 */
public class EntityRequestTest
{
    @Test
    public void getSetEntityParameters()
    {
        DocumentReference documentReference = new DocumentReference("wiki", "Space", "Page");
        EntityRequest entityRequest = new EntityRequest();

        assertTrue(entityRequest.getEntityParameters(documentReference).isEmpty());

        entityRequest.setEntityParameters(documentReference, Collections.singletonMap("foo", "bar"));
        assertEquals("bar", entityRequest.getEntityParameters(documentReference).get("foo"));
    }
}
