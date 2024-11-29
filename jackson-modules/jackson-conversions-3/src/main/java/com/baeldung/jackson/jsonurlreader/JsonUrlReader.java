package com.baeldung.jackson.jsonurlreader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUrlReader {

    public static void main(String[] args) throws Exception {
        String url = args[0];

        JsonNode node = JsonUrlReader.get(url);
        System.out.println(node.toPrettyString());
    }

    public static String stream(String url) throws Exception {
        try (InputStream input = new URI(url).toURL().openStream()) {
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

    public static JsonNode get(String url) throws StreamReadException, DatabindException, MalformedURLException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new URI(url).toURL());
        return json;
    }

    public static <T> T get(String url, Class<T> type) throws StreamReadException, DatabindException, MalformedURLException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        T entity = mapper.readValue(new URI(url).toURL(), type);
        return entity;
    }

    public static String getString(String url) throws Exception {
        return get(url).toPrettyString();
    }

    public static JSONObject getJson(String url) throws Exception {
        String json = IOUtils.toString(new URI(url).toURL(), Charset.forName("UTF-8"));
        JSONObject object = new JSONObject(json);
        return object;
    }
}
