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


package org.apache.catalina.startup;

import java.util.HashMap;

import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.digester.Rule;
import org.xml.sax.Attributes;

/**
 * Rule that uses the introspection utils to set properties.
 * 
 * @author Remy Maucherat
 * @author Filip Hanik
 */
public class SetAllPropertiesRule extends Rule {

    
    // ----------------------------------------------------------- Constructors
    public SetAllPropertiesRule() {}
    
    public SetAllPropertiesRule(String[] exclude) {
        for (int i=0; i<exclude.length; i++ ) if (exclude[i]!=null) this.excludes.put(exclude[i],exclude[i]);
    }

    // ----------------------------------------------------- Instance Variables
    protected HashMap<String,String> excludes = new HashMap<String,String>();

    // --------------------------------------------------------- Public Methods


    /**
     * Handle the beginning of an XML element.
     *
     * @param attributes The attributes of this element
     *
     * @exception Exception if a processing error occurs
     */
    @Override
    public void begin(String namespace, String nameX, Attributes attributes)
        throws Exception {

        for (int i = 0; i < attributes.getLength(); i++) {
            String name = attributes.getLocalName(i);
            if ("".equals(name)) {
                name = attributes.getQName(i);
            }
            String value = attributes.getValue(i);
            if ( !excludes.containsKey(name)) {
                if (!digester.isFakeAttribute(digester.peek(), name) 
                        && !IntrospectionUtils.setProperty(digester.peek(), name, value) 
                        && digester.getRulesValidation()) {
                    digester.getLogger().warn("[SetAllPropertiesRule]{" + digester.getMatch() +
                            "} Setting property '" + name + "' to '" +
                            value + "' did not find a matching property.");
                }
            }
        }

    }


}
