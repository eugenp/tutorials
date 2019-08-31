package com.baeldung.sax;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class SaxParserMainUnitTest {

    @Test
    public void parse_baeldung_xml_document() throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        SaxParserMain.BaeldungHandler baeldungHandler = new SaxParserMain.BaeldungHandler();
        Path path = Paths.get(new URL("src\\test\\resources\\sax\\baeldung.xml").toURI());
        saxParser.parse(path.toFile(), baeldungHandler);

        SaxParserMain.Baeldung result = baeldungHandler.getWebsite();

        assertNotNull(result);
        List<SaxParserMain.BaeldungArticle> articles = result.getArticleList();

        assertNotNull(articles);
        assertEquals(3, articles.size());

        SaxParserMain.BaeldungArticle articleOne = articles.get(0);
        assertEquals("Parsing an XML File Using SAX Parser", articleOne.getTitle());
        assertEquals("Lorem ipsum...", articleOne.getContent());

        SaxParserMain.BaeldungArticle articleTwo = articles.get(1);
        assertEquals("Parsing an XML File Using DOM Parser", articleTwo.getTitle());
        assertEquals("Lorem ipsum...", articleTwo.getContent());

        SaxParserMain.BaeldungArticle articleThree = articles.get(2);
        assertEquals("Parsing an XML File Using StAX Parser", articleThree.getTitle());
        assertEquals("Lorem ipsum...", articleThree.getContent());
    }
}
