package com.baeldung.java;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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
import org.dbdoclet.trafo.html.docbook.HtmlDocBookTrafo;
import org.dbdoclet.trafo.script.Script;
import org.junit.Test;
import org.w3c.dom.Document;

public class ApacheFOPHeroldLiveTest {
    private final String[] inputUrls = {// @formatter:off
           // "http://www.baeldung.com/spring-security-basic-authentication",
            "http://www.baeldung.com/spring-security-digest-authentication"
            //"http://www.baeldung.com/spring-httpmessageconverter-rest",
            //"http://www.baeldung.com/2011/11/06/restful-web-service-discoverability-part-4/",
            //"http://www.baeldung.com/2011/11/13/rest-service-discoverability-with-spring-part-5/",
            //"http://www.baeldung.com/2013/01/11/etags-for-rest-with-spring/",
            // "http://www.baeldung.com/2012/01/18/rest-pagination-in-spring/",
            //"http://inprogress.baeldung.com/?p=1430",
            //"http://www.baeldung.com/2013/01/31/exception-handling-for-rest-with-spring-3-2/",
            //"http://www.baeldung.com/rest-versioning",
            //"http://www.baeldung.com/2013/01/18/testing-rest-with-multiple-mime-types/"
    }; // @formatter:on

    private final String style_file = "src/test/resources/docbook-xsl/fo/docbook.xsl";
    private final String output_file = "src/test/resources/final_output.pdf";
    private final String xmlInput = "src/test/resources/input.xml";
    private final String xmlOutput = "src/test/resources/output.xml";

    // tests

    @Test
    public void whenTransformFromHeroldToPDF_thenCorrect() throws Exception {
        final int len = inputUrls.length;
        fromHTMLTOXMLUsingHerold(inputUrls[0], false);
        for (int i = 1; i < len; i++) {
            fromHTMLTOXMLUsingHerold(inputUrls[i], true);
        }
        fixXML(xmlInput, xmlOutput);
        final Document fo = fromXMLFileToFO();
        fromFODocumentToPDF(fo, output_file);
    }

    // UTIL

    private void fromHTMLTOXMLUsingHerold(final String input, final boolean append) throws Exception {
        Script script;
        final TrafoScriptManager mgr = new TrafoScriptManager();
        final File profileFile = new File("src/test/resources/default.her");
        script = mgr.parseScript(profileFile);
        final HtmlDocBookTrafo transformer = new HtmlDocBookTrafo();
        transformer.setInputStream(getInputStream(input));
        transformer.setOutputStream(new FileOutputStream(xmlInput, append));

        transformer.transform(script);
    }

    private Document fromXMLFileToFO() throws Exception {
        final Source source = new StreamSource(new FileInputStream(xmlOutput));
        final DOMResult result = new DOMResult();
        final Transformer transformer = createTransformer(style_file);
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

    private InputStream getInputStream(final String input) throws IOException {
        final URL url = new URL(input);
        final HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
        httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");
        return httpcon.getInputStream();
    }

    private void fixXML(final String input, final String output) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(input));
        final FileWriter writer = new FileWriter(output);
        String line = reader.readLine();
        int count = 0;
        while (line != null) {
            line = line.replaceAll("”", "\"");
            line = line.replaceAll("“", "\"");
            // line = line.replaceAll("[^\\x00-\\x7F]", "");

            if (line.contains("info>")) {
                writer.write(line.replace("info>", "section>"));
            } else if (!((line.startsWith("<?xml") || line.startsWith("<article") || line.startsWith("</article")) && (count > 4))) {
                writer.write(line.replaceAll("xml:id=\"", "xml:id=\"" + count));
            }
            writer.write("\n");

            line = reader.readLine();
            count++;
        }
        writer.write("</article>");
        reader.close();
        writer.close();
    }

}
