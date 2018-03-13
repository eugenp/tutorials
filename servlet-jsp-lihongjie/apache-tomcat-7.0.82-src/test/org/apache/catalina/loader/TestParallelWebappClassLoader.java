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
package org.apache.catalina.loader;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.compat.JreCompat;

public class TestParallelWebappClassLoader extends TomcatBaseTest {

    private static final String PARALLEL_CLASSLOADER =
            "org.apache.catalina.loader.ParallelWebappClassLoader";
    private static final String DUMMY_SERVLET =
            "org.apache.catalina.loader.DummyServlet";

    @Test
    public void testParallelCapableOnJre7() {
        if (!JreCompat.isJre7Available()) {
            // ignore on Jre6 or lower
            return;
        }
        try {
            Tomcat tomcat = getTomcatInstance();
            Context ctx = tomcat.addContext("", null);

            WebappLoader webappLoader = new WebappLoader();
            webappLoader.setLoaderClass(PARALLEL_CLASSLOADER);
            ctx.setLoader(webappLoader);

            tomcat.start();

            ClassLoader classloader = ctx.getLoader().getClassLoader();

            Assert.assertTrue(classloader instanceof ParallelWebappClassLoader);

            // parallel class loading capable
            Method getClassLoadingLock =
                    getDeclaredMethod(classloader.getClass(), "getClassLoadingLock", String.class);
            // make sure we have getClassLoadingLock on JRE7.
            Assert.assertNotNull(getClassLoadingLock);
            // give us permission to access protected method
            getClassLoadingLock.setAccessible(true);

            Object lock = getClassLoadingLock.invoke(classloader, DUMMY_SERVLET);
            // make sure it is not a ParallelWebappClassLoader object lock
            Assert.assertNotEquals(lock, classloader);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("testParallelCapableOnJre7 fails.");
        }
    }

    @Test
    public void testParallelIncapableOnJre6() {
        if (JreCompat.isJre7Available()) {
            // ignore on Jre7 or above
            return;
        }
        try {
            Tomcat tomcat = getTomcatInstance();
            // Must have a real docBase - just use temp
            Context ctx = tomcat.addContext("",
                    System.getProperty("java.io.tmpdir"));

            WebappLoader webappLoader = new WebappLoader();
            webappLoader.setLoaderClass(PARALLEL_CLASSLOADER);
            ctx.setLoader(webappLoader);

            tomcat.start();

            ClassLoader classloader = ctx.getLoader().getClassLoader();

            Assert.assertTrue(classloader instanceof ParallelWebappClassLoader);

            // parallel class loading capable
            Method getClassLoadingLock =
                    getDeclaredMethod(classloader.getClass(), "getClassLoadingLock", String.class);
            // make sure we don't have getClassLoadingLock on JRE6.
            Assert.assertNull(getClassLoadingLock);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("testParallelIncapableOnJre6 fails.");
        }
    }

    private Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        if (clazz == null) return null;
        for (Method method: clazz.getDeclaredMethods()) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        // find from super class
        return getDeclaredMethod(clazz.getSuperclass(), name, parameterTypes);
    }

    @SuppressWarnings("unused")
    private static final class DummyServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            // do nothing
        }
    }
}