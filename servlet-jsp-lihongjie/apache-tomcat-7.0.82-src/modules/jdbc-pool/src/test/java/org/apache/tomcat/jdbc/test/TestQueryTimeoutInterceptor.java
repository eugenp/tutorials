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
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Test;

import org.apache.tomcat.jdbc.pool.interceptor.QueryTimeoutInterceptor;
import org.apache.tomcat.jdbc.test.driver.Driver;

public class TestQueryTimeoutInterceptor extends DefaultTestCase {

    @Test
    public void testTimeout() throws Exception {
        int timeout = 10;
        int withoutuser =10;
        int withuser = withoutuser;
        this.datasource.setMaxActive(withuser+withoutuser);
        this.datasource.setJdbcInterceptors(QueryTimeoutInterceptor.class.getName()+"(queryTimeout="+timeout+")");
        this.datasource.setDriverClassName(Driver.class.getName());
        this.datasource.setUrl("jdbc:tomcat:test");
        Connection con = this.datasource.getConnection();
        Statement st = con.createStatement();
        Assert.assertEquals(st.getClass().getName(),timeout,st.getQueryTimeout());
        st.close();
        st = con.prepareStatement("");
        Assert.assertEquals(st.getClass().getName(),timeout,st.getQueryTimeout());
        st.close();
        st = con.prepareCall("");
        Assert.assertEquals(st.getClass().getName(),timeout,st.getQueryTimeout());
        st.close();
        con.close();
    }
}
