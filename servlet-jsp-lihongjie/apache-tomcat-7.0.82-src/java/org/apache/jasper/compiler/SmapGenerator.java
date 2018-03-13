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
import java.util.List;

/**
 * Represents a source map (SMAP), which serves to associate lines
 * of the input JSP file(s) to lines in the generated servlet in the
 * final .class file, according to the JSR-045 spec.
 * 
 * @author Shawn Bayern
 */
public class SmapGenerator {

    //*********************************************************************
    // Overview

    /*
     * The SMAP syntax is reasonably straightforward.  The purpose of this
     * class is currently twofold:
     *  - to provide a simple but low-level Java interface to build
     *    a logical SMAP
     *  - to serialize this logical SMAP for eventual inclusion directly
     *    into a .class file.
     */


    //*********************************************************************
    // Private state

    private String outputFileName;
    private String defaultStratum = "Java";
    private List<SmapStratum> strata = new ArrayList<SmapStratum>();
    private List<String> embedded = new ArrayList<String>();
    private boolean doEmbedded = true;

    //*********************************************************************
    // Methods for adding mapping data

    /**
     * Sets the filename (without path information) for the generated
     * source file.  E.g., "foo$jsp.java".
     */
    public synchronized void setOutputFileName(String x) {
        outputFileName = x;
    }

    /**
     * Adds the given SmapStratum object, representing a Stratum with
     * logically associated FileSection and LineSection blocks, to
     * the current SmapGenerator.  If <tt>default</tt> is true, this
     * stratum is made the default stratum, overriding any previously
     * set default.
     *
     * @param stratum the SmapStratum object to add
     * @param defaultStratum if <tt>true</tt>, this SmapStratum is considered
     *                to represent the default SMAP stratum unless
     *                overwritten
     */
    public synchronized void addStratum(SmapStratum stratum,
                                        boolean defaultStratum) {
        strata.add(stratum);
        if (defaultStratum)
            this.defaultStratum = stratum.getStratumName();
    }

    /**
     * Adds the given string as an embedded SMAP with the given stratum name.
     *
     * @param smap the SMAP to embed
     * @param stratumName the name of the stratum output by the compilation
     *                    that produced the <tt>smap</tt> to be embedded
     */
    public synchronized void addSmap(String smap, String stratumName) {
        embedded.add("*O " + stratumName + "\n"
                   + smap
                   + "*C " + stratumName + "\n");
    }

    /**
     * Instructs the SmapGenerator whether to actually print any embedded
     * SMAPs or not.  Intended for situations without an SMAP resolver.
     *
     * @param status If <tt>false</tt>, ignore any embedded SMAPs.
     */
    public void setDoEmbedded(boolean status) {
        doEmbedded = status;
    }

    //*********************************************************************
    // Methods for serializing the logical SMAP

    public synchronized String getString() {
        // check state and initialize buffer
        if (outputFileName == null)
            throw new IllegalStateException();
        StringBuilder out = new StringBuilder();

        // start the SMAP
        out.append("SMAP\n");
        out.append(outputFileName + '\n');
        out.append(defaultStratum + '\n');

        // include embedded SMAPs
        if (doEmbedded) {
            int nEmbedded = embedded.size();
            for (int i = 0; i < nEmbedded; i++) {
                out.append(embedded.get(i));
            }
        }

        // print our StratumSections, FileSections, and LineSections
        int nStrata = strata.size();
        for (int i = 0; i < nStrata; i++) {
            SmapStratum s = strata.get(i);
            out.append(s.getString());
        }

        // end the SMAP
        out.append("*E\n");

        return out.toString();
    }

    @Override
    public String toString() { return getString(); }

    //*********************************************************************
    // For testing (and as an example of use)...

    public static void main(String args[]) {
        SmapGenerator g = new SmapGenerator();
        g.setOutputFileName("foo.java");
        SmapStratum s = new SmapStratum("JSP");
        s.addFile("foo.jsp");
        s.addFile("bar.jsp", "/foo/foo/bar.jsp");
        s.addLineData(1, "foo.jsp", 1, 1, 1);
        s.addLineData(2, "foo.jsp", 1, 6, 1);
        s.addLineData(3, "foo.jsp", 2, 10, 5);
        s.addLineData(20, "bar.jsp", 1, 30, 1);
        g.addStratum(s, true);
        System.out.print(g);

        System.out.println("---");

        SmapGenerator embedded = new SmapGenerator();
        embedded.setOutputFileName("blargh.tier2");
        s = new SmapStratum("Tier2");
        s.addFile("1.tier2");
        s.addLineData(1, "1.tier2", 1, 1, 1);
        embedded.addStratum(s, true);
        g.addSmap(embedded.toString(), "JSP");
        System.out.println(g);
    }
}
