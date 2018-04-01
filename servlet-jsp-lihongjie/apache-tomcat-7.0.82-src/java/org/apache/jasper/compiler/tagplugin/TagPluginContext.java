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

package org.apache.jasper.compiler.tagplugin;


/**
 * This interface allows the plugin author to make inquiries about the
 * properties of the current tag, and to use Jasper resources to generate
 * direct Java codes in place of tag handler invocations.
 */

public interface TagPluginContext {
    /**
     * @return true if the body of the tag is scriptless.
     */
    boolean isScriptless();

    /**
     * @param attribute Name of the attribute
     * @return true if the attribute is specified in the tag
     */
    boolean isAttributeSpecified(String attribute);

    /**
     * @return An unique temporary variable name that the plugin can use.
     */
    String getTemporaryVariableName();

    /**
     * Generate an import statement
     * @param s Name of the import class, '*' allowed.
     */
    void generateImport(String s);

    /**
     * Generate a declaration in the of the generated class.  This can be
     * used to declare an inner class, a method, or a class variable.
     * @param id An unique ID identifying the declaration.  It is not
     *           part of the declaration, and is used to ensure that the
     *           declaration will only appear once.  If this method is
     *           invoked with the same id more than once in the translation
     *           unit, only the first declaration will be taken.
     * @param text The text of the declaration.
     **/
    void generateDeclaration(String id, String text);

    /**
     * Generate Java source codes
     */
    void generateJavaSource(String s);

    /**
     * @return true if the attribute is specified and its value is a
     *         translation-time constant.
     */
    boolean isConstantAttribute(String attribute);

    /**
     * @return A string that is the value of a constant attribute.  Undefined
     *         if the attribute is not a (translation-time) constant.
     *         null if the attribute is not specified.
     */
    String getConstantAttribute(String attribute);

    /**
     * Generate codes to evaluate value of a attribute in the custom tag
     * The codes is a Java expression.
     * NOTE: Currently cannot handle attributes that are fragments.
     * @param attribute The specified attribute
     */
    void generateAttribute(String attribute);

    /**
     * Generate codes for the body of the custom tag
     */
    void generateBody();

    /**
     * Abandon optimization for this tag handler, and instruct
     * Jasper to generate the tag handler calls, as usual.
     * Should be invoked if errors are detected, or when the tag body
     * is deemed too complicated for optimization.
     */
    void dontUseTagPlugin();

    /**
     * Get the PluginContext for the parent of this custom tag.  NOTE:
     * The operations available for PluginContext so obtained is limited
     * to getPluginAttribute and setPluginAttribute, and queries (e.g.
     * isScriptless().  There should be no calls to generate*().
     * @return The pluginContext for the parent node.
     *         null if the parent is not a custom tag, or if the pluginContext
     *         if not available (because useTagPlugin is false, e.g).
     */
    TagPluginContext getParentContext();

    /**
     * Associate the attribute with a value in the current tagplugin context.
     * The plugin attributes can be used for communication among tags that
     * must work together as a group.  See <c:when> for an example.
     */
    void setPluginAttribute(String attr, Object value);

    /**
     * Get the value of an attribute in the current tagplugin context.
     */
    Object getPluginAttribute(String attr);

    /**
     * Is the tag being used inside a tag file?
     */
    boolean isTagFile();
}

