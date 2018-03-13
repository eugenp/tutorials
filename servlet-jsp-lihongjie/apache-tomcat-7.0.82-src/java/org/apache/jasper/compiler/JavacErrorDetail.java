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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import org.apache.jasper.Constants;
import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;

/**
 * Class providing details about a javac compilation error.
 *
 * @author Jan Luehe
 * @author Kin-man Chung
 */
public class JavacErrorDetail {

    private String javaFileName;
    private int javaLineNum;
    private String jspFileName;
    private int jspBeginLineNum;
    private StringBuilder errMsg;
    private String jspExtract = null;

    /**
     * Constructor.
     *
     * @param javaFileName The name of the Java file in which the
     * compilation error occurred
     * @param javaLineNum The compilation error line number
     * @param errMsg The compilation error message
     */
    public JavacErrorDetail(String javaFileName,
                            int javaLineNum,
                            StringBuilder errMsg) {

        this.javaFileName = javaFileName;
        this.javaLineNum = javaLineNum;
        this.errMsg = errMsg;
        this.jspBeginLineNum = -1;
    }

    /**
     * Constructor.
     *
     * @param javaFileName The name of the Java file in which the
     * compilation error occurred
     * @param javaLineNum The compilation error line number
     * @param jspFileName The name of the JSP file from which the Java source
     * file was generated
     * @param jspBeginLineNum The start line number of the JSP element
     * responsible for the compilation error
     * @param errMsg The compilation error message
     * @param ctxt The compilation context
     */
    public JavacErrorDetail(String javaFileName,
            int javaLineNum,
            String jspFileName,
            int jspBeginLineNum,
            StringBuilder errMsg,
            JspCompilationContext ctxt) {

        this(javaFileName, javaLineNum, errMsg);
        this.jspFileName = jspFileName;
        this.jspBeginLineNum = jspBeginLineNum;

        if (jspBeginLineNum > 0 && ctxt != null) {
            InputStream is = null;
            FileInputStream  fis = null;

            try {
                // Read both files in, so we can inspect them
                JarFile jarFile = null;
                JarResource tagJarResource = ctxt.getTagFileJarResource();
                if (tagJarResource != null) {
                    jarFile = tagJarResource.getJarFile();
                }

                is = JspUtil.getInputStream(jspFileName, jarFile, ctxt, null);

                String[] jspLines = readFile(is);

                fis = new FileInputStream(ctxt.getServletJavaFileName());
                String[] javaLines = readFile(fis);

                if (jspLines.length < jspBeginLineNum) {
                    // Avoid ArrayIndexOutOfBoundsException
                    // Probably bug 48498 but could be some other cause
                    jspExtract = Localizer.getMessage("jsp.error.bug48498");
                    return;
                }

                // If the line contains the opening of a multi-line scriptlet
                // block, then the JSP line number we got back is probably
                // faulty.  Scan forward to match the java line...
                if (jspLines[jspBeginLineNum-1].lastIndexOf("<%") >
                    jspLines[jspBeginLineNum-1].lastIndexOf("%>")) {
                    String javaLine = javaLines[javaLineNum-1].trim();

                    for (int i=jspBeginLineNum-1; i<jspLines.length; i++) {
                        if (jspLines[i].indexOf(javaLine) != -1) {
                            // Update jsp line number
                            this.jspBeginLineNum = i+1;
                            break;
                        }
                    }
                }

                // copy out a fragment of JSP to display to the user
                StringBuilder fragment = new StringBuilder(1024);
                int startIndex = Math.max(0, this.jspBeginLineNum-1-3);
                int endIndex = Math.min(
                        jspLines.length-1, this.jspBeginLineNum-1+3);

                for (int i=startIndex;i<=endIndex; ++i) {
                    fragment.append(i+1);
                    fragment.append(": ");
                    fragment.append(jspLines[i]);
                    fragment.append(Constants.NEWLINE);
                }
                jspExtract = fragment.toString();

            } catch (JasperException je) {
                // Exception is never thrown - ignore
            } catch (IOException ioe) {
                // Can't read files - ignore
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ioe) {
                        // Ignore
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException ioe) {
                        // Ignore
                    }
                }
            }
        }
    }

    /**
     * Gets the name of the Java source file in which the compilation error
     * occurred.
     *
     * @return Java source file name
     */
    public String getJavaFileName() {
        return this.javaFileName;
    }

    /**
     * Gets the compilation error line number.
     *
     * @return Compilation error line number
     */
    public int getJavaLineNumber() {
        return this.javaLineNum;
    }

    /**
     * Gets the name of the JSP file from which the Java source file was
     * generated.
     *
     * @return JSP file from which the Java source file was generated.
     */
    public String getJspFileName() {
        return this.jspFileName;
    }

    /**
     * Gets the start line number (in the JSP file) of the JSP element
     * responsible for the compilation error.
     *
     * @return Start line number of the JSP element responsible for the
     * compilation error
     */
    public int getJspBeginLineNumber() {
        return this.jspBeginLineNum;
    }

    /**
     * Gets the compilation error message.
     *
     * @return Compilation error message
     */
    public String getErrorMessage() {
        return this.errMsg.toString();
    }

    /**
     * Gets the extract of the JSP that corresponds to this message.
     *
     * @return Extract of JSP where error occurred
     */
    public String getJspExtract() {
        return this.jspExtract;
    }

    /**
     * Reads a text file from an input stream into a String[]. Used to read in
     * the JSP and generated Java file when generating error messages.
     */
    private String[] readFile(InputStream s) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(s));
        List<String> lines = new ArrayList<String>();
        String line;

        while ( (line = reader.readLine()) != null ) {
            lines.add(line);
        }

        return lines.toArray( new String[lines.size()] );
    }
}
