package com.baeldung.xml.xml2pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XmlToPdfConverter {

    public static void convertXMLtoPDFUsingFop(String xmlFilePath, String xsltFilePath, String pdfFilePath) throws Exception {
        // Initialize FOP
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        // Specify output stream for PDF
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(new File(pdfFilePath).toPath()))) {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            // Create Transformer from XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltFilePath)));
            // Apply transformation
            Source src = new StreamSource(new File(xmlFilePath));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        }
    }

    public static void convertXMLtoPDFUsingIText(String xmlFilePath, String pdfFilePath) throws Exception {
        try (FileOutputStream outputStream = new FileOutputStream(pdfFilePath)) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            // Read XML content and add it to the Document
            String xmlContent = new String(Files.readAllBytes(Paths.get(xmlFilePath)));
            document.add(new Paragraph(xmlContent));
            document.close();
        }
    }
}
