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

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.PooledConnection;

import org.junit.Assert;
import org.junit.Test;

import org.apache.tomcat.jdbc.test.driver.Connection;
import org.apache.tomcat.jdbc.test.driver.Driver;

public class AlternateUsernameTest extends DefaultTestCase {

    private static final int iterations = 500000; //(new Random(System.currentTimeMillis())).nextInt(1000000)+100000;

    @Test
    public void testUsernameCompare() throws Exception {
        testUsername(true);
    }

    private void testUsername(boolean allowUsernameChange) throws Exception {
        long start = System.currentTimeMillis();
        int withoutuser =10;
        int withuser = withoutuser;
        this.datasource.setMaxActive(withuser+withoutuser);
        this.datasource.setDriverClassName(Driver.class.getName());
        this.datasource.setUrl("jdbc:tomcat:test");
        this.datasource.setAlternateUsernameAllowed(allowUsernameChange);
        this.datasource.getConnection().close();

        TestRunner[] runners = new TestRunner[withuser+withoutuser];
        for (int i=0; i<withuser; i++) {
            TestRunner with = new TestRunner("foo","bar",datasource.getPoolProperties().getUsername(),datasource.getPoolProperties().getPassword());
            TestRunner without = new TestRunner(null,null,datasource.getPoolProperties().getUsername(),datasource.getPoolProperties().getPassword());
            runners[i] = allowUsernameChange?with:without;
            runners[i+withuser] = without;
        }
        ExecutorService svc = Executors.newFixedThreadPool(withuser+withoutuser);
        List<Future<TestResult>> results =  svc.invokeAll(Arrays.asList(runners));
        int failures = 0;
        int total = 0;
        for (int i=0; i<withuser; i++) {
            failures += results.get(i).get().failures;
            total+=results.get(i).get().iterations;
            failures += results.get(i+withuser).get().failures;
            total+=results.get(i+withuser).get().iterations;
        }
        long stop = System.currentTimeMillis();
        Assert.assertEquals("Nr of failures was:"+failures,0, failures);
        svc.shutdownNow();
        this.datasource.close();
        System.out.println("Nr of connect() calls:"+Driver.connectCount.get());
        System.out.println("Nr of disconnect() calls:"+Driver.disconnectCount.get());
        System.out.println("Nr of iterations:"+total+" over "+(stop-start)+ " ms.");

    }

    @Test
    public void testUsernameCompareAgain() throws Exception {
        testUsernameCompare();
    }

    @Test
    public void testUsernameCompareNotAllowed() throws Exception {
        testUsername(false);
    }

    public static class TestResult {
        public int iterations;
        public int failures;
        public String lastMessage;
    }

    public class TestRunner implements Callable<TestResult> {
        String username;
        String password;
        volatile boolean done = false;
        TestResult result = null;
        boolean useuser = true;

        public TestRunner(String user, String pass, String guser, String gpass) {
            username = user==null?guser : user;
            password = pass==null?gpass : pass;
            useuser = user!=null;
        }

        @Override
        public TestResult call() {
            TestResult test = new TestResult();
            PooledConnection pcon = null;
            for (int i=0; (!done) && (i<iterations); i++) {
                test.iterations = i+1;
                try {


                    pcon = useuser ? (PooledConnection)AlternateUsernameTest.this.datasource.getConnection(username, password) :
                                     (PooledConnection)AlternateUsernameTest.this.datasource.getConnection();

                    Connection con = (Connection)pcon.getConnection();

                    Assert.assertTrue("Username mismatch: Requested User:"+username+" Actual user:"+con.getUsername(), con.getUsername().equals(username));
                    Assert.assertTrue("Password mismatch: Requested Password:"+password+" Actual password:"+con.getPassword(), con.getPassword().equals(password));
                }catch (SQLException x) {
                    test.failures++;
                    test.lastMessage = x.getMessage();
                    done = true;
                    x.printStackTrace();
                }catch (Exception x) {
                    test.failures++;
                    test.lastMessage = x.getMessage();
                    x.printStackTrace();
                } finally {
                    if (pcon!=null) {
                        try {
                            pcon.close();
                        } catch (Exception ignore) {
                            // Ignore
                        }
                        pcon = null;
                    }
                }
            }
            done = true;
            result = test;
            return result;
        }
    }
}
