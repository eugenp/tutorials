package com.baeldung.xml.stax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;


public class StaxParserUnitTest {
    
    @Test
    public void givenWebsitesXML_whenParsed_thenNotNull() {
        List<WebSite> websites = StaxParser.parse("src/test/resources/xml/websites.xml");
        assertNotNull(websites);
    }

    @Test
    public void givenWebsitesXML_whenParsed_thenSizeIsThree() {
        List<WebSite> websites = StaxParser.parse("src/test/resources/xml/websites.xml");
        assertEquals(3, websites.size());
    }
    
    @Test
    public void givenWebsitesXML_whenParsed_thenLocalhostExists() {
        List<WebSite> websites = StaxParser.parse("src/test/resources/xml/websites.xml");
        assertEquals("Localhost", websites.get(2).getName());
    }
}
