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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.jasper.Constants;
import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;
import org.xml.sax.SAXException;

/**
 * Class responsible for dispatching JSP parse and javac compilation errors
 * to the configured error handler.
 *
 * This class is also responsible for localizing any error codes before they
 * are passed on to the configured error handler.
 * 
 * In the case of a Java compilation error, the compiler error message is
 * parsed into an array of JavacErrorDetail instances, which is passed on to 
 * the configured error handler.
 *
 * @author Jan Luehe
 * @author Kin-man Chung
 */
public class ErrorDispatcher {

    // Custom error handler
    private ErrorHandler errHandler;

    // Indicates whether the compilation was initiated by JspServlet or JspC
    private boolean jspcMode = false;


    /*
     * Constructor.
     *
     * @param jspcMode true if compilation has been initiated by JspC, false
     * otherwise
     */
    public ErrorDispatcher(boolean jspcMode) {
        // XXX check web.xml for custom error handler
        errHandler = new DefaultErrorHandler();
        this.jspcMode = jspcMode;
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param errCode Error code
     */
    public void jspError(String errCode) throws JasperException {
        dispatch(null, errCode, null, null);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param where Error location
     * @param errCode Error code
     */
    public void jspError(Mark where, String errCode) throws JasperException {
        dispatch(where, errCode, null, null);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param n Node that caused the error
     * @param errCode Error code
     */
    public void jspError(Node n, String errCode) throws JasperException {
        dispatch(n.getStart(), errCode, null, null);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param errCode Error code
     * @param arg Argument for parametric replacement
     */
    public void jspError(String errCode, String arg) throws JasperException {
        dispatch(null, errCode, new Object[] {arg}, null);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param where Error location
     * @param errCode Error code
     * @param arg Argument for parametric replacement
     */
    public void jspError(Mark where, String errCode, String arg)
                throws JasperException {
        dispatch(where, errCode, new Object[] {arg}, null);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param n Node that caused the error
     * @param errCode Error code
     * @param arg Argument for parametric replacement
     */
    public void jspError(Node n, String errCode, String arg)
                throws JasperException {
        dispatch(n.getStart(), errCode, new Object[] {arg}, null);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param errCode Error code
     * @param arg1 First argument for parametric replacement
     * @param arg2 Second argument for parametric replacement
     */
    public void jspError(String errCode, String arg1, String arg2)
                throws JasperException {
        dispatch(null, errCode, new Object[] {arg1, arg2}, null);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param errCode Error code
     * @param arg1 First argument for parametric replacement
     * @param arg2 Second argument for parametric replacement
     * @param arg3 Third argument for parametric replacement
     */
    public void jspError(String errCode, String arg1, String arg2, String arg3)
                throws JasperException {
        dispatch(null, errCode, new Object[] {arg1, arg2, arg3}, null);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param where Error location
     * @param errCode Error code
     * @param arg1 First argument for parametric replacement
     * @param arg2 Second argument for parametric replacement
     */
    public void jspError(Mark where, String errCode, String arg1, String arg2)
                throws JasperException {
        dispatch(where, errCode, new Object[] {arg1, arg2}, null);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param where Error location
     * @param errCode Error code
     * @param arg1 First argument for parametric replacement
     * @param arg2 Second argument for parametric replacement
     * @param arg3 Third argument for parametric replacement
     */

    public void jspError(Mark where, String errCode, String arg1, String arg2,
                         String arg3)
                throws JasperException {
        dispatch(where, errCode, new Object[] {arg1, arg2, arg3}, null);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param n Node that caused the error
     * @param errCode Error code
     * @param arg1 First argument for parametric replacement
     * @param arg2 Second argument for parametric replacement
     */

    public void jspError(Node n, String errCode, String arg1, String arg2)
                throws JasperException {
        dispatch(n.getStart(), errCode, new Object[] {arg1, arg2}, null);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param n Node that caused the error
     * @param errCode Error code
     * @param arg1 First argument for parametric replacement
     * @param arg2 Second argument for parametric replacement
     * @param arg3 Third argument for parametric replacement
     */

    public void jspError(Node n, String errCode, String arg1, String arg2,
                         String arg3)
                throws JasperException {
        dispatch(n.getStart(), errCode, new Object[] {arg1, arg2, arg3}, null);
    }

    /*
     * Dispatches the given parsing exception to the configured error handler.
     *
     * @param e Parsing exception
     */
    public void jspError(Exception e) throws JasperException {
        dispatch(null, null, null, e);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param errCode Error code
     * @param arg Argument for parametric replacement
     * @param e Parsing exception
     */
    public void jspError(Exception e, String errCode, String arg)
                throws JasperException {
        dispatch(null, errCode, new Object[] {arg}, e);
    }

    /**
     * @deprecated Replaced by jspError(Exception, String, String)
     */
    @Deprecated
    public void jspError(String errCode, String arg, Exception e)
            throws JasperException {
        jspError(e, errCode, arg);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param where Error location
     * @param errCode Error code
     * @param args Arguments for parametric replacement
     * @param e Parsing exception
     */
    public void jspError(Mark where, Exception e, String errCode, String... args)
                throws JasperException {
        dispatch(where, errCode, args, e);
    }

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param n Node that caused the error
     * @param errCode Error code
     * @param arg Argument for parametric replacement
     * @param e Parsing exception
     */
    public void jspError(Node n, Exception e, String errCode, String arg)
                throws JasperException {
        dispatch(n.getStart(), errCode, new Object[] {arg}, e);
    }

    /**
     * @deprecated Replaced by jspError(Node, Exception, String, String)
     */
    @Deprecated
    public void jspError(Node n, String errCode, String arg, Exception e)
            throws JasperException {
        jspError(n, e, errCode, arg);
    }

    /**
     * Parses the given error message into an array of javac compilation error
     * messages (one per javac compilation error line number).
     *
     * @param errMsg Error message
     * @param fname Name of Java source file whose compilation failed
     * @param page Node representation of JSP page from which the Java source
     * file was generated
     *
     * @return Array of javac compilation errors, or null if the given error
     * message does not contain any compilation error line numbers
     */
    public static JavacErrorDetail[] parseJavacErrors(String errMsg,
                                                      String fname,
                                                      Node.Nodes page)
            throws JasperException, IOException {

        return parseJavacMessage(errMsg, fname, page);
    }

    /*
     * Dispatches the given javac compilation errors to the configured error
     * handler.
     *
     * @param javacErrors Array of javac compilation errors
     */
    public void javacError(JavacErrorDetail[] javacErrors)
            throws JasperException {

        errHandler.javacError(javacErrors);
    }


    /*
     * Dispatches the given compilation error report and exception to the
     * configured error handler.
     *
     * @param errorReport Compilation error report
     * @param e Compilation exception
     */
    public void javacError(String errorReport, Exception e)
                throws JasperException {

        errHandler.javacError(errorReport, e);
    }


    //*********************************************************************
    // Private utility methods

    /*
     * Dispatches the given JSP parse error to the configured error handler.
     *
     * The given error code is localized. If it is not found in the
     * resource bundle for localized error messages, it is used as the error
     * message.
     *
     * @param where Error location
     * @param errCode Error code
     * @param args Arguments for parametric replacement
     * @param e Parsing exception
     */
    private void dispatch(Mark where, String errCode, Object[] args,
                          Exception e) throws JasperException {
        String file = null;
        String errMsg = null;
        int line = -1;
        int column = -1;
        boolean hasLocation = false;

        // Localize
        if (errCode != null) {
            errMsg = Localizer.getMessage(errCode, args);
        } else if (e != null) {
            // give a hint about what's wrong
            errMsg = e.getMessage();
        }

        // Get error location
        if (where != null) {
            if (jspcMode) {
                // Get the full URL of the resource that caused the error
                try {
                    file = where.getURL().toString();
                } catch (MalformedURLException me) {
                    // Fallback to using context-relative path
                    file = where.getFile();
                }
            } else {
                // Get the context-relative resource path, so as to not
                // disclose any local filesystem details
                file = where.getFile();
            }
            line = where.getLineNumber();
            column = where.getColumnNumber();
            hasLocation = true;
        }

        // Get nested exception
        Exception nestedEx = e;
        if ((e instanceof SAXException)
                && (((SAXException) e).getException() != null)) {
            nestedEx = ((SAXException) e).getException();
        }

        if (hasLocation) {
            errHandler.jspError(file, line, column, errMsg, nestedEx);
        } else {
            errHandler.jspError(errMsg, nestedEx);
        }
    }

    /*
     * Parses the given Java compilation error message, which may contain one
     * or more compilation errors, into an array of JavacErrorDetail instances.
     *
     * Each JavacErrorDetail instance contains the information about a single
     * compilation error.
     *
     * @param errMsg Compilation error message that was generated by the
     * javac compiler
     * @param fname Name of Java source file whose compilation failed
     * @param page Node representation of JSP page from which the Java source
     * file was generated
     *
     * @return Array of JavacErrorDetail instances corresponding to the
     * compilation errors
     */
    private static JavacErrorDetail[] parseJavacMessage(
                                String errMsg, String fname, Node.Nodes page)
                throws IOException, JasperException {

        ArrayList<JavacErrorDetail> errors = new ArrayList<JavacErrorDetail>();
        StringBuilder errMsgBuf = null;
        int lineNum = -1;
        JavacErrorDetail javacError = null;
        
        BufferedReader reader = new BufferedReader(new StringReader(errMsg));
        
        /*
         * Parse compilation errors. Each compilation error consists of a file
         * path and error line number, followed by a number of lines describing
         * the error.
         */
        String line = null;
        while ((line = reader.readLine()) != null) {
            
            /*
             * Error line number is delimited by set of colons.
             * Ignore colon following drive letter on Windows (fromIndex = 2).
             * XXX Handle deprecation warnings that don't have line info
             */
            int beginColon = line.indexOf(':', 2); 
            int endColon = line.indexOf(':', beginColon + 1);
            if ((beginColon >= 0) && (endColon >= 0)) {
                if (javacError != null) {
                    // add previous error to error vector
                    errors.add(javacError);
                }
                
                String lineNumStr = line.substring(beginColon + 1, endColon);
                try {
                    lineNum = Integer.parseInt(lineNumStr);
                } catch (NumberFormatException e) {
                    lineNum = -1;
                }
                
                errMsgBuf = new StringBuilder();
                
                javacError = createJavacError(fname, page, errMsgBuf, lineNum);
            }
            
            // Ignore messages preceding first error
            if (errMsgBuf != null) {
                errMsgBuf.append(line);
                errMsgBuf.append(Constants.NEWLINE);
            }
        }
        
        // Add last error to error vector
        if (javacError != null) {
            errors.add(javacError);
        } 
        
        reader.close();
        
        JavacErrorDetail[] errDetails = null;
        if (errors.size() > 0) {
            errDetails = new JavacErrorDetail[errors.size()];
            errors.toArray(errDetails);
        }
        
        return errDetails;
    }


    /**
     * @param fname
     * @param page
     * @param errMsgBuf
     * @param lineNum
     * @return JavacErrorDetail The error details
     * @throws JasperException
     */
    public static JavacErrorDetail createJavacError(String fname,
            Node.Nodes page, StringBuilder errMsgBuf, int lineNum)
    throws JasperException {
        return createJavacError(fname, page, errMsgBuf, lineNum, null);
    }
    
    
    /**
     * @param fname
     * @param page
     * @param errMsgBuf
     * @param lineNum
     * @param ctxt
     * @return JavacErrorDetail The error details
     * @throws JasperException
     */
    public static JavacErrorDetail createJavacError(String fname,
            Node.Nodes page, StringBuilder errMsgBuf, int lineNum,
            JspCompilationContext ctxt) throws JasperException {
        JavacErrorDetail javacError;
        // Attempt to map javac error line number to line in JSP page
        ErrorVisitor errVisitor = new ErrorVisitor(lineNum);
        page.visit(errVisitor);
        Node errNode = errVisitor.getJspSourceNode();
        if ((errNode != null) && (errNode.getStart() != null)) {
            // If this is a scriplet node then there is a one to one mapping
            // between JSP lines and Java lines
            if (errVisitor.getJspSourceNode() instanceof Node.Scriptlet ||
                    errVisitor.getJspSourceNode() instanceof Node.Declaration) {
                javacError = new JavacErrorDetail(
                        fname,
                        lineNum,
                        errNode.getStart().getFile(),
                        errNode.getStart().getLineNumber() + lineNum -
                            errVisitor.getJspSourceNode().getBeginJavaLine(),
                        errMsgBuf,
                        ctxt);
            } else {
                javacError = new JavacErrorDetail(
                        fname,
                        lineNum,
                        errNode.getStart().getFile(),
                        errNode.getStart().getLineNumber(),
                        errMsgBuf,
                        ctxt);
            }
        } else {
            /*
             * javac error line number cannot be mapped to JSP page
             * line number. For example, this is the case if a 
             * scriptlet is missing a closing brace, which causes
             * havoc with the try-catch-finally block that the code
             * generator places around all generated code: As a result
             * of this, the javac error line numbers will be outside
             * the range of begin and end java line numbers that were
             * generated for the scriptlet, and therefore cannot be
             * mapped to the start line number of the scriptlet in the
             * JSP page.
             * Include just the javac error info in the error detail.
             */
            javacError = new JavacErrorDetail(
                    fname,
                    lineNum,
                    errMsgBuf);
        }
        return javacError;
    }


    /*
     * Visitor responsible for mapping a line number in the generated servlet
     * source code to the corresponding JSP node.
     */
    static class ErrorVisitor extends Node.Visitor {

        // Java source line number to be mapped
        private int lineNum;

        /*
         * JSP node whose Java source code range in the generated servlet
         * contains the Java source line number to be mapped
         */
        Node found;

        /*
         * Constructor.
         *
         * @param lineNum Source line number in the generated servlet code
         */
        public ErrorVisitor(int lineNum) {
            this.lineNum = lineNum;
        }

        @Override
        public void doVisit(Node n) throws JasperException {
            if ((lineNum >= n.getBeginJavaLine())
                    && (lineNum < n.getEndJavaLine())) {
                found = n;
            }
        }

        /*
         * Gets the JSP node to which the source line number in the generated
         * servlet code was mapped.
         *
         * @return JSP node to which the source line number in the generated
         * servlet code was mapped
         */
        public Node getJspSourceNode() {
            return found;
        }
    }
}
