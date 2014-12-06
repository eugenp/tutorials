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
    private String[] inputUrls = {// @formatter:off
            // "http://www.baeldung.com/2011/10/20/bootstraping-a-web-application-with-spring-3-1-and-java-based-configuration-part-1/",
            // "http://www.baeldung.com/2011/10/25/building-a-restful-web-service-with-spring-3-1-and-java-based-configuration-part-2/",
            "http://www.baeldung.com/2011/10/31/securing-a-restful-web-service-with-spring-security-3-1-part-3/",
            // "http://www.baeldung.com/spring-security-basic-authentication",
            // "http://www.baeldung.com/spring-security-digest-authentication",
            //"http://www.baeldung.com/2011/11/20/basic-and-digest-authentication-for-a-restful-service-with-spring-security-3-1/",
            //"http://www.baeldung.com/spring-httpmessageconverter-rest",
            //"http://www.baeldung.com/2011/11/06/restful-web-service-discoverability-part-4/",
            //"http://www.baeldung.com/2011/11/13/rest-service-discoverability-with-spring-part-5/",
            //"http://www.baeldung.com/2013/01/11/etags-for-rest-with-spring/",
            //"http://www.baeldung.com/2012/01/18/rest-pagination-in-spring/",
            //"http://www.baeldung.com/2013/01/31/exception-handling-for-rest-with-spring-3-2/",
            //"http://www.baeldung.com/rest-versioning",
            //"http://www.baeldung.com/2013/01/18/testing-rest-with-multiple-mime-types/"
    }; // @formatter:on

    private String style1 = "src/test/resources/docbook-xsl/fo/docbook.xsl";
    private String output_prefix = "src/test/resources/";
    private String xmlFile = "src/test/resources/input.xml";

    @Test
    public void whenTransformFromHeroldToPDF_thenCorrect() throws Exception {
        final int len = inputUrls.length;
        for (int i = 0; i < len; i++) {
            fromHTMLTOXMLUsingHerold(inputUrls[i]);
            final Document fo = fromXMLFileToFO();
            fromFODocumentToPDF(fo, output_prefix + i + ".pdf");
        }
    }

    // UTIL

    private void fromHTMLTOXMLUsingHerold(final String input) throws Exception {
        Script script;
        final TrafoScriptManager mgr = new TrafoScriptManager();
        final File profileFile = new File("src/test/resources/default.her");
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

    private InputStream getInputStream(final String input) throws IOException {
        final URL url = new URL(input);
        return url.openStream();
    }

}
