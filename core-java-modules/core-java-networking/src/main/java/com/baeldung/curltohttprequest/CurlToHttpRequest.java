package com.baeldung.curltohttprequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CurlToHttpRequest {

    private static final Logger logger = Logger.getLogger(CurlToHttpRequest.class.getName());

    public static String sendPostWithHttpURLConnection(String targetUrl) throws IOException {
        URL url = new URL(targetUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInput = "{\"key1\":\"value1\", \"key2\":\"value2\"}";
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }

    public static String sendPostWithApacheHttpClient(String targetUrl) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(targetUrl);
            httpPost.setHeader("Content-Type", "application/json");

            String jsonInput = "{\"key1\":\"value1\", \"key2\":\"value2\"}";
            httpPost.setEntity(new StringEntity(jsonInput));

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                return EntityUtils.toString(response.getEntity());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String sendPostWithOkHttp(String targetUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        String jsonInput = "{\"key1\":\"value1\", \"key2\":\"value2\"}";
        RequestBody body = RequestBody.create(jsonInput, JSON);
        Request request = new Request.Builder()
            .url(targetUrl)
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String sendPostWithSpringWebClient(String targetUrl) {
        WebClient webClient = WebClient.builder()
            .baseUrl(targetUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
            .build();

        String jsonInput = "{\"key1\":\"value1\", \"key2\":\"value2\"}";

        return webClient.post()
            .bodyValue(jsonInput)
            .retrieve()
            .bodyToMono(String.class)
            .block(); // Blocking for synchronous execution
    }
}
