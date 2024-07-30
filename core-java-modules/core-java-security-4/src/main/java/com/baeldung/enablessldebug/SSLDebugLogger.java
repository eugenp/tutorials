package com.baeldung.enablessldebug;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSLDebugLogger {
    private static final Logger logger = LoggerFactory.getLogger(SSLDebugLogger.class);

    public static void enableSSLDebugUsingSystemProperties() {
        System.setProperty("javax.net.debug", "ssl");
    }

    public static void makeHttpsRequest() throws Exception {
        String url = "https://github.com/eugenp/tutorials";
        URL httpsUrl = new URI(url).toURL();
        HttpsURLConnection connection = (HttpsURLConnection) httpsUrl.openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            logger.debug("Response from " + url + ":");
            while ((line = reader.readLine()) != null) {
                logger.debug(line);
            }
        }
    }
}
