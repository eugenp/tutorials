package com.baeldung.jsoup;

import java.io.IOException;
import org.jsoup.HttpStatusException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class JsoupParserTest {

    JsoupParser jsoupParser;

    @Before
    public void setUp() {
        jsoupParser = new JsoupParser();
    }

    @Test
    public void test404() throws IOException {
        try {
            jsoupParser.loadDocument("https://spring.io/will-not-be-found");
        } catch (HttpStatusException ex) {
            assertEquals(404, ex.getStatusCode());
        }
    }

    @Test
    public void testChange() throws IOException {
        jsoupParser.loadDocument("http://spring.io/blog");

        jsoupParser.examplesModifying();

        assertTrue(jsoupParser.getTidyHtml().contains("http://baeldung.com"));
    }
}
