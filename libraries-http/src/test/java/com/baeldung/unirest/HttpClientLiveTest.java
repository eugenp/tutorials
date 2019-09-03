package com.baeldung.unirest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.entity.ContentType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.baeldung.unirest.Article;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

public class HttpClientLiveTest {

    @BeforeClass
    public static void setup() {
        // Unirest.setProxy(new HttpHost("localhost", 8080));
        Unirest.setTimeouts(20000, 15000);
        Unirest.setDefaultHeader("X-app-name", "baeldung-unirest");
        Unirest.setDefaultHeader("X-request-id", "100004f00ab5");
        Unirest.setConcurrency(20, 5);
        Unirest.setObjectMapper(new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

            }

            public <T> T readValue(String value, Class<T> valueType) {

                try {
                    return mapper.readValue(value, valueType);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    @AfterClass
    public static void tearDown() throws IOException {
        Unirest.clearDefaultHeaders();
        Unirest.shutdown();
    }

    @Test
    public void shouldReturnStatusOkay() throws UnirestException {
        HttpResponse<JsonNode> jsonResponse = Unirest.get("http://www.mocky.io/v2/5a9ce37b3100004f00ab5154")
            .header("accept", "application/json")
            .queryString("apiKey", "123")
            .asJson();
        assertNotNull(jsonResponse.getBody());
        assertEquals(200, jsonResponse.getStatus());
    }

    @Test
    public void shouldReturnStatusAccepted() throws UnirestException {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("accept", "application/json");
        headers.put("Authorization", "Bearer 5a9ce37b3100004f00ab5154");

        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("name", "Sam Baeldung");
        fields.put("id", "PSP123");

        HttpResponse<JsonNode> jsonResponse = Unirest.put("http://www.mocky.io/v2/5a9ce7853100002a00ab515e")
            .headers(headers)
            .fields(fields)
            .asJson();
        assertNotNull(jsonResponse.getBody());
        assertEquals(202, jsonResponse.getStatus());
    }

    @Test
    public void givenRequestBodyWhenCreatedThenCorrect() throws UnirestException {

        HttpResponse<JsonNode> jsonResponse = Unirest.post("http://www.mocky.io/v2/5a9ce7663100006800ab515d")
            .body("{\"name\":\"Sam Baeldung\", \"city\":\"viena\"}")
            .asJson();
        assertEquals(201, jsonResponse.getStatus());
    }

    @Test
    @Ignore
    public void whenAysncRequestShouldReturnOk() throws InterruptedException, ExecutionException {
        Future<HttpResponse<JsonNode>> future = Unirest.post("http://www.mocky.io/v2/5a9ce37b3100004f00ab5154?mocky-delay=10000ms")
            .header("accept", "application/json")
            .asJsonAsync(new Callback<JsonNode>() {

                public void failed(UnirestException e) {
                    // Do something if the request failed
                }

                public void completed(HttpResponse<JsonNode> response) {
                    // Do something if the request is successful
                }

                public void cancelled() {
                    // Do something if the request is cancelled
                }

            });
        assertEquals(200, future.get()
            .getStatus());

    }

    @Test
    public void givenArticleWhenCreatedThenCorrect() throws UnirestException {
        Article article = new Article("ID1213", "Guide to Rest", "baeldung");
        HttpResponse<JsonNode> jsonResponse = Unirest.post("http://www.mocky.io/v2/5a9ce7663100006800ab515d")
            .body(article)
            .asJson();
        assertEquals(201, jsonResponse.getStatus());
    }

    // @Test
    public void givenFileWhenUploadedThenCorrect() throws UnirestException {

        HttpResponse<JsonNode> jsonResponse = Unirest.post("http://www.mocky.io/v2/5a9ce7663100006800ab515d")
            .field("file", new File("/path/to/file"))
            .asJson();
        assertEquals(201, jsonResponse.getStatus());
    }

    // @Test
    public void givenByteStreamWhenUploadedThenCorrect() throws IOException, UnirestException {
        try (InputStream inputStream = new FileInputStream(new File("/path/to/file/artcile.txt"))) {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            HttpResponse<JsonNode> jsonResponse = Unirest.post("http://www.mocky.io/v2/5a9ce7663100006800ab515d")
                .field("file", bytes, "article.txt")
                .asJson();
            assertEquals(201, jsonResponse.getStatus());
        }

    }

    // @Test
    public void givenInputStreamWhenUploadedThenCorrect() throws UnirestException, IOException {
        try (InputStream inputStream = new FileInputStream(new File("/path/to/file/artcile.txt"))) {

            HttpResponse<JsonNode> jsonResponse = Unirest.post("http://www.mocky.io/v2/5a9ce7663100006800ab515d")
                .field("file", inputStream, ContentType.APPLICATION_OCTET_STREAM, "article.txt")
                .asJson();
            assertEquals(201, jsonResponse.getStatus());

        }
    }
}
