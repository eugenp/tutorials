package com.baeldung.jackson.jsonurlreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUrlReader {

    public static void main(String[] args) throws StreamReadException, DatabindException, MalformedURLException, IOException {
        String url = args[0];

        JsonNode node = JsonUrlReader.get(url);
        System.out.println(node.toPrettyString());
    }

    public static String stream(String url) throws IOException {
        try (InputStream input = new URL(url).openStream()) {
            InputStreamReader isr = new InputStreamReader(input, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        }
    }

    public static JsonNode get(String url) throws StreamReadException, DatabindException, MalformedURLException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new URL(url));
        return json;
    }

    public static <T> T get(String url, Class<T> type) throws StreamReadException, DatabindException, MalformedURLException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        T entity = mapper.readValue(new URL(url), type);
        return entity;
    }

    public static String getString(String url) throws StreamReadException, DatabindException, MalformedURLException, IOException {
        return get(url).toPrettyString();
    }
}
