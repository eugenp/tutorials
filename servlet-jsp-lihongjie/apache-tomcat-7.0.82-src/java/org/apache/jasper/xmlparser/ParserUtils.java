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
package org.apache.jasper.xmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.jasper.Constants;
import org.apache.jasper.JasperException;
import org.apache.jasper.compiler.Localizer;
import org.apache.tomcat.util.descriptor.DigesterFactory;
import org.apache.tomcat.util.descriptor.LocalResolver;
import org.apache.tomcat.util.descriptor.XmlErrorHandler;
import org.apache.tomcat.util.security.PrivilegedGetTccl;
import org.apache.tomcat.util.security.PrivilegedSetTccl;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * XML parsing utilities for processing web application deployment
 * descriptor and tag library descriptor files.  FIXME - make these
 * use a separate class loader for the parser to be used.
 *
 * @author Craig R. McClanahan
 */
public class ParserUtils {

    /**
     * An entity resolver for use when parsing XML documents.
     */
    static EntityResolver entityResolver;
    
    private final EntityResolver entityResolverInstance;
    
    private final boolean validating;

    public ParserUtils(boolean validating) {
        this(validating, Constants.IS_SECURITY_ENABLED);
    }

    public ParserUtils(boolean validating, boolean blockExternal) {
        this.validating = validating;
        if (entityResolver == null) {
            this.entityResolverInstance = new LocalResolver(
                    DigesterFactory.SERVLET_API_PUBLIC_IDS,
                    DigesterFactory.SERVLET_API_SYSTEM_IDS, blockExternal);
        } else {
            this.entityResolverInstance = entityResolver;
        }
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Parse the specified XML document, and return a <code>TreeNode</code>
     * that corresponds to the root node of the document tree.
     *
     * @param location Location (eg URI) of the XML document being parsed
     * @param is Input source containing the deployment descriptor
     *
     * @exception JasperException if an input/output error occurs
     * @exception JasperException if a parsing error occurs
     */
    public TreeNode parseXMLDocument(String location, InputSource is)
        throws JasperException {

        Document document = null;

        // Perform an XML parse of this document, via JAXP
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
                        new PrivilegedSetTccl(ParserUtils.class.getClassLoader());
                AccessController.doPrivileged(pa);
            } else {
                Thread.currentThread().setContextClassLoader(
                        ParserUtils.class.getClassLoader());
            }
            
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
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
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(entityResolverInstance);
            XmlErrorHandler handler = new XmlErrorHandler();
            builder.setErrorHandler(handler);
            document = builder.parse(is);
            if (!handler.getErrors().isEmpty()) {
                // throw the first to indicate there was a error during processing
                throw handler.getErrors().iterator().next();
            }
        } catch (ParserConfigurationException ex) {
            throw new JasperException
                (Localizer.getMessage("jsp.error.parse.xml", location), ex);
        } catch (SAXParseException ex) {
            throw new JasperException
                (Localizer.getMessage("jsp.error.parse.xml.line",
                                      location,
                                      Integer.toString(ex.getLineNumber()),
                                      Integer.toString(ex.getColumnNumber())),
                 ex);
        } catch (SAXException sx) {
            throw new JasperException
                (Localizer.getMessage("jsp.error.parse.xml", location), sx);
        } catch (IOException io) {
            throw new JasperException
                (Localizer.getMessage("jsp.error.parse.xml", location), io);
        } finally {
            if (Constants.IS_SECURITY_ENABLED) {
                PrivilegedSetTccl pa = new PrivilegedSetTccl(original);
                AccessController.doPrivileged(pa);
            } else {
                Thread.currentThread().setContextClassLoader(original);
            }
        }

        // Convert the resulting document to a graph of TreeNodes
        return (convert(null, document.getDocumentElement()));
    }


    /**
     * Parse the specified XML document, and return a <code>TreeNode</code>
     * that corresponds to the root node of the document tree.
     *
     * @param uri URI of the XML document being parsed
     * @param is Input stream containing the deployment descriptor
     *
     * @exception JasperException if an input/output error occurs
     * @exception JasperException if a parsing error occurs
     */
    public TreeNode parseXMLDocument(String uri, InputStream is)
            throws JasperException {

        return (parseXMLDocument(uri, new InputSource(is)));
    }

    /**
     * Set the EntityResolver.
     * This is needed when the dtds and Jasper itself are in different
     * classloaders (e.g. OSGi environment).
     *
     * @param er EntityResolver to use.
     */
    public static void setEntityResolver(EntityResolver er) {

        entityResolver = er;
    }

    // ------------------------------------------------------ Protected Methods


    /**
     * Create and return a TreeNode that corresponds to the specified Node,
     * including processing all of the attributes and children nodes.
     *
     * @param parent The parent TreeNode (if any) for the new TreeNode
     * @param node The XML document Node to be converted
     */
    protected TreeNode convert(TreeNode parent, Node node) {

        // Construct a new TreeNode for this node
        TreeNode treeNode = new TreeNode(node.getNodeName(), parent);

        // Convert all attributes of this node
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            int n = attributes.getLength();
            for (int i = 0; i < n; i++) {
                Node attribute = attributes.item(i);
                treeNode.addAttribute(attribute.getNodeName(),
                                      attribute.getNodeValue());
            }
        }

        // Create and attach all children of this node
        NodeList children = node.getChildNodes();
        if (children != null) {
            int n = children.getLength();
            for (int i = 0; i < n; i++) {
                Node child = children.item(i);
                if (child instanceof Comment)
                    continue;
                if (child instanceof Text) {
                    String body = ((Text) child).getData();
                    if (body != null) {
                        body = body.trim();
                        if (body.length() > 0)
                            treeNode.setBody(body);
                    }
                } else {
                    convert(treeNode, child);
                }
            }
        }
        
        // Return the completed TreeNode graph
        return (treeNode);
    }
}
