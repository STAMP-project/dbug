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
package org.xwiki.validator;

import java.io.InputStream;
import java.util.List;

/**
 * Common Validator interface.
 * 
 * @version $Id: 162da489475f284b6ee6858a60a50a82f0d66ab8 $
 */
public interface Validator
{
    /**
     * Set document to validate.
     * 
     * @param document document to validate
     */
    void setDocument(InputStream document);
    
    /**
     * Run validation.
     * 
     * @return a list of validation errors
     */
    List<ValidationError> validate();
    
    /**
     * Get the list of validation errors.
     * 
     * @return a list of validation errors.
     */
    List<ValidationError> getErrors();
    
    /**
     * Clear the list of validation errors.
     */
    void clear();
    
    /**
     * @return the name of the validator
     */
    String getName();
}
