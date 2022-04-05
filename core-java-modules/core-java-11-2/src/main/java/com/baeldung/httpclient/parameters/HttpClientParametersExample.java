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

public class HttpClientParametersExample {

    public static void main(String[] args) throws Exception {
        queryParametersRequest();

        noBodyRequests();

        stringBodyRequests();

        byteArrayBodyRequests();

        fileBodyRequest();

        inputStreamBodyRequest();
    }

    private static void noBodyRequests() throws IOException, InterruptedException {
        noBodyRequest();
        noBodyRequestUsingMethod();
    }

    private static void byteArrayBodyRequests() throws IOException, InterruptedException {
        byteArrayBodyRequest();
        byteArrayBodyWithSpecificBytesRequest();
        byteArraysIteratorRequest();
    }

    private static void stringBodyRequests() throws IOException, InterruptedException {
        stringBodyRequest();
        stringBodyWithCharsetRequest();
        jsonDataStringBodyRequest();
        formDataStringBodyRequest();
    }

    public static void queryParametersRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/get?param1=value1&param2=value2"))
          .GET()
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

    public static void noBodyRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/post"))
          .POST(HttpRequest.BodyPublishers.noBody())
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

    private static void noBodyRequestUsingMethod() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/post"))
          .method("POST", HttpRequest.BodyPublishers.noBody())
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

    public static void stringBodyRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/post"))
          .headers("Content-Type", "text/plain;charset=UTF-8")
          .POST(HttpRequest.BodyPublishers.ofString("Sample Post Request Data"))
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

    public static void stringBodyWithCharsetRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/put"))
          .headers("Content-Type", "text/plain;charset=UTF-8")
          .PUT(HttpRequest.BodyPublishers.ofString("Sample Put Request Data", Charset.forName("UTF-8")))
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

    public static void jsonDataStringBodyRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/post"))
          .headers("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString("{\"content\":\"Sample\"}", Charset.forName("UTF-8")))
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

    public static void formDataStringBodyRequest() throws IOException, InterruptedException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("param2", "value2");

        String form = parameters.entrySet()
          .stream()
          .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
          .collect(Collectors.joining("&"));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/post"))
          .headers("Content-Type", "application/x-www-form-urlencoded")
          .POST(HttpRequest.BodyPublishers.ofString(form))
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

    public static void byteArrayBodyRequest() throws IOException, InterruptedException {
        byte byteData[] = "Sample Post Request Data".getBytes();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/post"))
          .headers("Content-Type", "text/plain;charset=UTF-8")
          .POST(HttpRequest.BodyPublishers.ofByteArray(byteData))
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

    public static void byteArrayBodyWithSpecificBytesRequest() throws IOException, InterruptedException {
        byte byteData[] = "Sample Put Request Data".getBytes();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/put"))
          .headers("Content-Type", "text/plain;charset=UTF-8")
          .PUT(HttpRequest.BodyPublishers.ofByteArray(byteData, 7, 11))
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

    public static void byteArraysIteratorRequest() throws IOException, InterruptedException {
        List<byte[]> byteArrays = new ArrayList<>();
        byteArrays.add("Hello".getBytes());
        byteArrays.add("World".getBytes());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/put"))
          .headers("Content-Type", "text/plain;charset=UTF-8")
          .PUT(HttpRequest.BodyPublishers.ofByteArrays(byteArrays))
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

    public static void fileBodyRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/post"))
          .headers("Content-Type", "text/plain;charset=UTF-8")
          .POST(HttpRequest.BodyPublishers.ofFile(Paths.get("src/test/resources/sample.txt")))
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

    public static void inputStreamBodyRequest() throws IOException, InterruptedException {
        byte byteData[] = "Sample Put Request Data".getBytes();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .uri(URI.create("https://postman-echo.com/put"))
          .headers("Content-Type", "text/plain;charset=UTF-8")
          .PUT(HttpRequest.BodyPublishers.ofInputStream(() -> new ByteArrayInputStream(byteData)))
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("Response body : " + responseBody);
        System.out.println("Response status code: " + responseStatusCode);
    }

}