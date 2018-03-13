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
 * Ant task that implements the JMX Query command 
 * (<code>/jmxproxy/?qry</code>) supported by the Tomcat manager application.
 *
 * @author Vivek Chopra
 */
public class JMXQueryTask extends AbstractCatalinaTask {

    // Properties

    /**
     * The JMX query string 
     * @see #setQuery(String)
     */
    protected String query      = null;

    // Public Methods
    
    /**
     * Get method for the JMX query string
     * @return Query string
     */
    public String getQuery () {
        return this.query;
    }

    /**
     * Set method for the JMX query string.
    * <P>Examples of query format:
     * <UL>
     * <LI>*:*</LI>
     * <LI>*:type=RequestProcessor,*</LI>
     * <LI>*:j2eeType=Servlet,*</LI>
     * <LI>Catalina:type=Environment,resourcetype=Global,name=simpleValue</LI>
     * </UL>
     * </P> 
     * @param query JMX Query string
     */
    public void setQuery (String query) {
        this.query = query;
    }

    /**
     * Execute the requested operation.
     *
     * @exception BuildException if an error occurs
     */
    @Override
    public void execute() throws BuildException {
        super.execute();
        String queryString;
        if (query == null) {
            queryString = "";
        } else {
            try {
                queryString = "?qry=" + URLEncoder.encode(query, getCharset());
            } catch (UnsupportedEncodingException e) {
                throw new BuildException
                    ("Invalid 'charset' attribute: " + getCharset());
            }
        }
        log("Query string is " + queryString); 
        execute ("/jmxproxy/" + queryString);
    }
}
