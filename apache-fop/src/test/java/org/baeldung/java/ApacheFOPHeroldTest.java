package org.baeldung.java;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

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
import org.dbdoclet.trafo.TrafoScriptManager;
import org.dbdoclet.trafo.html.docbook.DocBookTransformer;
import org.dbdoclet.trafo.script.Script;
import org.junit.Test;
import org.w3c.dom.Document;

public class ApacheFOPHeroldTest {
    private String[] inputUrls = { "http://inprogress.baeldung.com/?p=1430","http://www.baeldung.com/spring-events" };
    private String style1 = "src/test/resources/docbook-xsl/fo/docbook.xsl";
    private String output_prefix = "src/test/resources/";
    private String xmlFile = "src/test/resources/input.xml";

    @Test
    public void whenTransformFromHeroldToPDF_thenCorrect() throws Exception{
            int len = inputUrls.length;
            for (int i = 0; i < len; i++) {
                fromHTMLTOXMLUsingHerold(inputUrls[i]);
                final Document fo = fromXMLFileToFO();
                fromFODocumentToPDF(fo, output_prefix + i + ".pdf");
            }
    }

    private void fromHTMLTOXMLUsingHerold(String input) throws Exception {
        Script script;
        TrafoScriptManager mgr = new TrafoScriptManager();
        File profileFile = new File("src/test/resources/default.her");
        script = mgr.parseScript(profileFile);
        final DocBookTransformer transformer = new DocBookTransformer();
        transformer.setScript(script);

        transformer.convert(getInputStream(input), new FileOutputStream(xmlFile));
    }

    private Document fromXMLFileToFO() throws Exception {
        final Source source = new StreamSource(new FileInputStream(xmlFile));
        final DOMResult result = new DOMResult();
        final Transformer transformer = createTransformer(style1);
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

    private InputStream getInputStream(String input) throws IOException {
        URL url = new URL(input);
        return url.openStream();
    }

}

