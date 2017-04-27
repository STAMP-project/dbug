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

package com.xpn.xwiki.plugin.tag;

/**
 * The result of a {@link TagPlugin} operation.
 * 
 * @version $Id: bfda78a7e5d57fc55597ab24ebd385e2c1be6a9c $
 */
public enum TagOperationResult
{
    /** The operation was successful, and data changes occurred. */
    OK,

    /** The operation did not change any data. */
    NO_EFFECT,

    /** The current user does not have permission to perform the operation. */
    NOT_ALLOWED,

    /** The operation failed with an exception. */
    FAILED
}
