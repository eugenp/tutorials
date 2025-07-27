package com.baeldung.downloadwebpage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

class DownloadWebpageLiveTest {

    @Test
    public void givenURLConnection_whenRetrieveWebpage_thenWebpageIsNotNullAndContainsHtmlTag() throws IOException, URISyntaxException {
        URL url = new URI("https://example.com").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            assertNotNull(responseBuilder);
            assertTrue(responseBuilder.toString()
              .contains("<html>"));
        }

    }

    @Test
    public void givenJsoup_whenRetrievingWebpage_thenWebpageDocumentIsNotNullAndContainsHtmlTag() throws IOException {

        Document document = Jsoup.connect("https://example.com")
          .get();
        String webpage = document.html();

        assertNotNull(webpage);
        assertTrue(webpage.contains("<html>"));

    }

}
