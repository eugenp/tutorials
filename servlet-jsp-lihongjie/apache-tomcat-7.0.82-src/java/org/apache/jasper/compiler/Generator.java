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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.servlet.jsp.tagext.TagAttributeInfo;
import javax.servlet.jsp.tagext.TagInfo;
import javax.servlet.jsp.tagext.TagVariableInfo;
import javax.servlet.jsp.tagext.VariableInfo;

import org.apache.jasper.Constants;
import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.compiler.Node.NamedAttribute;
import org.apache.jasper.runtime.JspRuntimeLibrary;
import org.xml.sax.Attributes;

/**
 * Generate Java source from Nodes
 *
 * @author Anil K. Vijendran
 * @author Danno Ferrin
 * @author Mandar Raje
 * @author Rajiv Mordani
 * @author Pierre Delisle
 *
 * Tomcat 4.1.x and Tomcat 5:
 * @author Kin-man Chung
 * @author Jan Luehe
 * @author Shawn Bayern
 * @author Mark Roth
 * @author Denis Benoit
 *
 * Tomcat 6.x
 * @author Jacob Hookom
 * @author Remy Maucherat
 */

class Generator {

    private static final Class<?>[] OBJECT_CLASS = { Object.class };

    private static final String VAR_EXPRESSIONFACTORY =
        System.getProperty("org.apache.jasper.compiler.Generator.VAR_EXPRESSIONFACTORY", "_el_expressionfactory");
    private static final String VAR_INSTANCEMANAGER =
        System.getProperty("org.apache.jasper.compiler.Generator.VAR_INSTANCEMANAGER", "_jsp_instancemanager");
    private static final boolean POOL_TAGS_WITH_EXTENDS =
        Boolean.getBoolean("org.apache.jasper.compiler.Generator.POOL_TAGS_WITH_EXTENDS");

    /* System property that controls if the requirement to have the object
     * used in jsp:getProperty action to be previously "introduced"
     * to the JSP processor (see JSP.5.3) is enforced.
     */ 
    private static final boolean STRICT_GET_PROPERTY = Boolean.parseBoolean(
            System.getProperty(
                    "org.apache.jasper.compiler.Generator.STRICT_GET_PROPERTY",
                    "true"));

    private final ServletWriter out;

    private final ArrayList<GenBuffer> methodsBuffered;

    private final FragmentHelperClass fragmentHelperClass;

    private final ErrorDispatcher err;

    private final BeanRepository beanInfo;
    
    private final Set<String> varInfoNames;

    private final JspCompilationContext ctxt;

    private final boolean isPoolingEnabled;

    private final boolean breakAtLF;

    private String jspIdPrefix;

    private int jspId;

    private final PageInfo pageInfo;

    private final Vector<String> tagHandlerPoolNames;

    private GenBuffer charArrayBuffer;
    
    private final DateFormat timestampFormat;

    private final ELInterpreter elInterpreter;

    /**
     * @param s
     *            the input string
     * @return quoted and escaped string, per Java rule
     */
    static String quote(String s) {

        if (s == null)
            return "null";

        return '"' + escape(s) + '"';
    }

    /**
     * @param s
     *            the input string
     * @return escaped string, per Java rule
     */
    static String escape(String s) {

        if (s == null)
            return "";

        StringBuilder b = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '"')
                b.append('\\').append('"');
            else if (c == '\\')
                b.append('\\').append('\\');
            else if (c == '\n')
                b.append('\\').append('n');
            else if (c == '\r')
                b.append('\\').append('r');
            else
                b.append(c);
        }
        return b.toString();
    }

    /**
     * Single quote and escape a character
     */
    static String quote(char c) {

        StringBuilder b = new StringBuilder();
        b.append('\'');
        if (c == '\'')
            b.append('\\').append('\'');
        else if (c == '\\')
            b.append('\\').append('\\');
        else if (c == '\n')
            b.append('\\').append('n');
        else if (c == '\r')
            b.append('\\').append('r');
        else
            b.append(c);
        b.append('\'');
        return b.toString();
    }

    private String createJspId() {
        if (this.jspIdPrefix == null) {
            StringBuilder sb = new StringBuilder(32);
            String name = ctxt.getServletJavaFileName();
            sb.append("jsp_");
            // Cast to long to avoid issue with Integer.MIN_VALUE
            sb.append(Math.abs((long) name.hashCode()));
            sb.append('_');
            this.jspIdPrefix = sb.toString();
        }
        return this.jspIdPrefix + (this.jspId++);
    }

    /**
     * Generates declarations. This includes "info" of the page directive, and
     * scriptlet declarations.
     */
    private void generateDeclarations(Node.Nodes page) throws JasperException {

        class DeclarationVisitor extends Node.Visitor {

            private boolean getServletInfoGenerated = false;

            /*
             * Generates getServletInfo() method that returns the value of the
             * page directive's 'info' attribute, if present.
             *
             * The Validator has already ensured that if the translation unit
             * contains more than one page directive with an 'info' attribute,
             * their values match.
             */
            @Override
            public void visit(Node.PageDirective n) throws JasperException {

                if (getServletInfoGenerated) {
                    return;
                }

                String info = n.getAttributeValue("info");
                if (info == null)
                    return;

                getServletInfoGenerated = true;
                out.printil("public java.lang.String getServletInfo() {");
                out.pushIndent();
                out.printin("return ");
                out.print(quote(info));
                out.println(";");
                out.popIndent();
                out.printil("}");
                out.println();
            }

            @Override
            public void visit(Node.Declaration n) throws JasperException {
                n.setBeginJavaLine(out.getJavaLine());
                out.printMultiLn(n.getText());
                out.println();
                n.setEndJavaLine(out.getJavaLine());
            }

            // Custom Tags may contain declarations from tag plugins.
            @Override
            public void visit(Node.CustomTag n) throws JasperException {
                if (n.useTagPlugin()) {
                    if (n.getAtSTag() != null) {
                        n.getAtSTag().visit(this);
                    }
                    visitBody(n);
                    if (n.getAtETag() != null) {
                        n.getAtETag().visit(this);
                    }
                } else {
                    visitBody(n);
                }
            }
        }

        out.println();
        page.visit(new DeclarationVisitor());
    }

    /**
     * Compiles list of tag handler pool names.
     */
    private void compileTagHandlerPoolList(Node.Nodes page)
            throws JasperException {

        class TagHandlerPoolVisitor extends Node.Visitor {

            private final Vector<String> names;

            /*
             * Constructor
             *
             * @param v Vector of tag handler pool names to populate
             */
            TagHandlerPoolVisitor(Vector<String> v) {
                names = v;
            }

            /*
             * Gets the name of the tag handler pool for the given custom tag
             * and adds it to the list of tag handler pool names unless it is
             * already contained in it.
             */
            @Override
            public void visit(Node.CustomTag n) throws JasperException {

                if (!n.implementsSimpleTag()) {
                    String name = createTagHandlerPoolName(n.getPrefix(), n
                            .getLocalName(), n.getAttributes(), 
                            n.getNamedAttributeNodes(), n.hasEmptyBody());
                    n.setTagHandlerPoolName(name);
                    if (!names.contains(name)) {
                        names.add(name);
                    }
                }
                visitBody(n);
            }

            /*
             * Creates the name of the tag handler pool whose tag handlers may
             * be (re)used to service this action.
             *
             * @return The name of the tag handler pool
             */
            private String createTagHandlerPoolName(String prefix,
                    String shortName, Attributes attrs, Node.Nodes namedAttrs,
                    boolean hasEmptyBody) {
                StringBuilder poolName = new StringBuilder(64);
                poolName.append("_jspx_tagPool_").append(prefix).append('_')
                        .append(shortName);

                if (attrs != null) {
                    String[] attrNames =
                        new String[attrs.getLength() + namedAttrs.size()];
                    for (int i = 0; i < attrNames.length; i++) {
                        attrNames[i] = attrs.getQName(i);
                    }
                    for (int i = 0; i < namedAttrs.size(); i++) {
                        attrNames[attrs.getLength() + i] =
                            ((NamedAttribute) namedAttrs.getNode(i)).getQName();
                    }
                    Arrays.sort(attrNames, Collections.reverseOrder());
                    if (attrNames.length > 0) {
                        poolName.append('&');
                    }
                    for (int i = 0; i < attrNames.length; i++) {
                        poolName.append('_');
                        poolName.append(attrNames[i]);
                    }
                }
                if (hasEmptyBody) {
                    poolName.append("_nobody");
                }
                return JspUtil.makeJavaIdentifier(poolName.toString());
            }
        }

        page.visit(new TagHandlerPoolVisitor(tagHandlerPoolNames));
    }

    private void declareTemporaryScriptingVars(Node.Nodes page)
            throws JasperException {

        class ScriptingVarVisitor extends Node.Visitor {

            private final Vector<String> vars;

            ScriptingVarVisitor() {
                vars = new Vector<String>();
            }

            @Override
            public void visit(Node.CustomTag n) throws JasperException {
                // XXX - Actually there is no need to declare those
                // "_jspx_" + varName + "_" + nestingLevel variables when we are
                // inside a JspFragment.

                if (n.getCustomNestingLevel() > 0) {
                    TagVariableInfo[] tagVarInfos = n.getTagVariableInfos();
                    VariableInfo[] varInfos = n.getVariableInfos();

                    if (varInfos.length > 0) {
                        for (int i = 0; i < varInfos.length; i++) {
                            String varName = varInfos[i].getVarName();
                            String tmpVarName = "_jspx_" + varName + "_"
                                    + n.getCustomNestingLevel();
                            if (!vars.contains(tmpVarName)) {
                                vars.add(tmpVarName);
                                out.printin(varInfos[i].getClassName());
                                out.print(" ");
                                out.print(tmpVarName);
                                out.print(" = ");
                                out.print(null);
                                out.println(";");
                            }
                        }
                    } else {
                        for (int i = 0; i < tagVarInfos.length; i++) {
                            String varName = tagVarInfos[i].getNameGiven();
                            if (varName == null) {
                                varName = n.getTagData().getAttributeString(
                                        tagVarInfos[i].getNameFromAttribute());
                            } else if (tagVarInfos[i].getNameFromAttribute() != null) {
                                // alias
                                continue;
                            }
                            String tmpVarName = "_jspx_" + varName + "_"
                                    + n.getCustomNestingLevel();
                            if (!vars.contains(tmpVarName)) {
                                vars.add(tmpVarName);
                                out.printin(tagVarInfos[i].getClassName());
                                out.print(" ");
                                out.print(tmpVarName);
                                out.print(" = ");
                                out.print(null);
                                out.println(";");
                            }
                        }
                    }
                }

                visitBody(n);
            }
        }

        page.visit(new ScriptingVarVisitor());
    }

    /*
     * Generates getters for
     * - instance manager
     * - expression factory
     *
     * For JSPs these methods use lazy init. This is not an option for tag files
     * (at least it would be more complicated to generate) because the
     * ServletConfig is not readily available.
     */
    private void generateGetters() {
        out.printil("public javax.el.ExpressionFactory _jsp_getExpressionFactory() {");
        out.pushIndent();
        if (!ctxt.isTagFile()) {
            out.printin("if (");
            out.print(VAR_EXPRESSIONFACTORY);
            out.println(" == null) {");
            out.pushIndent();
            out.printil("synchronized (this) {");
            out.pushIndent();
            out.printin("if (");
            out.print(VAR_EXPRESSIONFACTORY);
            out.println(" == null) {");
            out.pushIndent();
            out.printin(VAR_EXPRESSIONFACTORY);
            out.println(" = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();");
            out.popIndent();
            out.printil("}");
            out.popIndent();
            out.printil("}");
            out.popIndent();
            out.printil("}");
        }
        out.printin("return ");
        out.print(VAR_EXPRESSIONFACTORY);
        out.println(";");
        out.popIndent();
        out.printil("}");

        out.println();

        out.printil("public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {");
        out.pushIndent();
        if (!ctxt.isTagFile()) {
            out.printin("if (");
            out.print(VAR_INSTANCEMANAGER);
            out.println(" == null) {");
            out.pushIndent();
            out.printil("synchronized (this) {");
            out.pushIndent();
            out.printin("if (");
            out.print(VAR_INSTANCEMANAGER);
            out.println(" == null) {");
            out.pushIndent();
            out.printin(VAR_INSTANCEMANAGER);
            out.println(" = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());");
            out.popIndent();
            out.printil("}");
            out.popIndent();
            out.printil("}");
            out.popIndent();
            out.printil("}");
        }
        out.printin("return ");
        out.print(VAR_INSTANCEMANAGER);
        out.println(";");
        out.popIndent();
        out.printil("}");

        out.println();
    }

    /**
     * Generates the _jspInit() method for instantiating the tag handler pools.
     * For tag file, _jspInit has to be invoked manually, and the ServletConfig
     * object explicitly passed.
     *
     * In JSP 2.1, we also instantiate an ExpressionFactory
     */
    private void generateInit() {

        if (ctxt.isTagFile()) {
            out.printil("private void _jspInit(javax.servlet.ServletConfig config) {");
        } else {
            out.printil("public void _jspInit() {");
        }

        out.pushIndent();
        if (isPoolingEnabled) {
            for (int i = 0; i < tagHandlerPoolNames.size(); i++) {
                out.printin(tagHandlerPoolNames.elementAt(i));
                out.print(" = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(");
                if (ctxt.isTagFile()) {
                    out.print("config");
                } else {
                    out.print("getServletConfig()");
                }
                out.println(");");
            }
        }

        // Tag files can't (easily) use lazy init for these so initialise them
        // here.
        if (ctxt.isTagFile()) {
            out.printin(VAR_EXPRESSIONFACTORY);
            out.println(" = _jspxFactory.getJspApplicationContext(config.getServletContext()).getExpressionFactory();");
            out.printin(VAR_INSTANCEMANAGER);
            out.println(" = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(config);");
        }

        out.popIndent();
        out.printil("}");
        out.println();
    }

    /**
     * Generates the _jspDestroy() method which is responsible for calling the
     * release() method on every tag handler in any of the tag handler pools.
     */
    private void generateDestroy() {

        out.printil("public void _jspDestroy() {");
        out.pushIndent();

        if (isPoolingEnabled) {
            for (int i = 0; i < tagHandlerPoolNames.size(); i++) {
                                out.printin(tagHandlerPoolNames.elementAt(i));
                                out.println(".release();");
            }
        }

        out.popIndent();
        out.printil("}");
        out.println();
    }

    /**
     * Generate preamble package name (shared by servlet and tag handler
     * preamble generation)
     */
    private void genPreamblePackage(String packageName) {
        if (!"".equals(packageName) && packageName != null) {
            out.printil("package " + packageName + ";");
            out.println();
        }
    }

    /**
     * Generate preamble imports (shared by servlet and tag handler preamble
     * generation)
     */
    private void genPreambleImports() {
        Iterator<String> iter = pageInfo.getImports().iterator();
        while (iter.hasNext()) {
            out.printin("import ");
            out.print(iter.next());
            out.println(";");
        }

        out.println();
    }

    /**
     * Generation of static initializers in preamble. For example, dependent
     * list, el function map, prefix map. (shared by servlet and tag handler
     * preamble generation)
     */
    private void genPreambleStaticInitializers() {
        out.printil("private static final javax.servlet.jsp.JspFactory _jspxFactory =");
        out.printil("        javax.servlet.jsp.JspFactory.getDefaultFactory();");
        out.println();

        // Static data for getDependants()
        out.printil("private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;");
        out.println();
        Map<String,Long> dependants = pageInfo.getDependants();
        Iterator<Entry<String,Long>> iter = dependants.entrySet().iterator();
        if (!dependants.isEmpty()) {
            out.printil("static {");
            out.pushIndent();
            out.printin("_jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(");
            out.print("" + dependants.size());
            out.println(");");
            while (iter.hasNext()) {
                Entry<String,Long> entry = iter.next();
                out.printin("_jspx_dependants.put(\"");
                out.print(entry.getKey());
                out.print("\", Long.valueOf(");
                out.print(entry.getValue().toString());
                out.println("L));");
            }
            out.popIndent();
            out.printil("}");
            out.println();
        }
    }

    /**
     * Declare tag handler pools (tags of the same type and with the same
     * attribute set share the same tag handler pool) (shared by servlet and tag
     * handler preamble generation)
     *
     * In JSP 2.1, we also scope an instance of ExpressionFactory
     */
    private void genPreambleClassVariableDeclarations() {
        if (isPoolingEnabled && !tagHandlerPoolNames.isEmpty()) {
            for (int i = 0; i < tagHandlerPoolNames.size(); i++) {
                out.printil("private org.apache.jasper.runtime.TagHandlerPool "
                        + tagHandlerPoolNames.elementAt(i) + ";");
            }
            out.println();
        }
        out.printin("private volatile javax.el.ExpressionFactory ");
        out.print(VAR_EXPRESSIONFACTORY);
        out.println(";");
        out.printin("private volatile org.apache.tomcat.InstanceManager ");
        out.print(VAR_INSTANCEMANAGER);
        out.println(";");
        out.println();
    }

    /**
     * Declare general-purpose methods (shared by servlet and tag handler
     * preamble generation)
     */
    private void genPreambleMethods() {
        // Method used to get compile time file dependencies
        out.printil("public java.util.Map<java.lang.String,java.lang.Long> getDependants() {");
        out.pushIndent();
        out.printil("return _jspx_dependants;");
        out.popIndent();
        out.printil("}");
        out.println();

        generateGetters();
        generateInit();
        generateDestroy();
    }

    /**
     * Generates the beginning of the static portion of the servlet.
     */
    private void generatePreamble(Node.Nodes page) throws JasperException {

        String servletPackageName = ctxt.getServletPackageName();
        String servletClassName = ctxt.getServletClassName();
        String serviceMethodName = Constants.SERVICE_METHOD_NAME;

        // First the package name:
        genPreamblePackage(servletPackageName);

        // Generate imports
        genPreambleImports();

        // Generate class declaration
        out.printin("public final class ");
        out.print(servletClassName);
        out.print(" extends ");
        out.println(pageInfo.getExtends());
        out.printin("    implements org.apache.jasper.runtime.JspSourceDependent");
        if (!pageInfo.isThreadSafe()) {
            out.println(",");
            out.printin("                 javax.servlet.SingleThreadModel");
        }
        out.println(" {");
        out.pushIndent();

        // Class body begins here
        generateDeclarations(page);

        // Static initializations here
        genPreambleStaticInitializers();

        // Class variable declarations
        genPreambleClassVariableDeclarations();

        // Methods here
        genPreambleMethods();

        // Now the service method
        out.printin("public void ");
        out.print(serviceMethodName);
        out.println("(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)");
        out.println("        throws java.io.IOException, javax.servlet.ServletException {");

        out.pushIndent();
        out.println();

        // Local variable declarations
        out.printil("final javax.servlet.jsp.PageContext pageContext;");

        if (pageInfo.isSession())
            out.printil("javax.servlet.http.HttpSession session = null;");

        if (pageInfo.isErrorPage()) {
            out.printil("java.lang.Throwable exception = org.apache.jasper.runtime.JspRuntimeLibrary.getThrowable(request);");
            out.printil("if (exception != null) {");
            out.pushIndent();
            out.printil("response.setStatus(javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR);");
            out.popIndent();
            out.printil("}");
        }

        out.printil("final javax.servlet.ServletContext application;");
        out.printil("final javax.servlet.ServletConfig config;");
        out.printil("javax.servlet.jsp.JspWriter out = null;");
        out.printil("final java.lang.Object page = this;");

        out.printil("javax.servlet.jsp.JspWriter _jspx_out = null;");
        out.printil("javax.servlet.jsp.PageContext _jspx_page_context = null;");
        out.println();

        declareTemporaryScriptingVars(page);
        out.println();

        out.printil("try {");
        out.pushIndent();

        out.printin("response.setContentType(");
        out.print(quote(pageInfo.getContentType()));
        out.println(");");

        if (ctxt.getOptions().isXpoweredBy()) {
            out.printil("response.addHeader(\"X-Powered-By\", \"JSP/2.1\");");
        }

        out.printil("pageContext = _jspxFactory.getPageContext(this, request, response,");
        out.printin("\t\t\t");
        out.print(quote(pageInfo.getErrorPage()));
        out.print(", " + pageInfo.isSession());
        out.print(", " + pageInfo.getBuffer());
        out.print(", " + pageInfo.isAutoFlush());
        out.println(");");
        out.printil("_jspx_page_context = pageContext;");

        out.printil("application = pageContext.getServletContext();");
        out.printil("config = pageContext.getServletConfig();");

        if (pageInfo.isSession())
            out.printil("session = pageContext.getSession();");
        out.printil("out = pageContext.getOut();");
        out.printil("_jspx_out = out;");
        out.println();
    }

    /**
     * Generates an XML Prolog, which includes an XML declaration and an XML
     * doctype declaration.
     */
    private void generateXmlProlog(Node.Nodes page) {

        /*
         * An XML declaration is generated under the following conditions: -
         * 'omit-xml-declaration' attribute of <jsp:output> action is set to
         * "no" or "false" - JSP document without a <jsp:root>
         */
        String omitXmlDecl = pageInfo.getOmitXmlDecl();
        if ((omitXmlDecl != null && !JspUtil.booleanValue(omitXmlDecl))
                || (omitXmlDecl == null && page.getRoot().isXmlSyntax()
                        && !pageInfo.hasJspRoot() && !ctxt.isTagFile())) {
            String cType = pageInfo.getContentType();
            String charSet = cType.substring(cType.indexOf("charset=") + 8);
            out.printil("out.write(\"<?xml version=\\\"1.0\\\" encoding=\\\""
                    + charSet + "\\\"?>\\n\");");
        }

        /*
         * Output a DOCTYPE declaration if the doctype-root-element appears. If
         * doctype-public appears: <!DOCTYPE name PUBLIC "doctypePublic"
         * "doctypeSystem"> else <!DOCTYPE name SYSTEM "doctypeSystem" >
         */

        String doctypeName = pageInfo.getDoctypeName();
        if (doctypeName != null) {
            String doctypePublic = pageInfo.getDoctypePublic();
            String doctypeSystem = pageInfo.getDoctypeSystem();
            out.printin("out.write(\"<!DOCTYPE ");
            out.print(doctypeName);
            if (doctypePublic == null) {
                out.print(" SYSTEM \\\"");
            } else {
                out.print(" PUBLIC \\\"");
                out.print(doctypePublic);
                out.print("\\\" \\\"");
            }
            out.print(doctypeSystem);
            out.println("\\\">\\n\");");
        }
    }

    /**
     * A visitor that generates codes for the elements in the page.
     */
    class GenerateVisitor extends Node.Visitor {

        /*
         * Hashtable containing introspection information on tag handlers:
         * <key>: tag prefix <value>: hashtable containing introspection on tag
         * handlers: <key>: tag short name <value>: introspection info of tag
         * handler for <prefix:shortName> tag
         */
        private final Hashtable<String,Hashtable<String,TagHandlerInfo>> handlerInfos;

        private final Hashtable<String,Integer> tagVarNumbers;

        private String parent;

        private boolean isSimpleTagParent; // Is parent a SimpleTag?

        private String pushBodyCountVar;

        private String simpleTagHandlerVar;

        private boolean isSimpleTagHandler;

        private boolean isFragment;

        private final boolean isTagFile;

        private ServletWriter out;

        private final ArrayList<GenBuffer> methodsBuffered;

        private final FragmentHelperClass fragmentHelperClass;

        private int methodNesting;

        private int charArrayCount;

        private HashMap<String,String> textMap;

        /**
         * Constructor.
         */
        public GenerateVisitor(boolean isTagFile, ServletWriter out,
                ArrayList<GenBuffer> methodsBuffered,
                FragmentHelperClass fragmentHelperClass) {

            this.isTagFile = isTagFile;
            this.out = out;
            this.methodsBuffered = methodsBuffered;
            this.fragmentHelperClass = fragmentHelperClass;
            methodNesting = 0;
            handlerInfos =
                new Hashtable<String,Hashtable<String,TagHandlerInfo>>();
            tagVarNumbers = new Hashtable<String,Integer>();
            textMap = new HashMap<String,String>();
        }

        /**
         * Returns an attribute value, optionally URL encoded. If the value is a
         * runtime expression, the result is the expression itself, as a string.
         * If the result is an EL expression, we insert a call to the
         * interpreter. If the result is a Named Attribute we insert the
         * generated variable name. Otherwise the result is a string literal,
         * quoted and escaped.
         *
         * @param attr
         *            An JspAttribute object
         * @param encode
         *            true if to be URL encoded
         * @param expectedType
         *            the expected type for an EL evaluation (ignored for
         *            attributes that aren't EL expressions)
         */
        private String attributeValue(Node.JspAttribute attr, boolean encode,
                Class<?> expectedType, boolean isXml) {
            String v = attr.getValue();
            if (!attr.isNamedAttribute() && (v == null))
                return "";

            if (attr.isExpression()) {
                if (encode) {
                    return "org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf("
                            + v + "), request.getCharacterEncoding())";
                }
                return v;
            } else if (attr.isELInterpreterInput()) {
                v = elInterpreter.interpreterCall(ctxt, this.isTagFile, v,
                        expectedType, attr.getEL().getMapName(), isXml);
                if (encode) {
                    return "org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("
                            + v + ", request.getCharacterEncoding())";
                }
                return v;
            } else if (attr.isNamedAttribute()) {
                return attr.getNamedAttributeNode().getTemporaryVariableName();
            } else {
                if (encode) {
                    return "org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("
                            + quote(v) + ", request.getCharacterEncoding())";
                }
                return quote(v);
            }
        }


        /**
         * Prints the attribute value specified in the param action, in the form
         * of name=value string.
         *
         * @param n
         *            the parent node for the param action nodes.
         */
        private void printParams(Node n, String pageParam, boolean literal)
                throws JasperException {

            class ParamVisitor extends Node.Visitor {
                String separator;

                ParamVisitor(String separator) {
                    this.separator = separator;
                }

                @Override
                public void visit(Node.ParamAction n) throws JasperException {

                    out.print(" + ");
                    out.print(separator);
                    out.print(" + ");
                    out.print("org.apache.jasper.runtime.JspRuntimeLibrary."
                            + "URLEncode(" + quote(n.getTextAttribute("name"))
                            + ", request.getCharacterEncoding())");
                    out.print("+ \"=\" + ");
                    out.print(attributeValue(n.getValue(), true, String.class,
                            n.getRoot().isXmlSyntax()));

                    // The separator is '&' after the second use
                    separator = "\"&\"";
                }
            }

            String sep;
            if (literal) {
                sep = pageParam.indexOf('?') > 0 ? "\"&\"" : "\"?\"";
            } else {
                sep = "((" + pageParam + ").indexOf('?')>0? '&': '?')";
            }
            if (n.getBody() != null) {
                n.getBody().visit(new ParamVisitor(sep));
            }
        }

        @Override
        public void visit(Node.Expression n) throws JasperException {
            n.setBeginJavaLine(out.getJavaLine());
            out.printin("out.print(");
            out.printMultiLn(n.getText());
            out.println(");");
            n.setEndJavaLine(out.getJavaLine());
        }

        @Override
        public void visit(Node.Scriptlet n) throws JasperException {
            n.setBeginJavaLine(out.getJavaLine());
            out.printMultiLn(n.getText());
            out.println();
            n.setEndJavaLine(out.getJavaLine());
        }

        @Override
        public void visit(Node.ELExpression n) throws JasperException {
            n.setBeginJavaLine(out.getJavaLine());
            if (!pageInfo.isELIgnored() && (n.getEL() != null)) {
                out.printil("out.write("
                        + elInterpreter.interpreterCall(ctxt, this.isTagFile,
                                n.getType() + "{" + n.getText() + "}",
                                String.class, n.getEL().getMapName(), false) +
                        ");");
            } else {
                out.printil("out.write("
                        + quote(n.getType() + "{" + n.getText() + "}") + ");");
            }
            n.setEndJavaLine(out.getJavaLine());
        }

        @Override
        public void visit(Node.IncludeAction n) throws JasperException {

            String flush = n.getTextAttribute("flush");
            Node.JspAttribute page = n.getPage();

            boolean isFlush = false; // default to false;
            if ("true".equals(flush))
                isFlush = true;

            n.setBeginJavaLine(out.getJavaLine());

            String pageParam;
            if (page.isNamedAttribute()) {
                // If the page for jsp:include was specified via
                // jsp:attribute, first generate code to evaluate
                // that body.
                pageParam = generateNamedAttributeValue(page
                        .getNamedAttributeNode());
            } else {
                pageParam = attributeValue(page, false, String.class,
                        n.getRoot().isXmlSyntax());
            }

            // If any of the params have their values specified by
            // jsp:attribute, prepare those values first.
            Node jspBody = findJspBody(n);
            if (jspBody != null) {
                prepareParams(jspBody);
            } else {
                prepareParams(n);
            }

            out.printin("org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "
                    + pageParam);
            printParams(n, pageParam, page.isLiteral());
            out.println(", out, " + isFlush + ");");

            n.setEndJavaLine(out.getJavaLine());
        }

        /**
         * Scans through all child nodes of the given parent for &lt;param&gt;
         * subelements. For each &lt;param&gt; element, if its value is specified via
         * a Named Attribute (&lt;jsp:attribute&gt;), generate the code to evaluate
         * those bodies first.
         * <p>
         * If parent is null, simply returns.
         */
        private void prepareParams(Node parent) throws JasperException {
            if (parent == null)
                return;

            Node.Nodes subelements = parent.getBody();
            if (subelements != null) {
                for (int i = 0; i < subelements.size(); i++) {
                    Node n = subelements.getNode(i);
                    if (n instanceof Node.ParamAction) {
                        Node.Nodes paramSubElements = n.getBody();
                        for (int j = 0; (paramSubElements != null)
                                && (j < paramSubElements.size()); j++) {
                            Node m = paramSubElements.getNode(j);
                            if (m instanceof Node.NamedAttribute) {
                                generateNamedAttributeValue((Node.NamedAttribute) m);
                            }
                        }
                    }
                }
            }
        }

        /**
         * Finds the &lt;jsp:body&gt; subelement of the given parent node. If not
         * found, null is returned.
         */
        private Node.JspBody findJspBody(Node parent) {
            Node.JspBody result = null;

            Node.Nodes subelements = parent.getBody();
            for (int i = 0; (subelements != null) && (i < subelements.size()); i++) {
                Node n = subelements.getNode(i);
                if (n instanceof Node.JspBody) {
                    result = (Node.JspBody) n;
                    break;
                }
            }

            return result;
        }

        @Override
        public void visit(Node.ForwardAction n) throws JasperException {
            Node.JspAttribute page = n.getPage();

            n.setBeginJavaLine(out.getJavaLine());

            out.printil("if (true) {"); // So that javac won't complain about
            out.pushIndent(); // codes after "return"

            String pageParam;
            if (page.isNamedAttribute()) {
                // If the page for jsp:forward was specified via
                // jsp:attribute, first generate code to evaluate
                // that body.
                pageParam = generateNamedAttributeValue(page
                        .getNamedAttributeNode());
            } else {
                pageParam = attributeValue(page, false, String.class,
                        n.getRoot().isXmlSyntax());
            }

            // If any of the params have their values specified by
            // jsp:attribute, prepare those values first.
            Node jspBody = findJspBody(n);
            if (jspBody != null) {
                prepareParams(jspBody);
            } else {
                prepareParams(n);
            }

            out.printin("_jspx_page_context.forward(");
            out.print(pageParam);
            printParams(n, pageParam, page.isLiteral());
            out.println(");");
            if (isTagFile || isFragment) {
                out.printil("throw new javax.servlet.jsp.SkipPageException();");
            } else {
                out.printil((methodNesting > 0) ? "return true;" : "return;");
            }
            out.popIndent();
            out.printil("}");

            n.setEndJavaLine(out.getJavaLine());
            // XXX Not sure if we can eliminate dead codes after this.
        }

        @Override
        public void visit(Node.GetProperty n) throws JasperException {
            String name = n.getTextAttribute("name");
            String property = n.getTextAttribute("property");

            n.setBeginJavaLine(out.getJavaLine());

            if (beanInfo.checkVariable(name)) {
                // Bean is defined using useBean, introspect at compile time
                Class<?> bean = beanInfo.getBeanType(name);
                String beanName = bean.getCanonicalName();
                java.lang.reflect.Method meth = JspRuntimeLibrary
                        .getReadMethod(bean, property);
                String methodName = meth.getName();
                out.printil("out.write(org.apache.jasper.runtime.JspRuntimeLibrary.toString("
                        + "((("
                        + beanName
                        + ")_jspx_page_context.findAttribute("
                        + "\""
                        + name + "\"))." + methodName + "())));");
            } else if (!STRICT_GET_PROPERTY || varInfoNames.contains(name)) {
                // The object is a custom action with an associated
                // VariableInfo entry for this name.
                // Get the class name and then introspect at runtime.
                out.printil("out.write(org.apache.jasper.runtime.JspRuntimeLibrary.toString"
                        + "(org.apache.jasper.runtime.JspRuntimeLibrary.handleGetProperty"
                        + "(_jspx_page_context.findAttribute(\""
                        + name
                        + "\"), \""
                        + property
                        + "\")));");
            } else {
                StringBuilder msg = new StringBuilder();
                msg.append("file:");
                msg.append(n.getStart());
                msg.append(" jsp:getProperty for bean with name '");
                msg.append(name);
                msg.append(
                        "'. Name was not previously introduced as per JSP.5.3");
                
                throw new JasperException(msg.toString());
            }

            n.setEndJavaLine(out.getJavaLine());
        }

        @Override
        public void visit(Node.SetProperty n) throws JasperException {
            String name = n.getTextAttribute("name");
            String property = n.getTextAttribute("property");
            String param = n.getTextAttribute("param");
            Node.JspAttribute value = n.getValue();

            n.setBeginJavaLine(out.getJavaLine());

            if ("*".equals(property)) {
                out.printil("org.apache.jasper.runtime.JspRuntimeLibrary.introspect("
                        + "_jspx_page_context.findAttribute("
                        + "\""
                        + name + "\"), request);");
            } else if (value == null) {
                if (param == null)
                    param = property; // default to same as property
                out.printil("org.apache.jasper.runtime.JspRuntimeLibrary.introspecthelper("
                        + "_jspx_page_context.findAttribute(\""
                        + name
                        + "\"), \""
                        + property
                        + "\", request.getParameter(\""
                        + param
                        + "\"), "
                        + "request, \""
                        + param
                        + "\", false);");
            } else if (value.isExpression()) {
                out.printil("org.apache.jasper.runtime.JspRuntimeLibrary.handleSetProperty("
                        + "_jspx_page_context.findAttribute(\""
                        + name
                        + "\"), \"" + property + "\",");
                out.print(attributeValue(value, false, null,
                        n.getRoot().isXmlSyntax()));
                out.println(");");
            } else if (value.isELInterpreterInput()) {
                // We've got to resolve the very call to the interpreter
                // at runtime since we don't know what type to expect
                // in the general case; we thus can't hard-wire the call
                // into the generated code. (XXX We could, however,
                // optimize the case where the bean is exposed with
                // <jsp:useBean>, much as the code here does for
                // getProperty.)

                // The following holds true for the arguments passed to
                // JspRuntimeLibrary.handleSetPropertyExpression():
                // - 'pageContext' is a VariableResolver.
                // - 'this' (either the generated Servlet or the generated tag
                // handler for Tag files) is a FunctionMapper.
                out.printil("org.apache.jasper.runtime.JspRuntimeLibrary.handleSetPropertyExpression("
                        + "_jspx_page_context.findAttribute(\""
                        + name
                        + "\"), \""
                        + property
                        + "\", "
                        + quote(value.getValue())
                        + ", "
                        + "_jspx_page_context, "
                        + value.getEL().getMapName() + ");");
            } else if (value.isNamedAttribute()) {
                // If the value for setProperty was specified via
                // jsp:attribute, first generate code to evaluate
                // that body.
                String valueVarName = generateNamedAttributeValue(value
                        .getNamedAttributeNode());
                out.printil("org.apache.jasper.runtime.JspRuntimeLibrary.introspecthelper("
                        + "_jspx_page_context.findAttribute(\""
                        + name
                        + "\"), \""
                        + property
                        + "\", "
                        + valueVarName
                        + ", null, null, false);");
            } else {
                out.printin("org.apache.jasper.runtime.JspRuntimeLibrary.introspecthelper("
                        + "_jspx_page_context.findAttribute(\""
                        + name
                        + "\"), \"" + property + "\", ");
                out.print(attributeValue(value, false, null,
                        n.getRoot().isXmlSyntax()));
                out.println(", null, null, false);");
            }

            n.setEndJavaLine(out.getJavaLine());
        }

        @Override
        public void visit(Node.UseBean n) throws JasperException {

            String name = n.getTextAttribute("id");
            String scope = n.getTextAttribute("scope");
            String klass = n.getTextAttribute("class");
            String type = n.getTextAttribute("type");
            Node.JspAttribute beanName = n.getBeanName();

            // If "class" is specified, try an instantiation at compile time
            boolean generateNew = false;
            String canonicalName = null; // Canonical name for klass
            if (klass != null) {
                try {
                    Class<?> bean = ctxt.getClassLoader().loadClass(klass);
                    if (klass.indexOf('$') >= 0) {
                        // Obtain the canonical type name
                        canonicalName = bean.getCanonicalName();
                    } else {
                        canonicalName = klass;
                    }
                    int modifiers = bean.getModifiers();
                    if (!Modifier.isPublic(modifiers)
                            || Modifier.isInterface(modifiers)
                            || Modifier.isAbstract(modifiers)) {
                        throw new Exception("Invalid bean class modifier");
                    }
                    // Check that there is a 0 arg constructor
                    bean.getConstructor(new Class[] {});
                    // At compile time, we have determined that the bean class
                    // exists, with a public zero constructor, new() can be
                    // used for bean instantiation.
                    generateNew = true;
                } catch (Exception e) {
                    // Cannot instantiate the specified class, either a
                    // compilation error or a runtime error will be raised,
                    // depending on a compiler flag.
                    if (ctxt.getOptions()
                            .getErrorOnUseBeanInvalidClassAttribute()) {
                        err.jspError(n, "jsp.error.invalid.bean", klass);
                    }
                    if (canonicalName == null) {
                        // Doing our best here to get a canonical name
                        // from the binary name, should work 99.99% of time.
                        canonicalName = klass.replace('$', '.');
                    }
                }
                if (type == null) {
                    // if type is unspecified, use "class" as type of bean
                    type = canonicalName;
                }
            }

            // JSP.5.1, Semantics, para 1 - lock not required for request or
            // page scope
            String scopename = "javax.servlet.jsp.PageContext.PAGE_SCOPE"; // Default to page
            String lock = null;

            if ("request".equals(scope)) {
                scopename = "javax.servlet.jsp.PageContext.REQUEST_SCOPE";
            } else if ("session".equals(scope)) {
                scopename = "javax.servlet.jsp.PageContext.SESSION_SCOPE";
                lock = "session";
            } else if ("application".equals(scope)) {
                scopename = "javax.servlet.jsp.PageContext.APPLICATION_SCOPE";
                lock = "application";
            }

            n.setBeginJavaLine(out.getJavaLine());

            // Declare bean
            out.printin(type);
            out.print(' ');
            out.print(name);
            out.println(" = null;");

            // Lock (if required) while getting or creating bean
            if (lock != null) {
                out.printin("synchronized (");
                out.print(lock);
                out.println(") {");
                out.pushIndent();
            }

            // Locate bean from context
            out.printin(name);
            out.print(" = (");
            out.print(type);
            out.print(") _jspx_page_context.getAttribute(");
            out.print(quote(name));
            out.print(", ");
            out.print(scopename);
            out.println(");");

            // Create bean
            /*
             * Check if bean is already there
             */
            out.printin("if (");
            out.print(name);
            out.println(" == null){");
            out.pushIndent();
            if (klass == null && beanName == null) {
                /*
                 * If both class name and beanName is not specified, the bean
                 * must be found locally, otherwise it's an error
                 */
                out.printin("throw new java.lang.InstantiationException(\"bean ");
                out.print(name);
                out.println(" not found within scope\");");
            } else {
                /*
                 * Instantiate the bean if it is not in the specified scope.
                 */
                if (!generateNew) {
                    String binaryName;
                    if (beanName != null) {
                        if (beanName.isNamedAttribute()) {
                            // If the value for beanName was specified via
                            // jsp:attribute, first generate code to evaluate
                            // that body.
                            binaryName = generateNamedAttributeValue(beanName
                                    .getNamedAttributeNode());
                        } else {
                            binaryName = attributeValue(beanName, false,
                                    String.class, n.getRoot().isXmlSyntax());
                        }
                    } else {
                        // Implies klass is not null
                        binaryName = quote(klass);
                    }
                    out.printil("try {");
                    out.pushIndent();
                    out.printin(name);
                    out.print(" = (");
                    out.print(type);
                    out.print(") java.beans.Beans.instantiate(");
                    out.print("this.getClass().getClassLoader(), ");
                    out.print(binaryName);
                    out.println(");");
                    out.popIndent();
                    /*
                     * Note: Beans.instantiate throws ClassNotFoundException if
                     * the bean class is abstract.
                     */
                    out.printil("} catch (java.lang.ClassNotFoundException exc) {");
                    out.pushIndent();
                    out.printil("throw new InstantiationException(exc.getMessage());");
                    out.popIndent();
                    out.printil("} catch (java.lang.Exception exc) {");
                    out.pushIndent();
                    out.printin("throw new javax.servlet.ServletException(");
                    out.print("\"Cannot create bean of class \" + ");
                    out.print(binaryName);
                    out.println(", exc);");
                    out.popIndent();
                    out.printil("}"); // close of try
                } else {
                    // Implies klass is not null
                    // Generate codes to instantiate the bean class
                    out.printin(name);
                    out.print(" = new ");
                    out.print(canonicalName);
                    out.println("();");
                }
                /*
                 * Set attribute for bean in the specified scope
                 */
                out.printin("_jspx_page_context.setAttribute(");
                out.print(quote(name));
                out.print(", ");
                out.print(name);
                out.print(", ");
                out.print(scopename);
                out.println(");");

                // Only visit the body when bean is instantiated
                visitBody(n);
            }
            out.popIndent();
            out.printil("}");

            // End of lock block
            if (lock != null) {
                out.popIndent();
                out.printil("}");
            }

            n.setEndJavaLine(out.getJavaLine());
        }

        /**
         * @return a string for the form 'attr = "value"'
         */
        private String makeAttr(String attr, String value) {
            if (value == null)
                return "";

            return " " + attr + "=\"" + value + '\"';
        }

        @Override
        public void visit(Node.PlugIn n) throws JasperException {

            /**
             * A visitor to handle &lt;jsp:param&gt; in a plugin
             */
            class ParamVisitor extends Node.Visitor {

                private final boolean ie;

                ParamVisitor(boolean ie) {
                    this.ie = ie;
                }

                @Override
                public void visit(Node.ParamAction n) throws JasperException {

                    String name = n.getTextAttribute("name");
                    if (name.equalsIgnoreCase("object"))
                        name = "java_object";
                    else if (name.equalsIgnoreCase("type"))
                        name = "java_type";

                    n.setBeginJavaLine(out.getJavaLine());
                    // XXX - Fixed a bug here - value used to be output
                    // inline, which is only okay if value is not an EL
                    // expression. Also, key/value pairs for the
                    // embed tag were not being generated correctly.
                    // Double check that this is now the correct behavior.
                    if (ie) {
                        // We want something of the form
                        // out.println( "<param name=\"blah\"
                        // value=\"" + ... + "\">" );
                        out.printil("out.write( \"<param name=\\\"" +
                                escape(name) +
                                "\\\" value=\\\"\" + " +
                                attributeValue(n.getValue(), false,
                                        String.class,
                                        n.getRoot().isXmlSyntax()) +
                                " + \"\\\">\" );");
                        out.printil("out.write(\"\\n\");");
                    } else {
                        // We want something of the form
                        // out.print( " blah=\"" + ... + "\"" );
                        out.printil("out.write( \" " +
                                escape(name) +
                                "=\\\"\" + " +
                                attributeValue(n.getValue(), false,
                                        String.class,
                                        n.getRoot().isXmlSyntax()) +
                                " + \"\\\"\" );");
                    }

                    n.setEndJavaLine(out.getJavaLine());
                }
            }

            String type = n.getTextAttribute("type");
            String code = n.getTextAttribute("code");
            String name = n.getTextAttribute("name");
            Node.JspAttribute height = n.getHeight();
            Node.JspAttribute width = n.getWidth();
            String hspace = n.getTextAttribute("hspace");
            String vspace = n.getTextAttribute("vspace");
            String align = n.getTextAttribute("align");
            String iepluginurl = n.getTextAttribute("iepluginurl");
            String nspluginurl = n.getTextAttribute("nspluginurl");
            String codebase = n.getTextAttribute("codebase");
            String archive = n.getTextAttribute("archive");
            String jreversion = n.getTextAttribute("jreversion");

            String widthStr = null;
            if (width != null) {
                if (width.isNamedAttribute()) {
                    widthStr = generateNamedAttributeValue(width
                            .getNamedAttributeNode());
                } else {
                    widthStr = attributeValue(width, false, String.class,
                            n.getRoot().isXmlSyntax());
                }
            }

            String heightStr = null;
            if (height != null) {
                if (height.isNamedAttribute()) {
                    heightStr = generateNamedAttributeValue(height
                            .getNamedAttributeNode());
                } else {
                    heightStr = attributeValue(height, false, String.class,
                            n.getRoot().isXmlSyntax());
                }
            }

            if (iepluginurl == null)
                iepluginurl = Constants.IE_PLUGIN_URL;
            if (nspluginurl == null)
                nspluginurl = Constants.NS_PLUGIN_URL;

            n.setBeginJavaLine(out.getJavaLine());

            // If any of the params have their values specified by
            // jsp:attribute, prepare those values first.
            // Look for a params node and prepare its param subelements:
            Node.JspBody jspBody = findJspBody(n);
            if (jspBody != null) {
                Node.Nodes subelements = jspBody.getBody();
                if (subelements != null) {
                    for (int i = 0; i < subelements.size(); i++) {
                        Node m = subelements.getNode(i);
                        if (m instanceof Node.ParamsAction) {
                            prepareParams(m);
                            break;
                        }
                    }
                }
            }

            // XXX - Fixed a bug here - width and height can be set
            // dynamically. Double-check if this generation is correct.

            // IE style plugin
            // <object ...>
            // First compose the runtime output string
            String s0 = "<object"
                    + makeAttr("classid", ctxt.getOptions().getIeClassId())
                    + makeAttr("name", name);

            String s1 = "";
            if (width != null) {
                s1 = " + \" width=\\\"\" + " + widthStr + " + \"\\\"\"";
            }

            String s2 = "";
            if (height != null) {
                s2 = " + \" height=\\\"\" + " + heightStr + " + \"\\\"\"";
            }

            String s3 = makeAttr("hspace", hspace) + makeAttr("vspace", vspace)
                    + makeAttr("align", align)
                    + makeAttr("codebase", iepluginurl) + '>';

            // Then print the output string to the java file
            out.printil("out.write(" + quote(s0) + s1 + s2 + " + " + quote(s3)
                    + ");");
            out.printil("out.write(\"\\n\");");

            // <param > for java_code
            s0 = "<param name=\"java_code\"" + makeAttr("value", code) + '>';
            out.printil("out.write(" + quote(s0) + ");");
            out.printil("out.write(\"\\n\");");

            // <param > for java_codebase
            if (codebase != null) {
                s0 = "<param name=\"java_codebase\""
                        + makeAttr("value", codebase) + '>';
                out.printil("out.write(" + quote(s0) + ");");
                out.printil("out.write(\"\\n\");");
            }

            // <param > for java_archive
            if (archive != null) {
                s0 = "<param name=\"java_archive\""
                        + makeAttr("value", archive) + '>';
                out.printil("out.write(" + quote(s0) + ");");
                out.printil("out.write(\"\\n\");");
            }

            // <param > for type
            s0 = "<param name=\"type\""
                    + makeAttr("value", "application/x-java-"
                            + type
                            + ((jreversion == null) ? "" : ";version="
                                    + jreversion)) + '>';
            out.printil("out.write(" + quote(s0) + ");");
            out.printil("out.write(\"\\n\");");

            /*
             * generate a <param> for each <jsp:param> in the plugin body
             */
            if (n.getBody() != null)
                n.getBody().visit(new ParamVisitor(true));

            /*
             * Netscape style plugin part
             */
            out.printil("out.write(" + quote("<comment>") + ");");
            out.printil("out.write(\"\\n\");");
            s0 = "<EMBED"
                    + makeAttr("type", "application/x-java-"
                            + type
                            + ((jreversion == null) ? "" : ";version="
                                    + jreversion)) + makeAttr("name", name);

            // s1 and s2 are the same as before.

            s3 = makeAttr("hspace", hspace) + makeAttr("vspace", vspace)
                    + makeAttr("align", align)
                    + makeAttr("pluginspage", nspluginurl)
                    + makeAttr("java_code", code)
                    + makeAttr("java_codebase", codebase)
                    + makeAttr("java_archive", archive);
            out.printil("out.write(" + quote(s0) + s1 + s2 + " + " + quote(s3)
                    + ");");

            /*
             * Generate a 'attr = "value"' for each <jsp:param> in plugin body
             */
            if (n.getBody() != null)
                n.getBody().visit(new ParamVisitor(false));

            out.printil("out.write(" + quote("/>") + ");");
            out.printil("out.write(\"\\n\");");

            out.printil("out.write(" + quote("<noembed>") + ");");
            out.printil("out.write(\"\\n\");");

            /*
             * Fallback
             */
            if (n.getBody() != null) {
                visitBody(n);
                out.printil("out.write(\"\\n\");");
            }

            out.printil("out.write(" + quote("</noembed>") + ");");
            out.printil("out.write(\"\\n\");");

            out.printil("out.write(" + quote("</comment>") + ");");
            out.printil("out.write(\"\\n\");");

            out.printil("out.write(" + quote("</object>") + ");");
            out.printil("out.write(\"\\n\");");

            n.setEndJavaLine(out.getJavaLine());
        }

        @Override
        public void visit(Node.NamedAttribute n) throws JasperException {
            // Don't visit body of this tag - we already did earlier.
        }

        @Override
        public void visit(Node.CustomTag n) throws JasperException {

            // Use plugin to generate more efficient code if there is one.
            if (n.useTagPlugin()) {
                generateTagPlugin(n);
                return;
            }

            TagHandlerInfo handlerInfo = getTagHandlerInfo(n);

            // Create variable names
            String baseVar = createTagVarName(n.getQName(), n.getPrefix(), n
                    .getLocalName());
            String tagEvalVar = "_jspx_eval_" + baseVar;
            String tagHandlerVar = "_jspx_th_" + baseVar;
            String tagPushBodyCountVar = "_jspx_push_body_count_" + baseVar;

            // If the tag contains no scripting element, generate its codes
            // to a method.
            ServletWriter outSave = null;
            Node.ChildInfo ci = n.getChildInfo();
            if (ci.isScriptless() && !ci.hasScriptingVars()) {
                // The tag handler and its body code can reside in a separate
                // method if it is scriptless and does not have any scripting
                // variable defined.

                String tagMethod = "_jspx_meth_" + baseVar;

                // Generate a call to this method
                out.printin("if (");
                out.print(tagMethod);
                out.print("(");
                if (parent != null) {
                    out.print(parent);
                    out.print(", ");
                }
                out.print("_jspx_page_context");
                if (pushBodyCountVar != null) {
                    out.print(", ");
                    out.print(pushBodyCountVar);
                }
                out.println("))");
                out.pushIndent();
                out.printil((methodNesting > 0) ? "return true;" : "return;");
                out.popIndent();

                // Set up new buffer for the method
                outSave = out;
                /*
                 * For fragments, their bodies will be generated in fragment
                 * helper classes, and the Java line adjustments will be done
                 * there, hence they are set to null here to avoid double
                 * adjustments.
                 */
                GenBuffer genBuffer = new GenBuffer(n,
                        n.implementsSimpleTag() ? null : n.getBody());
                methodsBuffered.add(genBuffer);
                out = genBuffer.getOut();

                methodNesting++;
                // Generate code for method declaration
                out.println();
                out.pushIndent();
                out.printin("private boolean ");
                out.print(tagMethod);
                out.print("(");
                if (parent != null) {
                    out.print("javax.servlet.jsp.tagext.JspTag ");
                    out.print(parent);
                    out.print(", ");
                }
                out.print("javax.servlet.jsp.PageContext _jspx_page_context");
                if (pushBodyCountVar != null) {
                    out.print(", int[] ");
                    out.print(pushBodyCountVar);
                }
                out.println(")");
                out.printil("        throws java.lang.Throwable {");
                out.pushIndent();

                // Initialize local variables used in this method.
                if (!isTagFile) {
                    out.printil("javax.servlet.jsp.PageContext pageContext = _jspx_page_context;");
                }
                out.printil("javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();");
                generateLocalVariables(out, n);
            }

            // Add the named objects to the list of 'introduced' names to enable
            // a later test as per JSP.5.3
            VariableInfo[] infos = n.getVariableInfos();
            if (infos != null && infos.length > 0) {
                for (int i = 0; i < infos.length; i++) {
                    VariableInfo info = infos[i];
                    if (info != null && info.getVarName() != null)
                        pageInfo.getVarInfoNames().add(info.getVarName());
                }
            }
            TagVariableInfo[] tagInfos = n.getTagVariableInfos();
            if (tagInfos != null && tagInfos.length > 0) {
                for (int i = 0; i < tagInfos.length; i++) {
                    TagVariableInfo tagInfo = tagInfos[i];
                    if (tagInfo != null) {
                        String name = tagInfo.getNameGiven();
                        if (name == null) {
                            String nameFromAttribute =
                                tagInfo.getNameFromAttribute();
                            name = n.getAttributeValue(nameFromAttribute);
                        }
                        pageInfo.getVarInfoNames().add(name);
                    }
                }
            }
            
            
            if (n.implementsSimpleTag()) {
                generateCustomDoTag(n, handlerInfo, tagHandlerVar);
            } else {
                /*
                 * Classic tag handler: Generate code for start element, body,
                 * and end element
                 */
                generateCustomStart(n, handlerInfo, tagHandlerVar, tagEvalVar,
                        tagPushBodyCountVar);

                // visit body
                String tmpParent = parent;
                parent = tagHandlerVar;
                boolean isSimpleTagParentSave = isSimpleTagParent;
                isSimpleTagParent = false;
                String tmpPushBodyCountVar = null;
                if (n.implementsTryCatchFinally()) {
                    tmpPushBodyCountVar = pushBodyCountVar;
                    pushBodyCountVar = tagPushBodyCountVar;
                }
                boolean tmpIsSimpleTagHandler = isSimpleTagHandler;
                isSimpleTagHandler = false;

                visitBody(n);

                parent = tmpParent;
                isSimpleTagParent = isSimpleTagParentSave;
                if (n.implementsTryCatchFinally()) {
                    pushBodyCountVar = tmpPushBodyCountVar;
                }
                isSimpleTagHandler = tmpIsSimpleTagHandler;

                generateCustomEnd(n, tagHandlerVar, tagEvalVar,
                        tagPushBodyCountVar);
            }

            if (ci.isScriptless() && !ci.hasScriptingVars()) {
                // Generate end of method
                if (methodNesting > 0) {
                    out.printil("return false;");
                }
                out.popIndent();
                out.printil("}");
                out.popIndent();

                methodNesting--;

                // restore previous writer
                out = outSave;
            }
            
        }

        private static final String DOUBLE_QUOTE = "\\\"";

        @Override
        public void visit(Node.UninterpretedTag n) throws JasperException {

            n.setBeginJavaLine(out.getJavaLine());

            /*
             * Write begin tag
             */
            out.printin("out.write(\"<");
            out.print(n.getQName());

            Attributes attrs = n.getNonTaglibXmlnsAttributes();
            if (attrs != null) {
                for (int i = 0; i < attrs.getLength(); i++) {
                    out.print(" ");
                    out.print(attrs.getQName(i));
                    out.print("=");
                    out.print(DOUBLE_QUOTE);
                    out.print(escape(attrs.getValue(i).replace("\"", "&quot;")));
                    out.print(DOUBLE_QUOTE);
                }
            }

            attrs = n.getAttributes();
            if (attrs != null) {
                Node.JspAttribute[] jspAttrs = n.getJspAttributes();
                for (int i = 0; i < attrs.getLength(); i++) {
                    out.print(" ");
                    out.print(attrs.getQName(i));
                    out.print("=");
                    if (jspAttrs[i].isELInterpreterInput()) {
                        out.print("\\\"\" + ");
                        String debug = attributeValue(jspAttrs[i], false,
                                String.class, n.getRoot().isXmlSyntax());
                        out.print(debug);
                        out.print(" + \"\\\"");
                    } else {
                        out.print(DOUBLE_QUOTE);
                        out.print(escape(jspAttrs[i].getValue().replace("\"", "&quot;")));
                        out.print(DOUBLE_QUOTE);
                    }
                }
            }

            if (n.getBody() != null) {
                out.println(">\");");

                // Visit tag body
                visitBody(n);

                /*
                 * Write end tag
                 */
                out.printin("out.write(\"</");
                out.print(n.getQName());
                out.println(">\");");
            } else {
                out.println("/>\");");
            }

            n.setEndJavaLine(out.getJavaLine());
        }

        @Override
        public void visit(Node.JspElement n) throws JasperException {

            n.setBeginJavaLine(out.getJavaLine());

            // Compute attribute value string for XML-style and named
            // attributes
            Hashtable<String,String> map = new Hashtable<String,String>();
            Node.JspAttribute[] attrs = n.getJspAttributes();
            for (int i = 0; attrs != null && i < attrs.length; i++) {
                String value = null;
                String nvp = null;
                if (attrs[i].isNamedAttribute()) {
                    NamedAttribute attr = attrs[i].getNamedAttributeNode();
                    Node.JspAttribute omitAttr = attr.getOmit();
                    String omit;
                    if (omitAttr == null) {
                        omit = "false";
                    } else {
                        omit = attributeValue(omitAttr, false, boolean.class,
                                n.getRoot().isXmlSyntax());
                        if ("true".equals(omit)) {
                            continue;
                        }
                    }
                    value = generateNamedAttributeValue(
                            attrs[i].getNamedAttributeNode());
                    if ("false".equals(omit)) {
                        nvp = " + \" " + attrs[i].getName() + "=\\\"\" + " +
                                value + " + \"\\\"\"";
                    } else {
                        nvp = " + (java.lang.Boolean.valueOf(" + omit + ")?\"\":\" " +
                                attrs[i].getName() + "=\\\"\" + " + value +
                                " + \"\\\"\")";
                    }
                } else {
                    value = attributeValue(attrs[i], false, Object.class,
                            n.getRoot().isXmlSyntax());
                    nvp = " + \" " + attrs[i].getName() + "=\\\"\" + " +
                            value + " + \"\\\"\"";
                }
                map.put(attrs[i].getName(), nvp);
            }

            // Write begin tag, using XML-style 'name' attribute as the
            // element name
            String elemName = attributeValue(n.getNameAttribute(), false,
                    String.class, n.getRoot().isXmlSyntax());
            out.printin("out.write(\"<\"");
            out.print(" + " + elemName);

            // Write remaining attributes
            Enumeration<String> enumeration = map.keys();
            while (enumeration.hasMoreElements()) {
                String attrName = enumeration.nextElement();
                out.print(map.get(attrName));
            }

            // Does the <jsp:element> have nested tags other than
            // <jsp:attribute>
            boolean hasBody = false;
            Node.Nodes subelements = n.getBody();
            if (subelements != null) {
                for (int i = 0; i < subelements.size(); i++) {
                    Node subelem = subelements.getNode(i);
                    if (!(subelem instanceof Node.NamedAttribute)) {
                        hasBody = true;
                        break;
                    }
                }
            }
            if (hasBody) {
                out.println(" + \">\");");

                // Smap should not include the body
                n.setEndJavaLine(out.getJavaLine());

                // Visit tag body
                visitBody(n);

                // Write end tag
                out.printin("out.write(\"</\"");
                out.print(" + " + elemName);
                out.println(" + \">\");");
            } else {
                out.println(" + \"/>\");");
                n.setEndJavaLine(out.getJavaLine());
            }
        }

        @Override
        public void visit(Node.TemplateText n) throws JasperException {

            String text = n.getText();

            int textSize = text.length();
            if (textSize == 0) {
                return;
            }

            if (textSize <= 3) {
                // Special case small text strings
                n.setBeginJavaLine(out.getJavaLine());
                int lineInc = 0;
                for (int i = 0; i < textSize; i++) {
                    char ch = text.charAt(i);
                    out.printil("out.write(" + quote(ch) + ");");
                    if (i > 0) {
                        n.addSmap(lineInc);
                    }
                    if (ch == '\n') {
                        lineInc++;
                    }
                }
                n.setEndJavaLine(out.getJavaLine());
                return;
            }

            if (ctxt.getOptions().genStringAsCharArray()) {
                // Generate Strings as char arrays, for performance
                ServletWriter caOut;
                if (charArrayBuffer == null) {
                    charArrayBuffer = new GenBuffer();
                    caOut = charArrayBuffer.getOut();
                    caOut.pushIndent();
                    textMap = new HashMap<String,String>();
                } else {
                    caOut = charArrayBuffer.getOut();
                }
                // UTF-8 is up to 4 bytes per character
                // String constants are limited to 64k bytes
                // Limit string constants here to 16k characters
                int textIndex = 0;
                int textLength = text.length();
                while (textIndex < textLength) {
                    int len = 0;
                    if (textLength - textIndex > 16384) {
                        len = 16384;
                    } else {
                        len = textLength - textIndex;
                    }
                    String output = text.substring(textIndex, textIndex + len);
                    String charArrayName = textMap.get(output);
                    if (charArrayName == null) {
                        charArrayName = "_jspx_char_array_" + charArrayCount++;
                        textMap.put(output, charArrayName);
                        caOut.printin("static char[] ");
                        caOut.print(charArrayName);
                        caOut.print(" = ");
                        caOut.print(quote(output));
                        caOut.println(".toCharArray();");
                    }
    
                    n.setBeginJavaLine(out.getJavaLine());
                    out.printil("out.write(" + charArrayName + ");");
                    n.setEndJavaLine(out.getJavaLine());
                    
                    textIndex = textIndex + len;
                }
                return;
            }

            n.setBeginJavaLine(out.getJavaLine());

            out.printin();
            StringBuilder sb = new StringBuilder("out.write(\"");
            int initLength = sb.length();
            int count = JspUtil.CHUNKSIZE;
            int srcLine = 0; // relative to starting source line
            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                --count;
                switch (ch) {
                case '"':
                    sb.append('\\').append('\"');
                    break;
                case '\\':
                    sb.append('\\').append('\\');
                    break;
                case '\r':
                    sb.append('\\').append('r');
                    break;
                case '\n':
                    sb.append('\\').append('n');
                    srcLine++;

                    if (breakAtLF || count < 0) {
                        // Generate an out.write() when see a '\n' in template
                        sb.append("\");");
                        out.println(sb.toString());
                        if (i < text.length() - 1) {
                            out.printin();
                        }
                        sb.setLength(initLength);
                        count = JspUtil.CHUNKSIZE;
                    }
                    // add a Smap for this line
                    n.addSmap(srcLine);
                    break;
                case '\t': // Not sure we need this
                    sb.append('\\').append('t');
                    break;
                default:
                    sb.append(ch);
                }
            }

            if (sb.length() > initLength) {
                sb.append("\");");
                out.println(sb.toString());
            }

            n.setEndJavaLine(out.getJavaLine());
        }

        @Override
        public void visit(Node.JspBody n) throws JasperException {
            if (n.getBody() != null) {
                if (isSimpleTagHandler) {
                    out.printin(simpleTagHandlerVar);
                    out.print(".setJspBody(");
                    generateJspFragment(n, simpleTagHandlerVar);
                    out.println(");");
                } else {
                    visitBody(n);
                }
            }
        }

        @Override
        public void visit(Node.InvokeAction n) throws JasperException {

            n.setBeginJavaLine(out.getJavaLine());

            // Copy virtual page scope of tag file to page scope of invoking
            // page
            out.printil("((org.apache.jasper.runtime.JspContextWrapper) this.jspContext).syncBeforeInvoke();");
            String varReaderAttr = n.getTextAttribute("varReader");
            String varAttr = n.getTextAttribute("var");
            if (varReaderAttr != null || varAttr != null) {
                out.printil("_jspx_sout = new java.io.StringWriter();");
            } else {
                out.printil("_jspx_sout = null;");
            }

            // Invoke fragment, unless fragment is null
            out.printin("if (");
            out.print(toGetterMethod(n.getTextAttribute("fragment")));
            out.println(" != null) {");
            out.pushIndent();
            out.printin(toGetterMethod(n.getTextAttribute("fragment")));
            out.println(".invoke(_jspx_sout);");
            out.popIndent();
            out.printil("}");

            // Store varReader in appropriate scope
            if (varReaderAttr != null || varAttr != null) {
                String scopeName = n.getTextAttribute("scope");
                out.printin("_jspx_page_context.setAttribute(");
                if (varReaderAttr != null) {
                    out.print(quote(varReaderAttr));
                    out.print(", new java.io.StringReader(_jspx_sout.toString())");
                } else {
                    out.print(quote(varAttr));
                    out.print(", _jspx_sout.toString()");
                }
                if (scopeName != null) {
                    out.print(", ");
                    out.print(getScopeConstant(scopeName));
                }
                out.println(");");
            }

            n.setEndJavaLine(out.getJavaLine());
        }

        @Override
        public void visit(Node.DoBodyAction n) throws JasperException {

            n.setBeginJavaLine(out.getJavaLine());

            // Copy virtual page scope of tag file to page scope of invoking
            // page
            out.printil("((org.apache.jasper.runtime.JspContextWrapper) this.jspContext).syncBeforeInvoke();");

            // Invoke body
            String varReaderAttr = n.getTextAttribute("varReader");
            String varAttr = n.getTextAttribute("var");
            if (varReaderAttr != null || varAttr != null) {
                out.printil("_jspx_sout = new java.io.StringWriter();");
            } else {
                out.printil("_jspx_sout = null;");
            }
            out.printil("if (getJspBody() != null)");
            out.pushIndent();
            out.printil("getJspBody().invoke(_jspx_sout);");
            out.popIndent();

            // Store varReader in appropriate scope
            if (varReaderAttr != null || varAttr != null) {
                String scopeName = n.getTextAttribute("scope");
                out.printin("_jspx_page_context.setAttribute(");
                if (varReaderAttr != null) {
                    out.print(quote(varReaderAttr));
                    out.print(", new java.io.StringReader(_jspx_sout.toString())");
                } else {
                    out.print(quote(varAttr));
                    out.print(", _jspx_sout.toString()");
                }
                if (scopeName != null) {
                    out.print(", ");
                    out.print(getScopeConstant(scopeName));
                }
                out.println(");");
            }

            // Restore EL context
            out.printil("jspContext.getELContext().putContext(javax.servlet.jsp.JspContext.class,getJspContext());");

            n.setEndJavaLine(out.getJavaLine());
        }

        @Override
        public void visit(Node.AttributeGenerator n) throws JasperException {
            Node.CustomTag tag = n.getTag();
            Node.JspAttribute[] attrs = tag.getJspAttributes();
            for (int i = 0; attrs != null && i < attrs.length; i++) {
                if (attrs[i].getName().equals(n.getName())) {
                    out.print(evaluateAttribute(getTagHandlerInfo(tag),
                            attrs[i], tag, null));
                    break;
                }
            }
        }

        private TagHandlerInfo getTagHandlerInfo(Node.CustomTag n)
                throws JasperException {
            Hashtable<String,TagHandlerInfo> handlerInfosByShortName =
                handlerInfos.get(n.getPrefix());
            if (handlerInfosByShortName == null) {
                handlerInfosByShortName =
                    new Hashtable<String,TagHandlerInfo>();
                handlerInfos.put(n.getPrefix(), handlerInfosByShortName);
            }
            TagHandlerInfo handlerInfo =
                handlerInfosByShortName.get(n.getLocalName());
            if (handlerInfo == null) {
                handlerInfo = new TagHandlerInfo(n, n.getTagHandlerClass(), err);
                handlerInfosByShortName.put(n.getLocalName(), handlerInfo);
            }
            return handlerInfo;
        }

        private void generateTagPlugin(Node.CustomTag n) throws JasperException {
            if (n.getAtSTag() != null) {
                n.getAtSTag().visit(this);
            }
            visitBody(n);
            if (n.getAtETag() != null) {
                n.getAtETag().visit(this);
            }
        }

        private void generateCustomStart(Node.CustomTag n,
                TagHandlerInfo handlerInfo, String tagHandlerVar,
                String tagEvalVar, String tagPushBodyCountVar)
                throws JasperException {

            Class<?> tagHandlerClass =
                handlerInfo.getTagHandlerClass();

            out.printin("//  ");
            out.println(n.getQName());
            n.setBeginJavaLine(out.getJavaLine());

            // Declare AT_BEGIN scripting variables
            declareScriptingVars(n, VariableInfo.AT_BEGIN);
            saveScriptingVars(n, VariableInfo.AT_BEGIN);

            String tagHandlerClassName = tagHandlerClass.getCanonicalName();
            if (isPoolingEnabled && !(n.implementsJspIdConsumer())) {
                out.printin(tagHandlerClassName);
                out.print(" ");
                out.print(tagHandlerVar);
                out.print(" = ");
                out.print("(");
                out.print(tagHandlerClassName);
                out.print(") ");
                out.print(n.getTagHandlerPoolName());
                out.print(".get(");
                out.print(tagHandlerClassName);
                out.println(".class);");
                out.printin("boolean ");
                out.print(tagHandlerVar);
                out.println("_reused = false;");
            } else {
                writeNewInstance(tagHandlerVar, tagHandlerClassName);
            }

            // Wrap use of tag in try/finally to ensure clean-up takes place
            out.printil("try {");
            out.pushIndent();

            // includes setting the context
            generateSetters(n, tagHandlerVar, handlerInfo, false);

            if (n.implementsTryCatchFinally()) {
                out.printin("int[] ");
                out.print(tagPushBodyCountVar);
                out.println(" = new int[] { 0 };");
                out.printil("try {");
                out.pushIndent();
            }
            out.printin("int ");
            out.print(tagEvalVar);
            out.print(" = ");
            out.print(tagHandlerVar);
            out.println(".doStartTag();");

            if (!n.implementsBodyTag()) {
                // Synchronize AT_BEGIN scripting variables
                syncScriptingVars(n, VariableInfo.AT_BEGIN);
            }

            if (!n.hasEmptyBody()) {
                out.printin("if (");
                out.print(tagEvalVar);
                out.println(" != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {");
                out.pushIndent();

                // Declare NESTED scripting variables
                declareScriptingVars(n, VariableInfo.NESTED);
                saveScriptingVars(n, VariableInfo.NESTED);

                if (n.implementsBodyTag()) {
                    out.printin("if (");
                    out.print(tagEvalVar);
                    out.println(" != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {");
                    // Assume EVAL_BODY_BUFFERED
                    out.pushIndent();
                    if (n.implementsTryCatchFinally()) {
                        out.printin(tagPushBodyCountVar);
                        out.println("[0]++;");
                    } else if (pushBodyCountVar != null) {
                        out.printin(pushBodyCountVar);
                        out.println("[0]++;");
                    }
                    out.printin("out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(");
                    out.print("_jspx_page_context, ");
                    out.print(tagHandlerVar);
                    out.println(");");
                    out.popIndent();
                    out.printil("}");

                    // Synchronize AT_BEGIN and NESTED scripting variables
                    syncScriptingVars(n, VariableInfo.AT_BEGIN);
                    syncScriptingVars(n, VariableInfo.NESTED);

                } else {
                    // Synchronize NESTED scripting variables
                    syncScriptingVars(n, VariableInfo.NESTED);
                }

                if (n.implementsIterationTag()) {
                    out.printil("do {");
                    out.pushIndent();
                }
            }
            // Map the Java lines that handles start of custom tags to the
            // JSP line for this tag
            n.setEndJavaLine(out.getJavaLine());
        }

        private void writeNewInstance(String tagHandlerVar, String tagHandlerClassName) {
            out.printin(tagHandlerClassName);
            out.print(" ");
            out.print(tagHandlerVar);
            out.print(" = ");
            if (Constants.USE_INSTANCE_MANAGER_FOR_TAGS) {
                out.print("(");
                out.print(tagHandlerClassName);
                out.print(")");
                out.print("_jsp_getInstanceManager().newInstance(\"");
                out.print(tagHandlerClassName);
                out.println("\", this.getClass().getClassLoader());");
            } else {
                out.print("new ");
                out.print(tagHandlerClassName);
                out.println("();");
                out.printin("_jsp_getInstanceManager().newInstance(");
                out.print(tagHandlerVar);
                out.println(");");
            }
        }

        private void writeDestroyInstance(String tagHandlerVar) {
            out.printin("_jsp_getInstanceManager().destroyInstance(");
            out.print(tagHandlerVar);
            out.println(");");
        }

        private void generateCustomEnd(Node.CustomTag n, String tagHandlerVar,
                String tagEvalVar, String tagPushBodyCountVar) {

            if (!n.hasEmptyBody()) {
                if (n.implementsIterationTag()) {
                    out.printin("int evalDoAfterBody = ");
                    out.print(tagHandlerVar);
                    out.println(".doAfterBody();");

                    // Synchronize AT_BEGIN and NESTED scripting variables
                    syncScriptingVars(n, VariableInfo.AT_BEGIN);
                    syncScriptingVars(n, VariableInfo.NESTED);

                    out.printil("if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)");
                    out.pushIndent();
                    out.printil("break;");
                    out.popIndent();

                    out.popIndent();
                    out.printil("} while (true);");
                }

                restoreScriptingVars(n, VariableInfo.NESTED);

                if (n.implementsBodyTag()) {
                    out.printin("if (");
                    out.print(tagEvalVar);
                    out.println(" != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {");
                    out.pushIndent();
                    out.printil("out = _jspx_page_context.popBody();");
                    if (n.implementsTryCatchFinally()) {
                        out.printin(tagPushBodyCountVar);
                        out.println("[0]--;");
                    } else if (pushBodyCountVar != null) {
                        out.printin(pushBodyCountVar);
                        out.println("[0]--;");
                    }
                    out.popIndent();
                    out.printil("}");
                }

                out.popIndent(); // EVAL_BODY
                out.printil("}");
            }

            out.printin("if (");
            out.print(tagHandlerVar);
            out.println(".doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {");
            out.pushIndent();
            if (isTagFile || isFragment) {
                out.printil("throw new javax.servlet.jsp.SkipPageException();");
            } else {
                out.printil((methodNesting > 0) ? "return true;" : "return;");
            }
            out.popIndent();
            out.printil("}");
            // Synchronize AT_BEGIN scripting variables
            syncScriptingVars(n, VariableInfo.AT_BEGIN);

            // TryCatchFinally
            if (n.implementsTryCatchFinally()) {
                out.popIndent(); // try
                out.printil("} catch (java.lang.Throwable _jspx_exception) {");
                out.pushIndent();

                out.printin("while (");
                out.print(tagPushBodyCountVar);
                out.println("[0]-- > 0)");
                out.pushIndent();
                out.printil("out = _jspx_page_context.popBody();");
                out.popIndent();

                out.printin(tagHandlerVar);
                out.println(".doCatch(_jspx_exception);");
                out.popIndent();
                out.printil("} finally {");
                out.pushIndent();
                out.printin(tagHandlerVar);
                out.println(".doFinally();");
            }

            if (n.implementsTryCatchFinally()) {
                out.popIndent();
                out.printil("}");
            }

            // Print tag reuse
            if (isPoolingEnabled && !(n.implementsJspIdConsumer())) {
                out.printin(n.getTagHandlerPoolName());
                out.print(".reuse(");
                out.print(tagHandlerVar);
                out.println(");");
                out.printin(tagHandlerVar);
                out.println("_reused = true;");
            }

            // Ensure clean-up takes place
            // Use JspRuntimeLibrary to minimise code in _jspService()
            out.popIndent();
            out.printil("} finally {");
            out.pushIndent();
            out.printin("org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(");
            out.print(tagHandlerVar);
            out.print(", _jsp_getInstanceManager(), ");
            if (isPoolingEnabled && !(n.implementsJspIdConsumer())) {
                out.print(tagHandlerVar);
                out.println("_reused);");
            } else {
                out.println("false);");
            }
            out.popIndent();
            out.printil("}");

            // Declare and synchronize AT_END scripting variables (must do this
            // outside the try/catch/finally block)
            declareScriptingVars(n, VariableInfo.AT_END);
            syncScriptingVars(n, VariableInfo.AT_END);

            restoreScriptingVars(n, VariableInfo.AT_BEGIN);
        }

        private void generateCustomDoTag(Node.CustomTag n,
                TagHandlerInfo handlerInfo, String tagHandlerVar)
                throws JasperException {

            Class<?> tagHandlerClass =
                handlerInfo.getTagHandlerClass();

            n.setBeginJavaLine(out.getJavaLine());
            out.printin("//  ");
            out.println(n.getQName());

            // Declare AT_BEGIN scripting variables
            declareScriptingVars(n, VariableInfo.AT_BEGIN);
            saveScriptingVars(n, VariableInfo.AT_BEGIN);

            // Declare AT_END scripting variables
            declareScriptingVars(n, VariableInfo.AT_END);

            String tagHandlerClassName = tagHandlerClass.getCanonicalName();
            writeNewInstance(tagHandlerVar, tagHandlerClassName);

            out.printil("try {");
            out.pushIndent();

            generateSetters(n, tagHandlerVar, handlerInfo, true);

            // Set the body
            if (findJspBody(n) == null) {
                /*
                 * Encapsulate body of custom tag invocation in JspFragment and
                 * pass it to tag handler's setJspBody(), unless tag body is
                 * empty
                 */
                if (!n.hasEmptyBody()) {
                    out.printin(tagHandlerVar);
                    out.print(".setJspBody(");
                    generateJspFragment(n, tagHandlerVar);
                    out.println(");");
                }
            } else {
                /*
                 * Body of tag is the body of the <jsp:body> element. The visit
                 * method for that element is going to encapsulate that
                 * element's body in a JspFragment and pass it to the tag
                 * handler's setJspBody()
                 */
                String tmpTagHandlerVar = simpleTagHandlerVar;
                simpleTagHandlerVar = tagHandlerVar;
                boolean tmpIsSimpleTagHandler = isSimpleTagHandler;
                isSimpleTagHandler = true;
                visitBody(n);
                simpleTagHandlerVar = tmpTagHandlerVar;
                isSimpleTagHandler = tmpIsSimpleTagHandler;
            }

            out.printin(tagHandlerVar);
            out.println(".doTag();");

            restoreScriptingVars(n, VariableInfo.AT_BEGIN);

            // Synchronize AT_BEGIN scripting variables
            syncScriptingVars(n, VariableInfo.AT_BEGIN);

            // Synchronize AT_END scripting variables
            syncScriptingVars(n, VariableInfo.AT_END);

            out.popIndent();
            out.printil("} finally {");
            out.pushIndent();

            // Resource injection
            writeDestroyInstance(tagHandlerVar);

            out.popIndent();
            out.printil("}");

            n.setEndJavaLine(out.getJavaLine());
        }

        private void declareScriptingVars(Node.CustomTag n, int scope) {
            if (isFragment) {
                // No need to declare Java variables, if we inside a
                // JspFragment, because a fragment is always scriptless.
                return;
            }

            List<Object> vec = n.getScriptingVars(scope);
            if (vec != null) {
                for (int i = 0; i < vec.size(); i++) {
                    Object elem = vec.get(i);
                    if (elem instanceof VariableInfo) {
                        VariableInfo varInfo = (VariableInfo) elem;
                        if (varInfo.getDeclare()) {
                            out.printin(varInfo.getClassName());
                            out.print(" ");
                            out.print(varInfo.getVarName());
                            out.println(" = null;");
                        }
                    } else {
                        TagVariableInfo tagVarInfo = (TagVariableInfo) elem;
                        if (tagVarInfo.getDeclare()) {
                            String varName = tagVarInfo.getNameGiven();
                            if (varName == null) {
                                varName = n.getTagData().getAttributeString(
                                        tagVarInfo.getNameFromAttribute());
                            } else if (tagVarInfo.getNameFromAttribute() != null) {
                                // alias
                                continue;
                            }
                            out.printin(tagVarInfo.getClassName());
                            out.print(" ");
                            out.print(varName);
                            out.println(" = null;");
                        }
                    }
                }
            }
        }

        /*
         * This method is called as part of the custom tag's start element.
         *
         * If the given custom tag has a custom nesting level greater than 0,
         * save the current values of its scripting variables to temporary
         * variables, so those values may be restored in the tag's end element.
         * This way, the scripting variables may be synchronized by the given
         * tag without affecting their original values.
         */
        private void saveScriptingVars(Node.CustomTag n, int scope) {
            if (n.getCustomNestingLevel() == 0) {
                return;
            }
            if (isFragment) {
                // No need to declare Java variables, if we inside a
                // JspFragment, because a fragment is always scriptless.
                // Thus, there is no need to save/ restore/ sync them.
                // Note, that JspContextWrapper.syncFoo() methods will take
                // care of saving/ restoring/ sync'ing of JspContext attributes.
                return;
            }

            TagVariableInfo[] tagVarInfos = n.getTagVariableInfos();
            VariableInfo[] varInfos = n.getVariableInfos();
            if ((varInfos.length == 0) && (tagVarInfos.length == 0)) {
                return;
            }

            List<Object> declaredVariables = n.getScriptingVars(scope);

            if (varInfos.length > 0) {
                for (int i = 0; i < varInfos.length; i++) {
                    if (varInfos[i].getScope() != scope)
                        continue;
                    // If the scripting variable has been declared, skip codes
                    // for saving and restoring it.
                    if (declaredVariables.contains(varInfos[i]))
                        continue;
                    String varName = varInfos[i].getVarName();
                    String tmpVarName = "_jspx_" + varName + "_"
                            + n.getCustomNestingLevel();
                    out.printin(tmpVarName);
                    out.print(" = ");
                    out.print(varName);
                    out.println(";");
                }
            } else {
                for (int i = 0; i < tagVarInfos.length; i++) {
                    if (tagVarInfos[i].getScope() != scope)
                        continue;
                    // If the scripting variable has been declared, skip codes
                    // for saving and restoring it.
                    if (declaredVariables.contains(tagVarInfos[i]))
                        continue;
                    String varName = tagVarInfos[i].getNameGiven();
                    if (varName == null) {
                        varName = n.getTagData().getAttributeString(
                                tagVarInfos[i].getNameFromAttribute());
                    } else if (tagVarInfos[i].getNameFromAttribute() != null) {
                        // alias
                        continue;
                    }
                    String tmpVarName = "_jspx_" + varName + "_"
                            + n.getCustomNestingLevel();
                    out.printin(tmpVarName);
                    out.print(" = ");
                    out.print(varName);
                    out.println(";");
                }
            }
        }

        /*
         * This method is called as part of the custom tag's end element.
         *
         * If the given custom tag has a custom nesting level greater than 0,
         * restore its scripting variables to their original values that were
         * saved in the tag's start element.
         */
        private void restoreScriptingVars(Node.CustomTag n, int scope) {
            if (n.getCustomNestingLevel() == 0) {
                return;
            }
            if (isFragment) {
                // No need to declare Java variables, if we inside a
                // JspFragment, because a fragment is always scriptless.
                // Thus, there is no need to save/ restore/ sync them.
                // Note, that JspContextWrapper.syncFoo() methods will take
                // care of saving/ restoring/ sync'ing of JspContext attributes.
                return;
            }

            TagVariableInfo[] tagVarInfos = n.getTagVariableInfos();
            VariableInfo[] varInfos = n.getVariableInfos();
            if ((varInfos.length == 0) && (tagVarInfos.length == 0)) {
                return;
            }

            List<Object> declaredVariables = n.getScriptingVars(scope);

            if (varInfos.length > 0) {
                for (int i = 0; i < varInfos.length; i++) {
                    if (varInfos[i].getScope() != scope)
                        continue;
                    // If the scripting variable has been declared, skip codes
                    // for saving and restoring it.
                    if (declaredVariables.contains(varInfos[i]))
                        continue;
                    String varName = varInfos[i].getVarName();
                    String tmpVarName = "_jspx_" + varName + "_"
                            + n.getCustomNestingLevel();
                    out.printin(varName);
                    out.print(" = ");
                    out.print(tmpVarName);
                    out.println(";");
                }
            } else {
                for (int i = 0; i < tagVarInfos.length; i++) {
                    if (tagVarInfos[i].getScope() != scope)
                        continue;
                    // If the scripting variable has been declared, skip codes
                    // for saving and restoring it.
                    if (declaredVariables.contains(tagVarInfos[i]))
                        continue;
                    String varName = tagVarInfos[i].getNameGiven();
                    if (varName == null) {
                        varName = n.getTagData().getAttributeString(
                                tagVarInfos[i].getNameFromAttribute());
                    } else if (tagVarInfos[i].getNameFromAttribute() != null) {
                        // alias
                        continue;
                    }
                    String tmpVarName = "_jspx_" + varName + "_"
                            + n.getCustomNestingLevel();
                    out.printin(varName);
                    out.print(" = ");
                    out.print(tmpVarName);
                    out.println(";");
                }
            }
        }

        /*
         * Synchronizes the scripting variables of the given custom tag for the
         * given scope.
         */
        private void syncScriptingVars(Node.CustomTag n, int scope) {
            if (isFragment) {
                // No need to declare Java variables, if we inside a
                // JspFragment, because a fragment is always scriptless.
                // Thus, there is no need to save/ restore/ sync them.
                // Note, that JspContextWrapper.syncFoo() methods will take
                // care of saving/ restoring/ sync'ing of JspContext attributes.
                return;
            }

            TagVariableInfo[] tagVarInfos = n.getTagVariableInfos();
            VariableInfo[] varInfos = n.getVariableInfos();

            if ((varInfos.length == 0) && (tagVarInfos.length == 0)) {
                return;
            }

            if (varInfos.length > 0) {
                for (int i = 0; i < varInfos.length; i++) {
                    if (varInfos[i].getScope() == scope) {
                        out.printin(varInfos[i].getVarName());
                        out.print(" = (");
                        out.print(varInfos[i].getClassName());
                        out.print(") _jspx_page_context.findAttribute(");
                        out.print(quote(varInfos[i].getVarName()));
                        out.println(");");
                    }
                }
            } else {
                for (int i = 0; i < tagVarInfos.length; i++) {
                    if (tagVarInfos[i].getScope() == scope) {
                        String name = tagVarInfos[i].getNameGiven();
                        if (name == null) {
                            name = n.getTagData().getAttributeString(
                                    tagVarInfos[i].getNameFromAttribute());
                        } else if (tagVarInfos[i].getNameFromAttribute() != null) {
                            // alias
                            continue;
                        }
                        out.printin(name);
                        out.print(" = (");
                        out.print(tagVarInfos[i].getClassName());
                        out.print(") _jspx_page_context.findAttribute(");
                        out.print(quote(name));
                        out.println(");");
                    }
                }
            }
        }

        private String getJspContextVar() {
            if (this.isTagFile) {
                return "this.getJspContext()";
            }
            return "_jspx_page_context";
        }

        /*
         * Creates a tag variable name by concatenating the given prefix and
         * shortName and encoded to make the resultant string a valid Java
         * Identifier.
         */
        private String createTagVarName(String fullName, String prefix,
                String shortName) {

            String varName;
            synchronized (tagVarNumbers) {
                varName = prefix + "_" + shortName + "_";
                if (tagVarNumbers.get(fullName) != null) {
                    Integer i = tagVarNumbers.get(fullName);
                    varName = varName + i.intValue();
                    tagVarNumbers.put(fullName,
                            Integer.valueOf(i.intValue() + 1));
                } else {
                    tagVarNumbers.put(fullName, Integer.valueOf(1));
                    varName = varName + "0";
                }
            }
            return JspUtil.makeJavaIdentifier(varName);
        }

        @SuppressWarnings("null")
        private String evaluateAttribute(TagHandlerInfo handlerInfo,
                Node.JspAttribute attr, Node.CustomTag n, String tagHandlerVar)
                throws JasperException {

            String attrValue = attr.getValue();
            if (attrValue == null) {
                if (attr.isNamedAttribute()) {
                    if (n.checkIfAttributeIsJspFragment(attr.getName())) {
                        // XXX - no need to generate temporary variable here
                        attrValue = generateNamedAttributeJspFragment(attr
                                .getNamedAttributeNode(), tagHandlerVar);
                    } else {
                        attrValue = generateNamedAttributeValue(attr
                                .getNamedAttributeNode());
                    }
                } else {
                    return null;
                }
            }

            String localName = attr.getLocalName();

            Method m = null;
            Class<?>[] c = null;
            if (attr.isDynamic()) {
                c = OBJECT_CLASS;
            } else {
                m = handlerInfo.getSetterMethod(localName);
                if (m == null) {
                    err.jspError(n, "jsp.error.unable.to_find_method", attr
                            .getName());
                }
                c = m.getParameterTypes();
                // XXX assert(c.length > 0)
            }

            if (attr.isExpression()) {
                // Do nothing
            } else if (attr.isNamedAttribute()) {
                if (!n.checkIfAttributeIsJspFragment(attr.getName())
                        && !attr.isDynamic()) {
                    attrValue = convertString(c[0], attrValue, localName,
                            handlerInfo.getPropertyEditorClass(localName), true);
                }
            } else if (attr.isELInterpreterInput()) {

                // results buffer
                StringBuilder sb = new StringBuilder(64);

                TagAttributeInfo tai = attr.getTagAttributeInfo();

                // generate elContext reference
                sb.append(getJspContextVar());
                sb.append(".getELContext()");
                String elContext = sb.toString();
                if (attr.getEL() != null && attr.getEL().getMapName() != null) {
                    sb.setLength(0);
                    sb.append("new org.apache.jasper.el.ELContextWrapper(");
                    sb.append(elContext);
                    sb.append(',');
                    sb.append(attr.getEL().getMapName());
                    sb.append(')');
                    elContext = sb.toString();
                }

                // reset buffer
                sb.setLength(0);

                // create our mark
                sb.append(n.getStart().toString());
                sb.append(" '");
                sb.append(attrValue);
                sb.append('\'');
                String mark = sb.toString();

                // reset buffer
                sb.setLength(0);

                // depending on type
                if (attr.isDeferredInput()
                        || ((tai != null) && ValueExpression.class.getName().equals(tai.getTypeName()))) {
                    sb.append("new org.apache.jasper.el.JspValueExpression(");
                    sb.append(quote(mark));
                    sb.append(",_jsp_getExpressionFactory().createValueExpression(");
                    if (attr.getEL() != null) { // optimize
                        sb.append(elContext);
                        sb.append(',');
                    }
                    sb.append(quote(attrValue));
                    sb.append(',');
                    sb.append(JspUtil.toJavaSourceTypeFromTld(attr.getExpectedTypeName()));
                    sb.append("))");
                    // should the expression be evaluated before passing to
                    // the setter?
                    boolean evaluate = false;
                    if (tai != null && tai.canBeRequestTime()) {
                        evaluate = true; // JSP.2.3.2
                    }
                    if (attr.isDeferredInput()) {
                        evaluate = false; // JSP.2.3.3
                    }
                    if (attr.isDeferredInput() && tai != null &&
                            tai.canBeRequestTime()) {
                        evaluate = !attrValue.contains("#{"); // JSP.2.3.5
                    }
                    if (evaluate) {
                        sb.append(".getValue(");
                        sb.append(getJspContextVar());
                        sb.append(".getELContext()");
                        sb.append(")");
                    }
                    attrValue = sb.toString();
                } else if (attr.isDeferredMethodInput()
                        || ((tai != null) && MethodExpression.class.getName().equals(tai.getTypeName()))) {
                    sb.append("new org.apache.jasper.el.JspMethodExpression(");
                    sb.append(quote(mark));
                    sb.append(",_jsp_getExpressionFactory().createMethodExpression(");
                    sb.append(elContext);
                    sb.append(',');
                    sb.append(quote(attrValue));
                    sb.append(',');
                    sb.append(JspUtil.toJavaSourceTypeFromTld(attr.getExpectedTypeName()));
                    sb.append(',');
                    sb.append("new java.lang.Class[] {");

                    String[] p = attr.getParameterTypeNames();
                    for (int i = 0; i < p.length; i++) {
                        sb.append(JspUtil.toJavaSourceTypeFromTld(p[i]));
                        sb.append(',');
                    }
                    if (p.length > 0) {
                        sb.setLength(sb.length() - 1);
                    }

                    sb.append("}))");
                    attrValue = sb.toString();
                } else {
                    // run attrValue through the expression interpreter
                    String mapName = (attr.getEL() != null) ? attr.getEL()
                            .getMapName() : null;
                    attrValue = elInterpreter.interpreterCall(ctxt,
                            this.isTagFile, attrValue, c[0], mapName, false);
                }
            } else {
                attrValue = convertString(c[0], attrValue, localName,
                        handlerInfo.getPropertyEditorClass(localName), false);
            }
            return attrValue;
        }

        /**
         * Generate code to create a map for the alias variables
         *
         * @return the name of the map
         */
        private String generateAliasMap(Node.CustomTag n,
                String tagHandlerVar) {

            TagVariableInfo[] tagVars = n.getTagVariableInfos();
            String aliasMapVar = null;

            boolean aliasSeen = false;
            for (int i = 0; i < tagVars.length; i++) {

                String nameFrom = tagVars[i].getNameFromAttribute();
                if (nameFrom != null) {
                    String aliasedName = n.getAttributeValue(nameFrom);
                    if (aliasedName == null)
                        continue;

                    if (!aliasSeen) {
                        out.printin("java.util.HashMap ");
                        aliasMapVar = tagHandlerVar + "_aliasMap";
                        out.print(aliasMapVar);
                        out.println(" = new java.util.HashMap();");
                        aliasSeen = true;
                    }
                    out.printin(aliasMapVar);
                    out.print(".put(");
                    out.print(quote(tagVars[i].getNameGiven()));
                    out.print(", ");
                    out.print(quote(aliasedName));
                    out.println(");");
                }
            }
            return aliasMapVar;
        }

        private void generateSetters(Node.CustomTag n, String tagHandlerVar,
                TagHandlerInfo handlerInfo, boolean simpleTag)
                throws JasperException {

            // Set context
            if (simpleTag) {
                // Generate alias map
                String aliasMapVar = null;
                if (n.isTagFile()) {
                    aliasMapVar = generateAliasMap(n, tagHandlerVar);
                }
                out.printin(tagHandlerVar);
                if (aliasMapVar == null) {
                    out.println(".setJspContext(_jspx_page_context);");
                } else {
                    out.print(".setJspContext(_jspx_page_context, ");
                    out.print(aliasMapVar);
                    out.println(");");
                }
            } else {
                out.printin(tagHandlerVar);
                out.println(".setPageContext(_jspx_page_context);");
            }

            // Set parent
            if (isTagFile && parent == null) {
                out.printin(tagHandlerVar);
                out.print(".setParent(");
                out.print("new javax.servlet.jsp.tagext.TagAdapter(");
                out.print("(javax.servlet.jsp.tagext.SimpleTag) this ));");
            } else if (!simpleTag) {
                out.printin(tagHandlerVar);
                out.print(".setParent(");
                if (parent != null) {
                    if (isSimpleTagParent) {
                        out.print("new javax.servlet.jsp.tagext.TagAdapter(");
                        out.print("(javax.servlet.jsp.tagext.SimpleTag) ");
                        out.print(parent);
                        out.println("));");
                    } else {
                        out.print("(javax.servlet.jsp.tagext.Tag) ");
                        out.print(parent);
                        out.println(");");
                    }
                } else {
                    out.println("null);");
                }
            } else {
                // The setParent() method need not be called if the value being
                // passed is null, since SimpleTag instances are not reused
                if (parent != null) {
                    out.printin(tagHandlerVar);
                    out.print(".setParent(");
                    out.print(parent);
                    out.println(");");
                }
            }

            // need to handle deferred values and methods
            Node.JspAttribute[] attrs = n.getJspAttributes();
            for (int i = 0; attrs != null && i < attrs.length; i++) {
                String attrValue = evaluateAttribute(handlerInfo, attrs[i], n,
                        tagHandlerVar);

                Mark m = n.getStart();
                out.printil("// "+m.getFile()+"("+m.getLineNumber()+","+m.getColumnNumber()+") "+ attrs[i].getTagAttributeInfo());
                if (attrs[i].isDynamic()) {
                    out.printin(tagHandlerVar);
                    out.print(".");
                    out.print("setDynamicAttribute(");
                    String uri = attrs[i].getURI();
                    if ("".equals(uri) || (uri == null)) {
                        out.print("null");
                    } else {
                        out.print("\"" + attrs[i].getURI() + "\"");
                    }
                    out.print(", \"");
                    out.print(attrs[i].getLocalName());
                    out.print("\", ");
                    out.print(attrValue);
                    out.println(");");
                } else {
                    out.printin(tagHandlerVar);
                    out.print(".");
                    out.print(handlerInfo.getSetterMethod(
                            attrs[i].getLocalName()).getName());
                    out.print("(");
                    out.print(attrValue);
                    out.println(");");
                }
            }

            // JspIdConsumer (after context has been set)
            if (n.implementsJspIdConsumer()) {
                out.printin(tagHandlerVar);
                out.print(".setJspId(\"");
                out.print(createJspId());
                out.println("\");");
            }
        }

        /*
         * @param c The target class to which to coerce the given string @param
         * s The string value @param attrName The name of the attribute whose
         * value is being supplied @param propEditorClass The property editor
         * for the given attribute @param isNamedAttribute true if the given
         * attribute is a named attribute (that is, specified using the
         * jsp:attribute standard action), and false otherwise
         */
        private String convertString(Class<?> c, String s, String attrName,
                Class<?> propEditorClass, boolean isNamedAttribute) {

            String quoted = s;
            if (!isNamedAttribute) {
                quoted = quote(s);
            }

            if (propEditorClass != null) {
                String className = c.getCanonicalName();
                return "("
                        + className
                        + ")org.apache.jasper.runtime.JspRuntimeLibrary.getValueFromBeanInfoPropertyEditor("
                        + className + ".class, \"" + attrName + "\", " + quoted
                        + ", " + propEditorClass.getCanonicalName() + ".class)";
            } else if (c == String.class) {
                return quoted;
            } else if (c == boolean.class) {
                return JspUtil.coerceToPrimitiveBoolean(s, isNamedAttribute);
            } else if (c == Boolean.class) {
                return JspUtil.coerceToBoolean(s, isNamedAttribute);
            } else if (c == byte.class) {
                return JspUtil.coerceToPrimitiveByte(s, isNamedAttribute);
            } else if (c == Byte.class) {
                return JspUtil.coerceToByte(s, isNamedAttribute);
            } else if (c == char.class) {
                return JspUtil.coerceToChar(s, isNamedAttribute);
            } else if (c == Character.class) {
                return JspUtil.coerceToCharacter(s, isNamedAttribute);
            } else if (c == double.class) {
                return JspUtil.coerceToPrimitiveDouble(s, isNamedAttribute);
            } else if (c == Double.class) {
                return JspUtil.coerceToDouble(s, isNamedAttribute);
            } else if (c == float.class) {
                return JspUtil.coerceToPrimitiveFloat(s, isNamedAttribute);
            } else if (c == Float.class) {
                return JspUtil.coerceToFloat(s, isNamedAttribute);
            } else if (c == int.class) {
                return JspUtil.coerceToInt(s, isNamedAttribute);
            } else if (c == Integer.class) {
                return JspUtil.coerceToInteger(s, isNamedAttribute);
            } else if (c == short.class) {
                return JspUtil.coerceToPrimitiveShort(s, isNamedAttribute);
            } else if (c == Short.class) {
                return JspUtil.coerceToShort(s, isNamedAttribute);
            } else if (c == long.class) {
                return JspUtil.coerceToPrimitiveLong(s, isNamedAttribute);
            } else if (c == Long.class) {
                return JspUtil.coerceToLong(s, isNamedAttribute);
            } else if (c == Object.class) {
                return quoted;
            } else {
                String className = c.getCanonicalName();
                return "("
                        + className
                        + ")org.apache.jasper.runtime.JspRuntimeLibrary.getValueFromPropertyEditorManager("
                        + className + ".class, \"" + attrName + "\", " + quoted
                        + ")";
            }
        }

        /*
         * Converts the scope string representation, whose possible values are
         * "page", "request", "session", and "application", to the corresponding
         * scope constant.
         */
        private String getScopeConstant(String scope) {
            String scopeName = "javax.servlet.jsp.PageContext.PAGE_SCOPE"; // Default to page

            if ("request".equals(scope)) {
                scopeName = "javax.servlet.jsp.PageContext.REQUEST_SCOPE";
            } else if ("session".equals(scope)) {
                scopeName = "javax.servlet.jsp.PageContext.SESSION_SCOPE";
            } else if ("application".equals(scope)) {
                scopeName = "javax.servlet.jsp.PageContext.APPLICATION_SCOPE";
            }

            return scopeName;
        }

        /**
         * Generates anonymous JspFragment inner class which is passed as an
         * argument to SimpleTag.setJspBody().
         */
        private void generateJspFragment(Node n, String tagHandlerVar)
                throws JasperException {
            // XXX - A possible optimization here would be to check to see
            // if the only child of the parent node is TemplateText. If so,
            // we know there won't be any parameters, etc, so we can
            // generate a low-overhead JspFragment that just echoes its
            // body. The implementation of this fragment can come from
            // the org.apache.jasper.runtime package as a support class.
            FragmentHelperClass.Fragment fragment = fragmentHelperClass
                    .openFragment(n, methodNesting);
            ServletWriter outSave = out;
            out = fragment.getGenBuffer().getOut();
            String tmpParent = parent;
            parent = "_jspx_parent";
            boolean isSimpleTagParentSave = isSimpleTagParent;
            isSimpleTagParent = true;
            boolean tmpIsFragment = isFragment;
            isFragment = true;
            String pushBodyCountVarSave = pushBodyCountVar;
            if (pushBodyCountVar != null) {
                // Use a fixed name for push body count, to simplify code gen
                pushBodyCountVar = "_jspx_push_body_count";
            }
            visitBody(n);
            out = outSave;
            parent = tmpParent;
            isSimpleTagParent = isSimpleTagParentSave;
            isFragment = tmpIsFragment;
            pushBodyCountVar = pushBodyCountVarSave;
            fragmentHelperClass.closeFragment(fragment, methodNesting);
            // XXX - Need to change pageContext to jspContext if
            // we're not in a place where pageContext is defined (e.g.
            // in a fragment or in a tag file.
            out.print("new " + fragmentHelperClass.getClassName() + "( "
                    + fragment.getId() + ", _jspx_page_context, "
                    + tagHandlerVar + ", " + pushBodyCountVar + ")");
        }

        /**
         * Generate the code required to obtain the runtime value of the given
         * named attribute.
         *
         * @return The name of the temporary variable the result is stored in.
         */
        public String generateNamedAttributeValue(Node.NamedAttribute n)
                throws JasperException {

            String varName = n.getTemporaryVariableName();

            // If the only body element for this named attribute node is
            // template text, we need not generate an extra call to
            // pushBody and popBody. Maybe we can further optimize
            // here by getting rid of the temporary variable, but in
            // reality it looks like javac does this for us.
            Node.Nodes body = n.getBody();
            if (body != null) {
                boolean templateTextOptimization = false;
                if (body.size() == 1) {
                    Node bodyElement = body.getNode(0);
                    if (bodyElement instanceof Node.TemplateText) {
                        templateTextOptimization = true;
                        out.printil("java.lang.String "
                                + varName
                                + " = "
                                + quote(((Node.TemplateText) bodyElement)
                                                .getText()) + ";");
                    }
                }

                // XXX - Another possible optimization would be for
                // lone EL expressions (no need to pushBody here either).

                if (!templateTextOptimization) {
                    out.printil("out = _jspx_page_context.pushBody();");
                    visitBody(n);
                    out.printil("java.lang.String " + varName + " = "
                            + "((javax.servlet.jsp.tagext.BodyContent)"
                            + "out).getString();");
                    out.printil("out = _jspx_page_context.popBody();");
                }
            } else {
                // Empty body must be treated as ""
                out.printil("java.lang.String " + varName + " = \"\";");
            }

            return varName;
        }

        /**
         * Similar to generateNamedAttributeValue, but create a JspFragment
         * instead.
         *
         * @param n
         *            The parent node of the named attribute
         * @param tagHandlerVar
         *            The variable the tag handler is stored in, so the fragment
         *            knows its parent tag.
         * @return The name of the temporary variable the fragment is stored in.
         */
        public String generateNamedAttributeJspFragment(Node.NamedAttribute n,
                String tagHandlerVar) throws JasperException {
            String varName = n.getTemporaryVariableName();

            out.printin("javax.servlet.jsp.tagext.JspFragment " + varName
                    + " = ");
            generateJspFragment(n, tagHandlerVar);
            out.println(";");

            return varName;
        }
    }

    private static void generateLocalVariables(ServletWriter out, Node n)
            throws JasperException {
        Node.ChildInfo ci;
        if (n instanceof Node.CustomTag) {
            ci = ((Node.CustomTag) n).getChildInfo();
        } else if (n instanceof Node.JspBody) {
            ci = ((Node.JspBody) n).getChildInfo();
        } else if (n instanceof Node.NamedAttribute) {
            ci = ((Node.NamedAttribute) n).getChildInfo();
        } else {
            // Cannot access err since this method is static, but at
            // least flag an error.
            throw new JasperException("Unexpected Node Type");
            // err.getString(
            // "jsp.error.internal.unexpected_node_type" ) );
        }

        if (ci.hasUseBean()) {
            out.printil("javax.servlet.http.HttpSession session = _jspx_page_context.getSession();");
            out.printil("javax.servlet.ServletContext application = _jspx_page_context.getServletContext();");
        }
        if (ci.hasUseBean() || ci.hasIncludeAction() || ci.hasSetProperty()
                || ci.hasParamAction()) {
            out.printil("javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest)_jspx_page_context.getRequest();");
        }
        if (ci.hasIncludeAction()) {
            out.printil("javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse)_jspx_page_context.getResponse();");
        }
    }

    /**
     * Common part of postamble, shared by both servlets and tag files.
     */
    private void genCommonPostamble() {
        // Append any methods that were generated in the buffer.
        for (int i = 0; i < methodsBuffered.size(); i++) {
            GenBuffer methodBuffer = methodsBuffered.get(i);
            methodBuffer.adjustJavaLines(out.getJavaLine() - 1);
            out.printMultiLn(methodBuffer.toString());
        }

        // Append the helper class
        if (fragmentHelperClass.isUsed()) {
            fragmentHelperClass.generatePostamble();
            fragmentHelperClass.adjustJavaLines(out.getJavaLine() - 1);
            out.printMultiLn(fragmentHelperClass.toString());
        }

        // Append char array declarations
        if (charArrayBuffer != null) {
            out.printMultiLn(charArrayBuffer.toString());
        }

        // Close the class definition
        out.popIndent();
        out.printil("}");
    }

    /**
     * Generates the ending part of the static portion of the servlet.
     */
    private void generatePostamble() {
        out.popIndent();
        out.printil("} catch (java.lang.Throwable t) {");
        out.pushIndent();
        out.printil("if (!(t instanceof javax.servlet.jsp.SkipPageException)){");
        out.pushIndent();
        out.printil("out = _jspx_out;");
        out.printil("if (out != null && out.getBufferSize() != 0)");
        out.pushIndent();
        out.printil("try {");
        out.pushIndent();
        out.printil("if (response.isCommitted()) {");
        out.pushIndent();
        out.printil("out.flush();");
        out.popIndent();
        out.printil("} else {");
        out.pushIndent();
        out.printil("out.clearBuffer();");
        out.popIndent();
        out.printil("}");
        out.popIndent();
        out.printil("} catch (java.io.IOException e) {}");
        out.popIndent();
        out.printil("if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);");
        out.printil("else throw new ServletException(t);");
        out.popIndent();
        out.printil("}");
        out.popIndent();
        out.printil("} finally {");
        out.pushIndent();
        out.printil("_jspxFactory.releasePageContext(_jspx_page_context);");
        out.popIndent();
        out.printil("}");

        // Close the service method
        out.popIndent();
        out.printil("}");

        // Generated methods, helper classes, etc.
        genCommonPostamble();
    }

    /**
     * Constructor.
     */
    Generator(ServletWriter out, Compiler compiler) throws JasperException {
        this.out = out;
        methodsBuffered = new ArrayList<GenBuffer>();
        charArrayBuffer = null;
        err = compiler.getErrorDispatcher();
        ctxt = compiler.getCompilationContext();
        fragmentHelperClass = new FragmentHelperClass("Helper");
        pageInfo = compiler.getPageInfo();

        ELInterpreter elInterpreter = null;
        try {
            elInterpreter = ELInterpreterFactory.getELInterpreter(
                    compiler.getCompilationContext().getServletContext());
        } catch (Exception e) {
            err.jspError("jsp.error.el_interpreter_class.instantiation",
                    e.getMessage());
        }
        this.elInterpreter = elInterpreter;

        /*
         * Temporary hack. If a JSP page uses the "extends" attribute of the
         * page directive, the _jspInit() method of the generated servlet class
         * will not be called (it is only called for those generated servlets
         * that extend HttpJspBase, the default), causing the tag handler pools
         * not to be initialized and resulting in a NPE. The JSP spec needs to
         * clarify whether containers can override init() and destroy(). For
         * now, we just disable tag pooling for pages that use "extends".
         */
        if (pageInfo.getExtends(false) == null || POOL_TAGS_WITH_EXTENDS) {
            isPoolingEnabled = ctxt.getOptions().isPoolingEnabled();
        } else {
            isPoolingEnabled = false;
        }
        beanInfo = pageInfo.getBeanRepository();
        varInfoNames = pageInfo.getVarInfoNames();
        breakAtLF = ctxt.getOptions().getMappedFile();
        if (isPoolingEnabled) {
            tagHandlerPoolNames = new Vector<String>();
        } else {
            tagHandlerPoolNames = null;
        }
        timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timestampFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * The main entry for Generator.
     *
     * @param out
     *            The servlet output writer
     * @param compiler
     *            The compiler
     * @param page
     *            The input page
     */
    public static void generate(ServletWriter out, Compiler compiler,
            Node.Nodes page) throws JasperException {

        Generator gen = new Generator(out, compiler);

        if (gen.isPoolingEnabled) {
            gen.compileTagHandlerPoolList(page);
        }
        gen.generateCommentHeader();
        if (gen.ctxt.isTagFile()) {
            JasperTagInfo tagInfo = (JasperTagInfo) gen.ctxt.getTagInfo();
            gen.generateTagHandlerPreamble(tagInfo, page);

            if (gen.ctxt.isPrototypeMode()) {
                return;
            }

            gen.generateXmlProlog(page);
            gen.fragmentHelperClass.generatePreamble();
            page.visit(gen.new GenerateVisitor(gen.ctxt.isTagFile(), out,
                    gen.methodsBuffered, gen.fragmentHelperClass));
            gen.generateTagHandlerPostamble(tagInfo);
        } else {
            gen.generatePreamble(page);
            gen.generateXmlProlog(page);
            gen.fragmentHelperClass.generatePreamble();
            page.visit(gen.new GenerateVisitor(gen.ctxt.isTagFile(), out,
                    gen.methodsBuffered, gen.fragmentHelperClass));
            gen.generatePostamble();
        }
    }

    private void generateCommentHeader() {
        out.println("/*");
        out.println(" * Generated by the Jasper component of Apache Tomcat");
        out.println(" * Version: " + ctxt.getServletContext().getServerInfo());
        out.println(" * Generated at: " + timestampFormat.format(new Date()) +
                " UTC");
        out.println(" * Note: The last modified time of this file was set to");
        out.println(" *       the last modified time of the source file after");
        out.println(" *       generation to assist with modification tracking.");
        out.println(" */");
    }

    /*
     * Generates tag handler preamble.
     */
    private void generateTagHandlerPreamble(JasperTagInfo tagInfo,
            Node.Nodes tag) throws JasperException {

        // Generate package declaration
        String className = tagInfo.getTagClassName();
        int lastIndex = className.lastIndexOf('.');
        if (lastIndex != -1) {
            String pkgName = className.substring(0, lastIndex);
            genPreamblePackage(pkgName);
            className = className.substring(lastIndex + 1);
        }

        // Generate imports
        genPreambleImports();

        // Generate class declaration
        out.printin("public final class ");
        out.println(className);
        out.printil("    extends javax.servlet.jsp.tagext.SimpleTagSupport");
        out.printin("    implements org.apache.jasper.runtime.JspSourceDependent");
        if (tagInfo.hasDynamicAttributes()) {
            out.println(",");
            out.printin("               javax.servlet.jsp.tagext.DynamicAttributes");
        }
        out.println(" {");
        out.println();
        out.pushIndent();

        /*
         * Class body begins here
         */
        generateDeclarations(tag);

        // Static initializations here
        genPreambleStaticInitializers();

        out.printil("private javax.servlet.jsp.JspContext jspContext;");

        // Declare writer used for storing result of fragment/body invocation
        // if 'varReader' or 'var' attribute is specified
        out.printil("private java.io.Writer _jspx_sout;");

        // Class variable declarations
        genPreambleClassVariableDeclarations();

        generateSetJspContext(tagInfo);

        // Tag-handler specific declarations
        generateTagHandlerAttributes(tagInfo);
        if (tagInfo.hasDynamicAttributes())
            generateSetDynamicAttribute();

        // Methods here
        genPreambleMethods();

        // Now the doTag() method
        out.printil("public void doTag() throws javax.servlet.jsp.JspException, java.io.IOException {");

        if (ctxt.isPrototypeMode()) {
            out.printil("}");
            out.popIndent();
            out.printil("}");
            return;
        }

        out.pushIndent();

        /*
         * According to the spec, 'pageContext' must not be made available as an
         * implicit object in tag files. Declare _jspx_page_context, so we can
         * share the code generator with JSPs.
         */
        out.printil("javax.servlet.jsp.PageContext _jspx_page_context = (javax.servlet.jsp.PageContext)jspContext;");

        // Declare implicit objects.
        out.printil("javax.servlet.http.HttpServletRequest request = "
                + "(javax.servlet.http.HttpServletRequest) _jspx_page_context.getRequest();");
        out.printil("javax.servlet.http.HttpServletResponse response = "
                + "(javax.servlet.http.HttpServletResponse) _jspx_page_context.getResponse();");
        out.printil("javax.servlet.http.HttpSession session = _jspx_page_context.getSession();");
        out.printil("javax.servlet.ServletContext application = _jspx_page_context.getServletContext();");
        out.printil("javax.servlet.ServletConfig config = _jspx_page_context.getServletConfig();");
        out.printil("javax.servlet.jsp.JspWriter out = jspContext.getOut();");
        out.printil("_jspInit(config);");

        // set current JspContext on ELContext
        out.printil("jspContext.getELContext().putContext(javax.servlet.jsp.JspContext.class,jspContext);");

        generatePageScopedVariables(tagInfo);

        declareTemporaryScriptingVars(tag);
        out.println();

        out.printil("try {");
        out.pushIndent();
    }

    private void generateTagHandlerPostamble(TagInfo tagInfo) {
        out.popIndent();

        // Have to catch Throwable because a classic tag handler
        // helper method is declared to throw Throwable.
        out.printil("} catch( java.lang.Throwable t ) {");
        out.pushIndent();
        out.printil("if( t instanceof javax.servlet.jsp.SkipPageException )");
        out.printil("    throw (javax.servlet.jsp.SkipPageException) t;");
        out.printil("if( t instanceof java.io.IOException )");
        out.printil("    throw (java.io.IOException) t;");
        out.printil("if( t instanceof java.lang.IllegalStateException )");
        out.printil("    throw (java.lang.IllegalStateException) t;");
        out.printil("if( t instanceof javax.servlet.jsp.JspException )");
        out.printil("    throw (javax.servlet.jsp.JspException) t;");
        out.printil("throw new javax.servlet.jsp.JspException(t);");
        out.popIndent();
        out.printil("} finally {");
        out.pushIndent();

        // handle restoring VariableMapper
        TagAttributeInfo[] attrInfos = tagInfo.getAttributes();
        for (int i = 0; i < attrInfos.length; i++) {
            if (attrInfos[i].isDeferredMethod() || attrInfos[i].isDeferredValue()) {
                out.printin("_el_variablemapper.setVariable(");
                out.print(quote(attrInfos[i].getName()));
                out.print(",_el_ve");
                out.print(i);
                out.println(");");
            }
        }

        // restore nested JspContext on ELContext
        out.printil("jspContext.getELContext().putContext(javax.servlet.jsp.JspContext.class,super.getJspContext());");

        out.printil("((org.apache.jasper.runtime.JspContextWrapper) jspContext).syncEndTagFile();");
        if (isPoolingEnabled && !tagHandlerPoolNames.isEmpty()) {
            out.printil("_jspDestroy();");
        }
        out.popIndent();
        out.printil("}");

        // Close the doTag method
        out.popIndent();
        out.printil("}");

        // Generated methods, helper classes, etc.
        genCommonPostamble();
    }

    /**
     * Generates declarations for tag handler attributes, and defines the getter
     * and setter methods for each.
     */
    private void generateTagHandlerAttributes(TagInfo tagInfo) {

        if (tagInfo.hasDynamicAttributes()) {
            out.printil("private java.util.HashMap _jspx_dynamic_attrs = new java.util.HashMap();");
        }

        // Declare attributes
        TagAttributeInfo[] attrInfos = tagInfo.getAttributes();
        for (int i = 0; i < attrInfos.length; i++) {
            out.printin("private ");
            if (attrInfos[i].isFragment()) {
                out.print("javax.servlet.jsp.tagext.JspFragment ");
            } else {
                out.print(JspUtil.toJavaSourceType(attrInfos[i].getTypeName()));
                out.print(" ");
            }
            out.print(JspUtil.makeJavaIdentifierForAttribute(
                    attrInfos[i].getName()));
            out.println(";");
        }
        out.println();

        // Define attribute getter and setter methods
        for (int i = 0; i < attrInfos.length; i++) {
            String javaName =
                JspUtil.makeJavaIdentifierForAttribute(attrInfos[i].getName());

            // getter method
            out.printin("public ");
            if (attrInfos[i].isFragment()) {
                out.print("javax.servlet.jsp.tagext.JspFragment ");
            } else {
                out.print(JspUtil.toJavaSourceType(attrInfos[i].getTypeName()));
                out.print(" ");
            }
            out.print(toGetterMethod(attrInfos[i].getName()));
            out.println(" {");
            out.pushIndent();
            out.printin("return this.");
            out.print(javaName);
            out.println(";");
            out.popIndent();
            out.printil("}");
            out.println();

            // setter method
            out.printin("public void ");
            out.print(toSetterMethodName(attrInfos[i].getName()));
            if (attrInfos[i].isFragment()) {
                out.print("(javax.servlet.jsp.tagext.JspFragment ");
            } else {
                out.print("(");
                out.print(JspUtil.toJavaSourceType(attrInfos[i].getTypeName()));
                out.print(" ");
            }
            out.print(javaName);
            out.println(") {");
            out.pushIndent();
            out.printin("this.");
            out.print(javaName);
            out.print(" = ");
            out.print(javaName);
            out.println(";");
            if (ctxt.isTagFile()) {
                // Tag files should also set jspContext attributes
                out.printin("jspContext.setAttribute(\"");
                out.print(attrInfos[i].getName());
                out.print("\", ");
                out.print(javaName);
                out.println(");");
            }
            out.popIndent();
            out.printil("}");
            out.println();
        }
    }

    /*
     * Generate setter for JspContext so we can create a wrapper and store both
     * the original and the wrapper. We need the wrapper to mask the page
     * context from the tag file and simulate a fresh page context. We need the
     * original to do things like sync AT_BEGIN and AT_END scripting variables.
     */
    private void generateSetJspContext(TagInfo tagInfo) {

        boolean nestedSeen = false;
        boolean atBeginSeen = false;
        boolean atEndSeen = false;

        // Determine if there are any aliases
        boolean aliasSeen = false;
        TagVariableInfo[] tagVars = tagInfo.getTagVariableInfos();
        for (int i = 0; i < tagVars.length; i++) {
            if (tagVars[i].getNameFromAttribute() != null
                    && tagVars[i].getNameGiven() != null) {
                aliasSeen = true;
                break;
            }
        }

        if (aliasSeen) {
            out.printil("public void setJspContext(javax.servlet.jsp.JspContext ctx, java.util.Map aliasMap) {");
        } else {
            out.printil("public void setJspContext(javax.servlet.jsp.JspContext ctx) {");
        }
        out.pushIndent();
        out.printil("super.setJspContext(ctx);");
        out.printil("java.util.ArrayList _jspx_nested = null;");
        out.printil("java.util.ArrayList _jspx_at_begin = null;");
        out.printil("java.util.ArrayList _jspx_at_end = null;");

        for (int i = 0; i < tagVars.length; i++) {

            switch (tagVars[i].getScope()) {
            case VariableInfo.NESTED:
                if (!nestedSeen) {
                    out.printil("_jspx_nested = new java.util.ArrayList();");
                    nestedSeen = true;
                }
                out.printin("_jspx_nested.add(");
                break;

            case VariableInfo.AT_BEGIN:
                if (!atBeginSeen) {
                    out.printil("_jspx_at_begin = new java.util.ArrayList();");
                    atBeginSeen = true;
                }
                out.printin("_jspx_at_begin.add(");
                break;

            case VariableInfo.AT_END:
                if (!atEndSeen) {
                    out.printil("_jspx_at_end = new java.util.ArrayList();");
                    atEndSeen = true;
                }
                out.printin("_jspx_at_end.add(");
                break;
            } // switch

            out.print(quote(tagVars[i].getNameGiven()));
            out.println(");");
        }
        if (aliasSeen) {
            out.printil("this.jspContext = new org.apache.jasper.runtime.JspContextWrapper(ctx, _jspx_nested, _jspx_at_begin, _jspx_at_end, aliasMap);");
        } else {
            out.printil("this.jspContext = new org.apache.jasper.runtime.JspContextWrapper(ctx, _jspx_nested, _jspx_at_begin, _jspx_at_end, null);");
        }
        out.popIndent();
        out.printil("}");
        out.println();
        out.printil("public javax.servlet.jsp.JspContext getJspContext() {");
        out.pushIndent();
        out.printil("return this.jspContext;");
        out.popIndent();
        out.printil("}");
    }

    /*
     * Generates implementation of
     * javax.servlet.jsp.tagext.DynamicAttributes.setDynamicAttribute() method,
     * which saves each dynamic attribute that is passed in so that a scoped
     * variable can later be created for it.
     */
    public void generateSetDynamicAttribute() {
        out.printil("public void setDynamicAttribute(java.lang.String uri, java.lang.String localName, java.lang.Object value) throws javax.servlet.jsp.JspException {");
        out.pushIndent();
        /*
         * According to the spec, only dynamic attributes with no uri are to be
         * present in the Map; all other dynamic attributes are ignored.
         */
        out.printil("if (uri == null)");
        out.pushIndent();
        out.printil("_jspx_dynamic_attrs.put(localName, value);");
        out.popIndent();
        out.popIndent();
        out.printil("}");
    }

    /*
     * Creates a page-scoped variable for each declared tag attribute. Also, if
     * the tag accepts dynamic attributes, a page-scoped variable is made
     * available for each dynamic attribute that was passed in.
     */
    private void generatePageScopedVariables(JasperTagInfo tagInfo) {

        // "normal" attributes
        TagAttributeInfo[] attrInfos = tagInfo.getAttributes();
        boolean variableMapperVar = false;
        for (int i = 0; i < attrInfos.length; i++) {
            String attrName = attrInfos[i].getName();

            // handle assigning deferred vars to VariableMapper, storing
            // previous values under '_el_ve[i]' for later re-assignment
            if (attrInfos[i].isDeferredValue() || attrInfos[i].isDeferredMethod()) {

                // we need to scope the modified VariableMapper for consistency and performance
                if (!variableMapperVar) {
                    out.printil("javax.el.VariableMapper _el_variablemapper = jspContext.getELContext().getVariableMapper();");
                    variableMapperVar = true;
                }

                out.printin("javax.el.ValueExpression _el_ve");
                out.print(i);
                out.print(" = _el_variablemapper.setVariable(");
                out.print(quote(attrName));
                out.print(',');
                if (attrInfos[i].isDeferredMethod()) {
                    out.print("_jsp_getExpressionFactory().createValueExpression(");
                    out.print(toGetterMethod(attrName));
                    out.print(",javax.el.MethodExpression.class)");
                } else {
                    out.print(toGetterMethod(attrName));
                }
                out.println(");");
            } else {
                out.printil("if( " + toGetterMethod(attrName) + " != null ) ");
                out.pushIndent();
                out.printin("_jspx_page_context.setAttribute(");
                out.print(quote(attrName));
                out.print(", ");
                out.print(toGetterMethod(attrName));
                out.println(");");
                out.popIndent();
            }
        }

        // Expose the Map containing dynamic attributes as a page-scoped var
        if (tagInfo.hasDynamicAttributes()) {
            out.printin("_jspx_page_context.setAttribute(\"");
            out.print(tagInfo.getDynamicAttributesMapName());
            out.print("\", _jspx_dynamic_attrs);");
        }
    }

    /*
     * Generates the getter method for the given attribute name.
     */
    private String toGetterMethod(String attrName) {
        char[] attrChars = attrName.toCharArray();
        attrChars[0] = Character.toUpperCase(attrChars[0]);
        return "get" + new String(attrChars) + "()";
    }

    /*
     * Generates the setter method name for the given attribute name.
     */
    private String toSetterMethodName(String attrName) {
        char[] attrChars = attrName.toCharArray();
        attrChars[0] = Character.toUpperCase(attrChars[0]);
        return "set" + new String(attrChars);
    }

    /**
     * Class storing the result of introspecting a custom tag handler.
     */
    private static class TagHandlerInfo {

        private Hashtable<String, Method> methodMaps;

        private Hashtable<String, Class<?>> propertyEditorMaps;

        private Class<?> tagHandlerClass;

        /**
         * Constructor.
         *
         * @param n
         *            The custom tag whose tag handler class is to be
         *            introspected
         * @param tagHandlerClass
         *            Tag handler class
         * @param err
         *            Error dispatcher
         */
        TagHandlerInfo(Node n, Class<?> tagHandlerClass,
                ErrorDispatcher err) throws JasperException {
            this.tagHandlerClass = tagHandlerClass;
            this.methodMaps = new Hashtable<String, Method>();
            this.propertyEditorMaps = new Hashtable<String, Class<?>>();

            try {
                BeanInfo tagClassInfo = Introspector
                        .getBeanInfo(tagHandlerClass);
                PropertyDescriptor[] pd = tagClassInfo.getPropertyDescriptors();
                for (int i = 0; i < pd.length; i++) {
                    /*
                     * FIXME: should probably be checking for things like
                     * pageContext, bodyContent, and parent here -akv
                     */
                    if (pd[i].getWriteMethod() != null) {
                        methodMaps.put(pd[i].getName(), pd[i].getWriteMethod());
                    }
                    if (pd[i].getPropertyEditorClass() != null)
                        propertyEditorMaps.put(pd[i].getName(), pd[i]
                                .getPropertyEditorClass());
                }
            } catch (IntrospectionException ie) {
                err.jspError(n, ie, "jsp.error.introspect.taghandler",
                        tagHandlerClass.getName());
            }
        }

        /**
         * XXX
         */
        public Method getSetterMethod(String attrName) {
            return methodMaps.get(attrName);
        }

        /**
         * XXX
         */
        public Class<?> getPropertyEditorClass(String attrName) {
            return propertyEditorMaps.get(attrName);
        }

        /**
         * XXX
         */
        public Class<?> getTagHandlerClass() {
            return tagHandlerClass;
        }
    }

    /**
     * A class for generating codes to a buffer. Included here are some support
     * for tracking source to Java lines mapping.
     */
    private static class GenBuffer {

        /*
         * For a CustomTag, the codes that are generated at the beginning of the
         * tag may not be in the same buffer as those for the body of the tag.
         * Two fields are used here to keep this straight. For codes that do not
         * corresponds to any JSP lines, they should be null.
         */
        private Node node;

        private Node.Nodes body;

        private java.io.CharArrayWriter charWriter;

        protected ServletWriter out;

        GenBuffer() {
            this(null, null);
        }

        GenBuffer(Node n, Node.Nodes b) {
            node = n;
            body = b;
            if (body != null) {
                body.setGeneratedInBuffer(true);
            }
            charWriter = new java.io.CharArrayWriter();
            out = new ServletWriter(new java.io.PrintWriter(charWriter));
        }

        public ServletWriter getOut() {
            return out;
        }

        @Override
        public String toString() {
            return charWriter.toString();
        }

        /**
         * Adjust the Java Lines. This is necessary because the Java lines
         * stored with the nodes are relative the beginning of this buffer and
         * need to be adjusted when this buffer is inserted into the source.
         */
        public void adjustJavaLines(final int offset) {

            if (node != null) {
                adjustJavaLine(node, offset);
            }

            if (body != null) {
                try {
                    body.visit(new Node.Visitor() {

                        @Override
                        public void doVisit(Node n) {
                            adjustJavaLine(n, offset);
                        }

                        @Override
                        public void visit(Node.CustomTag n)
                                throws JasperException {
                            Node.Nodes b = n.getBody();
                            if (b != null && !b.isGeneratedInBuffer()) {
                                // Don't adjust lines for the nested tags that
                                // are also generated in buffers, because the
                                // adjustments will be done elsewhere.
                                b.visit(this);
                            }
                        }
                    });
                } catch (JasperException ex) {
                    // Ignore
                }
            }
        }

        private static void adjustJavaLine(Node n, int offset) {
            if (n.getBeginJavaLine() > 0) {
                n.setBeginJavaLine(n.getBeginJavaLine() + offset);
                n.setEndJavaLine(n.getEndJavaLine() + offset);
            }
        }
    }

    /**
     * Keeps track of the generated Fragment Helper Class
     */
    private static class FragmentHelperClass {

        private static class Fragment {
            private GenBuffer genBuffer;

            private int id;

            public Fragment(int id, Node node) {
                this.id = id;
                genBuffer = new GenBuffer(null, node.getBody());
            }

            public GenBuffer getGenBuffer() {
                return this.genBuffer;
            }

            public int getId() {
                return this.id;
            }
        }

        // True if the helper class should be generated.
        private boolean used = false;

        private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        private String className;

        // Buffer for entire helper class
        private GenBuffer classBuffer = new GenBuffer();

        public FragmentHelperClass(String className) {
            this.className = className;
        }

        public String getClassName() {
            return this.className;
        }

        public boolean isUsed() {
            return this.used;
        }

        public void generatePreamble() {
            ServletWriter out = this.classBuffer.getOut();
            out.println();
            out.pushIndent();
            // Note: cannot be static, as we need to reference things like
            // _jspx_meth_*
            out.printil("private class " + className);
            out.printil("    extends "
                    + "org.apache.jasper.runtime.JspFragmentHelper");
            out.printil("{");
            out.pushIndent();
            out.printil("private javax.servlet.jsp.tagext.JspTag _jspx_parent;");
            out.printil("private int[] _jspx_push_body_count;");
            out.println();
            out.printil("public " + className
                    + "( int discriminator, javax.servlet.jsp.JspContext jspContext, "
                    + "javax.servlet.jsp.tagext.JspTag _jspx_parent, "
                    + "int[] _jspx_push_body_count ) {");
            out.pushIndent();
            out.printil("super( discriminator, jspContext, _jspx_parent );");
            out.printil("this._jspx_parent = _jspx_parent;");
            out.printil("this._jspx_push_body_count = _jspx_push_body_count;");
            out.popIndent();
            out.printil("}");
        }

        public Fragment openFragment(Node parent, int methodNesting)
        throws JasperException {
            Fragment result = new Fragment(fragments.size(), parent);
            fragments.add(result);
            this.used = true;
            parent.setInnerClassName(className);

            ServletWriter out = result.getGenBuffer().getOut();
            out.pushIndent();
            out.pushIndent();
            // XXX - Returns boolean because if a tag is invoked from
            // within this fragment, the Generator sometimes might
            // generate code like "return true". This is ignored for now,
            // meaning only the fragment is skipped. The JSR-152
            // expert group is currently discussing what to do in this case.
            // See comment in closeFragment()
            if (methodNesting > 0) {
                out.printin("public boolean invoke");
            } else {
                out.printin("public void invoke");
            }
            out.println(result.getId() + "( " + "javax.servlet.jsp.JspWriter out ) ");
            out.pushIndent();
            // Note: Throwable required because methods like _jspx_meth_*
            // throw Throwable.
            out.printil("throws java.lang.Throwable");
            out.popIndent();
            out.printil("{");
            out.pushIndent();
            generateLocalVariables(out, parent);

            return result;
        }

        public void closeFragment(Fragment fragment, int methodNesting) {
            ServletWriter out = fragment.getGenBuffer().getOut();
            // XXX - See comment in openFragment()
            if (methodNesting > 0) {
                out.printil("return false;");
            } else {
                out.printil("return;");
            }
            out.popIndent();
            out.printil("}");
        }

        public void generatePostamble() {
            ServletWriter out = this.classBuffer.getOut();
            // Generate all fragment methods:
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                fragment.getGenBuffer().adjustJavaLines(out.getJavaLine() - 1);
                out.printMultiLn(fragment.getGenBuffer().toString());
            }

            // Generate postamble:
            out.printil("public void invoke( java.io.Writer writer )");
            out.pushIndent();
            out.printil("throws javax.servlet.jsp.JspException");
            out.popIndent();
            out.printil("{");
            out.pushIndent();
            out.printil("javax.servlet.jsp.JspWriter out = null;");
            out.printil("if( writer != null ) {");
            out.pushIndent();
            out.printil("out = this.jspContext.pushBody(writer);");
            out.popIndent();
            out.printil("} else {");
            out.pushIndent();
            out.printil("out = this.jspContext.getOut();");
            out.popIndent();
            out.printil("}");
            out.printil("try {");
            out.pushIndent();
            out.printil("Object _jspx_saved_JspContext = this.jspContext.getELContext().getContext(javax.servlet.jsp.JspContext.class);");
            out.printil("this.jspContext.getELContext().putContext(javax.servlet.jsp.JspContext.class,this.jspContext);");
            out.printil("switch( this.discriminator ) {");
            out.pushIndent();
            for (int i = 0; i < fragments.size(); i++) {
                out.printil("case " + i + ":");
                out.pushIndent();
                out.printil("invoke" + i + "( out );");
                out.printil("break;");
                out.popIndent();
            }
            out.popIndent();
            out.printil("}"); // switch

            // restore nested JspContext on ELContext
            out.printil("jspContext.getELContext().putContext(javax.servlet.jsp.JspContext.class,_jspx_saved_JspContext);");

            out.popIndent();
            out.printil("}"); // try
            out.printil("catch( java.lang.Throwable e ) {");
            out.pushIndent();
            out.printil("if (e instanceof javax.servlet.jsp.SkipPageException)");
            out.printil("    throw (javax.servlet.jsp.SkipPageException) e;");
            out.printil("throw new javax.servlet.jsp.JspException( e );");
            out.popIndent();
            out.printil("}"); // catch
            out.printil("finally {");
            out.pushIndent();

            out.printil("if( writer != null ) {");
            out.pushIndent();
            out.printil("this.jspContext.popBody();");
            out.popIndent();
            out.printil("}");

            out.popIndent();
            out.printil("}"); // finally
            out.popIndent();
            out.printil("}"); // invoke method
            out.popIndent();
            out.printil("}"); // helper class
            out.popIndent();
        }

        @Override
        public String toString() {
            return classBuffer.toString();
        }

        public void adjustJavaLines(int offset) {
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                fragment.getGenBuffer().adjustJavaLines(offset);
            }
        }
    }
}
