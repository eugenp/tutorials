/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
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
package org.apache.tomcat.jdbc.test;

import org.junit.Before;
import org.junit.Test;

import org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;

public class Bug50571 extends DefaultTestCase{

    @Before
    public void setUp() throws Exception {
        this.datasource.setUrl("jdbc:h2:~/.h2/test;QUERY_TIMEOUT=0;DB_CLOSE_ON_EXIT=FALSE");
        this.datasource.setJdbcInterceptors(ConnectionState.class.getName());
        this.datasource.setDefaultTransactionIsolation(-55);
        this.datasource.setInitialSize(1);
    }

    @Test
    public void testBug50571() throws Exception {
        this.datasource.getConnection().close();
    }
}
