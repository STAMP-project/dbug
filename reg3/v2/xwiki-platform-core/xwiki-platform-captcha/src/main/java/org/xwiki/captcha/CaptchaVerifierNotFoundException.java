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
package org.xwiki.captcha;

/**
 * Thrown when the programmer tries to get a CaptchaVerifier which doesn't exist.
 *
 * @version $Id: 89684c5b3a631cb1be62774601c691121a326155 $
 * @since 2.2M2
 */
public class CaptchaVerifierNotFoundException extends Exception
{
    /**
     * The Constructor.
     *
     * @param message The message to give for the error.
     */
    public CaptchaVerifierNotFoundException(String message)
    {
        super(message);
    }
}
