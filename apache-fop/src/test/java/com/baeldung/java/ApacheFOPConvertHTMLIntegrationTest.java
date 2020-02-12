package com.baeldung.java;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.dbdoclet.trafo.html.docbook.HtmlDocBookTrafo;
import org.dbdoclet.trafo.script.Script;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

public class ApacheFOPConvertHTMLIntegrationTest {
    private final String inputFile = "src/test/resources/input.html";
    private final String style = "src/test/resources/xhtml2fo.xsl";
    private final String style1 = "src/test/resources/docbook-xsl/fo/docbook.xsl";
    private final String output_jtidy = "src/test/resources/output_jtidy.pdf";
    private final String output_html2fo = "src/test/resources/output_html2fo.pdf";
    private final String output_herold = "src/test/resources/output_herold.pdf";
    private final String foFile = "src/test/resources/input.fo";
    private final String xmlFile = "src/test/resources/input.xml";

    @Test
    public void whenTransformHTMLToPDFUsingJTidy_thenCorrect() throws Exception {
        final Document xhtml = fromHTMLToXHTML();
        final Document fo = fromXHTMLToFO(xhtml);
        fromFODocumentToPDF(fo, output_jtidy);
    }

    @Test
    public void whenTransformFromHTML2FOToPDF_thenCorrect() throws Exception {
        fromFOFileToPDF();
    }

    @Test
    public void whenTransformFromHeroldToPDF_thenCorrect() throws Exception {
        fromHTMLTOXMLUsingHerold();
        final Document fo = fromXMLFileToFO();
        fromFODocumentToPDF(fo, output_herold);
    }

    private Document fromHTMLToXHTML() throws FileNotFoundException {
        final FileInputStream reader = new FileInputStream(inputFile);
        final Tidy tidy = new Tidy();
        final Document xml = tidy.parseDOM(reader, null);
        return xml;
    }

    private Document fromXHTMLToFO(final Document xhtml) throws Exception {
        final DOMSource source = new DOMSource(xhtml);
        final DOMResult result = new DOMResult();
        final Transformer transformer = createTransformer(style);
        transformer.transform(source, result);
        return (Document) result.getNode();
    }

    private void fromFODocumentToPDF(final Document fo, final String outputFile) throws Exception {
        final FopFactory fopFactory = FopFactory.newInstance();
        final OutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(outputFile)));

        final Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, outStream);
        final TransformerFactory factory = TransformerFactory.newInstance();
        final Transformer transformer = factory.newTransformer();
        final DOMSource src = new DOMSource(fo);
        final Result res = new SAXResult(fop.getDefaultHandler());
        transformer.transform(src, res);

        outStream.close();
    }

    private Transformer createTransformer(final String styleFile) throws Exception {
        final TransformerFactory factory = TransformerFactory.newInstance();
        final Transformer transformer = factory.newTransformer(new StreamSource(styleFile));

        return transformer;
    }

    private void fromFOFileToPDF() throws Exception {
        final FopFactory fopFactory = FopFactory.newInstance();
        final OutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(output_html2fo)));
        final Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, outStream);

        final TransformerFactory factory = TransformerFactory.newInstance();
        final Transformer transformer = factory.newTransformer();
        final Source src = new StreamSource(new FileInputStream(foFile));
        final Result res = new SAXResult(fop.getDefaultHandler());
        transformer.transform(src, res);

        outStream.close();
    }

    private Document fromXMLFileToFO() throws Exception {
        final Source source = new StreamSource(new FileInputStream(xmlFile));
        final DOMResult result = new DOMResult();
        final Transformer transformer = createTransformer(style1);
        transformer.transform(source, result);
        return (Document) result.getNode();
    }

    private void fromHTMLTOXMLUsingHerold() throws Exception {
        final Script script = new Script();
        final HtmlDocBookTrafo transformer = new HtmlDocBookTrafo();
        transformer.setInputStream(new FileInputStream(inputFile));
        transformer.setOutputStream(new FileOutputStream(xmlFile));
        transformer.transform(script);
    }
}
