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

import org.apache.jasper.Constants;
import org.apache.jasper.JasperException;

/**
 * Default implementation of ErrorHandler interface.
 *
 * @author Jan Luehe
 */
class DefaultErrorHandler implements ErrorHandler {
    
    /*
     * Processes the given JSP parse error.
     *
     * @param fname Name of the JSP file in which the parse error occurred
     * @param line Parse error line number
     * @param column Parse error column number
     * @param errMsg Parse error message
     * @param exception Parse exception
     */
    @Override
    public void jspError(String fname, int line, int column, String errMsg,
            Exception ex) throws JasperException {
        throw new JasperException(fname + " (" +
                Localizer.getMessage("jsp.error.location",
                        Integer.toString(line), Integer.toString(column)) +
                ") " + errMsg, ex);
    }
    
    /*
     * Processes the given JSP parse error.
     *
     * @param errMsg Parse error message
     * @param exception Parse exception
     */
    @Override
    public void jspError(String errMsg, Exception ex) throws JasperException {
        throw new JasperException(errMsg, ex);
    }
    
    /*
     * Processes the given javac compilation errors.
     *
     * @param details Array of JavacErrorDetail instances corresponding to the
     * compilation errors
     */
    @Override
    public void javacError(JavacErrorDetail[] details) throws JasperException {
        
        if (details == null) {
            return;
        }
        
        Object[] args = null;
        StringBuilder buf = new StringBuilder();
        
        for (int i=0; i < details.length; i++) {
            if (details[i].getJspBeginLineNumber() >= 0) {
                args = new Object[] {
                        Integer.valueOf(details[i].getJspBeginLineNumber()), 
                        details[i].getJspFileName() };
                buf.append(Constants.NEWLINE);
                buf.append(Constants.NEWLINE);
                buf.append(Localizer.getMessage("jsp.error.single.line.number",
                        args));
                buf.append(Constants.NEWLINE);
                buf.append(details[i].getErrorMessage());
                buf.append(Constants.NEWLINE);
                buf.append(details[i].getJspExtract());
            } else {
                args = new Object[] {
                        Integer.valueOf(details[i].getJavaLineNumber()),
                        details[i].getJavaFileName() };
                buf.append(Constants.NEWLINE);
                buf.append(Constants.NEWLINE);
                buf.append(Localizer.getMessage("jsp.error.java.line.number",
                        args));
                buf.append(Constants.NEWLINE);
                buf.append(details[i].getErrorMessage());
            }
        }
        buf.append(Constants.NEWLINE);
        buf.append(Constants.NEWLINE);
        buf.append("Stacktrace:");
        throw new JasperException(
                Localizer.getMessage("jsp.error.unable.compile") + ": " + buf);
    }
    
    /**
     * Processes the given javac error report and exception.
     *
     * @param errorReport Compilation error report
     * @param exception Compilation exception
     */
    @Override
    public void javacError(String errorReport, Exception exception)
    throws JasperException {
        
        throw new JasperException(
                Localizer.getMessage("jsp.error.unable.compile"), exception);
    }
    
}
