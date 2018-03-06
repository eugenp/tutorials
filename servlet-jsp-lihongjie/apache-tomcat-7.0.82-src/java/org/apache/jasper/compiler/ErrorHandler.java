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

import org.apache.jasper.JasperException;

/**
 * Interface for handling JSP parse and javac compilation errors.
 * 
 * An implementation of this interface may be registered with the
 * ErrorDispatcher by setting the XXX initialization parameter in the JSP
 * page compiler and execution servlet in Catalina's web.xml file to the
 * implementation's fully qualified class name.
 *
 * @author Jan Luehe
 * @author Kin-man Chung
 */
public interface ErrorHandler {

    /**
     * Processes the given JSP parse error.
     *
     * @param fname Name of the JSP file in which the parse error occurred
     * @param line Parse error line number
     * @param column Parse error column number
     * @param msg Parse error message
     * @param exception Parse exception
     */
    public void jspError(String fname, int line, int column, String msg,
            Exception exception) throws JasperException;

    /**
     * Processes the given JSP parse error.
     *
     * @param msg Parse error message
     * @param exception Parse exception
     */
    public void jspError(String msg, Exception exception)
            throws JasperException;

    /**
     * Processes the given javac compilation errors.
     *
     * @param details Array of JavacErrorDetail instances corresponding to the
     * compilation errors
     */
    public void javacError(JavacErrorDetail[] details)
            throws JasperException;

    /**
     * Processes the given javac error report and exception.
     *
     * @param errorReport Compilation error report
     * @param exception Compilation exception
     */
    public void javacError(String errorReport, Exception exception)
            throws JasperException;
}
