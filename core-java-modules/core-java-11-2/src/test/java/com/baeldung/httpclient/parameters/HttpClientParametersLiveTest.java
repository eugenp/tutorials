package com.baeldung.httpclient.parameters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HttpClientParametersLiveTest {

    private static HttpClient client;

    @BeforeAll
    public static void setUp() {
        client = HttpClient.newHttpClient();
    }

    @Test
    public void givenQueryParams_whenGetRequest_thenResponseOk() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://postman-echo.com/get?param1=value1&param2=value2"))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void givenNoData_whenPostRequest_thenResponseOk() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://postman-echo.com/post"))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void givenString_whenPostRequest_thenResponseOk() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://postman-echo.com/post"))
            .headers("Content-Type", "text/plain;charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofString("Sample Post Request Data"))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void givenStringWithCharset_whenPutRequest_thenResponseOk() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://postman-echo.com/put"))
            .headers("Content-Type", "text/plain;charset=UTF-8")
            .PUT(HttpRequest.BodyPublishers.ofString("Sample Post Request Data", Charset.forName("UTF-8")))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void givenJsonDataString_whenPostRequest_thenResponseOk() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://postman-echo.com/post"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"content\":\"Sample\"}"))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void givenFormDataString_whenPostRequest_thenResponseOk() throws IOException, InterruptedException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("param2", "value2");

        String form = parameters.entrySet()
            .stream()
            .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
            .collect(Collectors.joining("&"));

        HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://postman-echo.com/post"))
            .headers("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(form))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void givenByteArray_whenPostRequest_thenResponseOk() throws IOException, InterruptedException {
        byte byteData[] = "Sample Post Request Data".getBytes();

        HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://postman-echo.com/post"))
            .headers("Content-Type", "text/plain;charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofByteArray(byteData))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void givenByteArrayWithSpecificBytes_whenPutRequest_thenResponseOk() throws IOException, InterruptedException {
        byte byteData[] = "Sample Put Request Data".getBytes();

        HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://postman-echo.com/put"))
            .headers("Content-Type", "text/plain;charset=UTF-8")
            .PUT(HttpRequest.BodyPublishers.ofByteArray(byteData, 7, 11))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void givenByteArraysIterable_whenPutRequest_thenResponseOk() throws IOException, InterruptedException {
        List<byte[]> byteArrays = new ArrayList<>();
        byteArrays.add("Hello".getBytes());
        byteArrays.add("World".getBytes());

        HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://postman-echo.com/put"))
            .headers("Content-Type", "text/plain;charset=UTF-8")
            .PUT(HttpRequest.BodyPublishers.ofByteArrays(byteArrays))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void givenFile_whenPostRequest_thenResponseOk() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://postman-echo.com/post"))
            .headers("Content-Type", "text/plain;charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofFile(Paths.get("src/test/resources/sample.txt")))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void givenInputStream_whenPutRequest_thenResponseOk() throws IOException, InterruptedException {
        byte byteData[] = "Sample Put Request Data".getBytes();

        HttpRequest request = HttpRequest.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://postman-echo.com/put"))
            .headers("Content-Type", "text/plain;charset=UTF-8")
            .PUT(HttpRequest.BodyPublishers.ofInputStream(() -> new ByteArrayInputStream(byteData)))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);
    }
}
