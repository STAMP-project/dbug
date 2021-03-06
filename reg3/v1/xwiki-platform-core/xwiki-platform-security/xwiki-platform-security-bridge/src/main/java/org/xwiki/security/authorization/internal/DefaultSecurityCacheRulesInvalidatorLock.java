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
package org.xwiki.security.authorization.internal;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;

/**
 * The component is used as a bridge between the {@link org.xwiki.observation.EventListener} and the
 * {@link org.xwiki.security.authorization.cache.SecurityCacheRulesInvalidator}.
 * 
 * @version $Id: 29eb4be512d2c23a70a0d9d880648f9d57d0af12 $
 * @since 4.0M2
 */
@Component(roles = ReadWriteLock.class)
@Named(DefaultSecurityCacheRulesInvalidator.NAME)
@Singleton
public class DefaultSecurityCacheRulesInvalidatorLock implements ReadWriteLock
{
    /**
     * Fair read-write lock to suspend the delivery of cache updates while there are loads in progress.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    @Override
    public Lock readLock()
    {
        return this.readWriteLock.readLock();
    }

    @Override
    public Lock writeLock()
    {
        return this.readWriteLock.writeLock();
    }
}
