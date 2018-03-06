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

import java.util.Map;

/**
 * Translation-time validator class for a JSP page. 
 * A validator operates on the XML view associated with the JSP page.
 *
 * <p>
 * The TLD file associates a TagLibraryValidator class and some init
 * arguments with a tag library.
 *
 * <p>
 * The JSP container is responsible for locating an appropriate
 * instance of the appropriate subclass by
 *
 * <ul>
 * <li> new a fresh instance, or reuse an available one
 * <li> invoke the setInitParams(Map) method on the instance
 * </ul>
 *
 * once initialized, the validate(String, String, PageData) method will
 * be invoked, where the first two arguments are the prefix
 * and uri for this tag library in the XML View.  The prefix is intended
 * to make it easier to produce an error message.  However, it is not
 * always accurate.  In the case where a single URI is mapped to more 
 * than one prefix in the XML view, the prefix of the first URI is provided.
 * Therefore, to provide high quality error messages in cases where the 
 * tag elements themselves are checked, the prefix parameter should be 
 * ignored and the actual prefix of the element should be used instead.  
 * TagLibraryValidators should always use the uri to identify elements 
 * as belonging to the tag library, not the prefix.
 *
 * <p>
 * A TagLibraryValidator instance
 * may create auxiliary objects internally to perform
 * the validation (e.g. an XSchema validator) and may reuse it for all
 * the pages in a given translation run.
 *
 * <p>
 * The JSP container is not guaranteed to serialize invocations of
 * validate() method, and TagLibraryValidators should perform any
 * synchronization they may require.
 *
 * <p>
 * As of JSP 2.0, a JSP container must provide a jsp:id attribute to
 * provide higher quality validation errors.
 * The container will track the JSP pages
 * as passed to the container, and will assign to each element
 * a unique "id", which is passed as the value of the jsp:id
 * attribute.  Each XML element in the XML view available will
 * be extended with this attribute.  The TagLibraryValidator
 * can then use the attribute in one or more ValidationMessage
 * objects.  The container then, in turn, can use these
 * values to provide more precise information on the location
 * of an error.
 *
 * <p>
 * The actual prefix of the <code>id</code> attribute may or may not be 
 * <code>jsp</code> but it will always map to the namespace
 * <code>http://java.sun.com/JSP/Page</code>.  A TagLibraryValidator
 * implementation must rely on the uri, not the prefix, of the <code>id</code>
 * attribute.
 */

public abstract class TagLibraryValidator {

    /**
     * Sole constructor. (For invocation by subclass constructors, 
     * typically implicit.)
     */
    public TagLibraryValidator() {
        // NOOP by default
    }
    
    /**
     * Set the init data in the TLD for this validator.
     * Parameter names are keys, and parameter values are the values.
     *
     * @param map A Map describing the init parameters
     */
    public void setInitParameters(Map<String, Object> map) {
        initParameters = map;
    }


    /**
     * Get the init parameters data as an immutable Map.
     * Parameter names are keys, and parameter values are the values.
     *
     * @return The init parameters as an immutable map.
     */
    public Map<String, Object> getInitParameters() {
        return initParameters;
    }

    /**
     * Validate a JSP page.
     * This will get invoked once per unique tag library URI in the
     * XML view.  This method will return null if the page is valid; otherwise
     * the method should return an array of ValidationMessage objects.
     * An array of length zero is also interpreted as no errors.
     *
     * @param prefix the first prefix with which the tag library is 
     *     associated, in the XML view.  Note that some tags may use 
     *     a different prefix if the namespace is redefined.
     * @param uri the tag library's unique identifier
     * @param page the JspData page object
     * @return A null object, or zero length array if no errors, an array
     * of ValidationMessages otherwise.
     */
    public ValidationMessage[] validate(String prefix, String uri, 
        PageData page) {
        return null;
    }

    /**
     * Release any data kept by this instance for validation purposes.
     */
    public void release() {
        initParameters = null;
    }

    // Private data
    private Map<String, Object> initParameters;

}
