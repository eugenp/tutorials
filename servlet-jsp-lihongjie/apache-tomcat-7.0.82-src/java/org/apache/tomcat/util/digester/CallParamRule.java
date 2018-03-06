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
 * <p>Rule implementation that saves a parameter for use by a surrounding 
 * <code>CallMethodRule<code>.</p>
 *
 * <p>This parameter may be:
 * <ul>
 * <li>from an attribute of the current element
 * See {@link #CallParamRule(int paramIndex, String attributeName)}
 * <li>from current the element body
 * See {@link #CallParamRule(int paramIndex)}
 * <li>from the top object on the stack. 
 * See {@link #CallParamRule(int paramIndex, boolean fromStack)}
 * <li>the current path being processed (separate <code>Rule</code>). 
 * See {@link PathCallParamRule}
 * </ul>
 * </p>
 */

public class CallParamRule extends Rule {

    // ----------------------------------------------------------- Constructors

    /**
     * Construct a "call parameter" rule that will save the body text of this
     * element as the parameter value.
     *
     * @param paramIndex The zero-relative parameter number
     */
    public CallParamRule(int paramIndex) {

        this(paramIndex, null);

    }


    /**
     * Construct a "call parameter" rule that will save the value of the
     * specified attribute as the parameter value.
     *
     * @param paramIndex The zero-relative parameter number
     * @param attributeName The name of the attribute to save
     */
    public CallParamRule(int paramIndex,
                         String attributeName) {

        this.paramIndex = paramIndex;
        this.attributeName = attributeName;

    }


    /**
     * Construct a "call parameter" rule.
     *
     * @param paramIndex The zero-relative parameter number
     * @param fromStack should this parameter be taken from the top of the stack?
     */    
    public CallParamRule(int paramIndex, boolean fromStack) {
    
        this.paramIndex = paramIndex;  
        this.fromStack = fromStack;

    }
    
    /**
     * Constructs a "call parameter" rule which sets a parameter from the stack.
     * If the stack contains too few objects, then the parameter will be set to null.
     *
     * @param paramIndex The zero-relative parameter number
     * @param stackIndex the index of the object which will be passed as a parameter. 
     * The zeroth object is the top of the stack, 1 is the next object down and so on.
     */    
    public CallParamRule(int paramIndex, int stackIndex) {
    
        this.paramIndex = paramIndex;  
        this.fromStack = true;
        this.stackIndex = stackIndex;
    }
 
    // ----------------------------------------------------- Instance Variables


    /**
     * The attribute from which to save the parameter value
     */
    protected String attributeName = null;


    /**
     * The zero-relative index of the parameter we are saving.
     */
    protected int paramIndex = 0;


    /**
     * Is the parameter to be set from the stack?
     */
    protected boolean fromStack = false;
    
    /**
     * The position of the object from the top of the stack
     */
    protected int stackIndex = 0;

    /** 
     * Stack is used to allow nested body text to be processed.
     * Lazy creation.
     */
    protected ArrayStack<String> bodyTextStack;

    // --------------------------------------------------------- Public Methods


    /**
     * Process the start of this element.
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     * @param attributes The attribute list for this element
     */
    @Override
    public void begin(String namespace, String name, Attributes attributes)
            throws Exception {

        Object param = null;
        
        if (attributeName != null) {
        
            param = attributes.getValue(attributeName);
            
        } else if(fromStack) {
        
            param = digester.peek(stackIndex);
            
            if (digester.log.isDebugEnabled()) {
            
                StringBuilder sb = new StringBuilder("[CallParamRule]{");
                sb.append(digester.match);
                sb.append("} Save from stack; from stack?").append(fromStack);
                sb.append("; object=").append(param);
                digester.log.debug(sb.toString());
            }   
        }
        
        // Have to save the param object to the param stack frame here.
        // Can't wait until end(). Otherwise, the object will be lost.
        // We can't save the object as instance variables, as 
        // the instance variables will be overwritten
        // if this CallParamRule is reused in subsequent nesting.
        
        if(param != null) {
            Object parameters[] = (Object[]) digester.peekParams();
            parameters[paramIndex] = param;
        }
    }


    /**
     * Process the body text of this element.
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     * @param bodyText The body text of this element
     */
    @Override
    public void body(String namespace, String name, String bodyText)
            throws Exception {

        if (attributeName == null && !fromStack) {
            // We must wait to set the parameter until end
            // so that we can make sure that the right set of parameters
            // is at the top of the stack
            if (bodyTextStack == null) {
                bodyTextStack = new ArrayStack<String>();
            }
            bodyTextStack.push(bodyText.trim());
        }

    }
    
    /**
     * Process any body texts now.
     */
    @Override
    public void end(String namespace, String name) {
        if (bodyTextStack != null && !bodyTextStack.empty()) {
            // what we do now is push one parameter onto the top set of parameters
            Object parameters[] = (Object[]) digester.peekParams();
            parameters[paramIndex] = bodyTextStack.pop();
        }
    }

    /**
     * Render a printable version of this Rule.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("CallParamRule[");
        sb.append("paramIndex=");
        sb.append(paramIndex);
        sb.append(", attributeName=");
        sb.append(attributeName);
        sb.append(", from stack=");
        sb.append(fromStack);
        sb.append("]");
        return (sb.toString());

    }


}
