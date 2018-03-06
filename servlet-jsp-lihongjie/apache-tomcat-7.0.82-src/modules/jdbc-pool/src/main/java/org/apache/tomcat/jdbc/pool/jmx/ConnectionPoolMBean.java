/* Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tomcat.jdbc.pool.jmx;

import org.apache.tomcat.jdbc.pool.PoolConfiguration;

public interface ConnectionPoolMBean extends PoolConfiguration  {

    //=================================================================
    //       POOL STATS
    //=================================================================

    public int getSize();

    public int getIdle();

    public int getActive();

    public int getNumIdle();

    public int getNumActive();

    public int getWaitCount();

    public long getBorrowedCount();

    public long getReturnedCount();

    public long getCreatedCount();

    public long getReleasedCount();

    public long getReconnectedCount();

    public long getRemoveAbandonedCount();

    public long getReleasedIdleCount();

    //=================================================================
    //       POOL OPERATIONS
    //=================================================================
    public void checkIdle();

    public void checkAbandoned();

    public void testIdle();

    /**
     * Purges all connections in the pool.
     * For connections currently in use, these connections will be
     * purged when returned on the pool. This call also
     * purges connections that are idle and in the pool
     * To only purge used/active connections see {@link #purgeOnReturn()}
     */
    public void purge();

    /**
     * Purges connections when they are returned from the pool.
     * This call does not purge idle connections until they are used.
     * To purge idle connections see {@link #purge()}
     */
    public void purgeOnReturn();

    /**
     * reset the statistics of this pool.
     */
    public void resetStats();

    //=================================================================
    //       POOL NOTIFICATIONS
    //=================================================================


}
