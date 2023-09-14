package com.baeldung.httpclient.readresponsebodystring;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.Test;

public class HttpUrlConnectionUnitTest {

    public static final String DUMMY_URL = "https://postman-echo.com/get";

    @Test
    void whenUseHttpUrlConnection_thenCorrect() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(DUMMY_URL).openConnection();

        InputStream inputStream = connection.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String currentLine;

        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);

        in.close();
        assertNotNull(response.toString());
        System.out.println("Response -> " + response.toString());
    }
}
