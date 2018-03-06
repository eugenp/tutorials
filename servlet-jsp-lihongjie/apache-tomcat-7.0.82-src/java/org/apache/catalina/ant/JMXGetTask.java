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


package org.apache.catalina.ant;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.tools.ant.BuildException;


/**
 * Ant task that implements the JMX Get command (<code>/jmxproxy/?get</code>)
 * supported by the Tomcat manager application.
 *
 * @author Peter Rossbach
 */
public class JMXGetTask extends AbstractCatalinaTask {

    // Properties

    /**
     * The full bean name
     */
    protected String bean      = null;

    /**
     * The attribute you wish to alter
     */
    protected String attribute = null;

    // Public Methods
    
    /**
     * Get method for the bean name
     * @return Bean name
     */
    public String getBean () {
        return this.bean;
    }

    /**
     * Set method for the bean name
     * @param bean Bean name
     */
    public void setBean (String bean) {
        this.bean = bean;
    }

    /**
     * Get method for the attribute name
     * @return Attribute name
     */
    public String getAttribute () {
        return this.attribute;
    }

    /**
     * Set method for the attribute name
     * @param attribute Attribute name
     */
    public void setAttribute (String attribute) {
        this.attribute = attribute;
    }

    /**
     * Execute the requested operation.
     *
     * @exception BuildException if an error occurs
     */
    @Override
    public void execute() throws BuildException {
        super.execute();
        if (bean == null || attribute == null) {
            throw new BuildException
                ("Must specify 'bean' and 'attribute' attributes");
        }
        log("Getting attribute " + attribute +
                " in bean " + bean );
        try {
            execute("/jmxproxy/?get=" + URLEncoder.encode(bean, getCharset()) 
                    + "&att=" + URLEncoder.encode(attribute, getCharset()));
        } catch (UnsupportedEncodingException e) {
            throw new BuildException
                ("Invalid 'charset' attribute: " + getCharset());
        }
    }
}
