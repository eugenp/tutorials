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

import javax.servlet.ServletContext;

import org.apache.jasper.JspCompilationContext;

/**
 * Provides {@link ELInterpreter} instances for JSP compilation.
 *
 * The search order is as follows:
 * <ol>
 * <li>ELInterpreter instance or implementation class name provided as a
 *     ServletContext attribute</li>
 * <li>Implementation class named in a ServletContext initialisation parameter
 *     </li>
 * <li>Default implementation</li>
 * </ol>
 */
public class ELInterpreterFactory {

    public static final String EL_INTERPRETER_CLASS_NAME =
            ELInterpreter.class.getName();

    private static final ELInterpreter DEFAULT_INSTANCE =
            new DefaultELInterpreter();


    /**
     * Obtain the correct EL Interpreter for the given web application.
     */
    public static ELInterpreter getELInterpreter(ServletContext context)
            throws Exception {

        ELInterpreter result = null;

        // Search for an implementation
        // 1. ServletContext attribute (set by application or cached by a
        //    previous call to this method).
        Object attribute = context.getAttribute(EL_INTERPRETER_CLASS_NAME);
        if (attribute instanceof ELInterpreter) {
            return (ELInterpreter) attribute;
        } else if (attribute instanceof String) {
            result = createInstance(context, (String) attribute);
        }

        // 2. ServletContext init parameter
        if (result == null) {
            String className =
                    context.getInitParameter(EL_INTERPRETER_CLASS_NAME);
            if (className != null) {
                result = createInstance(context, className);
            }
        }

        // 3. Default
        if (result == null) {
            result = DEFAULT_INSTANCE;
        }

        // Cache the result for next time
        context.setAttribute(EL_INTERPRETER_CLASS_NAME, result);
        return result;
    }


    private static ELInterpreter createInstance(ServletContext context,
            String className) throws Exception {
        return (ELInterpreter) context.getClassLoader().loadClass(
                    className).newInstance();
    }


    private ELInterpreterFactory() {
        // Utility class. Hide default constructor.
    }


    public static class DefaultELInterpreter implements ELInterpreter {

        @Override
        public String interpreterCall(JspCompilationContext context,
                boolean isTagFile, String expression,
                Class<?> expectedType, String fnmapvar, boolean xmlEscape) {
            return JspUtil.interpreterCall(isTagFile, expression, expectedType,
                    fnmapvar, xmlEscape);
        }
    }
}
