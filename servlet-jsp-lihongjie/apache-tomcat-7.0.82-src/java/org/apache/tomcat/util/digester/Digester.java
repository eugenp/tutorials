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


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Permission;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PropertyPermission;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.security.PermissionCheck;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.AttributesImpl;


/**
 * <p>A <strong>Digester</strong> processes an XML input stream by matching a
 * series of element nesting patterns to execute Rules that have been added
 * prior to the start of parsing.  This package was inspired by the
 * <code>XmlMapper</code> class that was part of Tomcat 3.0 and 3.1,
 * but is organized somewhat differently.</p>
 *
 * <p>See the <a href="package-summary.html#package_description">Digester
 * Developer Guide</a> for more information.</p>
 *
 * <p><strong>IMPLEMENTATION NOTE</strong> - A single Digester instance may
 * only be used within the context of a single thread at a time, and a call
 * to <code>parse()</code> must be completed before another can be initiated
 * even from the same thread.</p>
 *
 * <p><strong>IMPLEMENTATION NOTE</strong> - A bug in Xerces 2.0.2 prevents
 * the support of XML schema. You need Xerces 2.1/2.3 and up to make
 * this class working with XML schema</p>
 */
public class Digester extends DefaultHandler2 {


    // ---------------------------------------------------------- Static Fields

    protected static IntrospectionUtils.PropertySource propertySource = null;

    static {
        String className = System.getProperty("org.apache.tomcat.util.digester.PROPERTY_SOURCE");
        if (className!=null) {
            ClassLoader[] cls = new ClassLoader[] {Digester.class.getClassLoader(),Thread.currentThread().getContextClassLoader()};
            for (int i = 0; i < cls.length; i++) {
                try {
                    Class<?> clazz = Class.forName(className, true, cls[i]);
                    propertySource = (IntrospectionUtils.PropertySource) clazz.newInstance();
                    break;
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    LogFactory.getLog("org.apache.tomcat.util.digester.Digester").
                        error("Unable to load property source["+className+"].",t);
                }
            }
        }
    }


    // --------------------------------------------------------- Constructors


    /**
     * Construct a new Digester with default properties.
     */
    public Digester() {

        super();

        if (propertySource != null) {
            source = new IntrospectionUtils.PropertySource[] { propertySource, source[0] };
        }

    }


    /**
     * Construct a new Digester, allowing a SAXParser to be passed in.  This
     * allows Digester to be used in environments which are unfriendly to
     * JAXP1.1 (such as WebLogic 6.0).  Thanks for the request to change go to
     * James House (james@interobjective.com).  This may help in places where
     * you are able to load JAXP 1.1 classes yourself.
     */
    public Digester(SAXParser parser) {

        super();

        this.parser = parser;

        if (propertySource != null) {
            source = new IntrospectionUtils.PropertySource[] { propertySource, source[0] };
        }

    }


    /**
     * Construct a new Digester, allowing an XMLReader to be passed in.  This
     * allows Digester to be used in environments which are unfriendly to
     * JAXP1.1 (such as WebLogic 6.0).  Note that if you use this option you
     * have to configure namespace and validation support yourself, as these
     * properties only affect the SAXParser and empty constructor.
     */
    public Digester(XMLReader reader) {

        super();

        this.reader = reader;

        if (propertySource != null) {
            source = new IntrospectionUtils.PropertySource[] { propertySource, source[0] };
        }

    }


    // --------------------------------------------------- Instance Variables


    private class SystemPropertySource implements IntrospectionUtils.PropertySource {
        @Override
        public String getProperty(String key) {
            ClassLoader cl = getClassLoader();
            if (cl instanceof PermissionCheck) {
                Permission p = new PropertyPermission(key, "read");
                if (!((PermissionCheck) cl).check(p)) {
                    return null;
                }
            }
            return System.getProperty(key);
        }
    }


    protected IntrospectionUtils.PropertySource source[] = new IntrospectionUtils.PropertySource[] {
            new SystemPropertySource() };


    /**
     * The body text of the current element.
     */
    protected StringBuilder bodyText = new StringBuilder();


    /**
     * The stack of body text string buffers for surrounding elements.
     */
    protected ArrayStack<StringBuilder> bodyTexts =
        new ArrayStack<StringBuilder>();


    /**
     * Stack whose elements are List objects, each containing a list of
     * Rule objects as returned from Rules.getMatch(). As each xml element
     * in the input is entered, the matching rules are pushed onto this
     * stack. After the end tag is reached, the matches are popped again.
     * The depth of is stack is therefore exactly the same as the current
     * "nesting" level of the input xml. 
     *
     * @since 1.6
     */
    protected ArrayStack<List<Rule>> matches = new ArrayStack<List<Rule>>(10);
    
    /**
     * The class loader to use for instantiating application objects.
     * If not specified, the context class loader, or the class loader
     * used to load Digester itself, is used, based on the value of the
     * <code>useContextClassLoader</code> variable.
     */
    protected ClassLoader classLoader = null;


    /**
     * Has this Digester been configured yet.
     */
    protected boolean configured = false;


    /**
     * The EntityResolver used by the SAX parser. By default it use this class
     */
    protected EntityResolver entityResolver;
    
    /**
     * The URLs of entityValidator that have been registered, keyed by the public
     * identifier that corresponds.
     */
    protected HashMap<String,String> entityValidator =
        new HashMap<String,String>();


    /**
     * The application-supplied error handler that is notified when parsing
     * warnings, errors, or fatal errors occur.
     */
    protected ErrorHandler errorHandler = null;


    /**
     * The SAXParserFactory that is created the first time we need it.
     */
    protected SAXParserFactory factory = null;

    /**
     * The Locator associated with our parser.
     */
    protected Locator locator = null;


    /**
     * The current match pattern for nested element processing.
     */
    protected String match = "";


    /**
     * Do we want a "namespace aware" parser.
     */
    protected boolean namespaceAware = false;


    /**
     * Registered namespaces we are currently processing.  The key is the
     * namespace prefix that was declared in the document.  The value is an
     * ArrayStack of the namespace URIs this prefix has been mapped to --
     * the top Stack element is the most current one.  (This architecture
     * is required because documents can declare nested uses of the same
     * prefix for different Namespace URIs).
     */
    protected HashMap<String,ArrayStack<String>> namespaces =
        new HashMap<String,ArrayStack<String>>();


    /**
     * The parameters stack being utilized by CallMethodRule and
     * CallParamRule rules.
     */
    protected ArrayStack<Object> params = new ArrayStack<Object>();


    /**
     * The SAXParser we will use to parse the input stream.
     */
    protected SAXParser parser = null;


    /**
     * The public identifier of the DTD we are currently parsing under
     * (if any).
     */
    protected String publicId = null;


    /**
     * The XMLReader used to parse digester rules.
     */
    protected XMLReader reader = null;


    /**
     * The "root" element of the stack (in other words, the last object
     * that was popped.
     */
    protected Object root = null;


    /**
     * The <code>Rules</code> implementation containing our collection of
     * <code>Rule</code> instances and associated matching policy.  If not
     * established before the first rule is added, a default implementation
     * will be provided.
     */
    protected Rules rules = null;

    /**
     * The object stack being constructed.
     */
    protected ArrayStack<Object> stack = new ArrayStack<Object>();


    /**
     * Do we want to use the Context ClassLoader when loading classes
     * for instantiating new objects.  Default is <code>false</code>.
     */
    protected boolean useContextClassLoader = false;


    /**
     * Do we want to use a validating parser.
     */
    protected boolean validating = false;


    /**
     * Warn on missing attributes and elements.
     */
    protected boolean rulesValidation = false;

    
    /**
     * Fake attributes map (attributes are often used for object creation).
     */
    protected Map<Class<?>, List<String>> fakeAttributes = null;


    /**
     * The Log to which most logging calls will be made.
     */
    protected Log log =
        LogFactory.getLog("org.apache.tomcat.util.digester.Digester");


    /**
     * The Log to which all SAX event related logging calls will be made.
     */
    protected Log saxLog =
        LogFactory.getLog("org.apache.tomcat.util.digester.Digester.sax");
    
        
    /** Stacks used for interrule communication, indexed by name String */
    private HashMap<String,ArrayStack<Object>> stacksByName =
        new HashMap<String,ArrayStack<Object>>();
    
    // ------------------------------------------------------------- Properties

    /**
     * Return the currently mapped namespace URI for the specified prefix,
     * if any; otherwise return <code>null</code>.  These mappings come and
     * go dynamically as the document is parsed.
     *
     * @param prefix Prefix to look up
     */
    public String findNamespaceURI(String prefix) {
        
        ArrayStack<String> stack = namespaces.get(prefix);
        if (stack == null) {
            return (null);
        }
        try {
            return stack.peek();
        } catch (EmptyStackException e) {
            return (null);
        }

    }


    /**
     * Return the class loader to be used for instantiating application objects
     * when required.  This is determined based upon the following rules:
     * <ul>
     * <li>The class loader set by <code>setClassLoader()</code>, if any</li>
     * <li>The thread context class loader, if it exists and the
     *     <code>useContextClassLoader</code> property is set to true</li>
     * <li>The class loader used to load the Digester class itself.
     * </ul>
     */
    public ClassLoader getClassLoader() {

        if (this.classLoader != null) {
            return (this.classLoader);
        }
        if (this.useContextClassLoader) {
            ClassLoader classLoader =
                    Thread.currentThread().getContextClassLoader();
            if (classLoader != null) {
                return (classLoader);
            }
        }
        return (this.getClass().getClassLoader());

    }


    /**
     * Set the class loader to be used for instantiating application objects
     * when required.
     *
     * @param classLoader The new class loader to use, or <code>null</code>
     *  to revert to the standard rules
     */
    public void setClassLoader(ClassLoader classLoader) {

        this.classLoader = classLoader;

    }


    /**
     * Return the current depth of the element stack.
     */
    public int getCount() {

        return (stack.size());

    }


    /**
     * Return the name of the XML element that is currently being processed.
     */
    public String getCurrentElementName() {

        String elementName = match;
        int lastSlash = elementName.lastIndexOf('/');
        if (lastSlash >= 0) {
            elementName = elementName.substring(lastSlash + 1);
        }
        return (elementName);

    }


    /**
     * Return the error handler for this Digester.
     */
    public ErrorHandler getErrorHandler() {

        return (this.errorHandler);

    }


    /**
     * Set the error handler for this Digester.
     *
     * @param errorHandler The new error handler
     */
    public void setErrorHandler(ErrorHandler errorHandler) {

        this.errorHandler = errorHandler;

    }


    /**
     * Return the SAXParserFactory we will use, creating one if necessary.
     * @throws ParserConfigurationException 
     * @throws SAXNotSupportedException 
     * @throws SAXNotRecognizedException 
     */
    public SAXParserFactory getFactory()
    throws SAXNotRecognizedException, SAXNotSupportedException,
    ParserConfigurationException {

        if (factory == null) {
            factory = SAXParserFactory.newInstance();

            factory.setNamespaceAware(namespaceAware);
            // Preserve xmlns attributes
            if (namespaceAware) {
                factory.setFeature(
                        "http://xml.org/sax/features/namespace-prefixes",
                        true);
            }

            factory.setValidating(validating);
            if (validating) {
                // Enable DTD validation
                factory.setFeature(
                        "http://xml.org/sax/features/validation",
                        true);
                // Enable schema validation
                factory.setFeature(
                        "http://apache.org/xml/features/validation/schema",
                        true);
            }
        }
        return (factory);

    }


    /**
     * Returns a flag indicating whether the requested feature is supported
     * by the underlying implementation of <code>org.xml.sax.XMLReader</code>.
     * See <a href="http://www.saxproject.org/apidoc/xml/sax/package-summary.html#package-description"
     * http://www.saxproject.org/apidoc/xml/sax/package-summary.html#package-description</a>
     * for information about the standard SAX2 feature flags.
     *
     * @param feature Name of the feature to inquire about
     *
     * @exception ParserConfigurationException if a parser configuration error
     *  occurs
     * @exception SAXNotRecognizedException if the property name is
     *  not recognized
     * @exception SAXNotSupportedException if the property name is
     *  recognized but not supported
     */
    public boolean getFeature(String feature)
        throws ParserConfigurationException, SAXNotRecognizedException,
        SAXNotSupportedException {

        return (getFactory().getFeature(feature));

    }


    /**
     * Sets a flag indicating whether the requested feature is supported
     * by the underlying implementation of <code>org.xml.sax.XMLReader</code>.
     * See <a href="http://www.saxproject.org/apidoc/xml/sax/package-summary.html#package-description"
     * http://www.saxproject.org/apidoc/xml/sax/package-summary.html#package-description</a>
     * for information about the standard SAX2 feature flags.  In order to be
     * effective, this method must be called <strong>before</strong> the
     * <code>getParser()</code> method is called for the first time, either
     * directly or indirectly.
     *
     * @param feature Name of the feature to set the status for
     * @param value The new value for this feature
     *
     * @exception ParserConfigurationException if a parser configuration error
     *  occurs
     * @exception SAXNotRecognizedException if the property name is
     *  not recognized
     * @exception SAXNotSupportedException if the property name is
     *  recognized but not supported
     */
    public void setFeature(String feature, boolean value)
        throws ParserConfigurationException, SAXNotRecognizedException,
        SAXNotSupportedException {

        getFactory().setFeature(feature, value);

    }


    /**
     * Return the current Logger associated with this instance of the Digester
     */
    public Log getLogger() {

        return log;

    }


    /**
     * Set the current logger for this Digester.
     */
    public void setLogger(Log log) {

        this.log = log;

    }

    /**
     * Gets the logger used for logging SAX-related information.
     * <strong>Note</strong> the output is finely grained.
     *
     * @since 1.6
     */
    public Log getSAXLogger() {
        
        return saxLog;
    }
    

    /**
     * Sets the logger used for logging SAX-related information.
     * <strong>Note</strong> the output is finely grained.
     * @param saxLog Log, not null
     *
     * @since 1.6
     */    
    public void setSAXLogger(Log saxLog) {
    
        this.saxLog = saxLog;
    }

    /**
     * Return the current rule match path
     */
    public String getMatch() {

        return match;

    }


    /**
     * Return the "namespace aware" flag for parsers we create.
     */
    public boolean getNamespaceAware() {

        return (this.namespaceAware);

    }


    /**
     * Set the "namespace aware" flag for parsers we create.
     *
     * @param namespaceAware The new "namespace aware" flag
     */
    public void setNamespaceAware(boolean namespaceAware) {

        this.namespaceAware = namespaceAware;

    }

    
    /**
     * Set the public id of the current file being parse.
     * @param publicId the DTD/Schema public's id.
     */
    public void setPublicId(String publicId){
        this.publicId = publicId;
    }
    
    
    /**
     * Return the public identifier of the DTD we are currently
     * parsing under, if any.
     */
    public String getPublicId() {

        return (this.publicId);

    }


    /**
     * Return the namespace URI that will be applied to all subsequently
     * added <code>Rule</code> objects.
     */
    public String getRuleNamespaceURI() {

        return (getRules().getNamespaceURI());

    }


    /**
     * Set the namespace URI that will be applied to all subsequently
     * added <code>Rule</code> objects.
     *
     * @param ruleNamespaceURI Namespace URI that must match on all
     *  subsequently added rules, or <code>null</code> for matching
     *  regardless of the current namespace URI
     */
    public void setRuleNamespaceURI(String ruleNamespaceURI) {

        getRules().setNamespaceURI(ruleNamespaceURI);

    }


    /**
     * Return the SAXParser we will use to parse the input stream.  If there
     * is a problem creating the parser, return <code>null</code>.
     */
    public SAXParser getParser() {

        // Return the parser we already created (if any)
        if (parser != null) {
            return (parser);
        }

        // Create a new parser
        try {
            parser = getFactory().newSAXParser();
        } catch (Exception e) {
            log.error("Digester.getParser: ", e);
            return (null);
        }

        return (parser);

    }


    /**
     * Return the current value of the specified property for the underlying
     * <code>XMLReader</code> implementation.
     * See <a href="http://www.saxproject.org/apidoc/xml/sax/package-summary.html#package-description"
     * http://www.saxproject.org/apidoc/xml/sax/package-summary.html#package-description</a>
     * for information about the standard SAX2 properties.
     *
     * @param property Property name to be retrieved
     *
     * @exception SAXNotRecognizedException if the property name is
     *  not recognized
     * @exception SAXNotSupportedException if the property name is
     *  recognized but not supported
     */
    public Object getProperty(String property)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        return (getParser().getProperty(property));

    }


    /**
     * Set the current value of the specified property for the underlying
     * <code>XMLReader</code> implementation.
     * See <a href="http://www.saxproject.org/apidoc/xml/sax/package-summary.html#package-description"
     * http://www.saxproject.org/apidoc/xml/sax/package-summary.html#package-description</a>
     * for information about the standard SAX2 properties.
     *
     * @param property Property name to be set
     * @param value Property value to be set
     *
     * @exception SAXNotRecognizedException if the property name is
     *  not recognized
     * @exception SAXNotSupportedException if the property name is
     *  recognized but not supported
     */
    public void setProperty(String property, Object value)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        getParser().setProperty(property, value);

    }


    /**
     * Return the <code>Rules</code> implementation object containing our
     * rules collection and associated matching policy.  If none has been
     * established, a default implementation will be created and returned.
     */
    public Rules getRules() {

        if (this.rules == null) {
            this.rules = new RulesBase();
            this.rules.setDigester(this);
        }
        return (this.rules);

    }

    
    /**
     * Set the <code>Rules</code> implementation object containing our
     * rules collection and associated matching policy.
     *
     * @param rules New Rules implementation
     */
    public void setRules(Rules rules) {

        this.rules = rules;
        this.rules.setDigester(this);

    }


    /**
     * Return the boolean as to whether the context classloader should be used.
     */
    public boolean getUseContextClassLoader() {

        return useContextClassLoader;

    }


    /**
     * Determine whether to use the Context ClassLoader (the one found by
     * calling <code>Thread.currentThread().getContextClassLoader()</code>)
     * to resolve/load classes that are defined in various rules.  If not
     * using Context ClassLoader, then the class-loading defaults to
     * using the calling-class' ClassLoader.
     *
     * @param use determines whether to use Context ClassLoader.
     */
    public void setUseContextClassLoader(boolean use) {

        useContextClassLoader = use;

    }


    /**
     * Return the validating parser flag.
     */
    public boolean getValidating() {

        return (this.validating);

    }


    /**
     * Set the validating parser flag.  This must be called before
     * <code>parse()</code> is called the first time.
     *
     * @param validating The new validating parser flag.
     */
    public void setValidating(boolean validating) {

        this.validating = validating;

    }


    /**
     * Return the rules validation flag.
     */
    public boolean getRulesValidation() {

        return (this.rulesValidation);

    }


    /**
     * Set the rules validation flag.  This must be called before
     * <code>parse()</code> is called the first time.
     *
     * @param rulesValidation The new rules validation flag.
     */
    public void setRulesValidation(boolean rulesValidation) {

        this.rulesValidation = rulesValidation;

    }


    /**
     * Return the fake attributes list.
     */
    public Map<Class<?>, List<String>> getFakeAttributes() {

        return (this.fakeAttributes);

    }


    /**
     * Determine if an attribute is a fake attribute.
     */
    public boolean isFakeAttribute(Object object, String name) {

        if (fakeAttributes == null) {
            return false;
        }
        List<String> result = fakeAttributes.get(object.getClass());
        if (result == null) {
            result = fakeAttributes.get(Object.class);
        }
        if (result == null) {
            return false;
        } else {
            return result.contains(name);
        }

    }


    /**
     * Set the fake attributes.
     *
     * @param fakeAttributes The new fake attributes.
     */
    public void setFakeAttributes(Map<Class<?>, List<String>> fakeAttributes) {

        this.fakeAttributes = fakeAttributes;

    }


    /**
     * Return the XMLReader to be used for parsing the input document.
     *
     * FIX ME: there is a bug in JAXP/XERCES that prevent the use of a 
     * parser that contains a schema with a DTD.
     * @exception SAXException if no XMLReader can be instantiated
     */
    public XMLReader getXMLReader() throws SAXException {
        if (reader == null){
            reader = getParser().getXMLReader();
        }        
                               
        reader.setDTDHandler(this);           
        reader.setContentHandler(this);        
        
        if (entityResolver == null){
            reader.setEntityResolver(this);
        } else {
            reader.setEntityResolver(entityResolver);           
        }
        
        reader.setProperty(
                "http://xml.org/sax/properties/lexical-handler", this);

        reader.setErrorHandler(this);
        return reader;
    }

    // ------------------------------------------------- ContentHandler Methods


    /**
     * Process notification of character data received from the body of
     * an XML element.
     *
     * @param buffer The characters from the XML document
     * @param start Starting offset into the buffer
     * @param length Number of characters from the buffer
     *
     * @exception SAXException if a parsing error is to be reported
     */
    @Override
    public void characters(char buffer[], int start, int length)
            throws SAXException {

        if (saxLog.isDebugEnabled()) {
            saxLog.debug("characters(" + new String(buffer, start, length) + ")");
        }

        bodyText.append(buffer, start, length);

    }


    /**
     * Process notification of the end of the document being reached.
     *
     * @exception SAXException if a parsing error is to be reported
     */
    @Override
    public void endDocument() throws SAXException {

        if (saxLog.isDebugEnabled()) {
            if (getCount() > 1) {
                saxLog.debug("endDocument():  " + getCount() +
                             " elements left");
            } else {
                saxLog.debug("endDocument()");
            }
        }

        while (getCount() > 1) {
            pop();
        }

        // Fire "finish" events for all defined rules
        Iterator<Rule> rules = getRules().rules().iterator();
        while (rules.hasNext()) {
            Rule rule = rules.next();
            try {
                rule.finish();
            } catch (Exception e) {
                log.error("Finish event threw exception", e);
                throw createSAXException(e);
            } catch (Error e) {
                log.error("Finish event threw error", e);
                throw e;
            }
        }

        // Perform final cleanup
        clear();

    }


    /**
     * Process notification of the end of an XML element being reached.
     *
     * @param namespaceURI - The Namespace URI, or the empty string if the
     *   element has no Namespace URI or if Namespace processing is not
     *   being performed.
     * @param localName - The local name (without prefix), or the empty
     *   string if Namespace processing is not being performed.
     * @param qName - The qualified XML 1.0 name (with prefix), or the
     *   empty string if qualified names are not available.
     * @exception SAXException if a parsing error is to be reported
     */
    @Override
    public void endElement(String namespaceURI, String localName,
                           String qName) throws SAXException {

        boolean debug = log.isDebugEnabled();

        if (debug) {
            if (saxLog.isDebugEnabled()) {
                saxLog.debug("endElement(" + namespaceURI + "," + localName +
                        "," + qName + ")");
            }
            log.debug("  match='" + match + "'");
            log.debug("  bodyText='" + bodyText + "'");
        }

        // Parse system properties
        bodyText = updateBodyText(bodyText);

        // the actual element name is either in localName or qName, depending 
        // on whether the parser is namespace aware
        String name = localName;
        if ((name == null) || (name.length() < 1)) {
            name = qName;
        }

        // Fire "body" events for all relevant rules
        List<Rule> rules = matches.pop();
        if ((rules != null) && (rules.size() > 0)) {
            String bodyText = this.bodyText.toString();
            for (int i = 0; i < rules.size(); i++) {
                try {
                    Rule rule = rules.get(i);
                    if (debug) {
                        log.debug("  Fire body() for " + rule);
                    }
                    rule.body(namespaceURI, name, bodyText);
                } catch (Exception e) {
                    log.error("Body event threw exception", e);
                    throw createSAXException(e);
                } catch (Error e) {
                    log.error("Body event threw error", e);
                    throw e;
                }
            }
        } else {
            if (debug) {
                log.debug("  No rules found matching '" + match + "'.");
            }
            if (rulesValidation) {
                log.warn("  No rules found matching '" + match + "'.");
            }
        }

        // Recover the body text from the surrounding element
        bodyText = bodyTexts.pop();

        // Fire "end" events for all relevant rules in reverse order
        if (rules != null) {
            for (int i = 0; i < rules.size(); i++) {
                int j = (rules.size() - i) - 1;
                try {
                    Rule rule = rules.get(j);
                    if (debug) {
                        log.debug("  Fire end() for " + rule);
                    }
                    rule.end(namespaceURI, name);
                } catch (Exception e) {
                    log.error("End event threw exception", e);
                    throw createSAXException(e);
                } catch (Error e) {
                    log.error("End event threw error", e);
                    throw e;
                }
            }
        }

        // Recover the previous match expression
        int slash = match.lastIndexOf('/');
        if (slash >= 0) {
            match = match.substring(0, slash);
        } else {
            match = "";
        }

    }


    /**
     * Process notification that a namespace prefix is going out of scope.
     *
     * @param prefix Prefix that is going out of scope
     *
     * @exception SAXException if a parsing error is to be reported
     */
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {

        if (saxLog.isDebugEnabled()) {
            saxLog.debug("endPrefixMapping(" + prefix + ")");
        }

        // Deregister this prefix mapping
        ArrayStack<String> stack = namespaces.get(prefix);
        if (stack == null) {
            return;
        }
        try {
            stack.pop();
            if (stack.empty())
                namespaces.remove(prefix);
        } catch (EmptyStackException e) {
            throw createSAXException("endPrefixMapping popped too many times");
        }

    }


    /**
     * Process notification of ignorable whitespace received from the body of
     * an XML element.
     *
     * @param buffer The characters from the XML document
     * @param start Starting offset into the buffer
     * @param len Number of characters from the buffer
     *
     * @exception SAXException if a parsing error is to be reported
     */
    @Override
    public void ignorableWhitespace(char buffer[], int start, int len)
            throws SAXException {

        if (saxLog.isDebugEnabled()) {
            saxLog.debug("ignorableWhitespace(" +
                    new String(buffer, start, len) + ")");
        }

        // No processing required

    }


    /**
     * Process notification of a processing instruction that was encountered.
     *
     * @param target The processing instruction target
     * @param data The processing instruction data (if any)
     *
     * @exception SAXException if a parsing error is to be reported
     */
    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {

        if (saxLog.isDebugEnabled()) {
            saxLog.debug("processingInstruction('" + target + "','" + data + "')");
        }

        // No processing is required

    }


    /**
     * Gets the document locator associated with our parser.
     *
     * @return the Locator supplied by the document parser
     */
    public Locator getDocumentLocator() {

        return locator;

    }

    /**
     * Sets the document locator associated with our parser.
     *
     * @param locator The new locator
     */
    @Override
    public void setDocumentLocator(Locator locator) {

        if (saxLog.isDebugEnabled()) {
            saxLog.debug("setDocumentLocator(" + locator + ")");
        }

        this.locator = locator;

    }


    /**
     * Process notification of a skipped entity.
     *
     * @param name Name of the skipped entity
     *
     * @exception SAXException if a parsing error is to be reported
     */
    @Override
    public void skippedEntity(String name) throws SAXException {

        if (saxLog.isDebugEnabled()) {
            saxLog.debug("skippedEntity(" + name + ")");
        }

        // No processing required

    }


    /**
     * Process notification of the beginning of the document being reached.
     *
     * @exception SAXException if a parsing error is to be reported
     */
    @Override
    public void startDocument() throws SAXException {

        if (saxLog.isDebugEnabled()) {
            saxLog.debug("startDocument()");
        }

        // ensure that the digester is properly configured, as 
        // the digester could be used as a SAX ContentHandler
        // rather than via the parse() methods.
        configure();
    }


    /**
     * Process notification of the start of an XML element being reached.
     *
     * @param namespaceURI The Namespace URI, or the empty string if the element
     *   has no Namespace URI or if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty
     *   string if Namespace processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty
     *   string if qualified names are not available.\
     * @param list The attributes attached to the element. If there are
     *   no attributes, it shall be an empty Attributes object. 
     * @exception SAXException if a parsing error is to be reported
     */
    @Override
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes list)
            throws SAXException {
        boolean debug = log.isDebugEnabled();
        
        if (saxLog.isDebugEnabled()) {
            saxLog.debug("startElement(" + namespaceURI + "," + localName + "," +
                    qName + ")");
        }
        
        // Parse system properties
        list = updateAttributes(list);
        
        // Save the body text accumulated for our surrounding element
        bodyTexts.push(bodyText);
        bodyText = new StringBuilder();

        // the actual element name is either in localName or qName, depending 
        // on whether the parser is namespace aware
        String name = localName;
        if ((name == null) || (name.length() < 1)) {
            name = qName;
        }

        // Compute the current matching rule
        StringBuilder sb = new StringBuilder(match);
        if (match.length() > 0) {
            sb.append('/');
        }
        sb.append(name);
        match = sb.toString();
        if (debug) {
            log.debug("  New match='" + match + "'");
        }

        // Fire "begin" events for all relevant rules
        List<Rule> rules = getRules().match(namespaceURI, match);
        matches.push(rules);
        if ((rules != null) && (rules.size() > 0)) {
            for (int i = 0; i < rules.size(); i++) {
                try {
                    Rule rule = rules.get(i);
                    if (debug) {
                        log.debug("  Fire begin() for " + rule);
                    }
                    rule.begin(namespaceURI, name, list);
                } catch (Exception e) {
                    log.error("Begin event threw exception", e);
                    throw createSAXException(e);
                } catch (Error e) {
                    log.error("Begin event threw error", e);
                    throw e;
                }
            }
        } else {
            if (debug) {
                log.debug("  No rules found matching '" + match + "'.");
            }
        }

    }


    /**
     * Process notification that a namespace prefix is coming in to scope.
     *
     * @param prefix Prefix that is being declared
     * @param namespaceURI Corresponding namespace URI being mapped to
     *
     * @exception SAXException if a parsing error is to be reported
     */
    @Override
    public void startPrefixMapping(String prefix, String namespaceURI)
            throws SAXException {

        if (saxLog.isDebugEnabled()) {
            saxLog.debug("startPrefixMapping(" + prefix + "," + namespaceURI + ")");
        }

        // Register this prefix mapping
        ArrayStack<String> stack = namespaces.get(prefix);
        if (stack == null) {
            stack = new ArrayStack<String>();
            namespaces.put(prefix, stack);
        }
        stack.push(namespaceURI);

    }


    // ----------------------------------------------------- DTDHandler Methods


    /**
     * Receive notification of a notation declaration event.
     *
     * @param name The notation name
     * @param publicId The public identifier (if any)
     * @param systemId The system identifier (if any)
     */
    @Override
    public void notationDecl(String name, String publicId, String systemId) {

        if (saxLog.isDebugEnabled()) {
            saxLog.debug("notationDecl(" + name + "," + publicId + "," +
                    systemId + ")");
        }

    }


    /**
     * Receive notification of an unparsed entity declaration event.
     *
     * @param name The unparsed entity name
     * @param publicId The public identifier (if any)
     * @param systemId The system identifier (if any)
     * @param notation The name of the associated notation
     */
    @Override
    public void unparsedEntityDecl(String name, String publicId,
                                   String systemId, String notation) {

        if (saxLog.isDebugEnabled()) {
            saxLog.debug("unparsedEntityDecl(" + name + "," + publicId + "," +
                    systemId + "," + notation + ")");
        }

    }


    // ----------------------------------------------- EntityResolver Methods

    /**
     * Set the <code>EntityResolver</code> used by SAX when resolving
     * public id and system id.
     * This must be called before the first call to <code>parse()</code>.
     * @param entityResolver a class that implement the <code>EntityResolver</code> interface.
     */
    public void setEntityResolver(EntityResolver entityResolver){
        this.entityResolver = entityResolver;
    }
    
    
    /**
     * Return the Entity Resolver used by the SAX parser.
     * @return Return the Entity Resolver used by the SAX parser.
     */
    public EntityResolver getEntityResolver(){
        return entityResolver;
    }

    @Override
    public InputSource resolveEntity(String name, String publicId,
            String baseURI, String systemId) throws SAXException, IOException {

        if (saxLog.isDebugEnabled()) {
            saxLog.debug("resolveEntity('" + publicId + "', '" + systemId +
                    "', '" + baseURI + "')");
        }
        
        // Has this system identifier been registered?
        String entityURL = null;
        if (publicId != null) {
            entityURL = entityValidator.get(publicId);
        }
         
        if (entityURL == null) { 
            if (systemId == null) {
                // cannot resolve
                if (log.isDebugEnabled()) {
                    log.debug(" Cannot resolve entity: '" + publicId + "'");
                }
                return (null);
                
            } else {
                // try to resolve using system ID
                if (log.isDebugEnabled()) {
                    log.debug(" Trying to resolve using system ID '" +
                            systemId + "'");
                } 
                entityURL = systemId;
                // resolve systemId against baseURI if it is not absolute
                if (baseURI != null) {
                    try {
                        URI uri = new URI(systemId);
                        if (!uri.isAbsolute()) {
                            entityURL = new URI(baseURI).resolve(uri).toString();
                        }
                    } catch (URISyntaxException e) {
                        if (log.isDebugEnabled()) {
                            log.debug("Invalid URI '" + baseURI + "' or '" +
                                    systemId + "'");
                        }
                    }
                }
            }
        }
        
        // Return an input source to our alternative URL
        if (log.isDebugEnabled()) {
            log.debug(" Resolving to alternate DTD '" + entityURL + "'");
        }  
        
        try {
            return (new InputSource(entityURL));
        } catch (Exception e) {
            throw createSAXException(e);
        }
    }


    // ----------------------------------------------- LexicalHandler Methods

    @Override
    public void startDTD(String name, String publicId, String systemId)
            throws SAXException {
        setPublicId(publicId);
    }


    // ------------------------------------------------- ErrorHandler Methods

    /**
     * Forward notification of a parsing error to the application supplied
     * error handler (if any).
     *
     * @param exception The error information
     *
     * @exception SAXException if a parsing exception occurs
     */
    @Override
    public void error(SAXParseException exception) throws SAXException {

        log.error("Parse Error at line " + exception.getLineNumber() +
                " column " + exception.getColumnNumber() + ": " +
                exception.getMessage(), exception);
        if (errorHandler != null) {
            errorHandler.error(exception);
        }

    }


    /**
     * Forward notification of a fatal parsing error to the application
     * supplied error handler (if any).
     *
     * @param exception The fatal error information
     *
     * @exception SAXException if a parsing exception occurs
     */
    @Override
    public void fatalError(SAXParseException exception) throws SAXException {

        log.error("Parse Fatal Error at line " + exception.getLineNumber() +
                " column " + exception.getColumnNumber() + ": " +
                exception.getMessage(), exception);
        if (errorHandler != null) {
            errorHandler.fatalError(exception);
        }

    }


    /**
     * Forward notification of a parse warning to the application supplied
     * error handler (if any).
     *
     * @param exception The warning information
     *
     * @exception SAXException if a parsing exception occurs
     */
    @Override
    public void warning(SAXParseException exception) throws SAXException {
         if (errorHandler != null) {
            log.warn("Parse Warning Error at line " + exception.getLineNumber() +
                " column " + exception.getColumnNumber() + ": " +
                exception.getMessage(), exception);
            
            errorHandler.warning(exception);
        }

    }


    // ------------------------------------------------------- Public Methods

    /**
     * Parse the content of the specified file using this Digester.  Returns
     * the root element from the object stack (if any).
     *
     * @param file File containing the XML data to be parsed
     *
     * @exception IOException if an input/output error occurs
     * @exception SAXException if a parsing exception occurs
     */
    public Object parse(File file) throws IOException, SAXException {

        configure();
        InputSource input = new InputSource(new FileInputStream(file));
        input.setSystemId("file://" + file.getAbsolutePath());
        getXMLReader().parse(input);
        return (root);

    }   
    /**
     * Parse the content of the specified input source using this Digester.
     * Returns the root element from the object stack (if any).
     *
     * @param input Input source containing the XML data to be parsed
     *
     * @exception IOException if an input/output error occurs
     * @exception SAXException if a parsing exception occurs
     */
    public Object parse(InputSource input) throws IOException, SAXException {
 
        configure();
        getXMLReader().parse(input);
        return (root);

    }


    /**
     * Parse the content of the specified input stream using this Digester.
     * Returns the root element from the object stack (if any).
     *
     * @param input Input stream containing the XML data to be parsed
     *
     * @exception IOException if an input/output error occurs
     * @exception SAXException if a parsing exception occurs
     */
    public Object parse(InputStream input) throws IOException, SAXException {

        configure();
        InputSource is = new InputSource(input);
        getXMLReader().parse(is);
        return (root);

    }


    /**
     * Parse the content of the specified reader using this Digester.
     * Returns the root element from the object stack (if any).
     *
     * @param reader Reader containing the XML data to be parsed
     *
     * @exception IOException if an input/output error occurs
     * @exception SAXException if a parsing exception occurs
     */
    public Object parse(Reader reader) throws IOException, SAXException {

        configure();
        InputSource is = new InputSource(reader);
        getXMLReader().parse(is);
        return (root);

    }


    /**
     * Parse the content of the specified URI using this Digester.
     * Returns the root element from the object stack (if any).
     *
     * @param uri URI containing the XML data to be parsed
     *
     * @exception IOException if an input/output error occurs
     * @exception SAXException if a parsing exception occurs
     */
    public Object parse(String uri) throws IOException, SAXException {

        configure();
        InputSource is = new InputSource(uri);
        getXMLReader().parse(is);
        return (root);

    }


    /**
     * <p>Register the specified DTD URL for the specified public identifier.
     * This must be called before the first call to <code>parse()</code>.
     * </p><p>
     * <code>Digester</code> contains an internal <code>EntityResolver</code>
     * implementation. This maps <code>PUBLICID</code>'s to URLs 
     * (from which the resource will be loaded). A common use case for this
     * method is to register local URLs (possibly computed at runtime by a 
     * classloader) for DTDs. This allows the performance advantage of using
     * a local version without having to ensure every <code>SYSTEM</code>
     * URI on every processed xml document is local. This implementation provides
     * only basic functionality. If more sophisticated features are required,
     * using {@link #setEntityResolver} to set a custom resolver is recommended.
     * </p><p>
     * <strong>Note:</strong> This method will have no effect when a custom 
     * <code>EntityResolver</code> has been set. (Setting a custom 
     * <code>EntityResolver</code> overrides the internal implementation.) 
     * </p>
     * @param publicId Public identifier of the DTD to be resolved
     * @param entityURL The URL to use for reading this DTD
     */
    public void register(String publicId, String entityURL) {

        if (log.isDebugEnabled()) {
            log.debug("register('" + publicId + "', '" + entityURL + "'");
        }
        entityValidator.put(publicId, entityURL);

    }


    // --------------------------------------------------------- Rule Methods


    /**
     * <p>Register a new Rule matching the specified pattern.
     * This method sets the <code>Digester</code> property on the rule.</p>
     *
     * @param pattern Element matching pattern
     * @param rule Rule to be registered
     */
    public void addRule(String pattern, Rule rule) {

        rule.setDigester(this);
        getRules().add(pattern, rule);

    }


    /**
     * Register a set of Rule instances defined in a RuleSet.
     *
     * @param ruleSet The RuleSet instance to configure from
     */
    public void addRuleSet(RuleSet ruleSet) {

        String oldNamespaceURI = getRuleNamespaceURI();
        String newNamespaceURI = ruleSet.getNamespaceURI();
        if (log.isDebugEnabled()) {
            if (newNamespaceURI == null) {
                log.debug("addRuleSet() with no namespace URI");
            } else {
                log.debug("addRuleSet() with namespace URI " + newNamespaceURI);
            }
        }
        setRuleNamespaceURI(newNamespaceURI);
        ruleSet.addRuleInstances(this);
        setRuleNamespaceURI(oldNamespaceURI);

    }


    /**
     * Add an "call method" rule for a method which accepts no arguments.
     *
     * @param pattern Element matching pattern
     * @param methodName Method name to be called
     * @see CallMethodRule
     */
    public void addCallMethod(String pattern, String methodName) {

        addRule(
                pattern,
                new CallMethodRule(methodName));

    }

    /**
     * Add an "call method" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param methodName Method name to be called
     * @param paramCount Number of expected parameters (or zero
     *  for a single parameter from the body of this element)
     * @see CallMethodRule
     */
    public void addCallMethod(String pattern, String methodName,
                              int paramCount) {

        addRule(pattern,
                new CallMethodRule(methodName, paramCount));

    }


    /**
     * Add an "call method" rule for the specified parameters.
     * If <code>paramCount</code> is set to zero the rule will use
     * the body of the matched element as the single argument of the
     * method, unless <code>paramTypes</code> is null or empty, in this
     * case the rule will call the specified method with no arguments.
     *
     * @param pattern Element matching pattern
     * @param methodName Method name to be called
     * @param paramCount Number of expected parameters (or zero
     *  for a single parameter from the body of this element)
     * @param paramTypes Set of Java class names for the types
     *  of the expected parameters
     *  (if you wish to use a primitive type, specify the corresponding
     *  Java wrapper class instead, such as <code>java.lang.Boolean</code>
     *  for a <code>boolean</code> parameter)
     * @see CallMethodRule
     */
    public void addCallMethod(String pattern, String methodName,
                              int paramCount, String paramTypes[]) {

        addRule(pattern,
                new CallMethodRule(
                                    methodName,
                                    paramCount, 
                                    paramTypes));

    }


    /**
     * Add an "call method" rule for the specified parameters.
     * If <code>paramCount</code> is set to zero the rule will use
     * the body of the matched element as the single argument of the
     * method, unless <code>paramTypes</code> is null or empty, in this
     * case the rule will call the specified method with no arguments.
     *
     * @param pattern Element matching pattern
     * @param methodName Method name to be called
     * @param paramCount Number of expected parameters (or zero
     *  for a single parameter from the body of this element)
     * @param paramTypes The Java class names of the arguments
     *  (if you wish to use a primitive type, specify the corresponding
     *  Java wrapper class instead, such as <code>java.lang.Boolean</code>
     *  for a <code>boolean</code> parameter)
     * @see CallMethodRule
     */
    public void addCallMethod(String pattern, String methodName,
                              int paramCount, Class<?> paramTypes[]) {

        addRule(pattern,
                new CallMethodRule(
                                    methodName,
                                    paramCount, 
                                    paramTypes));

    }


    /**
     * Add a "call parameter" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param paramIndex Zero-relative parameter index to set
     *  (from the body of this element)
     * @see CallParamRule
     */
    public void addCallParam(String pattern, int paramIndex) {

        addRule(pattern,
                new CallParamRule(paramIndex));

    }


    /**
     * Add a "call parameter" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param paramIndex Zero-relative parameter index to set
     *  (from the specified attribute)
     * @param attributeName Attribute whose value is used as the
     *  parameter value
     * @see CallParamRule
     */
    public void addCallParam(String pattern, int paramIndex,
                             String attributeName) {

        addRule(pattern,
                new CallParamRule(paramIndex, attributeName));

    }


    /**
     * Add a "call parameter" rule.
     * This will either take a parameter from the stack 
     * or from the current element body text. 
     *
     * @param paramIndex The zero-relative parameter number
     * @param fromStack Should the call parameter be taken from the top of the stack?
     * @see CallParamRule
     */    
    public void addCallParam(String pattern, int paramIndex, boolean fromStack) {
    
        addRule(pattern,
                new CallParamRule(paramIndex, fromStack));
      
    }

    /**
     * Add a "call parameter" rule that sets a parameter from the stack.
     * This takes a parameter from the given position on the stack.
     *
     * @param paramIndex The zero-relative parameter number
     * @param stackIndex set the call parameter to the stackIndex'th object down the stack,
     * where 0 is the top of the stack, 1 the next element down and so on
     * @see CallMethodRule
     */    
    public void addCallParam(String pattern, int paramIndex, int stackIndex) {
    
        addRule(pattern,
                new CallParamRule(paramIndex, stackIndex));
      
    }
    
    /**
     * Add a "call parameter" rule that sets a parameter from the current 
     * <code>Digester</code> matching path.
     * This is sometimes useful when using rules that support wildcards.
     *
     * @param pattern the pattern that this rule should match
     * @param paramIndex The zero-relative parameter number
     * @see CallMethodRule
     */
    public void addCallParamPath(String pattern,int paramIndex) {
        addRule(pattern, new PathCallParamRule(paramIndex));
    }
    
    /**
     * Add a "call parameter" rule that sets a parameter from a 
     * caller-provided object. This can be used to pass constants such as
     * strings to methods; it can also be used to pass mutable objects,
     * providing ways for objects to do things like "register" themselves
     * with some shared object.
     * <p>
     * Note that when attempting to locate a matching method to invoke,
     * the true type of the paramObj is used, so that despite the paramObj
     * being passed in here as type Object, the target method can declare
     * its parameters as being the true type of the object (or some ancestor
     * type, according to the usual type-conversion rules).
     *
     * @param paramIndex The zero-relative parameter number
     * @param paramObj Any arbitrary object to be passed to the target
     * method.
     * @see CallMethodRule
     *
     * @since 1.6
     */    
    public void addObjectParam(String pattern, int paramIndex, 
                               Object paramObj) {
    
        addRule(pattern,
                new ObjectParamRule(paramIndex, paramObj));
      
    }
    
    /**
     * Add a "factory create" rule for the specified parameters.
     * Exceptions thrown during the object creation process will be propagated.
     *
     * @param pattern Element matching pattern
     * @param className Java class name of the object creation factory class
     * @see FactoryCreateRule
     */
    public void addFactoryCreate(String pattern, String className) {

        addFactoryCreate(pattern, className, false);

    }


    /**
     * Add a "factory create" rule for the specified parameters.
     * Exceptions thrown during the object creation process will be propagated.
     *
     * @param pattern Element matching pattern
     * @param clazz Java class of the object creation factory class
     * @see FactoryCreateRule
     */
    public void addFactoryCreate(String pattern, Class<?> clazz) {

        addFactoryCreate(pattern, clazz, false);

    }


    /**
     * Add a "factory create" rule for the specified parameters.
     * Exceptions thrown during the object creation process will be propagated.
     *
     * @param pattern Element matching pattern
     * @param className Java class name of the object creation factory class
     * @param attributeName Attribute name which, if present, overrides the
     *  value specified by <code>className</code>
     * @see FactoryCreateRule
     */
    public void addFactoryCreate(String pattern, String className,
                                 String attributeName) {

        addFactoryCreate(pattern, className, attributeName, false);

    }


    /**
     * Add a "factory create" rule for the specified parameters.
     * Exceptions thrown during the object creation process will be propagated.
     *
     * @param pattern Element matching pattern
     * @param clazz Java class of the object creation factory class
     * @param attributeName Attribute name which, if present, overrides the
     *  value specified by <code>className</code>
     * @see FactoryCreateRule
     */
    public void addFactoryCreate(String pattern, Class<?> clazz,
                                 String attributeName) {

        addFactoryCreate(pattern, clazz, attributeName, false);

    }


    /**
     * Add a "factory create" rule for the specified parameters.
     * Exceptions thrown during the object creation process will be propagated.
     *
     * @param pattern Element matching pattern
     * @param creationFactory Previously instantiated ObjectCreationFactory
     *  to be utilized
     * @see FactoryCreateRule
     */
    public void addFactoryCreate(String pattern,
                                 ObjectCreationFactory creationFactory) {

        addFactoryCreate(pattern, creationFactory, false);

    }

    /**
     * Add a "factory create" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param className Java class name of the object creation factory class
     * @param ignoreCreateExceptions when <code>true</code> any exceptions thrown during
     * object creation will be ignored.
     * @see FactoryCreateRule
     */
    public void addFactoryCreate(
                                    String pattern, 
                                    String className,
                                    boolean ignoreCreateExceptions) {

        addRule(
                pattern,
                new FactoryCreateRule(className, ignoreCreateExceptions));

    }


    /**
     * Add a "factory create" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param clazz Java class of the object creation factory class
     * @param ignoreCreateExceptions when <code>true</code> any exceptions thrown during
     * object creation will be ignored.
     * @see FactoryCreateRule
     */
    public void addFactoryCreate(
                                    String pattern, 
                                    Class<?> clazz,
                                    boolean ignoreCreateExceptions) {

        addRule(
                pattern,
                new FactoryCreateRule(clazz, ignoreCreateExceptions));

    }


    /**
     * Add a "factory create" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param className Java class name of the object creation factory class
     * @param attributeName Attribute name which, if present, overrides the
     *  value specified by <code>className</code>
     * @param ignoreCreateExceptions when <code>true</code> any exceptions thrown during
     * object creation will be ignored.
     * @see FactoryCreateRule
     */
    public void addFactoryCreate(
                                String pattern, 
                                String className,
                                String attributeName,
                                boolean ignoreCreateExceptions) {

        addRule(
                pattern,
                new FactoryCreateRule(className, attributeName, ignoreCreateExceptions));

    }


    /**
     * Add a "factory create" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param clazz Java class of the object creation factory class
     * @param attributeName Attribute name which, if present, overrides the
     *  value specified by <code>className</code>
     * @param ignoreCreateExceptions when <code>true</code> any exceptions thrown during
     * object creation will be ignored.
     * @see FactoryCreateRule
     */
    public void addFactoryCreate(
                                    String pattern, 
                                    Class<?> clazz,
                                    String attributeName,
                                    boolean ignoreCreateExceptions) {

        addRule(
                pattern,
                new FactoryCreateRule(clazz, attributeName, ignoreCreateExceptions));

    }


    /**
     * Add a "factory create" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param creationFactory Previously instantiated ObjectCreationFactory
     *  to be utilized
     * @param ignoreCreateExceptions when <code>true</code> any exceptions thrown during
     * object creation will be ignored.
     * @see FactoryCreateRule
     */
    public void addFactoryCreate(String pattern,
                                 ObjectCreationFactory creationFactory,
                                 boolean ignoreCreateExceptions) {

        creationFactory.setDigester(this);
        addRule(pattern,
                new FactoryCreateRule(creationFactory, ignoreCreateExceptions));

    }

    /**
     * Add an "object create" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param className Java class name to be created
     * @see ObjectCreateRule
     */
    public void addObjectCreate(String pattern, String className) {

        addRule(pattern,
                new ObjectCreateRule(className));

    }


    /**
     * Add an "object create" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param clazz Java class to be created
     * @see ObjectCreateRule
     */
    public void addObjectCreate(String pattern, Class<?> clazz) {

        addRule(pattern,
                new ObjectCreateRule(clazz));

    }


    /**
     * Add an "object create" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param className Default Java class name to be created
     * @param attributeName Attribute name that optionally overrides
     *  the default Java class name to be created
     * @see ObjectCreateRule
     */
    public void addObjectCreate(String pattern, String className,
                                String attributeName) {

        addRule(pattern,
                new ObjectCreateRule(className, attributeName));

    }


    /**
     * Add an "object create" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param attributeName Attribute name that optionally overrides
     * @param clazz Default Java class to be created
     *  the default Java class name to be created
     * @see ObjectCreateRule
     */
    public void addObjectCreate(String pattern,
                                String attributeName,
                                Class<?> clazz) {

        addRule(pattern,
                new ObjectCreateRule(attributeName, clazz));

    }

    /**
     * Add a "set next" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param methodName Method name to call on the parent element
     * @see SetNextRule
     */
    public void addSetNext(String pattern, String methodName) {

        addRule(pattern,
                new SetNextRule(methodName));

    }


    /**
     * Add a "set next" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param methodName Method name to call on the parent element
     * @param paramType Java class name of the expected parameter type
     *  (if you wish to use a primitive type, specify the corresponding
     *  Java wrapper class instead, such as <code>java.lang.Boolean</code>
     *  for a <code>boolean</code> parameter)
     * @see SetNextRule
     */
    public void addSetNext(String pattern, String methodName,
                           String paramType) {

        addRule(pattern,
                new SetNextRule(methodName, paramType));

    }


    /**
     * Add {@link SetRootRule} with the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param methodName Method name to call on the root object
     * @see SetRootRule
     */
    public void addSetRoot(String pattern, String methodName) {

        addRule(pattern,
                new SetRootRule(methodName));

    }


    /**
     * Add {@link SetRootRule} with the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param methodName Method name to call on the root object
     * @param paramType Java class name of the expected parameter type
     * @see SetRootRule
     */
    public void addSetRoot(String pattern, String methodName,
                           String paramType) {

        addRule(pattern,
                new SetRootRule(methodName, paramType));

    }

    /**
     * Add a "set properties" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @see SetPropertiesRule
     */
    public void addSetProperties(String pattern) {

        addRule(pattern,
                new SetPropertiesRule());

    }

    /**
     * Add a "set properties" rule with a single overridden parameter.
     * See {@link SetPropertiesRule#SetPropertiesRule(String attributeName, String propertyName)}
     *
     * @param pattern Element matching pattern
     * @param attributeName map this attribute
     * @param propertyName to this property
     * @see SetPropertiesRule
     */
    public void addSetProperties(
                                String pattern, 
                                String attributeName,
                                String propertyName) {

        addRule(pattern,
                new SetPropertiesRule(attributeName, propertyName));

    }

    /**
     * Add a "set properties" rule with overridden parameters.
     * See {@link SetPropertiesRule#SetPropertiesRule(String [] attributeNames, String [] propertyNames)}
     *
     * @param pattern Element matching pattern
     * @param attributeNames names of attributes with custom mappings
     * @param propertyNames property names these attributes map to
     * @see SetPropertiesRule
     */
    public void addSetProperties(
                                String pattern, 
                                String [] attributeNames,
                                String [] propertyNames) {

        addRule(pattern,
                new SetPropertiesRule(attributeNames, propertyNames));

    }


    /**
     * Add a "set property" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param name Attribute name containing the property name to be set
     * @param value Attribute name containing the property value to set
     * @see SetPropertyRule
     */
    public void addSetProperty(String pattern, String name, String value) {

        addRule(pattern,
                new SetPropertyRule(name, value));

    }


    /**
     * Add a "set top" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param methodName Method name to call on the parent element
     * @see SetTopRule
     */
    public void addSetTop(String pattern, String methodName) {

        addRule(pattern,
                new SetTopRule(methodName));

    }


    /**
     * Add a "set top" rule for the specified parameters.
     *
     * @param pattern Element matching pattern
     * @param methodName Method name to call on the parent element
     * @param paramType Java class name of the expected parameter type
     *  (if you wish to use a primitive type, specify the corresponding
     *  Java wrapper class instead, such as <code>java.lang.Boolean</code>
     *  for a <code>boolean</code> parameter)
     * @see SetTopRule
     */
    public void addSetTop(String pattern, String methodName,
                          String paramType) {

        addRule(pattern,
                new SetTopRule(methodName, paramType));

    }


    // --------------------------------------------------- Object Stack Methods


    /**
     * Clear the current contents of the object stack.
     * <p>
     * Calling this method <i>might</i> allow another document of the same type
     * to be correctly parsed. However this method was not intended for this 
     * purpose. In general, a separate Digester object should be created for
     * each document to be parsed.
     */
    public void clear() {

        match = "";
        bodyTexts.clear();
        params.clear();
        publicId = null;
        stack.clear();
        log = null;
        saxLog = null;
        configured = false;
        
    }

    
    public void reset() {
        root = null;
        setErrorHandler(null);
        clear();
    }


    /**
     * Return the top object on the stack without removing it.  If there are
     * no objects on the stack, return <code>null</code>.
     */
    public Object peek() {

        try {
            return (stack.peek());
        } catch (EmptyStackException e) {
            log.warn("Empty stack (returning null)");
            return (null);
        }

    }


    /**
     * Return the n'th object down the stack, where 0 is the top element
     * and [getCount()-1] is the bottom element.  If the specified index
     * is out of range, return <code>null</code>.
     *
     * @param n Index of the desired element, where 0 is the top of the stack,
     *  1 is the next element down, and so on.
     */
    public Object peek(int n) {

        try {
            return (stack.peek(n));
        } catch (EmptyStackException e) {
            log.warn("Empty stack (returning null)");
            return (null);
        }

    }


    /**
     * Pop the top object off of the stack, and return it.  If there are
     * no objects on the stack, return <code>null</code>.
     */
    public Object pop() {

        try {
            return (stack.pop());
        } catch (EmptyStackException e) {
            log.warn("Empty stack (returning null)");
            return (null);
        }

    }


    /**
     * Push a new object onto the top of the object stack.
     *
     * @param object The new object
     */
    public void push(Object object) {

        if (stack.size() == 0) {
            root = object;
        }
        stack.push(object);

    }

    /**
     * Pushes the given object onto the stack with the given name.
     * If no stack already exists with the given name then one will be created.
     * 
     * @param stackName the name of the stack onto which the object should be pushed
     * @param value the Object to be pushed onto the named stack.
     *
     * @since 1.6
     */
    public void push(String stackName, Object value) {
        ArrayStack<Object> namedStack = stacksByName.get(stackName);
        if (namedStack == null) {
            namedStack = new ArrayStack<Object>();
            stacksByName.put(stackName, namedStack);
        }
        namedStack.push(value);
    }

    /**
     * <p>Pops (gets and removes) the top object from the stack with the given name.</p>
     *
     * <p><strong>Note:</strong> a stack is considered empty
     * if no objects have been pushed onto it yet.</p>
     * 
     * @param stackName the name of the stack from which the top value is to be popped
     * @return the top <code>Object</code> on the stack or or null if the stack is either 
     * empty or has not been created yet
     * @throws EmptyStackException if the named stack is empty
     *
     * @since 1.6
     */
    public Object pop(String stackName) {
        Object result = null;
        ArrayStack<Object> namedStack = stacksByName.get(stackName);
        if (namedStack == null) {
            if (log.isDebugEnabled()) {
                log.debug("Stack '" + stackName + "' is empty");
            }
            throw new EmptyStackException();
            
        } else {
        
            result = namedStack.pop();
        }
        return result;
    }
    
    /**
     * <p>Gets the top object from the stack with the given name.
     * This method does not remove the object from the stack.
     * </p>
     * <p><strong>Note:</strong> a stack is considered empty
     * if no objects have been pushed onto it yet.</p>
     *
     * @param stackName the name of the stack to be peeked
     * @return the top <code>Object</code> on the stack or null if the stack is either 
     * empty or has not been created yet
     * @throws EmptyStackException if the named stack is empty 
     *
     * @since 1.6
     */
    public Object peek(String stackName) {
        Object result = null;
        ArrayStack<Object> namedStack = stacksByName.get(stackName);
        if (namedStack == null ) {
            if (log.isDebugEnabled()) {
                log.debug("Stack '" + stackName + "' is empty");
            }        
            throw new EmptyStackException();
        
        } else {
        
            result = namedStack.peek();
        }
        return result;
    }

    /**
     * <p>Is the stack with the given name empty?</p>
     * <p><strong>Note:</strong> a stack is considered empty
     * if no objects have been pushed onto it yet.</p>
     * @param stackName the name of the stack whose emptiness 
     * should be evaluated
     * @return true if the given stack if empty 
     *
     * @since 1.6
     */
    public boolean isEmpty(String stackName) {
        boolean result = true;
        ArrayStack<Object> namedStack = stacksByName.get(stackName);
        if (namedStack != null ) {
            result = namedStack.isEmpty();
        }
        return result;
    }
    
    /**
     * When the Digester is being used as a SAXContentHandler, 
     * this method allows you to access the root object that has been
     * created after parsing.
     * 
     * @return the root object that has been created after parsing
     *  or null if the digester has not parsed any XML yet.
     */
    public Object getRoot() {
        return root;
    }
    

    // ------------------------------------------------ Parameter Stack Methods


    // ------------------------------------------------------ Protected Methods


    /**
     * <p>
     * Provide a hook for lazy configuration of this <code>Digester</code>
     * instance.  The default implementation does nothing, but subclasses
     * can override as needed.
     * </p>
     *
     * <p>
     * <strong>Note</strong> This method may be called more than once.
     * Once only initialization code should be placed in {@link #initialize}
     * or the code should take responsibility by checking and setting the 
     * {@link #configured} flag.
     * </p>
     */
    protected void configure() {

        // Do not configure more than once
        if (configured) {
            return;
        }

        log = LogFactory.getLog("org.apache.tomcat.util.digester.Digester");
        saxLog = LogFactory.getLog("org.apache.tomcat.util.digester.Digester.sax");

        // Perform lazy configuration as needed
        initialize(); // call hook method for subclasses that want to be initialized once only
        // Nothing else required by default

        // Set the configuration flag to avoid repeating
        configured = true;

    }
    
    /**
     * <p>
     * Provides a hook for lazy initialization of this <code>Digester</code>
     * instance.  
     * The default implementation does nothing, but subclasses
     * can override as needed.
     * Digester (by default) only calls this method once.
     * </p>
     *
     * <p>
     * <strong>Note</strong> This method will be called by {@link #configure} 
     * only when the {@link #configured} flag is false. 
     * Subclasses that override <code>configure</code> or who set <code>configured</code>
     * may find that this method may be called more than once.
     * </p>
     *
     * @since 1.6
     */
    protected void initialize() {

        // Perform lazy initialization as needed
        // Nothing required by default

    }    

    // -------------------------------------------------------- Package Methods


    /**
     * Return the set of DTD URL registrations, keyed by public identifier.
     */
    Map<String,String> getRegistrations() {

        return (entityValidator);

    }


    /**
     * <p>Return the top object on the parameters stack without removing it.  If there are
     * no objects on the stack, return <code>null</code>.</p>
     *
     * <p>The parameters stack is used to store <code>CallMethodRule</code> parameters. 
     * See {@link #params}.</p>
     */
    public Object peekParams() {

        try {
            return (params.peek());
        } catch (EmptyStackException e) {
            log.warn("Empty stack (returning null)");
            return (null);
        }

    }


    /**
     * <p>Return the n'th object down the parameters stack, where 0 is the top element
     * and [getCount()-1] is the bottom element.  If the specified index
     * is out of range, return <code>null</code>.</p>
     *
     * <p>The parameters stack is used to store <code>CallMethodRule</code> parameters. 
     * See {@link #params}.</p>
     *
     * @param n Index of the desired element, where 0 is the top of the stack,
     *  1 is the next element down, and so on.
     */
    public Object peekParams(int n) {

        try {
            return (params.peek(n));
        } catch (EmptyStackException e) {
            log.warn("Empty stack (returning null)");
            return (null);
        }

    }


    /**
     * <p>Pop the top object off of the parameters stack, and return it.  If there are
     * no objects on the stack, return <code>null</code>.</p>
     *
     * <p>The parameters stack is used to store <code>CallMethodRule</code> parameters. 
     * See {@link #params}.</p>
     */
    public Object popParams() {

        try {
            if (log.isTraceEnabled()) {
                log.trace("Popping params");
            }
            return (params.pop());
        } catch (EmptyStackException e) {
            log.warn("Empty stack (returning null)");
            return (null);
        }

    }


    /**
     * <p>Push a new object onto the top of the parameters stack.</p>
     *
     * <p>The parameters stack is used to store <code>CallMethodRule</code> parameters. 
     * See {@link #params}.</p>
     *
     * @param object The new object
     */
    public void pushParams(Object object) {
        if (log.isTraceEnabled()) {
            log.trace("Pushing params");
        }
        params.push(object);

    }

    /**
     * Create a SAX exception which also understands about the location in
     * the digester file where the exception occurs
     *
     * @return the new exception
     */
    public SAXException createSAXException(String message, Exception e) {
        if ((e != null) &&
            (e instanceof InvocationTargetException)) {
            Throwable t = e.getCause();
            if (t instanceof ThreadDeath) {
                throw (ThreadDeath) t;
            }
            if (t instanceof VirtualMachineError) {
                throw (VirtualMachineError) t;
            }
            if (t instanceof Exception) {
                e = (Exception) t;
            }
        }
        if (locator != null) {
            String error = "Error at (" + locator.getLineNumber() + ", " +
                    locator.getColumnNumber() + ") : " + message;
            if (e != null) {
                return new SAXParseException(error, locator, e);
            } else {
                return new SAXParseException(error, locator);
            }
        }
        log.error("No Locator!");
        if (e != null) {
            return new SAXException(message, e);
        } else {
            return new SAXException(message);
        }
    }

    /**
     * Create a SAX exception which also understands about the location in
     * the digester file where the exception occurs
     *
     * @return the new exception
     */
    public SAXException createSAXException(Exception e) {
        if (e instanceof InvocationTargetException) {
            Throwable t = e.getCause();
            if (t instanceof ThreadDeath) {
                throw (ThreadDeath) t;
            }
            if (t instanceof VirtualMachineError) {
                throw (VirtualMachineError) t;
            }
            if (t instanceof Exception) {
                e = (Exception) t;
            }
        }
        return createSAXException(e.getMessage(), e);
    }

    /**
     * Create a SAX exception which also understands about the location in
     * the digester file where the exception occurs
     *
     * @return the new exception
     */
    public SAXException createSAXException(String message) {
        return createSAXException(message, null);
    }
    

    // ------------------------------------------------------- Private Methods


   /**
     * Returns an attributes list which contains all the attributes
     * passed in, with any text of form "${xxx}" in an attribute value
     * replaced by the appropriate value from the system property.
     */
    private Attributes updateAttributes(Attributes list) {

        if (list.getLength() == 0) {
            return list;
        }
        
        AttributesImpl newAttrs = new AttributesImpl(list);
        int nAttributes = newAttrs.getLength();
        for (int i = 0; i < nAttributes; ++i) {
            String value = newAttrs.getValue(i);
            try {
                String newValue = 
                    IntrospectionUtils.replaceProperties(value, null, source);
                if (value != newValue) {
                    newAttrs.setValue(i, newValue);
                }
            }
            catch (Exception e) {
                log.warn("Attribute [" + newAttrs.getLocalName(i) + "] failed to update and remains [" + value + "].", e);
            }
        }

        return newAttrs;

    }


    /**
     * Return a new StringBuilder containing the same contents as the
     * input buffer, except that data of form ${varname} have been
     * replaced by the value of that var as defined in the system property.
     */
    private StringBuilder updateBodyText(StringBuilder bodyText) {
        String in = bodyText.toString();
        String out;
        try {
            out = IntrospectionUtils.replaceProperties(in, null, source);
        } catch(Exception e) {
            return bodyText; // return unchanged data
        }

        if (out == in)  {
            // No substitutions required. Don't waste memory creating
            // a new buffer
            return bodyText;
        } else {
            return new StringBuilder(out);
        }
    }


}
