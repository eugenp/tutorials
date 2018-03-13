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


import org.apache.catalina.Container;
import org.apache.catalina.LifecycleListener;
import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.digester.Rule;
import org.xml.sax.Attributes;


/**
 * Rule that creates a new {@link LifecycleListener} and associates it with the
 * top object on the stack which must implement {@link Container}. The
 * implementation class to be used is determined by:
 * <ol>
 * <li>Does the top element on the stack specify an implementation class using
 *     the attribute specified when this rule was created?</li>
 * <li>Does the parent {@link Container} of the {@link Container} on the top of
 *     the stack specify an implementation class using the attribute specified
 *     when this rule was created?</li>
 * <li>Use the default implementation class specified when this rule was
 *     created.</li>
 * </ol>
 */
public class LifecycleListenerRule extends Rule {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new instance of this Rule.
     *
     * @param listenerClass Default name of the LifecycleListener
     *  implementation class to be created
     * @param attributeName Name of the attribute that optionally
     *  includes an override name of the LifecycleListener class
     */
    public LifecycleListenerRule(String listenerClass, String attributeName) {

        this.listenerClass = listenerClass;
        this.attributeName = attributeName;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The attribute name of an attribute that can override the
     * implementation class name.
     */
    private String attributeName;


    /**
     * The name of the <code>LifecycleListener</code> implementation class.
     */
    private String listenerClass;


    // --------------------------------------------------------- Public Methods


    /**
     * Handle the beginning of an XML element.
     *
     * @param attributes The attributes of this element
     *
     * @exception Exception if a processing error occurs
     */
    @Override
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {

        Container c = (Container) digester.peek();
        Container p = null;
        Object obj = digester.peek(1);
        if (obj instanceof Container) {
            p = (Container) obj;
        }

        String className = null;
        
        // Check the container for the specified attribute
        if (attributeName != null) {
            String value = attributes.getValue(attributeName);
            if (value != null)
                className = value;
        }

        // Check the container's parent for the specified attribute
        if (p != null && className == null) {
            String configClass =
                (String) IntrospectionUtils.getProperty(p, attributeName);
            if (configClass != null && configClass.length() > 0) {
                className = configClass;
            }
        }
        
        // Use the default
        if (className == null) {
            className = listenerClass;
        }
        
        // Instantiate a new LifecycleListener implementation object
        Class<?> clazz = Class.forName(className);
        LifecycleListener listener =
            (LifecycleListener) clazz.newInstance();

        // Add this LifecycleListener to our associated component
        c.addLifecycleListener(listener);
    }


}
