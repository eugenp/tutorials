package com.baeldung.xmlhtml.helpers.jaxp;

import org.w3c.dom.Document;
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

/**
 * @author Adam In Tae Gerard
 *
 * All JAXP and SAX Parser logic in here :)
 */

public class JAXPHelper {

    /**
     * Print to console.
     */

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

    /**
     * SAX Parser Helper
     *
     * Unmarshall into POJO.
     */

    public static void saxParser() {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);

        try {

            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new CustomHandler());
            xmlReader.parse(JAXP_FILE_IN);

        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }
    }

     /**
     * Read then write from document.
     */

    public static void documentBuilder() {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File(JAXP_FILE_IN));

            print(document);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            tFactory
                    .newTransformer()
                    .transform(new DOMSource(document), new StreamResult(new File(JAXP_FILE_OUT)));

        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }

    }

}
