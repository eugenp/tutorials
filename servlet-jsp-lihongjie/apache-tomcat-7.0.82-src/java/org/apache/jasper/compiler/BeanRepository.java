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
package org.apache.jasper.compiler;


import java.util.HashMap;

import org.apache.jasper.JasperException;

/**
 * Repository of {page, request, session, application}-scoped beans 
 *
 * @author Mandar Raje
 * @author Remy Maucherat
 */
public class BeanRepository {

    protected HashMap<String, String> beanTypes;
    protected ClassLoader loader;
    protected ErrorDispatcher errDispatcher;

    /**
     * Constructor.
     */    
    public BeanRepository(ClassLoader loader, ErrorDispatcher err) {
        this.loader = loader;
        this.errDispatcher = err;
        beanTypes = new HashMap<String, String>();
    }

    public void addBean(Node.UseBean n, String s, String type, String scope)
        throws JasperException {

        if (!(scope == null || scope.equals("page") || scope.equals("request") 
                || scope.equals("session") || scope.equals("application"))) {
            errDispatcher.jspError(n, "jsp.error.usebean.badScope");
        }

        beanTypes.put(s, type);
    }
            
    public Class<?> getBeanType(String bean)
        throws JasperException {
        Class<?> clazz = null;
        try {
            clazz = loader.loadClass(beanTypes.get(bean));
        } catch (ClassNotFoundException ex) {
            throw new JasperException (ex);
        }
        return clazz;
    }
      
    public boolean checkVariable(String bean) {
        return beanTypes.containsKey(bean);
    }

}


