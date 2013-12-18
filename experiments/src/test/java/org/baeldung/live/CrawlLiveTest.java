package org.baeldung.live;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.cyberneko.html.parsers.DOMParser;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class CrawlLiveTest {

    private DefaultHttpClient client;

    // fixtures

    @Before
    public final void before() {
        final HttpParams httpParameters = new BasicHttpParams();
        client = new DefaultHttpClient(httpParameters);
    }

    // tests

    @Test
    public final void when_then() throws ClientProtocolException, IOException, XPathExpressionException, SAXException, ParserConfigurationException {
        final String url = "http://sales.starcitygames.com/category.php?cat=5260&amp;start=50";
        final String xpathEx = ".//*[@id='search_results_table']/tbody/tr/td[1]";

        HttpGet request = null;
        HttpEntity httpEntity = null;
        InputStream entityContentStream = null;

        try {
            request = new HttpGet(url);
            final HttpResponse httpResponse = client.execute(request);

            httpEntity = httpResponse.getEntity();
            entityContentStream = httpEntity.getContent();

            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.parse(entityContentStream);

            final XPathFactory xPathfactory = XPathFactory.newInstance();
            final XPath xpath = xPathfactory.newXPath();
            final XPathExpression expr = xpath.compile(xpathEx);
            final Object evaluated = expr.evaluate(doc, XPathConstants.STRING);
            System.out.println(evaluated);
        } catch (final RuntimeException runEx) {
            runEx.printStackTrace();
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
            if (entityContentStream != null) {
                entityContentStream.close();
            }
            if (httpEntity != null) {
                EntityUtils.consume(httpEntity);
            }
        }
    }

    // http://htmlcleaner.sourceforge.net/parameters.php
    @SuppressWarnings("unused")
    @Test
    public final void givenCleaningWithHtmlCleaner_whenPageIsRetrieved_thenContentCanBeExtracted() throws XPatherException, MalformedURLException, IOException {
        final String url = "http://sales.starcitygames.com/category.php?cat=5260&amp;start=50";
        final String xpathEx1 = ".//*[@id='search_results_table']/tbody/tr/td[1]/b/a/text()";
        final String xpathEx2 = ".//*[@id='search_results_table']/tbody/tr";

        final CleanerProperties props = new CleanerProperties();
        // set some properties to non-default values
        props.setAdvancedXmlEscape(true);
        // props.setOmitComments(true);

        // do parsing
        final TagNode tagNode = new HtmlCleaner(props).clean(new URL(url));
        final Object[] evaluateXPath = tagNode.evaluateXPath(xpathEx2);
        final Object ex = evaluateXPath[7];

        System.out.println(ex);
        // System.out.println(Arrays.toString(evaluateXPath));
        // new PrettyXmlSerializer(props).writeToStream(tagNode, System.out);
    }

    @SuppressWarnings("unused")
    @Test
    public final void givenCleaningWithNeko_whenPageIsRetrieved_thenContentCanBeExtracted() throws XPatherException, MalformedURLException, IOException, XPathExpressionException, SAXException {
        final String url = "http://sales.starcitygames.com/category.php?cat=5260&amp;start=50";
        final String xpathEx1 = ".//*[@id='search_results_table']/tbody/tr/td[1]/b/a/text()";
        final String xpathEx2 = ".//*[@id='search_results_table']/tbody/tr";

        final DOMParser parser = new DOMParser();
        parser.setFeature("http://xml.org/sax/features/namespaces", false);
        parser.parse(url);
        final Document document = parser.getDocument();

        final XPathFactory xpf = XPathFactory.newInstance();
        final XPath xpath = xpf.newXPath();
        final Object evaluate = xpath.evaluate(xpathEx2, document);

        System.out.println(evaluate);
    }

}
