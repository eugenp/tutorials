/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.jdbc.test;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

import org.apache.tomcat.jdbc.pool.interceptor.TestInterceptor;

public class TestInterceptorShortName extends DefaultTestCase {

    @Test
    public void testShortInterceptor() throws Exception {
        this.datasource = this.createDefaultDataSource();
        this.datasource.setJdbcInterceptors("TestInterceptor");
        this.datasource.setUseDisposableConnectionFacade(false);
        this.datasource.setMaxActive(1);
        this.datasource.createPool();
        Assert.assertEquals("Only one interceptor should have been called setProperties[1]",1,TestInterceptor.instancecount.get());
        TestInterceptor.instancecount.set(0);
        Connection con = this.datasource.getConnection();
        Assert.assertTrue("Pool should have been started.",TestInterceptor.poolstarted);
        Assert.assertEquals("Only one interceptor should have been called setProperties[2]",1,TestInterceptor.instancecount.get());
        con.close();
        this.datasource.close();
        Assert.assertTrue("Pool should have been closed.",TestInterceptor.poolclosed);
    }
}
