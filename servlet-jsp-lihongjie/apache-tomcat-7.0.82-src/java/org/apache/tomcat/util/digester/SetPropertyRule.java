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


package org.apache.tomcat.util.digester;


import org.apache.tomcat.util.IntrospectionUtils;
import org.xml.sax.Attributes;


/**
 * Rule implementation that sets an individual property on the object at the
 * top of the stack, based on attributes with specified names.
 */

public class SetPropertyRule extends Rule {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a "set property" rule with the specified name and value
     * attributes.
     *
     * @param digester The digester with which this rule is associated
     * @param name Name of the attribute that will contain the name of the
     *  property to be set
     * @param value Name of the attribute that will contain the value to which
     *  the property should be set
     *
     * @deprecated The digester instance is now set in the {@link Digester#addRule} method. 
     * Use {@link #SetPropertyRule(String name, String value)} instead.
     */
    @Deprecated
    public SetPropertyRule(Digester digester, String name, String value) {

        this(name, value);

    }

    /**
     * Construct a "set property" rule with the specified name and value
     * attributes.
     *
     * @param name Name of the attribute that will contain the name of the
     *  property to be set
     * @param value Name of the attribute that will contain the value to which
     *  the property should be set
     */
    public SetPropertyRule(String name, String value) {

        this.name = name;
        this.value = value;

    }

    // ----------------------------------------------------- Instance Variables


    /**
     * The attribute that will contain the property name.
     */
    protected String name = null;


    /**
     * The attribute that will contain the property value.
     */
    protected String value = null;


    // --------------------------------------------------------- Public Methods


    /**
     * Process the beginning of this element.
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param theName the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     * @param attributes The attribute list for this element
     * 
     * @exception NoSuchMethodException if the bean does not
     *  have a writable property of the specified name
     */
    @Override
    public void begin(String namespace, String theName, Attributes attributes)
            throws Exception {

        // Identify the actual property name and value to be used
        String actualName = null;
        String actualValue = null;
        for (int i = 0; i < attributes.getLength(); i++) {
            String name = attributes.getLocalName(i);
            if ("".equals(name)) {
                name = attributes.getQName(i);
            }
            String value = attributes.getValue(i);
            if (name.equals(this.name)) {
                actualName = value;
            } else if (name.equals(this.value)) {
                actualValue = value;
            }
        }

        // Get a reference to the top object
        Object top = digester.peek();

        // Log some debugging information
        if (digester.log.isDebugEnabled()) {
            digester.log.debug("[SetPropertyRule]{" + digester.match +
                    "} Set " + top.getClass().getName() + " property " +
                    actualName + " to " + actualValue);
        }

        // Set the property (with conversion as necessary)
        if (!digester.isFakeAttribute(top, actualName) 
                && !IntrospectionUtils.setProperty(top, actualName, actualValue) 
                && digester.getRulesValidation()) {
            digester.log.warn("[SetPropertyRule]{" + digester.match +
                    "} Setting property '" + name + "' to '" +
                    value + "' did not find a matching property.");
        }

    }


    /**
     * Render a printable version of this Rule.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("SetPropertyRule[");
        sb.append("name=");
        sb.append(name);
        sb.append(", value=");
        sb.append(value);
        sb.append("]");
        return (sb.toString());

    }


}
