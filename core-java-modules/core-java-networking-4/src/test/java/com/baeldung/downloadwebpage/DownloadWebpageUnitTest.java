package com.baeldung.downloadwebpage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

class DownloadWebpageUnitTest {

    @Test
    public void givenURLConnection_whenRetrieveWebpage_thenWebpageisNotNullAndContainsHtmlTag() throws IOException {
        URL url = new URL("https://example.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        reader.close();

        assertNotNull(responseBuilder);
        assertTrue(responseBuilder.toString()
          .contains("<html>"));

    }

    @Test
    public void givenJsoup_whenRetrievingWebpage_thenWebpageDocumenttIsNotNullAndContainsHtmlTag() throws IOException {

        Document doc = Jsoup.connect("https://example.com")
          .get();
        String webpage = doc.html();

        assertNotNull(webpage);
        assertTrue(webpage.contains("<html>"));

    }

}
