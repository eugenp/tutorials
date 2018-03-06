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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.TagVariableInfo;
import javax.servlet.jsp.tagext.VariableInfo;

import org.apache.jasper.JasperException;

/**
 * Class responsible for determining the scripting variables that every
 * custom action needs to declare.
 *
 * @author Jan Luehe
 */
class ScriptingVariabler {

    private static final Integer MAX_SCOPE = Integer.valueOf(Integer.MAX_VALUE);

    /*
     * Assigns an identifier (of type integer) to every custom tag, in order
     * to help identify, for every custom tag, the scripting variables that it
     * needs to declare.
     */
    static class CustomTagCounter extends Node.Visitor {

        private int count;
        private Node.CustomTag parent;

        @Override
        public void visit(Node.CustomTag n) throws JasperException {
            n.setCustomTagParent(parent);
            Node.CustomTag tmpParent = parent;
            parent = n;
            visitBody(n);
            parent = tmpParent;
            n.setNumCount(Integer.valueOf(count++));
        }
    }

    /*
     * For every custom tag, determines the scripting variables it needs to
     * declare. 
     */
    static class ScriptingVariableVisitor extends Node.Visitor {

        private ErrorDispatcher err;
        private Map<String, Integer> scriptVars;

        public ScriptingVariableVisitor(ErrorDispatcher err) {
            this.err = err;
            scriptVars = new HashMap<String,Integer>();
        }

        @Override
        public void visit(Node.CustomTag n) throws JasperException {
            setScriptingVars(n, VariableInfo.AT_BEGIN);
            setScriptingVars(n, VariableInfo.NESTED);
            visitBody(n);
            setScriptingVars(n, VariableInfo.AT_END);
        }

        private void setScriptingVars(Node.CustomTag n, int scope)
                throws JasperException {

            TagVariableInfo[] tagVarInfos = n.getTagVariableInfos();
            VariableInfo[] varInfos = n.getVariableInfos();
            if (tagVarInfos.length == 0 && varInfos.length == 0) {
                return;
            }

            List<Object> vec = new ArrayList<Object>();

            Integer ownRange = null;
            Node.CustomTag parent = n.getCustomTagParent();
            if (scope == VariableInfo.AT_BEGIN
                    || scope == VariableInfo.AT_END) {
                if (parent == null)
                    ownRange = MAX_SCOPE;
                else
                    ownRange = parent.getNumCount();
            } else {
                // NESTED
                ownRange = n.getNumCount();
            }

            if (varInfos.length > 0) {
                for (int i=0; i<varInfos.length; i++) {
                    if (varInfos[i].getScope() != scope
                            || !varInfos[i].getDeclare()) {
                        continue;
                    }
                    String varName = varInfos[i].getVarName();
                    
                    Integer currentRange = scriptVars.get(varName);
                    if (currentRange == null ||
                            ownRange.compareTo(currentRange) > 0) {
                        scriptVars.put(varName, ownRange);
                        vec.add(varInfos[i]);
                    }
                }
            } else {
                for (int i=0; i<tagVarInfos.length; i++) {
                    if (tagVarInfos[i].getScope() != scope
                            || !tagVarInfos[i].getDeclare()) {
                        continue;
                    }
                    String varName = tagVarInfos[i].getNameGiven();
                    if (varName == null) {
                        varName = n.getTagData().getAttributeString(
                                        tagVarInfos[i].getNameFromAttribute());
                        if (varName == null) {
                            err.jspError(n,
                                    "jsp.error.scripting.variable.missing_name",
                                    tagVarInfos[i].getNameFromAttribute());
                        }
                    }

                    Integer currentRange = scriptVars.get(varName);
                    if (currentRange == null ||
                            ownRange.compareTo(currentRange) > 0) {
                        scriptVars.put(varName, ownRange);
                        vec.add(tagVarInfos[i]);
                    }
                }
            }

            n.setScriptingVars(vec, scope);
        }
    }

    public static void set(Node.Nodes page, ErrorDispatcher err)
            throws JasperException {
        page.visit(new CustomTagCounter());
        page.visit(new ScriptingVariableVisitor(err));
    }
}
