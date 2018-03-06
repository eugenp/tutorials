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
package org.apache.catalina.util;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.apache.catalina.Container;
import org.apache.catalina.Globals;
import org.apache.juli.logging.Log;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.res.StringManager;

/**
 * Provides introspection utilities that either require knowledge of Tomcat
 * internals or are solely used by Tomcat internals.
 */
public class Introspection {

    private static StringManager sm =
            StringManager.getManager("org.apache.catalina.util");


    /**
     * Extract the Java Bean property name from the setter name.
     *
     * Note: This method assumes that the method name has already been checked
     *       for correctness.
     */
    public static String getPropertyName(Method setter) {
        return Introspector.decapitalize(setter.getName().substring(3));
    }


    /**
     * Determines if a method has a valid name and signature for a Java Bean
     * setter.
     *
     * @param method    The method to test
     *
     * @return  <code>true</code> if the method does have a valid name and
     *          signature, else <code>false</code>
     */
    public static boolean isValidSetter(Method method) {
        if (method.getName().startsWith("set")
                && method.getName().length() > 3
                && method.getParameterTypes().length == 1
                && method.getReturnType().getName().equals("void")) {
            return true;
        }
        return false;
    }

    /**
     * Determines if a method is a valid lifecycle callback method.
     *
     * @param method
     *            The method to test
     *
     * @return <code>true</code> if the method is a valid lifecycle callback
     *         method, else <code>false</code>
     */
    public static boolean isValidLifecycleCallback(Method method) {
        if (method.getParameterTypes().length != 0
                || Modifier.isStatic(method.getModifiers())
                || method.getExceptionTypes().length > 0
                || !method.getReturnType().getName().equals("void")) {
            return false;
        }
        return true;
    }

    /**
     * Obtain the declared fields for a class taking account of any security
     * manager that may be configured.
     */
    public static Field[] getDeclaredFields(final Class<?> clazz) {
        Field[] fields = null;
        if (Globals.IS_SECURITY_ENABLED) {
            fields = AccessController.doPrivileged(
                    new PrivilegedAction<Field[]>(){
                @Override
                public Field[] run(){
                    return clazz.getDeclaredFields();
                }
            });
        } else {
            fields = clazz.getDeclaredFields();
        }
        return fields;
    }


    /**
     * Obtain the declared methods for a class taking account of any security
     * manager that may be configured.
     */
    public static Method[] getDeclaredMethods(final Class<?> clazz) {
        Method[] methods = null;
        if (Globals.IS_SECURITY_ENABLED) {
            methods = AccessController.doPrivileged(
                    new PrivilegedAction<Method[]>(){
                @Override
                public Method[] run(){
                    return clazz.getDeclaredMethods();
                }
            });
        } else {
            methods = clazz.getDeclaredMethods();
        }
        return methods;
    }


    /**
     * Attempt to load a class using the given Container's class loader. If the
     * class cannot be loaded, a debug level log message will be written to the
     * Container's log and null will be returned.
     */
    public static Class<?> loadClass(Container container, String className) {
        ClassLoader cl = container.getLoader().getClassLoader();
        Log log = container.getLogger();
        Class<?> clazz = null;
        try {
            clazz = cl.loadClass(className);
        } catch (ClassNotFoundException e) {
            log.debug(sm.getString("introspection.classLoadFailed", className), e);
        } catch (NoClassDefFoundError e) {
            log.debug(sm.getString("introspection.classLoadFailed", className), e);
        } catch (ClassFormatError e) {
            log.debug(sm.getString("introspection.classLoadFailed", className), e);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            log.debug(sm.getString("introspection.classLoadFailed", className), t);
        }
        return clazz;
    }

    /**
     * Converts the primitive type to its corresponding wrapper.
     *
     * @param clazz
     *            Class that will be evaluated
     * @return if the parameter is a primitive type returns its wrapper;
     *         otherwise returns the same class
     */
    public static Class<?> convertPrimitiveType(Class<?> clazz) {
        if (clazz.equals(char.class)) {
            return Character.class;
        } else if (clazz.equals(int.class)) {
            return Integer.class;
        } else if (clazz.equals(boolean.class)) {
            return Boolean.class;
        } else if (clazz.equals(double.class)) {
            return Double.class;
        } else if (clazz.equals(byte.class)) {
            return Byte.class;
        } else if (clazz.equals(short.class)) {
            return Short.class;
        } else if (clazz.equals(long.class)) {
            return Long.class;
        } else if (clazz.equals(float.class)) {
            return Float.class;
        } else {
            return clazz;
        }
    }
}
