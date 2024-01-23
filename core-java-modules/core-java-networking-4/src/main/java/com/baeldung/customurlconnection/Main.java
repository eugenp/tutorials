package com.baeldung.customurlconnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        URL.setURLStreamHandlerFactory(new CustomURLStreamHandlerFactory());

        try {
            URL url = new URL("myprotocol://example.com/resource");

            CustomURLConnection customConnection = (CustomURLConnection) url.openConnection();
            customConnection.connect();

            InputStream inputStream = customConnection.getInputStream();

            String content = new Scanner(inputStream).useDelimiter("\\A").next();
            System.out.println(content);

            int contentLength = customConnection.getContentLength();
            System.out.println("Content Length: " + contentLength);

            String headerValue = customConnection.getHeaderField("SimulatedHeader");
            System.out.println("Header Value: " + headerValue);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
