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

import org.xml.sax.Attributes;


/**
 * <p>Rule implementation that uses an {@link ObjectCreationFactory} to create
 * a new object which it pushes onto the object stack.  When the element is
 * complete, the object will be popped.</p>
 *
 * <p>This rule is intended in situations where the element's attributes are
 * needed before the object can be created.  A common scenario is for the
 * ObjectCreationFactory implementation to use the attributes  as parameters
 * in a call to either a factory method or to a non-empty constructor.
 */

public class FactoryCreateRule extends Rule {

    // ----------------------------------------------------------- Fields
    
    /** Should exceptions thrown by the factory be ignored? */
    private boolean ignoreCreateExceptions;
    /** Stock to manage */
    private ArrayStack<Boolean> exceptionIgnoredStack;

    // ----------------------------------------------------------- Constructors


    /**
     * <p>Construct a factory create rule that will use the specified
     * class name to create an {@link ObjectCreationFactory} which will
     * then be used to create an object and push it on the stack.</p>
     *
     * <p>Exceptions thrown during the object creation process will be propagated.</p>
     *
     * @param className Java class name of the object creation factory class
     */
    public FactoryCreateRule(String className) {

        this(className, false);

    }


    /**
     * <p>Construct a factory create rule that will use the specified
     * class to create an {@link ObjectCreationFactory} which will
     * then be used to create an object and push it on the stack.</p>
     *
     * <p>Exceptions thrown during the object creation process will be propagated.</p>
     *
     * @param clazz Java class name of the object creation factory class
     */
    public FactoryCreateRule(Class<?> clazz) {

        this(clazz, false);

    }


    /**
     * <p>Construct a factory create rule that will use the specified
     * class name (possibly overridden by the specified attribute if present)
     * to create an {@link ObjectCreationFactory}, which will then be used
     * to instantiate an object instance and push it onto the stack.</p>
     *
     * <p>Exceptions thrown during the object creation process will be propagated.</p>
     *
     * @param className Default Java class name of the factory class
     * @param attributeName Attribute name which, if present, contains an
     *  override of the class name of the object creation factory to create.
     */
    public FactoryCreateRule(String className, String attributeName) {

        this(className, attributeName, false);

    }


    /**
     * <p>Construct a factory create rule that will use the specified
     * class (possibly overridden by the specified attribute if present)
     * to create an {@link ObjectCreationFactory}, which will then be used
     * to instantiate an object instance and push it onto the stack.</p>
     *
     * <p>Exceptions thrown during the object creation process will be propagated.</p>
     *
     * @param clazz Default Java class name of the factory class
     * @param attributeName Attribute name which, if present, contains an
     *  override of the class name of the object creation factory to create.
     */
    public FactoryCreateRule(Class<?> clazz, String attributeName) {

        this(clazz, attributeName, false);

    }


    /**
     * <p>Construct a factory create rule using the given, already instantiated,
     * {@link ObjectCreationFactory}.</p>
     *
     * <p>Exceptions thrown during the object creation process will be propagated.</p>
     *
     * @param creationFactory called on to create the object.
     */
    public FactoryCreateRule(ObjectCreationFactory creationFactory) {

        this(creationFactory, false);

    }
    
    /**
     * Construct a factory create rule that will use the specified
     * class name to create an {@link ObjectCreationFactory} which will
     * then be used to create an object and push it on the stack.
     *
     * @param className Java class name of the object creation factory class
     * @param ignoreCreateExceptions if true, exceptions thrown by the object
     *  creation factory
     * will be ignored.
     */
    public FactoryCreateRule(String className, boolean ignoreCreateExceptions) {

        this(className, null, ignoreCreateExceptions);

    }


    /**
     * Construct a factory create rule that will use the specified
     * class to create an {@link ObjectCreationFactory} which will
     * then be used to create an object and push it on the stack.
     *
     * @param clazz Java class name of the object creation factory class
     * @param ignoreCreateExceptions if true, exceptions thrown by the
     *  object creation factory
     * will be ignored.
     */
    public FactoryCreateRule(Class<?> clazz, boolean ignoreCreateExceptions) {

        this(clazz, null, ignoreCreateExceptions);

    }


    /**
     * Construct a factory create rule that will use the specified
     * class name (possibly overridden by the specified attribute if present)
     * to create an {@link ObjectCreationFactory}, which will then be used
     * to instantiate an object instance and push it onto the stack.
     *
     * @param className Default Java class name of the factory class
     * @param attributeName Attribute name which, if present, contains an
     *  override of the class name of the object creation factory to create.
     * @param ignoreCreateExceptions if true, exceptions thrown by the object
     *  creation factory will be ignored.
     */
    public FactoryCreateRule(
                                String className, 
                                String attributeName,
                                boolean ignoreCreateExceptions) {

        this.className = className;
        this.attributeName = attributeName;
        this.ignoreCreateExceptions = ignoreCreateExceptions;

    }


    /**
     * Construct a factory create rule that will use the specified
     * class (possibly overridden by the specified attribute if present)
     * to create an {@link ObjectCreationFactory}, which will then be used
     * to instantiate an object instance and push it onto the stack.
     *
     * @param clazz Default Java class name of the factory class
     * @param attributeName Attribute name which, if present, contains an
     *  override of the class name of the object creation factory to create.
     * @param ignoreCreateExceptions if true, exceptions thrown by the object
     *  creation factory will be ignored.
     */
    public FactoryCreateRule(
                                Class<?> clazz, 
                                String attributeName,
                                boolean ignoreCreateExceptions) {

        this(clazz.getName(), attributeName, ignoreCreateExceptions);

    }


    /**
     * Construct a factory create rule using the given, already instantiated,
     * {@link ObjectCreationFactory}.
     *
     * @param creationFactory called on to create the object.
     * @param ignoreCreateExceptions if true, exceptions thrown by the object
     *  creation factory will be ignored.
     */
    public FactoryCreateRule(
                            ObjectCreationFactory creationFactory, 
                            boolean ignoreCreateExceptions) {

        this.creationFactory = creationFactory;
        this.ignoreCreateExceptions = ignoreCreateExceptions;
    }

    // ----------------------------------------------------- Instance Variables


    /**
     * The attribute containing an override class name if it is present.
     */
    protected String attributeName = null;


    /**
     * The Java class name of the ObjectCreationFactory to be created.
     * This class must have a no-arguments constructor.
     */
    protected String className = null;


    /**
     * The object creation factory we will use to instantiate objects
     * as required based on the attributes specified in the matched XML
     * element.
     */
    protected ObjectCreationFactory creationFactory = null;


    // --------------------------------------------------------- Public Methods


    /**
     * Process the beginning of this element.
     *
     * @param attributes The attribute list of this element
     */
    @Override
    public void begin(String namespace, String name, Attributes attributes) throws Exception {
        
        if (ignoreCreateExceptions) {
        
            if (exceptionIgnoredStack == null) {
                exceptionIgnoredStack = new ArrayStack<Boolean>();
            }
            
            try {
                Object instance = getFactory(attributes).createObject(attributes);
                
                if (digester.log.isDebugEnabled()) {
                    digester.log.debug("[FactoryCreateRule]{" + digester.match +
                            "} New " + instance.getClass().getName());
                }
                digester.push(instance);
                exceptionIgnoredStack.push(Boolean.FALSE);
                
            } catch (Exception e) {
                // log message and error
                if (digester.log.isInfoEnabled()) {
                    digester.log.info("[FactoryCreateRule] Create exception ignored: " +
                        ((e.getMessage() == null) ? e.getClass().getName() : e.getMessage()));
                    if (digester.log.isDebugEnabled()) {
                        digester.log.debug("[FactoryCreateRule] Ignored exception:", e);
                    }
                }
                exceptionIgnoredStack.push(Boolean.TRUE);
            }
            
        } else {
            Object instance = getFactory(attributes).createObject(attributes);
            
            if (digester.log.isDebugEnabled()) {
                digester.log.debug("[FactoryCreateRule]{" + digester.match +
                        "} New " + instance.getClass().getName());
            }
            digester.push(instance);
        }
    }


    /**
     * Process the end of this element.
     */
    @Override
    public void end(String namespace, String name) throws Exception {
        
        // check if object was created 
        // this only happens if an exception was thrown and we're ignoring them
        if (
                ignoreCreateExceptions &&
                exceptionIgnoredStack != null &&
                !(exceptionIgnoredStack.empty())) {
                
            if ((exceptionIgnoredStack.pop()).booleanValue()) {
                // creation exception was ignored
                // nothing was put onto the stack
                if (digester.log.isTraceEnabled()) {
                    digester.log.trace("[FactoryCreateRule] No creation so no push so no pop");
                }
                return;
            }
        } 

        Object top = digester.pop();
        if (digester.log.isDebugEnabled()) {
            digester.log.debug("[FactoryCreateRule]{" + digester.match +
                    "} Pop " + top.getClass().getName());
        }

    }


    /**
     * Clean up after parsing is complete.
     */
    @Override
    public void finish() throws Exception {

        if (attributeName != null) {
            creationFactory = null;
        }

    }


    /**
     * Render a printable version of this Rule.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("FactoryCreateRule[");
        sb.append("className=");
        sb.append(className);
        sb.append(", attributeName=");
        sb.append(attributeName);
        if (creationFactory != null) {
            sb.append(", creationFactory=");
            sb.append(creationFactory);
        }
        sb.append("]");
        return (sb.toString());

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Return an instance of our associated object creation factory,
     * creating one if necessary.
     *
     * @param attributes Attributes passed to our factory creation element
     *
     * @exception Exception if any error occurs
     */
    protected ObjectCreationFactory getFactory(Attributes attributes)
            throws Exception {

        if (creationFactory == null) {
            String realClassName = className;
            if (attributeName != null) {
                String value = attributes.getValue(attributeName);
                if (value != null) {
                    realClassName = value;
                }
            }
            if (digester.log.isDebugEnabled()) {
                digester.log.debug("[FactoryCreateRule]{" + digester.match +
                        "} New factory " + realClassName);
            }
            Class<?> clazz = digester.getClassLoader().loadClass(realClassName);
            creationFactory = (ObjectCreationFactory)
                    clazz.newInstance();
            creationFactory.setDigester(digester);
        }
        return (creationFactory);

    }    
}
