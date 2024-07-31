package com.baeldung.xml.prettyprint;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class XmlPrettyPrinter {

    public static String prettyPrintByTransformer(String xmlString, int indent, boolean ignoreDeclaration) {

        try {
            final InputSource src = new InputSource(new StringReader(xmlString));
            final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(new StringReader(readPrettyPrintXslt())));
            // Using the default transformer will create empty lines in Java9+
//         Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, ignoreDeclaration ? "yes" : "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // Alternatively, we can set indent-size on the transformer object
            // transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));
            Writer out = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(out));
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error occurs when pretty-printing xml:\n" + xmlString, e);
        }
    }


    public static String prettyPrintByDom4j(String xmlString, int indent, boolean skipDeclaration) {
        try {
            final OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            format.setIndentSize(indent);
            format.setSuppressDeclaration(skipDeclaration);

            final org.dom4j.Document document = DocumentHelper.parseText(xmlString);
            final StringWriter sw = new StringWriter();
            final XMLWriter writer = new XMLWriter(sw, format);
            writer.write(document);
            return sw.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error occurs when pretty-printing xml:\n" + xmlString, e);
        }
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = XmlPrettyPrinter.class.getResourceAsStream("/xml/emails.xml");
        String xmlString = readFromInputStream(inputStream);
        System.out.println("Pretty printing by Transformer");
        System.out.println("=============================================");
        System.out.println(prettyPrintByTransformer(xmlString, 2, true));
        System.out.println("=============================================");
        System.out.println("Pretty printing by Dom4j");
        System.out.println("=============================================");
        System.out.println(prettyPrintByDom4j(xmlString, 8, false));
        System.out.println("=============================================");
    }


    private static String readPrettyPrintXslt() throws IOException {
        InputStream inputStream = XmlPrettyPrinter.class.getResourceAsStream("/xml/prettyprint.xsl");
        return readFromInputStream(inputStream);
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
