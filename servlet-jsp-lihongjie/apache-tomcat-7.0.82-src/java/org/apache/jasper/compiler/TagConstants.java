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

package org.apache.jasper.compiler;

public interface TagConstants {

    public static final String JSP_URI = "http://java.sun.com/JSP/Page";

    public static final String DIRECTIVE_ACTION = "directive.";

    public static final String ROOT_ACTION = "root";
    public static final String JSP_ROOT_ACTION = "jsp:root";

    public static final String PAGE_DIRECTIVE_ACTION = "directive.page";
    public static final String JSP_PAGE_DIRECTIVE_ACTION = "jsp:directive.page";

    public static final String INCLUDE_DIRECTIVE_ACTION = "directive.include";
    public static final String JSP_INCLUDE_DIRECTIVE_ACTION = "jsp:directive.include";

    public static final String DECLARATION_ACTION = "declaration";
    public static final String JSP_DECLARATION_ACTION = "jsp:declaration";

    public static final String SCRIPTLET_ACTION = "scriptlet";
    public static final String JSP_SCRIPTLET_ACTION = "jsp:scriptlet";

    public static final String EXPRESSION_ACTION = "expression";
    public static final String JSP_EXPRESSION_ACTION = "jsp:expression";

    public static final String USE_BEAN_ACTION = "useBean";
    public static final String JSP_USE_BEAN_ACTION = "jsp:useBean";

    public static final String SET_PROPERTY_ACTION = "setProperty";
    public static final String JSP_SET_PROPERTY_ACTION = "jsp:setProperty";

    public static final String GET_PROPERTY_ACTION = "getProperty";
    public static final String JSP_GET_PROPERTY_ACTION = "jsp:getProperty";

    public static final String INCLUDE_ACTION = "include";
    public static final String JSP_INCLUDE_ACTION = "jsp:include";

    public static final String FORWARD_ACTION = "forward";
    public static final String JSP_FORWARD_ACTION = "jsp:forward";

    public static final String PARAM_ACTION = "param";
    public static final String JSP_PARAM_ACTION = "jsp:param";

    public static final String PARAMS_ACTION = "params";
    public static final String JSP_PARAMS_ACTION = "jsp:params";

    public static final String PLUGIN_ACTION = "plugin";
    public static final String JSP_PLUGIN_ACTION = "jsp:plugin";

    public static final String FALLBACK_ACTION = "fallback";
    public static final String JSP_FALLBACK_ACTION = "jsp:fallback";

    public static final String TEXT_ACTION = "text";
    public static final String JSP_TEXT_ACTION = "jsp:text";
    public static final String JSP_TEXT_ACTION_END = "</jsp:text>";

    public static final String ATTRIBUTE_ACTION = "attribute";
    public static final String JSP_ATTRIBUTE_ACTION = "jsp:attribute";

    public static final String BODY_ACTION = "body";
    public static final String JSP_BODY_ACTION = "jsp:body";

    public static final String ELEMENT_ACTION = "element";
    public static final String JSP_ELEMENT_ACTION = "jsp:element";

    public static final String OUTPUT_ACTION = "output";
    public static final String JSP_OUTPUT_ACTION = "jsp:output";

    public static final String TAGLIB_DIRECTIVE_ACTION = "taglib";
    public static final String JSP_TAGLIB_DIRECTIVE_ACTION = "jsp:taglib";

    /*
     * Tag Files
     */
    public static final String INVOKE_ACTION = "invoke";
    public static final String JSP_INVOKE_ACTION = "jsp:invoke";

    public static final String DOBODY_ACTION = "doBody";
    public static final String JSP_DOBODY_ACTION = "jsp:doBody";

    /*
     * Tag File Directives
     */
    public static final String TAG_DIRECTIVE_ACTION = "directive.tag";
    public static final String JSP_TAG_DIRECTIVE_ACTION = "jsp:directive.tag";

    public static final String ATTRIBUTE_DIRECTIVE_ACTION = "directive.attribute";
    public static final String JSP_ATTRIBUTE_DIRECTIVE_ACTION = "jsp:directive.attribute";

    public static final String VARIABLE_DIRECTIVE_ACTION = "directive.variable";
    public static final String JSP_VARIABLE_DIRECTIVE_ACTION = "jsp:directive.variable";

    /*
     * Directive attributes
     */
    public static final String URN_JSPTAGDIR = "urn:jsptagdir:";
    public static final String URN_JSPTLD = "urn:jsptld:";
}
