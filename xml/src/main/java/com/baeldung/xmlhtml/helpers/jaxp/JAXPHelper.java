package com.baeldung.xmlhtml.helpers.jaxp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.XMLReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

import static com.baeldung.xmlhtml.Constants.*;

public class JAXPHelper {

    private static void print(Document document) {
        NodeList list = document.getChildNodes();
        try {
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                String message =
                        node.getNodeType()
                                + WHITE_SPACE
                                + node.getNodeName()
                                + LINE_BREAK;
                System.out.println(message);
            }
        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }
    }

    public static void saxParser() {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        try {
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new CustomHandler());
            xmlReader.parse(XML_FILE_IN);
        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }
    }

    public static void documentBuilder() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document parsed = db.parse(new File(XML_FILE_IN));
            Element xml = parsed.getDocumentElement();

            Document doc = db.newDocument();
            Element html = doc.createElement("html");
            Element head = doc.createElement("head");
            html.appendChild(head);

            Element body = doc.createElement("body");
            Element descendantOne = doc.createElement("p");
            descendantOne.setTextContent("descendantOne: " +
                    xml.getElementsByTagName("descendantOne")
                            .item(0).getTextContent());
            Element descendantThree = doc.createElement("p");
            descendantThree.setTextContent("descendantThree: " +
                    xml.getElementsByTagName("descendantThree")
                            .item(0).getTextContent());
            Element nested = doc.createElement("div");
            nested.appendChild(descendantThree);

            body.appendChild(descendantOne);
            body.appendChild(nested);
            html.appendChild(body);
            doc.appendChild(html);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            tFactory
                    .newTransformer()
                    .transform(new DOMSource(doc), new StreamResult(new File(JAXP_FILE_OUT)));

        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }
    }
}
