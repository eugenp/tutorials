package com.baeldung.xml.xml2csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Xml2CsvExample {

    private static final String BASE_PATH = "xml/src/main/resources/xml2csv/";

    private static final String STYLE_XSL = BASE_PATH + "style.xsl";
    private static final String DATA_XML = BASE_PATH + "data.xml";
    private static final String OUTPUT_CSV_XSTL = BASE_PATH + "output_xstl.csv";
    private static final String OUTPUT_CSV_STAX = BASE_PATH + "output_stax.csv";

    public static void main(String[] args) {
        try {
            convertXml2CsvXslt(STYLE_XSL, DATA_XML, OUTPUT_CSV_XSTL);
            convertXml2CsvStax(DATA_XML, OUTPUT_CSV_STAX);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    protected static void convertXml2CsvXslt(String xslPath, String xmlPath, String csvPath) throws IOException, TransformerException {
        StreamSource styleSource = new StreamSource(new File(xslPath));
        Transformer transformer = TransformerFactory.newInstance()
            .newTransformer(styleSource);
        Source source = new StreamSource(new File(xmlPath));
        Result outputTarget = new StreamResult(new File(csvPath));
        transformer.transform(source, outputTarget);
    }

    protected static void convertXml2CsvStax(String xmlFilePath, String csvFilePath) throws IOException, TransformerException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

        try (InputStream in = Files.newInputStream(Paths.get(xmlFilePath)); BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {

            writer.write("bookstore_id,book_id,category,title,author_id,author_name,price\n");

            XMLStreamReader reader = inputFactory.createXMLStreamReader(in);

            String currentElement;
            StringBuilder csvRow = new StringBuilder();
            StringBuilder bookstoreInfo = new StringBuilder();

            while (reader.hasNext()) {
                int eventType = reader.next();

                switch (eventType) {
                case XMLStreamConstants.START_ELEMENT:
                    currentElement = reader.getLocalName();
                    if ("Bookstore".equals(currentElement)) {
                        bookstoreInfo.setLength(0);
                        bookstoreInfo.append(reader.getAttributeValue(null, "id"))
                            .append(",");
                    }
                    if ("Book".equals(currentElement)) {
                        csvRow.append(bookstoreInfo)
                            .append(reader.getAttributeValue(null, "id"))
                            .append(",")
                            .append(reader.getAttributeValue(null, "category"))
                            .append(",");
                    }
                    if ("Author".equals(currentElement)) {
                        csvRow.append(reader.getAttributeValue(null, "id"))
                            .append(",");
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    if (!reader.isWhiteSpace()) {
                        csvRow.append(reader.getText()
                                .trim())
                            .append(",");
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    if ("Book".equals(reader.getLocalName())) {
                        csvRow.setLength(csvRow.length() - 1);
                        csvRow.append("\n");
                        writer.write(csvRow.toString());

                        csvRow.setLength(0);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
