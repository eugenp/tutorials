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

import java.util.Hashtable;

/**
 * The (translation-time only) attribute/value information for a tag instance.
 *
 * <p>
 * TagData is only used as an argument to the isValid, validate, and 
 * getVariableInfo methods of TagExtraInfo, which are invoked at 
 * translation time.
 */

public class TagData implements Cloneable {

    /**
     * Distinguished value for an attribute to indicate its value
     * is a request-time expression (which is not yet available because
     * TagData instances are used at translation-time).
     */

    public static final Object REQUEST_TIME_VALUE = new Object();


    /**
     * Constructor for TagData.
     *
     * <p>
     * A typical constructor may be
     * <pre>
     * static final Object[][] att = {{"connection", "conn0"}, {"id", "query0"}};
     * static final TagData td = new TagData(att);
     * </pre>
     *
     * All values must be Strings except for those holding the
     * distinguished object REQUEST_TIME_VALUE.

     * @param atts the static attribute and values.  May be null.
     */
    public TagData(Object[] atts[]) {
        if (atts == null) {
            attributes = new Hashtable<String, Object>();
        } else {
            attributes = new Hashtable<String, Object>(atts.length);
        }

        if (atts != null) {
            for (int i = 0; i < atts.length; i++) {
                attributes.put((String) atts[i][0], atts[i][1]);
            }
        }
    }

    /**
     * Constructor for a TagData.
     *
     * If you already have the attributes in a hashtable, use this
     * constructor. 
     *
     * @param attrs A hashtable to get the values from.
     */
    public TagData(Hashtable<String, Object> attrs) {
        this.attributes = attrs;
    }

    /**
     * The value of the tag's id attribute.
     *
     * @return the value of the tag's id attribute, or null if no such
     *     attribute was specified.
     */

    public String getId() {
        return getAttributeString(TagAttributeInfo.ID);
    }

    /**
     * The value of the attribute.
     * If a static value is specified for an attribute that accepts a
     * request-time attribute expression then that static value is returned,
     * even if the value is provided in the body of a &lt;jsp:attribute&gt;
     * action. The distinguished object REQUEST_TIME_VALUE is only returned if
     * the value is specified as a request-time attribute expression
     * or via the &lt;jsp:attribute&gt; action with a body that contains
     * dynamic content (scriptlets, scripting expressions, EL expressions, 
     * standard actions, or custom actions).  Returns null if the attribute 
     * is not set. 
     *
     * @param attName the name of the attribute
     * @return the attribute's value
     */

    public Object getAttribute(String attName) {
        return attributes.get(attName);
    }

    /**
     * Set the value of an attribute.
     *
     * @param attName the name of the attribute
     * @param value the value.
     */
    public void setAttribute(String attName,
                             Object value) {
        attributes.put(attName, value);
    }

    /**
     * Get the value for a given attribute.
     *
     * @param attName the name of the attribute
     * @return the attribute value string
     * @throws ClassCastException if attribute value is not a String
     */

    public String getAttributeString(String attName) {
        Object o = attributes.get(attName);
        if (o == null) {
            return null;
        }
        return (String) o;
    }

    /**
     * Enumerates the attributes.
     *
     *@return An enumeration of the attributes in a TagData
     */
    public java.util.Enumeration<String> getAttributes() {
        return attributes.keys();
    }

    // private data

    private final Hashtable<String, Object> attributes;        // the tagname/value map
}
