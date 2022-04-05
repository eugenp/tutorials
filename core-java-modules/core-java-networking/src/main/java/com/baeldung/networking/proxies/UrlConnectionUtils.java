package com.baeldung.networking.proxies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;

class UrlConnectionUtils {

    public static String contentAsString(URLConnection con) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader 
                = new BufferedReader(new InputStreamReader(con.getInputStream()))){
            while (reader.ready()) {
                builder.append(reader.readLine());
            }
        }
        return builder.toString();
    }

}
