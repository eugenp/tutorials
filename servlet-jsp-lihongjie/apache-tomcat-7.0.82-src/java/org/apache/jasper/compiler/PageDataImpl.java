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

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ListIterator;

import javax.servlet.jsp.tagext.PageData;

import org.apache.jasper.JasperException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

/**
 * An implementation of <tt>javax.servlet.jsp.tagext.PageData</tt> which
 * builds the XML view of a given page.
 *
 * The XML view is built in two passes:
 *
 * During the first pass, the FirstPassVisitor collects the attributes of the
 * top-level jsp:root and those of the jsp:root elements of any included
 * pages, and adds them to the jsp:root element of the XML view.
 * In addition, any taglib directives are converted into xmlns: attributes and
 * added to the jsp:root element of the XML view.
 * This pass ignores any nodes other than JspRoot and TaglibDirective.
 *
 * During the second pass, the SecondPassVisitor produces the XML view, using
 * the combined jsp:root attributes determined in the first pass and any
 * remaining pages nodes (this pass ignores any JspRoot and TaglibDirective
 * nodes).
 *
 * @author Jan Luehe
 */
class PageDataImpl extends PageData implements TagConstants {

    private static final String JSP_VERSION = "2.0";
    private static final String CDATA_START_SECTION = "<![CDATA[\n";
    private static final String CDATA_END_SECTION = "]]>\n";
    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    // string buffer used to build XML view
    private StringBuilder buf;

    /**
     * Constructor.
     *
     * @param page the page nodes from which to generate the XML view
     */
    public PageDataImpl(Node.Nodes page, Compiler compiler)
                throws JasperException {

        // First pass
        FirstPassVisitor firstPass = new FirstPassVisitor(page.getRoot(),
                                                          compiler.getPageInfo());
        page.visit(firstPass);

        // Second pass
        buf = new StringBuilder();
        SecondPassVisitor secondPass
            = new SecondPassVisitor(page.getRoot(), buf, compiler,
                                    firstPass.getJspIdPrefix());
        page.visit(secondPass);
    }

    /**
     * Returns the input stream of the XML view.
     *
     * @return the input stream of the XML view
     */
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(buf.toString().getBytes(CHARSET_UTF8));
    }

    /*
     * First-pass Visitor for JspRoot nodes (representing jsp:root elements)
     * and TablibDirective nodes, ignoring any other nodes.
     *
     * The purpose of this Visitor is to collect the attributes of the
     * top-level jsp:root and those of the jsp:root elements of any included
     * pages, and add them to the jsp:root element of the XML view.
     * In addition, this Visitor converts any taglib directives into xmlns:
     * attributes and adds them to the jsp:root element of the XML view.
     */
    static class FirstPassVisitor
                extends Node.Visitor implements TagConstants {

        private Node.Root root;
        private AttributesImpl rootAttrs;
        private PageInfo pageInfo;

        // Prefix for the 'id' attribute
        private String jspIdPrefix;

        /*
         * Constructor
         */
        public FirstPassVisitor(Node.Root root, PageInfo pageInfo) {
            this.root = root;
            this.pageInfo = pageInfo;
            this.rootAttrs = new AttributesImpl();
            this.rootAttrs.addAttribute("", "", "version", "CDATA",
                                        JSP_VERSION);
            this.jspIdPrefix = "jsp";
        }

        @Override
        public void visit(Node.Root n) throws JasperException {
            visitBody(n);
            if (n == root) {
                /*
                 * Top-level page.
                 *
                 * Add
                 *   xmlns:jsp="http://java.sun.com/JSP/Page"
                 * attribute only if not already present.
                 */
                if (!JSP_URI.equals(rootAttrs.getValue("xmlns:jsp"))) {
                    rootAttrs.addAttribute("", "", "xmlns:jsp", "CDATA",
                                           JSP_URI);
                }

                if (pageInfo.isJspPrefixHijacked()) {
                    /*
                     * 'jsp' prefix has been hijacked, that is, bound to a
                     * namespace other than the JSP namespace. This means that
                     * when adding an 'id' attribute to each element, we can't
                     * use the 'jsp' prefix. Therefore, create a new prefix 
                     * (one that is unique across the translation unit) for use
                     * by the 'id' attribute, and bind it to the JSP namespace
                     */
                    jspIdPrefix += "jsp";
                    while (pageInfo.containsPrefix(jspIdPrefix)) {
                        jspIdPrefix += "jsp";
                    }
                    rootAttrs.addAttribute("", "", "xmlns:" + jspIdPrefix,
                                           "CDATA", JSP_URI);
                }

                root.setAttributes(rootAttrs);
            }
        }

        @Override
        public void visit(Node.JspRoot n) throws JasperException {
            addAttributes(n.getTaglibAttributes());
            addAttributes(n.getNonTaglibXmlnsAttributes());
            addAttributes(n.getAttributes());

            visitBody(n);
        }

        /*
         * Converts taglib directive into "xmlns:..." attribute of jsp:root
         * element.
         */
        @Override
        public void visit(Node.TaglibDirective n) throws JasperException {
            Attributes attrs = n.getAttributes();
            if (attrs != null) {
                String qName = "xmlns:" + attrs.getValue("prefix");
                /*
                 * According to javadocs of org.xml.sax.helpers.AttributesImpl,
                 * the addAttribute method does not check to see if the
                 * specified attribute is already contained in the list: This
                 * is the application's responsibility!
                 */
                if (rootAttrs.getIndex(qName) == -1) {
                    String location = attrs.getValue("uri");
                    if (location != null) {
                        if (location.startsWith("/")) {
                            location = URN_JSPTLD + location;
                        }
                        rootAttrs.addAttribute("", "", qName, "CDATA",
                                               location);
                    } else {
                        location = attrs.getValue("tagdir");
                        rootAttrs.addAttribute("", "", qName, "CDATA",
                                               URN_JSPTAGDIR + location);
                    }
                }
            }
        }

        public String getJspIdPrefix() {
            return jspIdPrefix;
        }

        private void addAttributes(Attributes attrs) {
            if (attrs != null) {
                int len = attrs.getLength();

                for (int i=0; i<len; i++) {
                    String qName = attrs.getQName(i);
                    if ("version".equals(qName)) {
                        continue;
                    }

                    // Bugzilla 35252: http://bz.apache.org/bugzilla/show_bug.cgi?id=35252
                    if(rootAttrs.getIndex(qName) == -1) {
                        rootAttrs.addAttribute(attrs.getURI(i),
                                               attrs.getLocalName(i),
                                               qName,
                                               attrs.getType(i),
                                               attrs.getValue(i));
                    }
                }
            }
        }
    }


    /*
     * Second-pass Visitor responsible for producing XML view and assigning
     * each element a unique jsp:id attribute.
     */
    static class SecondPassVisitor extends Node.Visitor
                implements TagConstants {

        private Node.Root root;
        private StringBuilder buf;
        private Compiler compiler;
        private String jspIdPrefix;
        private boolean resetDefaultNS = false;

        // Current value of jsp:id attribute
        private int jspId;

        /*
         * Constructor
         */
        public SecondPassVisitor(Node.Root root, StringBuilder buf,
                                 Compiler compiler, String jspIdPrefix) {
            this.root = root;
            this.buf = buf;
            this.compiler = compiler;
            this.jspIdPrefix = jspIdPrefix;
        }

        /*
         * Visits root node.
         */
        @Override
    public void visit(Node.Root n) throws JasperException {
            if (n == this.root) {
                // top-level page
                appendXmlProlog();
                appendTag(n);
            } else {
                boolean resetDefaultNSSave = resetDefaultNS;
                if (n.isXmlSyntax()) {
                    resetDefaultNS = true;
                }
                visitBody(n);
                resetDefaultNS = resetDefaultNSSave;
            }
        }

        /*
         * Visits jsp:root element of JSP page in XML syntax.
         *
         * Any nested jsp:root elements (from pages included via an
         * include directive) are ignored.
         */
        @Override
    public void visit(Node.JspRoot n) throws JasperException {
            visitBody(n);
        }

        @Override
    public void visit(Node.PageDirective n) throws JasperException {
            appendPageDirective(n);
        }

        @Override
    public void visit(Node.IncludeDirective n) throws JasperException {
            // expand in place
            visitBody(n);
        }

        @Override
    public void visit(Node.Comment n) throws JasperException {
            // Comments are ignored in XML view
        }

        @Override
    public void visit(Node.Declaration n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.Expression n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.Scriptlet n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.JspElement n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.ELExpression n) throws JasperException {
            if (!n.getRoot().isXmlSyntax()) {
                buf.append("<").append(JSP_TEXT_ACTION);
                buf.append(" ");
                buf.append(jspIdPrefix);
                buf.append(":id=\"");
                buf.append(jspId++).append("\">");
            }
            buf.append("${");
            buf.append(JspUtil.escapeXml(n.getText()));
            buf.append("}");
            if (!n.getRoot().isXmlSyntax()) {
                buf.append(JSP_TEXT_ACTION_END);
            }
            buf.append("\n");
        }

        @Override
    public void visit(Node.IncludeAction n) throws JasperException {
            appendTag(n);
        }
    
        @Override
    public void visit(Node.ForwardAction n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.GetProperty n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.SetProperty n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.ParamAction n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.ParamsAction n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.FallBackAction n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.UseBean n) throws JasperException {
            appendTag(n);
        }
        
        @Override
    public void visit(Node.PlugIn n) throws JasperException {
            appendTag(n);
        }

        @Override
        public void visit(Node.NamedAttribute n) throws JasperException {
            appendTag(n);
        }
        
        @Override
        public void visit(Node.JspBody n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.CustomTag n) throws JasperException {
            boolean resetDefaultNSSave = resetDefaultNS;
            appendTag(n, resetDefaultNS);
            resetDefaultNS = resetDefaultNSSave;
        }

        @Override
    public void visit(Node.UninterpretedTag n) throws JasperException {
            boolean resetDefaultNSSave = resetDefaultNS;
            appendTag(n, resetDefaultNS);
            resetDefaultNS = resetDefaultNSSave;
        }

        @Override
    public void visit(Node.JspText n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.DoBodyAction n) throws JasperException {
            appendTag(n);
        }

        @Override
        public void visit(Node.InvokeAction n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.TagDirective n) throws JasperException {
            appendTagDirective(n);
        }

        @Override
    public void visit(Node.AttributeDirective n) throws JasperException {
            appendTag(n);
        }

        @Override
    public void visit(Node.VariableDirective n) throws JasperException {
            appendTag(n);
        }
        
        @Override
    public void visit(Node.TemplateText n) throws JasperException {
            /*
             * If the template text came from a JSP page written in JSP syntax,
             * create a jsp:text element for it (JSP 5.3.2).
             */
            appendText(n.getText(), !n.getRoot().isXmlSyntax());
        }

        /*
         * Appends the given tag, including its body, to the XML view.
         */
        private void appendTag(Node n) throws JasperException {
            appendTag(n, false);
        }

        /*
         * Appends the given tag, including its body, to the XML view,
         * and optionally reset default namespace to "", if none specified.
         */
        private void appendTag(Node n, boolean addDefaultNS)
                throws JasperException {

            Node.Nodes body = n.getBody();
            String text = n.getText();

            buf.append("<").append(n.getQName());
            buf.append("\n");

            printAttributes(n, addDefaultNS);
            buf.append("  ").append(jspIdPrefix).append(":id").append("=\"");
            buf.append(jspId++).append("\"\n");

            if (ROOT_ACTION.equals(n.getLocalName()) || body != null
                        || text != null) {
                buf.append(">\n");
                if (ROOT_ACTION.equals(n.getLocalName())) {
                    if (compiler.getCompilationContext().isTagFile()) {
                        appendTagDirective();
                    } else {
                        appendPageDirective();
                    }
                }
                if (body != null) {
                    body.visit(this);
                } else {
                    appendText(text, false);
                }
                buf.append("</" + n.getQName() + ">\n");
            } else {
                buf.append("/>\n");
            }
        }

        /*
         * Appends the page directive with the given attributes to the XML
         * view.
         *
         * Since the import attribute of the page directive is the only page
         * attribute that is allowed to appear multiple times within the same
         * document, and since XML allows only single-value attributes,
         * the values of multiple import attributes must be combined into one,
         * separated by comma.
         *
         * If the given page directive contains just 'contentType' and/or
         * 'pageEncoding' attributes, we ignore it, as we've already appended
         * a page directive containing just these two attributes.
         */
        private void appendPageDirective(Node.PageDirective n) {
            boolean append = false;
            Attributes attrs = n.getAttributes();
            int len = (attrs == null) ? 0 : attrs.getLength();
            for (int i=0; i<len; i++) {
                @SuppressWarnings("null")  // If attrs==null, len == 0
                String attrName = attrs.getQName(i);
                if (!"pageEncoding".equals(attrName)
                        && !"contentType".equals(attrName)) {
                    append = true;
                    break;
                }
            }
            if (!append) {
                return;
            }

            buf.append("<").append(n.getQName());
            buf.append("\n");

            // append jsp:id
            buf.append("  ").append(jspIdPrefix).append(":id").append("=\"");
            buf.append(jspId++).append("\"\n");

            // append remaining attributes
            for (int i=0; i<len; i++) {
                @SuppressWarnings("null")  // If attrs==null, len == 0
                String attrName = attrs.getQName(i);
                if ("import".equals(attrName) || "contentType".equals(attrName)
                        || "pageEncoding".equals(attrName)) {
                    /*
                     * Page directive's 'import' attribute is considered
                     * further down, and its 'pageEncoding' and 'contentType'
                     * attributes are ignored, since we've already appended
                     * a new page directive containing just these two
                     * attributes
                     */
                    continue;
                }
                String value = attrs.getValue(i);
                buf.append("  ").append(attrName).append("=\"");
                buf.append(JspUtil.getExprInXml(value)).append("\"\n");
            }
            if (n.getImports().size() > 0) {
                // Concatenate names of imported classes/packages
                boolean first = true;
                ListIterator<String> iter = n.getImports().listIterator();
                while (iter.hasNext()) {
                    if (first) {
                        first = false;
                        buf.append("  import=\"");
                    } else {
                        buf.append(",");
                    }
                    buf.append(JspUtil.getExprInXml(iter.next()));
                }
                buf.append("\"\n");
            }
            buf.append("/>\n");
        }

        /*
         * Appends a page directive with 'pageEncoding' and 'contentType'
         * attributes.
         *
         * The value of the 'pageEncoding' attribute is hard-coded
         * to UTF-8, whereas the value of the 'contentType' attribute, which
         * is identical to what the container will pass to
         * ServletResponse.setContentType(), is derived from the pageInfo.
         */
        private void appendPageDirective() {
            buf.append("<").append(JSP_PAGE_DIRECTIVE_ACTION);
            buf.append("\n");

            // append jsp:id
            buf.append("  ").append(jspIdPrefix).append(":id").append("=\"");
            buf.append(jspId++).append("\"\n");
            buf.append("  ").append("pageEncoding").append("=\"UTF-8\"\n");
            buf.append("  ").append("contentType").append("=\"");
            buf.append(compiler.getPageInfo().getContentType()).append("\"\n");
            buf.append("/>\n");            
        }

        /*
         * Appends the tag directive with the given attributes to the XML
         * view.
         *
         * If the given tag directive contains just a 'pageEncoding'
         * attributes, we ignore it, as we've already appended
         * a tag directive containing just this attributes.
         */
        private void appendTagDirective(Node.TagDirective n)
                throws JasperException {

            boolean append = false;
            Attributes attrs = n.getAttributes();
            int len = (attrs == null) ? 0 : attrs.getLength();
            for (int i=0; i<len; i++) {
                @SuppressWarnings("null")  // If attrs==null, len == 0
                String attrName = attrs.getQName(i);
                if (!"pageEncoding".equals(attrName)) {
                    append = true;
                    break;
                }
            }
            if (!append) {
                return;
            }

            appendTag(n);
        }

        /*
         * Appends a tag directive containing a single 'pageEncoding'
         * attribute whose value is hard-coded to UTF-8.
         */
        private void appendTagDirective() {
            buf.append("<").append(JSP_TAG_DIRECTIVE_ACTION);
            buf.append("\n");

            // append jsp:id
            buf.append("  ").append(jspIdPrefix).append(":id").append("=\"");
            buf.append(jspId++).append("\"\n");
            buf.append("  ").append("pageEncoding").append("=\"UTF-8\"\n");
            buf.append("/>\n");            
        }

        private void appendText(String text, boolean createJspTextElement) {
            if (createJspTextElement) {
                buf.append("<").append(JSP_TEXT_ACTION);
                buf.append("\n");

                // append jsp:id
                buf.append("  ").append(jspIdPrefix).append(":id").append("=\"");
                buf.append(jspId++).append("\"\n");
                buf.append(">\n");

                appendCDATA(text);
                buf.append(JSP_TEXT_ACTION_END);
                buf.append("\n");
            } else {
                appendCDATA(text);
            }
        }
        
        /*
         * Appends the given text as a CDATA section to the XML view, unless
         * the text has already been marked as CDATA.
         */
        private void appendCDATA(String text) {
            buf.append(CDATA_START_SECTION);
            buf.append(escapeCDATA(text));
            buf.append(CDATA_END_SECTION);
        }

        /*
         * Escapes any occurrences of "]]>" (by replacing them with "]]&gt;")
         * within the given text, so it can be included in a CDATA section.
         */
        private String escapeCDATA(String text) {
            if( text==null ) return "";
            int len = text.length();
            CharArrayWriter result = new CharArrayWriter(len);
            for (int i=0; i<len; i++) {
                if (((i+2) < len)
                        && (text.charAt(i) == ']')
                        && (text.charAt(i+1) == ']')
                        && (text.charAt(i+2) == '>')) {
                    // match found
                    result.write(']');
                    result.write(']');
                    result.write('&');
                    result.write('g');
                    result.write('t');
                    result.write(';');
                    i += 2;
                } else {
                    result.write(text.charAt(i));
                }
            }
            return result.toString();
        }

        /*
         * Appends the attributes of the given Node to the XML view.
         */
        private void printAttributes(Node n, boolean addDefaultNS) {

            /*
             * Append "xmlns" attributes that represent tag libraries
             */
            Attributes attrs = n.getTaglibAttributes();
            int len = (attrs == null) ? 0 : attrs.getLength();
            for (int i=0; i<len; i++) {
                @SuppressWarnings("null")  // If attrs==null, len == 0
                String name = attrs.getQName(i);
                String value = attrs.getValue(i);
                buf.append("  ").append(name).append("=\"").append(value).append("\"\n");
            }

            /*
             * Append "xmlns" attributes that do not represent tag libraries
             */
            attrs = n.getNonTaglibXmlnsAttributes();
            len = (attrs == null) ? 0 : attrs.getLength();
            boolean defaultNSSeen = false;
            for (int i=0; i<len; i++) {
                @SuppressWarnings("null")  // If attrs==null, len == 0
                String name = attrs.getQName(i);
                String value = attrs.getValue(i);
                buf.append("  ").append(name).append("=\"").append(value).append("\"\n");
                defaultNSSeen |= "xmlns".equals(name);
            }
            if (addDefaultNS && !defaultNSSeen) {
                buf.append("  xmlns=\"\"\n");
            }
            resetDefaultNS = false;

            /*
             * Append all other attributes
             */
            attrs = n.getAttributes();
            len = (attrs == null) ? 0 : attrs.getLength();
            for (int i=0; i<len; i++) {
                @SuppressWarnings("null")  // If attrs==null, len == 0
                String name = attrs.getQName(i);
                String value = attrs.getValue(i);
                buf.append("  ").append(name).append("=\"");
                buf.append(JspUtil.getExprInXml(value)).append("\"\n");
            }
        }

        /*
         * Appends XML prolog with encoding declaration.
         */
        private void appendXmlProlog() {
            buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        }
    }
}

