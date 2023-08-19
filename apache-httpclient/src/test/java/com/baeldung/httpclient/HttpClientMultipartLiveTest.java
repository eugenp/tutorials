package com.baeldung.httpclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

class HttpClientMultipartLiveTest extends GetRequestMockServer {

    // No longer available
    // private static final String SERVER = "http://echo.200please.com";

    private static final String SERVER = "http://localhost:8080/spring-mvc-java/stub/multipart";
    private static final String TEXTFILENAME = "temp.txt";
    private static final String IMAGEFILENAME = "image.jpg";
    private static final String ZIPFILENAME = "zipFile.zip";
    private HttpPost post;
    private BufferedReader rd;

    @BeforeEach
    public void before() {
        post = new HttpPost(SERVER);
        String URL = "http://localhost:" + serverPort + "/spring-mvc-java/stub/multipart";
        post = new HttpPost(URL);
    }

    @Test
    void givenFileandMultipleTextParts_whenUploadwithAddPart_thenNoExceptions() throws IOException {
        final URL url = Thread.currentThread()
            .getContextClassLoader()
            .getResource("uploads/" + TEXTFILENAME);

        final File file = new File(url.getPath());
        final FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
        final StringBody stringBody1 = new StringBody("This is message 1", ContentType.MULTIPART_FORM_DATA);
        final StringBody stringBody2 = new StringBody("This is message 2", ContentType.MULTIPART_FORM_DATA);
        //
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.LEGACY);
        builder.addPart("file", fileBody);
        builder.addPart("text1", stringBody1);
        builder.addPart("text2", stringBody2);
        final HttpEntity entity = builder.build();

        post.setEntity(entity);
        try (CloseableHttpClient client = HttpClientBuilder.create()
            .build()) {

            client.execute(post, response -> {
                final int statusCode = response.getCode();
                final String responseString = getContent(response.getEntity());
                final String contentTypeInHeader = getContentTypeHeader();

                assertThat(statusCode, equalTo(HttpStatus.SC_OK));
                assertTrue(contentTypeInHeader.contains("multipart/form-data"));
                System.out.println(responseString);
                System.out.println("POST Content Type: " + contentTypeInHeader);
                return response;
            });
        }
    }

    @Test
    void givenFileandTextPart_whenUploadwithAddBinaryBodyandAddTextBody_ThenNoExeption() throws IOException {
        final URL url = Thread.currentThread()
            .getContextClassLoader()
            .getResource("uploads/" + TEXTFILENAME);
        final File file = new File(url.getPath());
        final String message = "This is a multipart post";
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.LEGACY);
        builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, TEXTFILENAME);
        builder.addTextBody("text", message, ContentType.DEFAULT_BINARY);
        final HttpEntity entity = builder.build();
        post.setEntity(entity);

        try (CloseableHttpClient client = HttpClientBuilder.create()
            .build()) {

            client.execute(post, response -> {

                final int statusCode = response.getCode();
                final String responseString = getContent(response.getEntity());
                final String contentTypeInHeader = getContentTypeHeader();
                assertThat(statusCode, equalTo(HttpStatus.SC_OK));
                assertTrue(contentTypeInHeader.contains("multipart/form-data"));
                System.out.println(responseString);
                System.out.println("POST Content Type: " + contentTypeInHeader);
                return response;
            });
        }
    }

    @Test
    void givenFileAndInputStreamandText_whenUploadwithAddBinaryBodyandAddTextBody_ThenNoException() throws IOException {
        final URL url = Thread.currentThread()
            .getContextClassLoader()
            .getResource("uploads/" + ZIPFILENAME);
        final URL url2 = Thread.currentThread()
            .getContextClassLoader()
            .getResource("uploads/" + IMAGEFILENAME);
        final InputStream inputStream = new FileInputStream(url.getPath());
        final File file = new File(url2.getPath());
        final String message = "This is a multipart post";
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.LEGACY);
        builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, IMAGEFILENAME);
        builder.addBinaryBody("upstream", inputStream, ContentType.create("application/zip"), ZIPFILENAME);
        builder.addTextBody("text", message, ContentType.TEXT_PLAIN);
        final HttpEntity entity = builder.build();
        post.setEntity(entity);

        try (CloseableHttpClient client = HttpClientBuilder.create()
            .build()) {

            client.execute(post, response -> {
                final int statusCode = response.getCode();
                final String responseString = getContent(response.getEntity());
                final String contentTypeInHeader = getContentTypeHeader();
                assertThat(statusCode, equalTo(HttpStatus.SC_OK));
                assertTrue(contentTypeInHeader.contains("multipart/form-data;"));
                System.out.println(responseString);
                System.out.println("POST Content Type: " + contentTypeInHeader);
                inputStream.close();
                return response;
            });
        }
    }

    @Test
    void givenCharArrayandText_whenUploadwithAddBinaryBodyandAddTextBody_ThenNoException() throws IOException, ParseException {
        final String message = "This is a multipart post";
        final byte[] bytes = "binary code".getBytes();
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.LEGACY);
        builder.addBinaryBody("file", bytes, ContentType.DEFAULT_BINARY, TEXTFILENAME);
        builder.addTextBody("text", message, ContentType.TEXT_PLAIN);
        final HttpEntity entity = builder.build();
        post.setEntity(entity);

        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
            .build()) {

            httpClient.execute(post, response -> {
                final int statusCode = response.getCode();
                final String responseString = getContent(response.getEntity());
                final String contentTypeInHeader = getContentTypeHeader();
                assertThat(statusCode, equalTo(HttpStatus.SC_OK));
                assertTrue(contentTypeInHeader.contains("multipart/form-data;"));
                System.out.println(responseString);
                System.out.println("POST Content Type: " + contentTypeInHeader);
                return response;
            });
        }
    }

    // UTIL

    private String getContent(HttpEntity httpEntity) throws IOException {
        rd = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
        String body = "";
        StringBuilder content = new StringBuilder();
        while ((body = rd.readLine()) != null) {
            content.append(body)
                .append("\n");
        }
        return content.toString()
            .trim();
    }

    private String getContentTypeHeader() {
        return post.getEntity()
            .getContentType();
    }

}
