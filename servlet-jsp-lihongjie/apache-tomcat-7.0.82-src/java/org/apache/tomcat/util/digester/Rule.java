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
 * Concrete implementations of this class implement actions to be taken when
 * a corresponding nested pattern of XML elements has been matched.
 */

public abstract class Rule {


    // ----------------------------------------------------------- Constructors


    /**
     * <p>Base constructor.
     * Now the digester will be set when the rule is added.</p>
     */
    public Rule() {}


    // ----------------------------------------------------- Instance Variables


    /**
     * The Digester with which this Rule is associated.
     */
    protected Digester digester = null;


    /**
     * The namespace URI for which this Rule is relevant, if any.
     */
    protected String namespaceURI = null;


    // ------------------------------------------------------------- Properties


    /**
     * Return the Digester with which this Rule is associated.
     */
    public Digester getDigester() {

        return (this.digester);

    }
    
    /**
     * Set the <code>Digester</code> with which this <code>Rule</code> is associated.
     */
    public void setDigester(Digester digester) {
        
        this.digester = digester;
        
    }

    /**
     * Return the namespace URI for which this Rule is relevant, if any.
     */
    public String getNamespaceURI() {

        return (this.namespaceURI);

    }


    /**
     * Set the namespace URI for which this Rule is relevant, if any.
     *
     * @param namespaceURI Namespace URI for which this Rule is relevant,
     *  or <code>null</code> to match independent of namespace.
     */
    public void setNamespaceURI(String namespaceURI) {

        this.namespaceURI = namespaceURI;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * This method is called when the beginning of a matching XML element
     * is encountered.
     *
     * @param attributes The attribute list of this element
     * @deprecated Use the {@link #begin(String,String,Attributes) begin}
     *   method with <code>namespace</code> and <code>name</code>
     *   parameters instead.
     */
    @Deprecated
    public void begin(Attributes attributes) throws Exception {
        // The default implementation does nothing
    }


    /**
     * This method is called when the beginning of a matching XML element
     * is encountered. The default implementation delegates to the deprecated
     * method {@link #begin(Attributes) begin} without the 
     * <code>namespace</code> and <code>name</code> parameters, to retain 
     * backwards compatibility.
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     * @param attributes The attribute list of this element
     * @since Digester 1.4
     */
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {

        begin(attributes);

    }


    /**
     * This method is called when the body of a matching XML element
     * is encountered.  If the element has no body, this method is
     * not called at all.
     *
     * @param text The text of the body of this element
     * @deprecated Use the {@link #body(String,String,String) body} method
     *   with <code>namespace</code> and <code>name</code> parameters
     *   instead.
     */
    @Deprecated
    public void body(String text) throws Exception {
        // The default implementation does nothing
    }


    /**
     * This method is called when the body of a matching XML element is 
     * encountered.  If the element has no body, this method is not called at 
     * all. The default implementation delegates to the deprecated method 
     * {@link #body(String) body} without the <code>namespace</code> and
     * <code>name</code> parameters, to retain backwards compatibility.
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     * @param text The text of the body of this element
     * @since Digester 1.4
     */
    public void body(String namespace, String name, String text)
        throws Exception {

        body(text);

    }


    /**
     * This method is called when the end of a matching XML element
     * is encountered.
     * 
     * @deprecated Use the {@link #end(String,String) end} method with 
     *   <code>namespace</code> and <code>name</code> parameters instead.
     */
    @Deprecated
    public void end() throws Exception {
        // The default implementation does nothing
    }


    /**
     * This method is called when the end of a matching XML element
     * is encountered. The default implementation delegates to the deprecated
     * method {@link #end end} without the 
     * <code>namespace</code> and <code>name</code> parameters, to retain 
     * backwards compatibility.
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     * @since Digester 1.4
     */
    public void end(String namespace, String name)
        throws Exception {

        end();

    }


    /**
     * This method is called after all parsing methods have been
     * called, to allow Rules to remove temporary data.
     */
    public void finish() throws Exception {
        // The default implementation does nothing
    }


}
