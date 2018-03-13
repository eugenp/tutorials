/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package javax.servlet.jsp.tagext;

import javax.servlet.jsp.JspException;

/**
 * For a tag to declare that it accepts dynamic attributes, it must implement
 * this interface.  The entry for the tag in the Tag Library Descriptor must 
 * also be configured to indicate dynamic attributes are accepted.
 * <br>
 * For any attribute that is not declared in the Tag Library Descriptor for
 * this tag, instead of getting an error at translation time, the 
 * <code>setDynamicAttribute()</code> method is called, with the name and
 * value of the attribute.  It is the responsibility of the tag to 
 * remember the names and values of the dynamic attributes.
 *
 * @since 2.0
 */
public interface DynamicAttributes {
    
    /**
     * Called when a tag declared to accept dynamic attributes is passed
     * an attribute that is not declared in the Tag Library Descriptor.
     * 
     * @param uri the namespace of the attribute, or null if in the default
     *     namespace.
     * @param localName the name of the attribute being set.
     * @param value the value of the attribute
     * @throws JspException if the tag handler wishes to
     *     signal that it does not accept the given attribute.  The 
     *     container must not call doStartTag() or doTag() for this tag.
     */
    public void setDynamicAttribute(
        String uri, String localName, Object value ) 
        throws JspException;
    
}
