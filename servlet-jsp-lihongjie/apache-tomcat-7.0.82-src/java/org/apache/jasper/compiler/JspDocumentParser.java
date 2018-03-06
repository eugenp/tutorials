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

import java.io.CharArrayWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarFile;

import javax.servlet.jsp.tagext.TagFileInfo;
import javax.servlet.jsp.tagext.TagInfo;
import javax.servlet.jsp.tagext.TagLibraryInfo;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.jasper.Constants;
import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;
import org.apache.tomcat.util.descriptor.DigesterFactory;
import org.apache.tomcat.util.descriptor.LocalResolver;
import org.apache.tomcat.util.security.PrivilegedGetTccl;
import org.apache.tomcat.util.security.PrivilegedSetTccl;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.ext.EntityResolver2;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Class implementing a parser for a JSP document, that is, a JSP page in XML
 * syntax.
 *
 * @author Jan Luehe
 * @author Kin-man Chung
 */

class JspDocumentParser
    extends DefaultHandler2
    implements TagConstants {

    private static final String LEXICAL_HANDLER_PROPERTY =
        "http://xml.org/sax/properties/lexical-handler";
    private static final String JSP_URI = "http://java.sun.com/JSP/Page";

    private ParserController parserController;
    private JspCompilationContext ctxt;
    private PageInfo pageInfo;
    private String path;
    private StringBuilder charBuffer;

    // Node representing the XML element currently being parsed
    private Node current;

    /*
     * Outermost (in the nesting hierarchy) node whose body is declared to be
     * scriptless. If a node's body is declared to be scriptless, all its
     * nested nodes must be scriptless, too.
     */
    private Node scriptlessBodyNode;

    private Locator locator;

    //Mark representing the start of the current element.  Note
    //that locator.getLineNumber() and locator.getColumnNumber()
    //return the line and column numbers for the character
    //immediately _following_ the current element.  The underlying
    //XMl parser eats white space that is not part of character
    //data, so for Nodes that are not created from character data,
    //this is the best we can do.  But when we parse character data,
    //we get an accurate starting location by starting with startMark
    //as set by the previous element, and updating it as we advance
    //through the characters.
    private Mark startMark;

    // Flag indicating whether we are inside DTD declarations
    private boolean inDTD;

    private boolean isValidating;
    private final EntityResolver2 entityResolver;

    private ErrorDispatcher err;
    private boolean isTagFile;
    private boolean directivesOnly;
    private boolean isTop;

    // Nesting level of Tag dependent bodies
    private int tagDependentNesting = 0;
    // Flag set to delay incrementing tagDependentNesting until jsp:body
    // is first encountered
    private boolean tagDependentPending = false;

    /*
     * Constructor
     */
    public JspDocumentParser(
        ParserController pc,
        String path,
        boolean isTagFile,
        boolean directivesOnly) {
        this.parserController = pc;
        this.ctxt = pc.getJspCompilationContext();
        this.pageInfo = pc.getCompiler().getPageInfo();
        this.err = pc.getCompiler().getErrorDispatcher();
        this.path = path;
        this.isTagFile = isTagFile;
        this.directivesOnly = directivesOnly;
        this.isTop = true;

        String blockExternalString = ctxt.getServletContext().getInitParameter(
                Constants.XML_BLOCK_EXTERNAL_INIT_PARAM);
        boolean blockExternal;
        if (blockExternalString == null) {
            blockExternal = true;
        } else {
            blockExternal = Boolean.parseBoolean(blockExternalString);
        }

        this.entityResolver = new LocalResolver(
                DigesterFactory.SERVLET_API_PUBLIC_IDS,
                DigesterFactory.SERVLET_API_SYSTEM_IDS,
                blockExternal);
    }

    /*
     * Parses a JSP document by responding to SAX events.
     *
     * @throws JasperException
     */
    public static Node.Nodes parse(
        ParserController pc,
        String path,
        JarFile jarFile,
        Node parent,
        boolean isTagFile,
        boolean directivesOnly,
        String pageEnc,
        String jspConfigPageEnc,
        boolean isEncodingSpecifiedInProlog,
        boolean isBomPresent)
        throws JasperException {

        JspDocumentParser jspDocParser =
            new JspDocumentParser(pc, path, isTagFile, directivesOnly);
        Node.Nodes pageNodes = null;

        try {

            // Create dummy root and initialize it with given page encodings
            Node.Root dummyRoot = new Node.Root(null, parent, true);
            dummyRoot.setPageEncoding(pageEnc);
            dummyRoot.setJspConfigPageEncoding(jspConfigPageEnc);
            dummyRoot.setIsEncodingSpecifiedInProlog(
                isEncodingSpecifiedInProlog);
            dummyRoot.setIsBomPresent(isBomPresent);
            jspDocParser.current = dummyRoot;
            if (parent == null) {
                jspDocParser.addInclude(
                    dummyRoot,
                    jspDocParser.pageInfo.getIncludePrelude());
            } else {
                jspDocParser.isTop = false;
            }

            jspDocParser.isValidating = false;

            // Parse the input
            SAXParser saxParser = getSAXParser(false, jspDocParser);
            InputStream inStream = null;
            try {
                inStream = JspUtil.getInputStream(path, jarFile,
                                                  jspDocParser.ctxt,
                                                  jspDocParser.err);
                saxParser.parse(new InputSource(inStream), jspDocParser);
            } catch (EnableDTDValidationException e) {
                saxParser = getSAXParser(true, jspDocParser);
                jspDocParser.isValidating = true;
                try {
                    inStream.close();
                } catch (Exception any) {
                }
                inStream = JspUtil.getInputStream(path, jarFile,
                                                  jspDocParser.ctxt,
                                                  jspDocParser.err);
                saxParser.parse(new InputSource(inStream), jspDocParser);
            } finally {
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (Exception any) {
                    }
                }
            }

            if (parent == null) {
                jspDocParser.addInclude(
                    dummyRoot,
                    jspDocParser.pageInfo.getIncludeCoda());
            }

            // Create Node.Nodes from dummy root
            pageNodes = new Node.Nodes(dummyRoot);

        } catch (IOException ioe) {
            jspDocParser.err.jspError(ioe, "jsp.error.data.file.read", path);
        } catch (SAXParseException e) {
            jspDocParser.err.jspError
                (new Mark(jspDocParser.ctxt, path, e.getLineNumber(),
                          e.getColumnNumber()),
                e, e.getMessage());
        } catch (Exception e) {
            jspDocParser.err.jspError(e, "jsp.error.data.file.processing", path);
        }

        return pageNodes;
    }

    /*
     * Processes the given list of included files.
     *
     * This is used to implement the include-prelude and include-coda
     * subelements of the jsp-config element in web.xml
     */
    private void addInclude(Node parent, List<String> files) throws SAXException {
        if (files != null) {
            Iterator<String> iter = files.iterator();
            while (iter.hasNext()) {
                String file = iter.next();
                AttributesImpl attrs = new AttributesImpl();
                attrs.addAttribute("", "file", "file", "CDATA", file);

                // Create a dummy Include directive node
                    Node includeDir =
                        new Node.IncludeDirective(attrs, null, // XXX
    parent);
                processIncludeDirective(file, includeDir);
            }
        }
    }

    
    @Override
    public InputSource getExternalSubset(String name, String baseURI)
            throws SAXException, IOException {
        return entityResolver.getExternalSubset(name, baseURI);
    }

    
    
    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException {
        return entityResolver.resolveEntity(publicId, systemId);
    }

    
    @Override
    public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId)
            throws SAXException, IOException {
        return entityResolver.resolveEntity(name, publicId, baseURI, systemId);
    }

    
    /*
     * Receives notification of the start of an element.
     *
     * This method assigns the given tag attributes to one of 3 buckets:
     *
     * - "xmlns" attributes that represent (standard or custom) tag libraries.
     * - "xmlns" attributes that do not represent tag libraries.
     * - all remaining attributes.
     *
     * For each "xmlns" attribute that represents a custom tag library, the
     * corresponding TagLibraryInfo object is added to the set of custom
     * tag libraries.
     */
    @Override
    public void startElement(
        String uri,
        String localName,
        String qName,
        Attributes attrs)
        throws SAXException {

        AttributesImpl taglibAttrs = null;
        AttributesImpl nonTaglibAttrs = null;
        AttributesImpl nonTaglibXmlnsAttrs = null;

        processChars();

        checkPrefixes(uri, qName, attrs);

        if (directivesOnly &&
            !(JSP_URI.equals(uri) && localName.startsWith(DIRECTIVE_ACTION))) {
            return;
        }

        // jsp:text must not have any subelements
        if (current instanceof Node.JspText) {
            throw new SAXParseException(
                Localizer.getMessage("jsp.error.text.has_subelement"),
                locator);
        }

        startMark = new Mark(ctxt, path, locator.getLineNumber(),
                             locator.getColumnNumber());

        /*
         * Notice that due to a bug in the underlying SAX parser, the
         * attributes must be enumerated in descending order.
         */
        boolean isTaglib = false;
        for (int i = attrs.getLength() - 1; i >= 0; i--) {
            isTaglib = false;
            String attrQName = attrs.getQName(i);
            if (!attrQName.startsWith("xmlns")) {
                if (nonTaglibAttrs == null) {
                    nonTaglibAttrs = new AttributesImpl();
                }
                nonTaglibAttrs.addAttribute(
                    attrs.getURI(i),
                    attrs.getLocalName(i),
                    attrs.getQName(i),
                    attrs.getType(i),
                    attrs.getValue(i));
            } else {
                if (attrQName.startsWith("xmlns:jsp")) {
                    isTaglib = true;
                } else {
                    String attrUri = attrs.getValue(i);
                    // TaglibInfo for this uri already established in
                    // startPrefixMapping
                    isTaglib = pageInfo.hasTaglib(attrUri);
                }
                if (isTaglib) {
                    if (taglibAttrs == null) {
                        taglibAttrs = new AttributesImpl();
                    }
                    taglibAttrs.addAttribute(
                        attrs.getURI(i),
                        attrs.getLocalName(i),
                        attrs.getQName(i),
                        attrs.getType(i),
                        attrs.getValue(i));
                } else {
                    if (nonTaglibXmlnsAttrs == null) {
                        nonTaglibXmlnsAttrs = new AttributesImpl();
                    }
                    nonTaglibXmlnsAttrs.addAttribute(
                        attrs.getURI(i),
                        attrs.getLocalName(i),
                        attrs.getQName(i),
                        attrs.getType(i),
                        attrs.getValue(i));
                }
            }
        }

        Node node = null;

        if (tagDependentPending && JSP_URI.equals(uri) &&
                     localName.equals(BODY_ACTION)) {
            tagDependentPending = false;
            tagDependentNesting++;
            current =
                parseStandardAction(
                    qName,
                    localName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    startMark);
            return;
        }

        if (tagDependentPending && JSP_URI.equals(uri) &&
                     localName.equals(ATTRIBUTE_ACTION)) {
            current =
                parseStandardAction(
                    qName,
                    localName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    startMark);
            return;
        }

        if (tagDependentPending) {
            tagDependentPending = false;
            tagDependentNesting++;
        }

        if (tagDependentNesting > 0) {
            node =
                new Node.UninterpretedTag(
                    qName,
                    localName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    startMark,
                    current);
        } else if (JSP_URI.equals(uri)) {
            node =
                parseStandardAction(
                    qName,
                    localName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    startMark);
        } else {
            node =
                parseCustomAction(
                    qName,
                    localName,
                    uri,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    startMark,
                    current);
            if (node == null) {
                node =
                    new Node.UninterpretedTag(
                        qName,
                        localName,
                        nonTaglibAttrs,
                        nonTaglibXmlnsAttrs,
                        taglibAttrs,
                        startMark,
                        current);
            } else {
                // custom action
                String bodyType = getBodyType((Node.CustomTag) node);

                if (scriptlessBodyNode == null
                        && bodyType.equalsIgnoreCase(TagInfo.BODY_CONTENT_SCRIPTLESS)) {
                    scriptlessBodyNode = node;
                }
                else if (TagInfo.BODY_CONTENT_TAG_DEPENDENT.equalsIgnoreCase(bodyType)) {
                    tagDependentPending = true;
                }
            }
        }

        current = node;
    }

    /*
     * Receives notification of character data inside an element.
     *
     * The SAX does not call this method with all of the template text, but may
     * invoke this method with chunks of it.  This is a problem when we try
     * to determine if the text contains only whitespaces, or when we are
     * looking for an EL expression string.  Therefore it is necessary to
     * buffer and concatenate the chunks and process the concatenated text
     * later (at beginTag and endTag)
     *
     * @param buf The characters
     * @param offset The start position in the character array
     * @param len The number of characters to use from the character array
     *
     * @throws SAXException
     */
    @Override
    public void characters(char[] buf, int offset, int len) {

        if (charBuffer == null) {
            charBuffer = new StringBuilder();
        }
        charBuffer.append(buf, offset, len);
    }

    private void processChars() throws SAXException {

        if (charBuffer == null || directivesOnly) {
            return;
        }

        /*
         * JSP.6.1.1: All textual nodes that have only white space are to be
         * dropped from the document, except for nodes in a jsp:text element,
         * and any leading and trailing white-space-only textual nodes in a
         * jsp:attribute whose 'trim' attribute is set to FALSE, which are to
         * be kept verbatim.
         * JSP.6.2.3 defines white space characters.
         */
        boolean isAllSpace = true;
        if (!(current instanceof Node.JspText)
            && !(current instanceof Node.NamedAttribute)) {
            for (int i = 0; i < charBuffer.length(); i++) {
                if (!(charBuffer.charAt(i) == ' '
                    || charBuffer.charAt(i) == '\n'
                    || charBuffer.charAt(i) == '\r'
                    || charBuffer.charAt(i) == '\t')) {
                    isAllSpace = false;
                    break;
                }
            }
        }

        if (!isAllSpace && tagDependentPending) {
            tagDependentPending = false;
            tagDependentNesting++;
        }

        if (tagDependentNesting > 0 || pageInfo.isELIgnored() ||
                current instanceof Node.ScriptingElement) {
            if (charBuffer.length() > 0) {
                new Node.TemplateText(charBuffer.toString(), startMark, current);
            }
            startMark = new Mark(ctxt, path, locator.getLineNumber(),
                                 locator.getColumnNumber());
            charBuffer = null;
            return;
        }

        if ((current instanceof Node.JspText)
            || (current instanceof Node.NamedAttribute)
            || !isAllSpace) {

            int line = startMark.getLineNumber();
            int column = startMark.getColumnNumber();

            CharArrayWriter ttext = new CharArrayWriter();
            int lastCh = 0, elType = 0;
            for (int i = 0; i < charBuffer.length(); i++) {

                int ch = charBuffer.charAt(i);
                if (ch == '\n') {
                    column = 1;
                    line++;
                } else {
                    column++;
                }
                if ((lastCh == '$' || lastCh == '#') && ch == '{') {
                    elType = lastCh;
                    if (ttext.size() > 0) {
                        new Node.TemplateText(
                            ttext.toString(),
                            startMark,
                            current);
                        ttext = new CharArrayWriter();
                        //We subtract two from the column number to
                        //account for the '[$,#]{' that we've already parsed
                        startMark = new Mark(ctxt, path, line, column - 2);
                    }
                    // following "${" || "#{" to first unquoted "}"
                    i++;
                    boolean singleQ = false;
                    boolean doubleQ = false;
                    lastCh = 0;
                    for (;; i++) {
                        if (i >= charBuffer.length()) {
                            throw new SAXParseException(
                                Localizer.getMessage(
                                    "jsp.error.unterminated",
                                    (char) elType + "{"),
                                locator);

                        }
                        ch = charBuffer.charAt(i);
                        if (ch == '\n') {
                            column = 1;
                            line++;
                        } else {
                            column++;
                        }
                        if (lastCh == '\\' && (singleQ || doubleQ)) {
                            ttext.write(ch);
                            lastCh = 0;
                            continue;
                        }
                        if (ch == '}') {
                            new Node.ELExpression((char) elType,
                                ttext.toString(),
                                startMark,
                                current);
                            ttext = new CharArrayWriter();
                            startMark = new Mark(ctxt, path, line, column);
                            break;
                        }
                        if (ch == '"')
                            doubleQ = !doubleQ;
                        else if (ch == '\'')
                            singleQ = !singleQ;

                        ttext.write(ch);
                        lastCh = ch;
                    }
                } else if (lastCh == '\\' && (ch == '$' || ch == '#')) {
                    if (pageInfo.isELIgnored()) {
                        ttext.write('\\');
                    }
                    ttext.write(ch);
                    ch = 0;  // Not start of EL anymore
                } else {
                    if (lastCh == '$' || lastCh == '#' || lastCh == '\\') {
                        ttext.write(lastCh);
                    }
                    if (ch != '$' && ch != '#' && ch != '\\') {
                        ttext.write(ch);
                    }
                }
                lastCh = ch;
            }
            if (lastCh == '$' || lastCh == '#' || lastCh == '\\') {
                ttext.write(lastCh);
            }
            if (ttext.size() > 0) {
                new Node.TemplateText(ttext.toString(), startMark, current);
            }
        }
        startMark = new Mark(ctxt, path, locator.getLineNumber(),
                             locator.getColumnNumber());

        charBuffer = null;
    }

    /*
     * Receives notification of the end of an element.
     */
    @Override
    public void endElement(String uri, String localName, String qName)
        throws SAXException {

        processChars();

        if (directivesOnly &&
            !(JSP_URI.equals(uri) && localName.startsWith(DIRECTIVE_ACTION))) {
            return;
        }

        if (current instanceof Node.NamedAttribute) {
            boolean isTrim = ((Node.NamedAttribute)current).isTrim();
            Node.Nodes subElems = ((Node.NamedAttribute)current).getBody();
            for (int i = 0; subElems != null && i < subElems.size(); i++) {
                Node subElem = subElems.getNode(i);
                if (!(subElem instanceof Node.TemplateText)) {
                    continue;
                }
                // Ignore any whitespace (including spaces, carriage returns,
                // line feeds, and tabs, that appear at the beginning and at
                // the end of the body of the <jsp:attribute> action, if the
                // action's 'trim' attribute is set to TRUE (default).
                // In addition, any textual nodes in the <jsp:attribute> that
                // have only white space are dropped from the document, with
                // the exception of leading and trailing white-space-only
                // textual nodes in a <jsp:attribute> whose 'trim' attribute
                // is set to FALSE, which must be kept verbatim.
                if (i == 0) {
                    if (isTrim) {
                        ((Node.TemplateText)subElem).ltrim();
                    }
                } else if (i == subElems.size() - 1) {
                    if (isTrim) {
                        ((Node.TemplateText)subElem).rtrim();
                    }
                } else {
                    if (((Node.TemplateText)subElem).isAllSpace()) {
                        subElems.remove(subElem);
                    }
                }
            }
        } else if (current instanceof Node.ScriptingElement) {
            checkScriptingBody((Node.ScriptingElement)current);
        }

        if ( isTagDependent(current)) {
            tagDependentNesting--;
        }

        if (scriptlessBodyNode != null
                && current.equals(scriptlessBodyNode)) {
            scriptlessBodyNode = null;
        }

        if (current instanceof Node.CustomTag) {
            String bodyType = getBodyType((Node.CustomTag) current);
            if (TagInfo.BODY_CONTENT_EMPTY.equalsIgnoreCase(bodyType)) {
                // Children - if any - must be JSP attributes
                Node.Nodes children = current.getBody();
                if (children != null && children.size() > 0) {
                    for (int i = 0; i < children.size(); i++) {
                        Node child = children.getNode(i);
                        if (!(child instanceof Node.NamedAttribute)) {
                            throw new SAXParseException(Localizer.getMessage(
                                    "jasper.error.emptybodycontent.nonempty",
                                    current.qName), locator);
                        }
                    }
                }
            }
        }
        if (current.getParent() != null) {
            current = current.getParent();
        }
    }

    /*
     * Receives the document locator.
     *
     * @param locator the document locator
     */
    @Override
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }

    /*
     * See org.xml.sax.ext.LexicalHandler.
     */
    @Override
    public void comment(char[] buf, int offset, int len) throws SAXException {

        processChars();  // Flush char buffer and remove white spaces

        // ignore comments in the DTD
        if (!inDTD) {
            startMark =
                new Mark(
                    ctxt,
                    path,
                    locator.getLineNumber(),
                    locator.getColumnNumber());
            new Node.Comment(new String(buf, offset, len), startMark, current);
        }
    }

    /*
     * See org.xml.sax.ext.LexicalHandler.
     */
    @Override
    public void startCDATA() throws SAXException {

        processChars();  // Flush char buffer and remove white spaces
        startMark = new Mark(ctxt, path, locator.getLineNumber(),
                             locator.getColumnNumber());
    }

    /*
     * See org.xml.sax.ext.LexicalHandler.
     */
    @Override
    public void endCDATA() throws SAXException {
        processChars();  // Flush char buffer and remove white spaces
    }

    /*
     * See org.xml.sax.ext.LexicalHandler.
     */
    @Override
    public void startEntity(String name) throws SAXException {
        // do nothing
    }

    /*
     * See org.xml.sax.ext.LexicalHandler.
     */
    @Override
    public void endEntity(String name) throws SAXException {
        // do nothing
    }

    /*
     * See org.xml.sax.ext.LexicalHandler.
     */
    @Override
    public void startDTD(String name, String publicId, String systemId)
        throws SAXException {
        if (!isValidating) {
            fatalError(new EnableDTDValidationException(
                    "jsp.error.enable_dtd_validation", null));
        }

        inDTD = true;
    }

    /*
     * See org.xml.sax.ext.LexicalHandler.
     */
    @Override
    public void endDTD() throws SAXException {
        inDTD = false;
    }

    /*
     * Receives notification of a non-recoverable error.
     */
    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        throw e;
    }

    /*
     * Receives notification of a recoverable error.
     */
    @Override
    public void error(SAXParseException e) throws SAXException {
        throw e;
    }

    /*
     * Receives notification of the start of a Namespace mapping.
     */
    @Override
    public void startPrefixMapping(String prefix, String uri)
        throws SAXException {
        TagLibraryInfo taglibInfo;

        if (directivesOnly && !(JSP_URI.equals(uri))) {
            return;
        }

        try {
            taglibInfo = getTaglibInfo(prefix, uri);
        } catch (JasperException je) {
            throw new SAXParseException(
                Localizer.getMessage("jsp.error.could.not.add.taglibraries"),
                locator,
                je);
        }

        if (taglibInfo != null) {
            if (pageInfo.getTaglib(uri) == null) {
                pageInfo.addTaglib(uri, taglibInfo);
            }
            pageInfo.pushPrefixMapping(prefix, uri);
        } else {
            pageInfo.pushPrefixMapping(prefix, null);
        }
    }

    /*
     * Receives notification of the end of a Namespace mapping.
     */
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {

        if (directivesOnly) {
            String uri = pageInfo.getURI(prefix);
            if (!JSP_URI.equals(uri)) {
                return;
            }
        }

        pageInfo.popPrefixMapping(prefix);
    }

    //*********************************************************************
    // Private utility methods

    private Node parseStandardAction(
        String qName,
        String localName,
        Attributes nonTaglibAttrs,
        Attributes nonTaglibXmlnsAttrs,
        Attributes taglibAttrs,
        Mark start)
        throws SAXException {

        Node node = null;

        if (localName.equals(ROOT_ACTION)) {
            if (!(current instanceof Node.Root)) {
                throw new SAXParseException(
                    Localizer.getMessage("jsp.error.nested_jsproot"),
                    locator);
            }
            node =
                new Node.JspRoot(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
            if (isTop) {
                pageInfo.setHasJspRoot(true);
            }
        } else if (localName.equals(PAGE_DIRECTIVE_ACTION)) {
            if (isTagFile) {
                throw new SAXParseException(
                    Localizer.getMessage(
                        "jsp.error.action.istagfile",
                        localName),
                    locator);
            }
            node =
                new Node.PageDirective(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
            String imports = nonTaglibAttrs.getValue("import");
            // There can only be one 'import' attribute per page directive
            if (imports != null) {
                ((Node.PageDirective)node).addImport(imports);
            }
        } else if (localName.equals(INCLUDE_DIRECTIVE_ACTION)) {
            node =
                new Node.IncludeDirective(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
            processIncludeDirective(nonTaglibAttrs.getValue("file"), node);
        } else if (localName.equals(DECLARATION_ACTION)) {
            if (scriptlessBodyNode != null) {
                // We're nested inside a node whose body is
                // declared to be scriptless
                throw new SAXParseException(
                    Localizer.getMessage(
                        "jsp.error.no.scriptlets",
                        localName),
                    locator);
            }
            node =
                new Node.Declaration(
                    qName,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(SCRIPTLET_ACTION)) {
            if (scriptlessBodyNode != null) {
                // We're nested inside a node whose body is
                // declared to be scriptless
                throw new SAXParseException(
                    Localizer.getMessage(
                        "jsp.error.no.scriptlets",
                        localName),
                    locator);
            }
            node =
                new Node.Scriptlet(
                    qName,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(EXPRESSION_ACTION)) {
            if (scriptlessBodyNode != null) {
                // We're nested inside a node whose body is
                // declared to be scriptless
                throw new SAXParseException(
                    Localizer.getMessage(
                        "jsp.error.no.scriptlets",
                        localName),
                    locator);
            }
            node =
                new Node.Expression(
                    qName,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(USE_BEAN_ACTION)) {
            node =
                new Node.UseBean(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(SET_PROPERTY_ACTION)) {
            node =
                new Node.SetProperty(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(GET_PROPERTY_ACTION)) {
            node =
                new Node.GetProperty(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(INCLUDE_ACTION)) {
            node =
                new Node.IncludeAction(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(FORWARD_ACTION)) {
            node =
                new Node.ForwardAction(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(PARAM_ACTION)) {
            node =
                new Node.ParamAction(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(PARAMS_ACTION)) {
            node =
                new Node.ParamsAction(
                    qName,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(PLUGIN_ACTION)) {
            node =
                new Node.PlugIn(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(TEXT_ACTION)) {
            node =
                new Node.JspText(
                    qName,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(BODY_ACTION)) {
            node =
                new Node.JspBody(
                    qName,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(ATTRIBUTE_ACTION)) {
            node =
                new Node.NamedAttribute(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(OUTPUT_ACTION)) {
            node =
                new Node.JspOutput(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(TAG_DIRECTIVE_ACTION)) {
            if (!isTagFile) {
                throw new SAXParseException(
                    Localizer.getMessage(
                        "jsp.error.action.isnottagfile",
                        localName),
                    locator);
            }
            node =
                new Node.TagDirective(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
            String imports = nonTaglibAttrs.getValue("import");
            // There can only be one 'import' attribute per tag directive
            if (imports != null) {
                ((Node.TagDirective)node).addImport(imports);
            }
        } else if (localName.equals(ATTRIBUTE_DIRECTIVE_ACTION)) {
            if (!isTagFile) {
                throw new SAXParseException(
                    Localizer.getMessage(
                        "jsp.error.action.isnottagfile",
                        localName),
                    locator);
            }
            node =
                new Node.AttributeDirective(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(VARIABLE_DIRECTIVE_ACTION)) {
            if (!isTagFile) {
                throw new SAXParseException(
                    Localizer.getMessage(
                        "jsp.error.action.isnottagfile",
                        localName),
                    locator);
            }
            node =
                new Node.VariableDirective(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(INVOKE_ACTION)) {
            if (!isTagFile) {
                throw new SAXParseException(
                    Localizer.getMessage(
                        "jsp.error.action.isnottagfile",
                        localName),
                    locator);
            }
            node =
                new Node.InvokeAction(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(DOBODY_ACTION)) {
            if (!isTagFile) {
                throw new SAXParseException(
                    Localizer.getMessage(
                        "jsp.error.action.isnottagfile",
                        localName),
                    locator);
            }
            node =
                new Node.DoBodyAction(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(ELEMENT_ACTION)) {
            node =
                new Node.JspElement(
                    qName,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else if (localName.equals(FALLBACK_ACTION)) {
            node =
                new Node.FallBackAction(
                    qName,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    current);
        } else {
            throw new SAXParseException(
                Localizer.getMessage(
                    "jsp.error.xml.badStandardAction",
                    localName),
                locator);
        }

        return node;
    }

    /*
     * Checks if the XML element with the given tag name is a custom action,
     * and returns the corresponding Node object.
     */
    private Node parseCustomAction(
        String qName,
        String localName,
        String uri,
        Attributes nonTaglibAttrs,
        Attributes nonTaglibXmlnsAttrs,
        Attributes taglibAttrs,
        Mark start,
        Node parent)
        throws SAXException {

        // Check if this is a user-defined (custom) tag
        TagLibraryInfo tagLibInfo = pageInfo.getTaglib(uri);
        if (tagLibInfo == null) {
            return null;
        }

        TagInfo tagInfo = tagLibInfo.getTag(localName);
        TagFileInfo tagFileInfo = tagLibInfo.getTagFile(localName);
        if (tagInfo == null && tagFileInfo == null) {
            throw new SAXParseException(
                Localizer.getMessage("jsp.error.xml.bad_tag", localName, uri),
                locator);
        }
        Class<?> tagHandlerClass = null;
        if (tagInfo != null) {
            String handlerClassName = tagInfo.getTagClassName();
            try {
                tagHandlerClass =
                    ctxt.getClassLoader().loadClass(handlerClassName);
            } catch (Exception e) {
                throw new SAXParseException(
                    Localizer.getMessage("jsp.error.loadclass.taghandler",
                                         handlerClassName,
                                         qName),
                    locator, e);
            }
        }

        String prefix = getPrefix(qName);

        Node.CustomTag ret = null;
        if (tagInfo != null) {
            ret =
                new Node.CustomTag(
                    qName,
                    prefix,
                    localName,
                    uri,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    parent,
                    tagInfo,
                    tagHandlerClass);
        } else {
            ret =
                new Node.CustomTag(
                    qName,
                    prefix,
                    localName,
                    uri,
                    nonTaglibAttrs,
                    nonTaglibXmlnsAttrs,
                    taglibAttrs,
                    start,
                    parent,
                    tagFileInfo);
        }

        return ret;
    }

    /*
     * Creates the tag library associated with the given uri namespace, and
     * returns it.
     *
     * @param prefix The prefix of the xmlns attribute
     * @param uri The uri namespace (value of the xmlns attribute)
     *
     * @return The tag library associated with the given uri namespace
     */
    private TagLibraryInfo getTaglibInfo(String prefix, String uri)
        throws JasperException {

        TagLibraryInfo result = null;

        if (uri.startsWith(URN_JSPTAGDIR)) {
            // uri (of the form "urn:jsptagdir:path") references tag file dir
            String tagdir = uri.substring(URN_JSPTAGDIR.length());
            result =
                new ImplicitTagLibraryInfo(
                    ctxt,
                    parserController,
                    pageInfo,
                    prefix,
                    tagdir,
                    err);
        } else {
            // uri references TLD file
            boolean isPlainUri = false;
            if (uri.startsWith(URN_JSPTLD)) {
                // uri is of the form "urn:jsptld:path"
                uri = uri.substring(URN_JSPTLD.length());
            } else {
                isPlainUri = true;
            }

            TldLocation location = ctxt.getTldLocation(uri);
            if (location != null || !isPlainUri) {
                if (ctxt.getOptions().isCaching()) {
                    result = ctxt.getOptions().getCache().get(uri);
                }
                if (result == null) {
                    /*
                     * If the uri value is a plain uri, a translation error must
                     * not be generated if the uri is not found in the taglib map.
                     * Instead, any actions in the namespace defined by the uri
                     * value must be treated as uninterpreted.
                     */
                    result =
                        new TagLibraryInfoImpl(
                            ctxt,
                            parserController,
                            pageInfo,
                            prefix,
                            uri,
                            location,
                            err,
                            null);
                    if (ctxt.getOptions().isCaching()) {
                        ctxt.getOptions().getCache().put(uri, result);
                    }
                }
            }
        }

        return result;
    }

    /*
     * Ensures that the given body only contains nodes that are instances of
     * TemplateText.
     *
     * This check is performed only for the body of a scripting (that is:
     * declaration, scriptlet, or expression) element, after the end tag of a
     * scripting element has been reached.
     */
    private void checkScriptingBody(Node.ScriptingElement scriptingElem)
        throws SAXException {
        Node.Nodes body = scriptingElem.getBody();
        if (body != null) {
            int size = body.size();
            for (int i = 0; i < size; i++) {
                Node n = body.getNode(i);
                if (!(n instanceof Node.TemplateText)) {
                    String elemType = SCRIPTLET_ACTION;
                    if (scriptingElem instanceof Node.Declaration)
                        elemType = DECLARATION_ACTION;
                    if (scriptingElem instanceof Node.Expression)
                        elemType = EXPRESSION_ACTION;
                    String msg =
                        Localizer.getMessage(
                            "jsp.error.parse.xml.scripting.invalid.body",
                            elemType);
                    throw new SAXParseException(msg, locator);
                }
            }
        }
    }

    /*
     * Parses the given file included via an include directive.
     *
     * @param fname The path to the included resource, as specified by the
     * 'file' attribute of the include directive
     * @param parent The Node representing the include directive
     */
    private void processIncludeDirective(String fname, Node parent)
        throws SAXException {

        if (fname == null) {
            return;
        }

        try {
            parserController.parse(fname, parent, null);
        } catch (FileNotFoundException fnfe) {
            throw new SAXParseException(
                Localizer.getMessage("jsp.error.file.not.found", fname),
                locator,
                fnfe);
        } catch (Exception e) {
            throw new SAXParseException(e.getMessage(), locator, e);
        }
    }

    /*
     * Checks an element's given URI, qname, and attributes to see if any
     * of them hijack the 'jsp' prefix, that is, bind it to a namespace other
     * than http://java.sun.com/JSP/Page.
     *
     * @param uri The element's URI
     * @param qName The element's qname
     * @param attrs The element's attributes
     */
    private void checkPrefixes(String uri, String qName, Attributes attrs) {

        checkPrefix(uri, qName);

        int len = attrs.getLength();
        for (int i = 0; i < len; i++) {
            checkPrefix(attrs.getURI(i), attrs.getQName(i));
        }
    }

    /*
     * Checks the given URI and qname to see if they hijack the 'jsp' prefix,
     * which would be the case if qName contained the 'jsp' prefix and
     * uri was different from http://java.sun.com/JSP/Page.
     *
     * @param uri The URI to check
     * @param qName The qname to check
     */
    private void checkPrefix(String uri, String qName) {

        String prefix = getPrefix(qName);
        if (prefix.length() > 0) {
            pageInfo.addPrefix(prefix);
            if ("jsp".equals(prefix) && !JSP_URI.equals(uri)) {
                pageInfo.setIsJspPrefixHijacked(true);
            }
        }
    }

    private String getPrefix(String qName) {
        int index = qName.indexOf(':');
        if (index != -1) {
            return qName.substring(0, index);
        }
        return "";
    }

    /*
     * Gets SAXParser.
     *
     * @param validating Indicates whether the requested SAXParser should
     * be validating
     * @param jspDocParser The JSP document parser
     *
     * @return The SAXParser
     */
    private static SAXParser getSAXParser(
        boolean validating,
        JspDocumentParser jspDocParser)
        throws Exception {

        ClassLoader original;
        if (Constants.IS_SECURITY_ENABLED) {
            PrivilegedGetTccl pa = new PrivilegedGetTccl();
            original = AccessController.doPrivileged(pa);
        } else {
            original = Thread.currentThread().getContextClassLoader();
        }
        try {
            if (Constants.IS_SECURITY_ENABLED) {
                PrivilegedSetTccl pa =
                        new PrivilegedSetTccl(JspDocumentParser.class.getClassLoader());
                AccessController.doPrivileged(pa);
            } else {
                Thread.currentThread().setContextClassLoader(
                        JspDocumentParser.class.getClassLoader());
            }

            SAXParserFactory factory = SAXParserFactory.newInstance();

            factory.setNamespaceAware(true);
            // Preserve xmlns attributes
            factory.setFeature(
                "http://xml.org/sax/features/namespace-prefixes",
                true);

            factory.setValidating(validating);
            if (validating) {
                // Enable DTD validation
                factory.setFeature(
                        "http://xml.org/sax/features/validation",
                        true);
                // Enable schema validation
                factory.setFeature(
                        "http://apache.org/xml/features/validation/schema",
                        true);
            }

            // Configure the parser
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setProperty(LEXICAL_HANDLER_PROPERTY, jspDocParser);
            xmlReader.setErrorHandler(jspDocParser);

            return saxParser;
        } finally {
            if (Constants.IS_SECURITY_ENABLED) {
                PrivilegedSetTccl pa = new PrivilegedSetTccl(original);
                AccessController.doPrivileged(pa);
            } else {
                Thread.currentThread().setContextClassLoader(original);
            }
        }
    }

    /*
     * Exception indicating that a DOCTYPE declaration is present, but
     * validation is turned off.
     */
    private static class EnableDTDValidationException
            extends SAXParseException {

        private static final long serialVersionUID = 1L;

        EnableDTDValidationException(String message, Locator loc) {
            super(message, loc);
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            // This class does not provide a stack trace
            return this;
        }
    }

    private static String getBodyType(Node.CustomTag custom) {

        if (custom.getTagInfo() != null) {
            return custom.getTagInfo().getBodyContent();
        }

        return custom.getTagFileInfo().getTagInfo().getBodyContent();
    }

    private boolean isTagDependent(Node n) {

        if (n instanceof Node.CustomTag) {
            String bodyType = getBodyType((Node.CustomTag) n);
            return
                TagInfo.BODY_CONTENT_TAG_DEPENDENT.equalsIgnoreCase(bodyType);
        }
        return false;
    }
}
