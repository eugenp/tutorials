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


package org.apache.catalina.core;


import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;


/**
 * Facade for the <b>StandardWrapper</b> object.
 *
 * @author Remy Maucherat
 */
public final class StandardWrapperFacade
    implements ServletConfig {


    // ----------------------------------------------------------- Constructors


    /**
     * Create a new facade around a StandardWrapper.
     */
    public StandardWrapperFacade(StandardWrapper config) {

        super();
        this.config = config;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Wrapped config.
     */
    private ServletConfig config = null;


    /**
     * Wrapped context (facade).
     */
    private ServletContext context = null;


    // -------------------------------------------------- ServletConfig Methods


    @Override
    public String getServletName() {
        return config.getServletName();
    }


    @Override
    public ServletContext getServletContext() {
        if (context == null) {
            context = config.getServletContext();
            if ((context != null) && (context instanceof ApplicationContext))
                context = ((ApplicationContext) context).getFacade();
        }
        return (context);
    }


    @Override
    public String getInitParameter(String name) {
        return config.getInitParameter(name);
    }


    @Override
    public Enumeration<String> getInitParameterNames() {
        return config.getInitParameterNames();
    }


}
