package com.baeldung.httpclient.readresponsebodystring;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpUrlConnectionUnitTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String DUMMY_URL = "https://postman-echo.com/get";

    @Test
    void whenUseHttpUrlConnection_thenCorrect() throws IOException, URISyntaxException {
        HttpURLConnection connection = (HttpURLConnection) new URI(DUMMY_URL).toURL().openConnection();

        InputStream inputStream = connection.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String currentLine;

        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);

        in.close();
        assertNotNull(response.toString());
        logger.debug("Response -> {}", response.toString());
    }
}
