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
package org.apache.naming.resources;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class TesterFactory implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
            Hashtable<?,?> environment) throws Exception {

        if (obj instanceof Reference) {
            Reference ref = (Reference)obj;
            String className = ref.getClassName();

            if (className == null) {
                throw new RuntimeException();
            }

            if (className.equals("org.apache.naming.resources.TesterObject")) {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                Class<?> clazz =
                    cl.loadClass("org.apache.naming.resources.TesterObject");
                return clazz.newInstance();
            }
        }
        return null;
    }
}
