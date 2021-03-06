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
package org.xwiki.test.integration;

import org.apache.commons.exec.LogOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs the stdout and stderr streams of an external process started using Commons Exec.
 *
 * @version $Id: 8cee40d30b40eb4c81d0a5723ccccb36fe13266a $
 * @since 4.3.1
 */
public class XWikiLogOutputStream extends LogOutputStream
{
    /**
     * Represents the stdout stream.
     */
    public static final int STDOUT = 0;

    /**
     * Represents the stderr stream.
     */
    public static final int STDERR = 1;

    /**
     * The logger to use logs generated by the XWiki process.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(XWikiLogOutputStream.class);

    /**
     * @param level the level under which to log (if the actual level is less than this value it'll get logged)
     */
    public XWikiLogOutputStream(int level)
    {
        super(level);
    }

    @Override
    protected void processLine(String line, int level)
    {
        if (level == STDOUT) {
            LOGGER.info(line);
        } else {
            LOGGER.error(line);
        }
    }
}
