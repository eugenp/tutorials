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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;

/**
 * Contains static utilities for generating SMAP data based on the
 * current version of Jasper.
 *
 * @author Jayson Falkner
 * @author Shawn Bayern
 * @author Robert Field (inner SDEInstaller class)
 * @author Mark Roth
 * @author Kin-man Chung
 */
public class SmapUtil {

    //*********************************************************************
    // Constants

    public static final String SMAP_ENCODING = "UTF-8";

    //*********************************************************************
    // Public entry points

    /**
     * Generates an appropriate SMAP representing the current compilation
     * context.  (JSR-045.)
     *
     * @param ctxt Current compilation context
     * @param pageNodes The current JSP page
     * @return a SMAP for the page
     */
    public static String[] generateSmap(
        JspCompilationContext ctxt,
        Node.Nodes pageNodes)
        throws IOException {

        // Scan the nodes for presence of Jasper generated inner classes
        PreScanVisitor psVisitor = new PreScanVisitor();
        try {
            pageNodes.visit(psVisitor);
        } catch (JasperException ex) {
        }
        HashMap<String, SmapStratum> map = psVisitor.getMap();

        // set up our SMAP generator
        SmapGenerator g = new SmapGenerator();

        /** Disable reading of input SMAP because:
            1. There is a bug here: getRealPath() is null if .jsp is in a jar
               Bugzilla 14660.
            2. Mappings from other sources into .jsp files are not supported.
            TODO: fix 1. if 2. is not true.
        // determine if we have an input SMAP
        String smapPath = inputSmapPath(ctxt.getRealPath(ctxt.getJspFile()));
            File inputSmap = new File(smapPath);
            if (inputSmap.exists()) {
                byte[] embeddedSmap = null;
            byte[] subSmap = SDEInstaller.readWhole(inputSmap);
            String subSmapString = new String(subSmap, SMAP_ENCODING);
            g.addSmap(subSmapString, "JSP");
        }
        **/

        // now, assemble info about our own stratum (JSP) using JspLineMap
        SmapStratum s = new SmapStratum("JSP");

        g.setOutputFileName(unqualify(ctxt.getServletJavaFileName()));

        // Map out Node.Nodes
        evaluateNodes(pageNodes, s, map, ctxt.getOptions().getMappedFile());
        s.optimizeLineSection();
        g.addStratum(s, true);

        if (ctxt.getOptions().isSmapDumped()) {
            File outSmap = new File(ctxt.getClassFileName() + ".smap");
            PrintWriter so =
                new PrintWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(outSmap),
                        SMAP_ENCODING));
            so.print(g.getString());
            so.close();
        }

        String classFileName = ctxt.getClassFileName();
        int innerClassCount = map.size();
        String [] smapInfo = new String[2 + innerClassCount*2];
        smapInfo[0] = classFileName;
        smapInfo[1] = g.getString();

        int count = 2;
        Iterator<Map.Entry<String,SmapStratum>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,SmapStratum> entry = iter.next();
            String innerClass = entry.getKey();
            s = entry.getValue();
            s.optimizeLineSection();
            g = new SmapGenerator();
            g.setOutputFileName(unqualify(ctxt.getServletJavaFileName()));
            g.addStratum(s, true);

            String innerClassFileName =
                classFileName.substring(0, classFileName.indexOf(".class")) +
                '$' + innerClass + ".class";
            if (ctxt.getOptions().isSmapDumped()) {
                File outSmap = new File(innerClassFileName + ".smap");
                PrintWriter so =
                    new PrintWriter(
                        new OutputStreamWriter(
                            new FileOutputStream(outSmap),
                            SMAP_ENCODING));
                so.print(g.getString());
                so.close();
            }
            smapInfo[count] = innerClassFileName;
            smapInfo[count+1] = g.getString();
            count += 2;
        }

        return smapInfo;
    }

    public static void installSmap(String[] smap)
        throws IOException {
        if (smap == null) {
            return;
        }

        for (int i = 0; i < smap.length; i += 2) {
            File outServlet = new File(smap[i]);
            SDEInstaller.install(outServlet,
                    smap[i+1].getBytes(Charset.defaultCharset()));
        }
    }

    //*********************************************************************
    // Private utilities

    /**
     * Returns an unqualified version of the given file path.
     */
    private static String unqualify(String path) {
        path = path.replace('\\', '/');
        return path.substring(path.lastIndexOf('/') + 1);
    }

    //*********************************************************************
    // Installation logic (from Robert Field, JSR-045 spec lead)
    private static class SDEInstaller {

        private final org.apache.juli.logging.Log log=
            org.apache.juli.logging.LogFactory.getLog( SDEInstaller.class );

        static final String nameSDE = "SourceDebugExtension";

        byte[] orig;
        byte[] sdeAttr;
        byte[] gen;

        int origPos = 0;
        int genPos = 0;

        int sdeIndex;

        static void install(File classFile, byte[] smap) throws IOException {
            File tmpFile = new File(classFile.getPath() + "tmp");
            new SDEInstaller(classFile, smap, tmpFile);
            if (!classFile.delete()) {
                throw new IOException("classFile.delete() failed");
            }
            if (!tmpFile.renameTo(classFile)) {
                throw new IOException("tmpFile.renameTo(classFile) failed");
            }
        }

        SDEInstaller(File inClassFile, byte[] sdeAttr, File outClassFile)
            throws IOException {
            if (!inClassFile.exists()) {
                throw new FileNotFoundException("no such file: " + inClassFile);
            }

            this.sdeAttr = sdeAttr;
            // get the bytes
            orig = readWhole(inClassFile);
            gen = new byte[orig.length + sdeAttr.length + 100];

            // do it
            addSDE();

            // write result
            FileOutputStream outStream = null;
            try {
                outStream = new FileOutputStream(outClassFile);
                outStream.write(gen, 0, genPos);
            } finally {
                if (outStream != null) {
                    try {
                        outStream.close();
                    } catch (Exception e) {
                    }
                }
            }
        }

        static byte[] readWhole(File input) throws IOException {
            int len = (int)input.length();
            byte[] bytes = new byte[len];
            FileInputStream inStream = null;
            try {
                inStream = new FileInputStream(input);
                if (inStream.read(bytes, 0, len) != len) {
                    throw new IOException("expected size: " + len);
                }
            } finally {
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (Exception e) {
                    }
                }
            }
            return bytes;
        }

        void addSDE() throws UnsupportedEncodingException, IOException {
            copy(4 + 2 + 2); // magic min/maj version
            int constantPoolCountPos = genPos;
            int constantPoolCount = readU2();
            if (log.isDebugEnabled())
                log.debug("constant pool count: " + constantPoolCount);
            writeU2(constantPoolCount);

            // copy old constant pool return index of SDE symbol, if found
            sdeIndex = copyConstantPool(constantPoolCount);
            if (sdeIndex < 0) {
                // if "SourceDebugExtension" symbol not there add it
                writeUtf8ForSDE();

                // increment the constantPoolCount
                sdeIndex = constantPoolCount;
                ++constantPoolCount;
                randomAccessWriteU2(constantPoolCountPos, constantPoolCount);

                if (log.isDebugEnabled())
                    log.debug("SourceDebugExtension not found, installed at: " + sdeIndex);
            } else {
                if (log.isDebugEnabled())
                    log.debug("SourceDebugExtension found at: " + sdeIndex);
            }
            copy(2 + 2 + 2); // access, this, super
            int interfaceCount = readU2();
            writeU2(interfaceCount);
            if (log.isDebugEnabled())
                log.debug("interfaceCount: " + interfaceCount);
            copy(interfaceCount * 2);
            copyMembers(); // fields
            copyMembers(); // methods
            int attrCountPos = genPos;
            int attrCount = readU2();
            writeU2(attrCount);
            if (log.isDebugEnabled())
                log.debug("class attrCount: " + attrCount);
            // copy the class attributes, return true if SDE attr found (not copied)
            if (!copyAttrs(attrCount)) {
                // we will be adding SDE and it isn't already counted
                ++attrCount;
                randomAccessWriteU2(attrCountPos, attrCount);
                if (log.isDebugEnabled())
                    log.debug("class attrCount incremented");
            }
            writeAttrForSDE(sdeIndex);
        }

        void copyMembers() {
            int count = readU2();
            writeU2(count);
            if (log.isDebugEnabled())
                log.debug("members count: " + count);
            for (int i = 0; i < count; ++i) {
                copy(6); // access, name, descriptor
                int attrCount = readU2();
                writeU2(attrCount);
                if (log.isDebugEnabled())
                    log.debug("member attr count: " + attrCount);
                copyAttrs(attrCount);
            }
        }

        boolean copyAttrs(int attrCount) {
            boolean sdeFound = false;
            for (int i = 0; i < attrCount; ++i) {
                int nameIndex = readU2();
                // don't write old SDE
                if (nameIndex == sdeIndex) {
                    sdeFound = true;
                    if (log.isDebugEnabled())
                        log.debug("SDE attr found");
                } else {
                    writeU2(nameIndex); // name
                    int len = readU4();
                    writeU4(len);
                    copy(len);
                    if (log.isDebugEnabled())
                        log.debug("attr len: " + len);
                }
            }
            return sdeFound;
        }

        void writeAttrForSDE(int index) {
            writeU2(index);
            writeU4(sdeAttr.length);
            for (int i = 0; i < sdeAttr.length; ++i) {
                writeU1(sdeAttr[i]);
            }
        }

        void randomAccessWriteU2(int pos, int val) {
            int savePos = genPos;
            genPos = pos;
            writeU2(val);
            genPos = savePos;
        }

        int readU1() {
            return orig[origPos++] & 0xFF;
        }

        int readU2() {
            int res = readU1();
            return (res << 8) + readU1();
        }

        int readU4() {
            int res = readU2();
            return (res << 16) + readU2();
        }

        void writeU1(int val) {
            gen[genPos++] = (byte)val;
        }

        void writeU2(int val) {
            writeU1(val >> 8);
            writeU1(val & 0xFF);
        }

        void writeU4(int val) {
            writeU2(val >> 16);
            writeU2(val & 0xFFFF);
        }

        void copy(int count) {
            for (int i = 0; i < count; ++i) {
                gen[genPos++] = orig[origPos++];
            }
        }

        byte[] readBytes(int count) {
            byte[] bytes = new byte[count];
            for (int i = 0; i < count; ++i) {
                bytes[i] = orig[origPos++];
            }
            return bytes;
        }

        void writeBytes(byte[] bytes) {
            for (int i = 0; i < bytes.length; ++i) {
                gen[genPos++] = bytes[i];
            }
        }

        int copyConstantPool(int constantPoolCount)
            throws UnsupportedEncodingException, IOException {
            int sdeIndex = -1;
            // copy const pool index zero not in class file
            for (int i = 1; i < constantPoolCount; ++i) {
                int tag = readU1();
                writeU1(tag);
                switch (tag) {
                    case 7 :  // Class
                    case 8 :  // String
                    case 16 : // MethodType
                        if (log.isDebugEnabled())
                            log.debug(i + " copying 2 bytes");
                        copy(2);
                        break;
                    case 15 : // MethodHandle
                        if (log.isDebugEnabled())
                            log.debug(i + " copying 3 bytes");
                        copy(3);
                        break;
                    case 9 :  // Field
                    case 10 : // Method
                    case 11 : // InterfaceMethod
                    case 3 :  // Integer
                    case 4 :  // Float
                    case 12 : // NameAndType
                    case 18 : // InvokeDynamic
                        if (log.isDebugEnabled())
                            log.debug(i + " copying 4 bytes");
                        copy(4);
                        break;
                    case 5 : // Long
                    case 6 : // Double
                        if (log.isDebugEnabled())
                            log.debug(i + " copying 8 bytes");
                        copy(8);
                        i++;
                        break;
                    case 1 : // Utf8
                        int len = readU2();
                        writeU2(len);
                        byte[] utf8 = readBytes(len);
                        String str = new String(utf8, "UTF-8");
                        if (log.isDebugEnabled())
                            log.debug(i + " read class attr -- '" + str + "'");
                        if (str.equals(nameSDE)) {
                            sdeIndex = i;
                        }
                        writeBytes(utf8);
                        break;
                    default :
                        throw new IOException("unexpected tag: " + tag);
                }
            }
            return sdeIndex;
        }

        void writeUtf8ForSDE() {
            int len = nameSDE.length();
            writeU1(1); // Utf8 tag
            writeU2(len);
            for (int i = 0; i < len; ++i) {
                writeU1(nameSDE.charAt(i));
            }
        }
    }

    public static void evaluateNodes(
        Node.Nodes nodes,
        SmapStratum s,
        HashMap<String, SmapStratum> innerClassMap,
        boolean breakAtLF) {
        try {
            nodes.visit(new SmapGenVisitor(s, breakAtLF, innerClassMap));
        } catch (JasperException ex) {
        }
    }

    static class SmapGenVisitor extends Node.Visitor {

        private SmapStratum smap;
        private boolean breakAtLF;
        private HashMap<String, SmapStratum> innerClassMap;

        SmapGenVisitor(SmapStratum s, boolean breakAtLF, HashMap<String, SmapStratum> map) {
            this.smap = s;
            this.breakAtLF = breakAtLF;
            this.innerClassMap = map;
        }

        @Override
        public void visitBody(Node n) throws JasperException {
            SmapStratum smapSave = smap;
            String innerClass = n.getInnerClassName();
            if (innerClass != null) {
                this.smap = innerClassMap.get(innerClass);
            }
            super.visitBody(n);
            smap = smapSave;
        }

        @Override
        public void visit(Node.Declaration n) throws JasperException {
            doSmapText(n);
        }

        @Override
        public void visit(Node.Expression n) throws JasperException {
            doSmapText(n);
        }

        @Override
        public void visit(Node.Scriptlet n) throws JasperException {
            doSmapText(n);
        }

        @Override
        public void visit(Node.IncludeAction n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.ForwardAction n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.GetProperty n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.SetProperty n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.UseBean n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.PlugIn n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.CustomTag n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.UninterpretedTag n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.JspElement n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.JspText n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.NamedAttribute n) throws JasperException {
            visitBody(n);
        }

        @Override
        public void visit(Node.JspBody n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.InvokeAction n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.DoBodyAction n) throws JasperException {
            doSmap(n);
            visitBody(n);
        }

        @Override
        public void visit(Node.ELExpression n) throws JasperException {
            doSmap(n);
        }

        @Override
        public void visit(Node.TemplateText n) throws JasperException {
            Mark mark = n.getStart();
            if (mark == null) {
                return;
            }

            //Add the file information
            String fileName = mark.getFile();
            smap.addFile(unqualify(fileName), fileName);

            //Add a LineInfo that corresponds to the beginning of this node
            int iInputStartLine = mark.getLineNumber();
            int iOutputStartLine = n.getBeginJavaLine();
            int iOutputLineIncrement = breakAtLF? 1: 0;
            smap.addLineData(iInputStartLine, fileName, 1, iOutputStartLine,
                             iOutputLineIncrement);

            // Output additional mappings in the text
            java.util.ArrayList<Integer> extraSmap = n.getExtraSmap();

            if (extraSmap != null) {
                for (int i = 0; i < extraSmap.size(); i++) {
                    iOutputStartLine += iOutputLineIncrement;
                    smap.addLineData(
                        iInputStartLine+extraSmap.get(i).intValue(),
                        fileName,
                        1,
                        iOutputStartLine,
                        iOutputLineIncrement);
                }
            }
        }

        private void doSmap(
            Node n,
            int inLineCount,
            int outIncrement,
            int skippedLines) {
            Mark mark = n.getStart();
            if (mark == null) {
                return;
            }

            String unqualifiedName = unqualify(mark.getFile());
            smap.addFile(unqualifiedName, mark.getFile());
            smap.addLineData(
                mark.getLineNumber() + skippedLines,
                mark.getFile(),
                inLineCount - skippedLines,
                n.getBeginJavaLine() + skippedLines,
                outIncrement);
        }

        private void doSmap(Node n) {
            doSmap(n, 1, n.getEndJavaLine() - n.getBeginJavaLine(), 0);
        }

        private void doSmapText(Node n) {
            String text = n.getText();
            int index = 0;
            int next = 0;
            int lineCount = 1;
            int skippedLines = 0;
            boolean slashStarSeen = false;
            boolean beginning = true;

            // Count lines inside text, but skipping comment lines at the
            // beginning of the text.
            while ((next = text.indexOf('\n', index)) > -1) {
                if (beginning) {
                    String line = text.substring(index, next).trim();
                    if (!slashStarSeen && line.startsWith("/*")) {
                        slashStarSeen = true;
                    }
                    if (slashStarSeen) {
                        skippedLines++;
                        int endIndex = line.indexOf("*/");
                        if (endIndex >= 0) {
                            // End of /* */ comment
                            slashStarSeen = false;
                            if (endIndex < line.length() - 2) {
                                // Some executable code after comment
                                skippedLines--;
                                beginning = false;
                            }
                        }
                    } else if (line.length() == 0 || line.startsWith("//")) {
                        skippedLines++;
                    } else {
                        beginning = false;
                    }
                }
                lineCount++;
                index = next + 1;
            }

            doSmap(n, lineCount, 1, skippedLines);
        }
    }

    private static class PreScanVisitor extends Node.Visitor {

        HashMap<String, SmapStratum> map = new HashMap<String, SmapStratum>();

        @Override
        public void doVisit(Node n) {
            String inner = n.getInnerClassName();
            if (inner != null && !map.containsKey(inner)) {
                map.put(inner, new SmapStratum("JSP"));
            }
        }

        HashMap<String, SmapStratum> getMap() {
            return map;
        }
    }

}
