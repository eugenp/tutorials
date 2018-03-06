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
package org.apache.tomcat;

import java.lang.reflect.InvocationTargetException;

import javax.naming.NamingException;

public interface InstanceManager {

    public Object newInstance(Class<?> clazz)
            throws IllegalAccessException, InvocationTargetException, NamingException,
                InstantiationException;

    public Object newInstance(String className)
        throws IllegalAccessException, InvocationTargetException, NamingException,
            InstantiationException, ClassNotFoundException;

    public Object newInstance(String fqcn, ClassLoader classLoader)
        throws IllegalAccessException, InvocationTargetException, NamingException,
            InstantiationException, ClassNotFoundException;

    public void newInstance(Object o)
        throws IllegalAccessException, InvocationTargetException, NamingException;

    public void destroyInstance(Object o)
        throws IllegalAccessException, InvocationTargetException;
}
