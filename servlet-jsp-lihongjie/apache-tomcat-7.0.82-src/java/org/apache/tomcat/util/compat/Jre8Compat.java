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
package org.apache.tomcat.util.compat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocket;

class Jre8Compat extends Jre7Compat {

    private static final Method getSSLParametersMethod;
    private static final Method setUseCipherSuitesOrderMethod;
    private static final Method setSSLParametersMethod;


    static {
        Method m1 = null;
        Method m2 = null;
        Method m3 = null;
        try {
            // The class is Java6+...
            Class<?> c2 = Class.forName("javax.net.ssl.SSLParameters");
            m1 = SSLServerSocket.class.getMethod("getSSLParameters");
            // ...but this method is Java8+
            m2 = c2.getMethod("setUseCipherSuitesOrder", boolean.class);
            m3 = SSLServerSocket.class.getMethod("setSSLParameters", c2);
        } catch (SecurityException e) {
            // Should never happen
        } catch (NoSuchMethodException e) {
            // Expected on Java < 8
        } catch (ClassNotFoundException e) {
            // Expected on Java < 7
        }
        getSSLParametersMethod = m1;
        setUseCipherSuitesOrderMethod = m2;
        setSSLParametersMethod = m3;
    }


    static boolean isSupported() {
        return setUseCipherSuitesOrderMethod != null;
    }


    @Override
    public void setUseServerCipherSuitesOrder(SSLServerSocket socket,
            boolean useCipherSuitesOrder) {
        try {
            Object sslParameters = getSSLParametersMethod.invoke(socket);
            setUseCipherSuitesOrderMethod.invoke(
                    sslParameters, Boolean.valueOf(useCipherSuitesOrder));
            setSSLParametersMethod.invoke(socket, sslParameters);
            return;
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException(e);
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        } catch (InvocationTargetException e) {
            throw new UnsupportedOperationException(e);
        }
    }


    @Override
    public void setUseServerCipherSuitesOrder(SSLEngine engine,
            boolean useCipherSuitesOrder) {
        SSLParameters sslParameters = engine.getSSLParameters();
        try {
            setUseCipherSuitesOrderMethod.invoke(sslParameters, Boolean.valueOf(useCipherSuitesOrder));
            engine.setSSLParameters(sslParameters);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException(e);
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        } catch (InvocationTargetException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
