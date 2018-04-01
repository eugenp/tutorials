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

import org.apache.jasper.JspCompilationContext;

/**
 * Defines the interface for the expression language interpreter. This allows
 * users to provide custom EL interpreter implementations that can optimise
 * EL processing for an application by , for example, performing code generation
 * for simple expressions.
 */
public interface ELInterpreter {

    /**
     * Returns the string representing the code that will be inserted into the
     * servlet generated for JSP. The default implementation creates a call to
     * {@link org.apache.jasper.runtime.PageContextImpl#proprietaryEvaluate(
     * String, Class, javax.servlet.jsp.PageContext,
     * org.apache.jasper.runtime.ProtectedFunctionMapper, boolean)} but other
     * implementations may produce more optimised code.
     *
     * @param expression a String containing zero or more "${}" expressions
     * @param expectedType the expected type of the interpreted result
     * @param fnmapvar Variable pointing to a function map.
     * @param xmlEscape True if the result should do XML escaping
     * @return a String representing a call to the EL interpreter.
     */
    public String interpreterCall(JspCompilationContext context,
            boolean isTagFile, String expression,
            Class<?> expectedType, String fnmapvar, boolean xmlEscape);
}
