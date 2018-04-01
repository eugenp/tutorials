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


package org.apache.catalina.deploy;


import java.io.Serializable;

import org.apache.catalina.util.RequestUtil;


/**
 * Representation of a web resource collection for a web application's security
 * constraint, as represented in a <code>&lt;web-resource-collection&gt;</code>
 * element in the deployment descriptor.
 * <p>
 * <b>WARNING</b>:  It is assumed that instances of this class will be created
 * and modified only within the context of a single thread, before the instance
 * is made visible to the remainder of the application.  After that, only read
 * access is expected.  Therefore, none of the read and write access within
 * this class is synchronized.
 *
 * @author Craig R. McClanahan
 */
public class SecurityCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new security collection instance with default values.
     */
    public SecurityCollection() {

        this(null, null);

    }


    /**
     * Construct a new security collection instance with specified values.
     *
     * @param name Name of this security collection
     */
    public SecurityCollection(String name) {

        this(name, null);

    }


    /**
     * Construct a new security collection instance with specified values.
     *
     * @param name Name of this security collection
     * @param description Description of this security collection
     */
    public SecurityCollection(String name, String description) {

        super();
        setName(name);
        setDescription(description);

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Description of this web resource collection.
     */
    private String description = null;


    /**
     * The HTTP methods explicitly covered by this web resource collection.
     */
    private String methods[] = new String[0];


    /**
     * The HTTP methods explicitly excluded from this web resource collection.
     */
    private String omittedMethods[] = new String[0];

    /**
     * The name of this web resource collection.
     */
    private String name = null;


    /**
     * The URL patterns protected by this security collection.
     */
    private String patterns[] = new String[0];


    /**
     * This security collection was established by a deployment descriptor.
     * Defaults to <code>true</code>.
     */
    private boolean isFromDescriptor = true;

    // ------------------------------------------------------------- Properties


    /**
     * Return the description of this web resource collection.
     */
    public String getDescription() {

        return (this.description);

    }


    /**
     * Set the description of this web resource collection.
     *
     * @param description The new description
     */
    public void setDescription(String description) {

        this.description = description;

    }


    /**
     * Return the name of this web resource collection.
     */
    public String getName() {

        return (this.name);

    }


    /**
     * Set the name of this web resource collection
     *
     * @param name The new name
     */
    public void setName(String name) {

        this.name = name;

    }


    /**
     * Return if this constraint was defined in a deployment descriptor.
     */
    public boolean isFromDescriptor() {
        return isFromDescriptor;
    }


    /**
     * Set if this constraint was defined in a deployment descriptor.
     */
    public void setFromDescriptor(boolean isFromDescriptor) {
        this.isFromDescriptor = isFromDescriptor;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Add an HTTP request method to be explicitly part of this web resource
     * collection.
     */
    public void addMethod(String method) {

        if (method == null)
            return;
        String results[] = new String[methods.length + 1];
        for (int i = 0; i < methods.length; i++)
            results[i] = methods[i];
        results[methods.length] = method;
        methods = results;

    }


    /**
     * Add an HTTP request method to the methods explicitly excluded from this
     * web resource collection.
     */
    public void addOmittedMethod(String method) {
        if (method == null)
            return;
        String results[] = new String[omittedMethods.length + 1];
        for (int i = 0; i < omittedMethods.length; i++)
            results[i] = omittedMethods[i];
        results[omittedMethods.length] = method;
        omittedMethods = results;
    }

    /**
     * Add a URL pattern to be part of this web resource collection.
     */
    public void addPattern(String pattern) {

        if (pattern == null)
            return;

        String decodedPattern = RequestUtil.URLDecode(pattern);
        String results[] = new String[patterns.length + 1];
        for (int i = 0; i < patterns.length; i++) {
            results[i] = patterns[i];
        }
        results[patterns.length] = decodedPattern;
        patterns = results;

    }


    /**
     * Return <code>true</code> if the specified HTTP request method is
     * part of this web resource collection.
     *
     * @param method Request method to check
     */
    public boolean findMethod(String method) {

        if (methods.length == 0 && omittedMethods.length == 0)
            return (true);
        if (methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].equals(method))
                    return true;
            }
            return false;
        }
        if (omittedMethods.length > 0) {
            for (int i = 0; i < omittedMethods.length; i++) {
                if (omittedMethods[i].equals(method))
                    return false;
            }
        }
        return true;
    }


    /**
     * Return the set of HTTP request methods that are part of this web
     * resource collection, or a zero-length array if no methods have been
     * explicitly included.
     */
    public String[] findMethods() {

        return (methods);

    }


    /**
     * Return the set of HTTP request methods that are explicitly excluded from
     * this web resource collection, or a zero-length array if no request
     * methods are excluded.
     */
    public String[] findOmittedMethods() {

        return (omittedMethods);

    }


    /**
     * Is the specified pattern part of this web resource collection?
     *
     * @param pattern Pattern to be compared
     */
    public boolean findPattern(String pattern) {

        for (int i = 0; i < patterns.length; i++) {
            if (patterns[i].equals(pattern))
                return (true);
        }
        return (false);

    }


    /**
     * Return the set of URL patterns that are part of this web resource
     * collection.  If none have been specified, a zero-length array is
     * returned.
     */
    public String[] findPatterns() {

        return (patterns);

    }


    /**
     * Remove the specified HTTP request method from those that are part
     * of this web resource collection.
     *
     * @param method Request method to be removed
     */
    public void removeMethod(String method) {

        if (method == null)
            return;
        int n = -1;
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].equals(method)) {
                n = i;
                break;
            }
        }
        if (n >= 0) {
            int j = 0;
            String results[] = new String[methods.length - 1];
            for (int i = 0; i < methods.length; i++) {
                if (i != n)
                    results[j++] = methods[i];
            }
            methods = results;
        }

    }


    /**
     * Remove the specified HTTP request method from those that are explicitly
     * excluded from this web resource collection.
     *
     * @param method Request method to be removed
     */
    public void removeOmittedMethod(String method) {

        if (method == null)
            return;
        int n = -1;
        for (int i = 0; i < omittedMethods.length; i++) {
            if (omittedMethods[i].equals(method)) {
                n = i;
                break;
            }
        }
        if (n >= 0) {
            int j = 0;
            String results[] = new String[omittedMethods.length - 1];
            for (int i = 0; i < omittedMethods.length; i++) {
                if (i != n)
                    results[j++] = omittedMethods[i];
            }
            omittedMethods = results;
        }

    }


    /**
     * Remove the specified URL pattern from those that are part of this
     * web resource collection.
     *
     * @param pattern Pattern to be removed
     */
    public void removePattern(String pattern) {

        if (pattern == null)
            return;
        int n = -1;
        for (int i = 0; i < patterns.length; i++) {
            if (patterns[i].equals(pattern)) {
                n = i;
                break;
            }
        }
        if (n >= 0) {
            int j = 0;
            String results[] = new String[patterns.length - 1];
            for (int i = 0; i < patterns.length; i++) {
                if (i != n)
                    results[j++] = patterns[i];
            }
            patterns = results;
        }

    }


    /**
     * Return a String representation of this security collection.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("SecurityCollection[");
        sb.append(name);
        if (description != null) {
            sb.append(", ");
            sb.append(description);
        }
        sb.append("]");
        return (sb.toString());

    }


}
