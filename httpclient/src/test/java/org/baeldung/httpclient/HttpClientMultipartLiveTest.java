package org.baeldung.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class HttpClientMultipartLiveTest {

    // No longer available
    // private static final String SERVER = "http://echo.200please.com";

    private static final String SERVER = "http://localhost:8080/spring-mvc-java/stub/multipart";
    private static final String TEXTFILENAME = "temp.txt";
    private static final String IMAGEFILENAME = "image.jpg";
    private static final String ZIPFILENAME = "zipFile.zip";
    private static final Logger LOGGER = Logger.getLogger("org.baeldung.httpclient.HttpClientMultipartLiveTest");
    private CloseableHttpClient client;
    private HttpPost post;
    private BufferedReader rd;
    private CloseableHttpResponse response;

    @Before
    public final void before() {
        client = HttpClientBuilder.create()
          .build();
        post = new HttpPost(SERVER);
    }

    @After
    public final void after() throws IllegalStateException, IOException {
        post.completed();
        try {
            client.close();
        } catch (final IOException e1) {
            LOGGER.log(Level.SEVERE, e1.getMessage(), e1);
            throw e1;
        }
        try {
            rd.close();
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        ResponseUtil.closeResponse(response);
    }

    // tests

    @Test
    public final void givenFileandMultipleTextParts_whenUploadwithAddPart_thenNoExceptions() throws IOException {
        final URL url = Thread.currentThread()
          .getContextClassLoader()
          .getResource("uploads/" + TEXTFILENAME);

        final File file = new File(url.getPath());
        final FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
        final StringBody stringBody1 = new StringBody("This is message 1", ContentType.MULTIPART_FORM_DATA);
        final StringBody stringBody2 = new StringBody("This is message 2", ContentType.MULTIPART_FORM_DATA);
        //
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("file", fileBody);
        builder.addPart("text1", stringBody1);
        builder.addPart("text2", stringBody2);
        final HttpEntity entity = builder.build();
        //
        post.setEntity(entity);
        response = client.execute(post);

        final int statusCode = response.getStatusLine()
          .getStatusCode();
        final String responseString = getContent();
        final String contentTypeInHeader = getContentTypeHeader();
        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        // assertTrue(responseString.contains("Content-Type: multipart/form-data;"));
        assertTrue(contentTypeInHeader.contains("Content-Type: multipart/form-data;"));
        System.out.println(responseString);
        System.out.println("POST Content Type: " + contentTypeInHeader);
    }

    @Test
    public final void givenFileandTextPart_whenUploadwithAddBinaryBodyandAddTextBody_ThenNoExeption() throws IOException {
        final URL url = Thread.currentThread()
          .getContextClassLoader()
          .getResource("uploads/" + TEXTFILENAME);
        final File file = new File(url.getPath());
        final String message = "This is a multipart post";
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, TEXTFILENAME);
        builder.addTextBody("text", message, ContentType.DEFAULT_BINARY);
        final HttpEntity entity = builder.build();
        post.setEntity(entity);
        response = client.execute(post);
        final int statusCode = response.getStatusLine()
          .getStatusCode();
        final String responseString = getContent();
        final String contentTypeInHeader = getContentTypeHeader();
        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        // assertTrue(responseString.contains("Content-Type: multipart/form-data;"));
        assertTrue(contentTypeInHeader.contains("Content-Type: multipart/form-data;"));
        System.out.println(responseString);
        System.out.println("POST Content Type: " + contentTypeInHeader);
    }

    @Test
    public final void givenFileAndInputStreamandText_whenUploadwithAddBinaryBodyandAddTextBody_ThenNoException() throws IOException {
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
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, IMAGEFILENAME);
        builder.addBinaryBody("upstream", inputStream, ContentType.create("application/zip"), ZIPFILENAME);
        builder.addTextBody("text", message, ContentType.TEXT_PLAIN);
        final HttpEntity entity = builder.build();
        post.setEntity(entity);
        response = client.execute(post);
        final int statusCode = response.getStatusLine()
          .getStatusCode();
        final String responseString = getContent();
        final String contentTypeInHeader = getContentTypeHeader();
        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        // assertTrue(responseString.contains("Content-Type: multipart/form-data;"));
        assertTrue(contentTypeInHeader.contains("Content-Type: multipart/form-data;"));
        System.out.println(responseString);
        System.out.println("POST Content Type: " + contentTypeInHeader);
        inputStream.close();
    }

    @Test
    public final void givenCharArrayandText_whenUploadwithAddBinaryBodyandAddTextBody_ThenNoException() throws IOException {
        final String message = "This is a multipart post";
        final byte[] bytes = "binary code".getBytes();
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("file", bytes, ContentType.DEFAULT_BINARY, TEXTFILENAME);
        builder.addTextBody("text", message, ContentType.TEXT_PLAIN);
        final HttpEntity entity = builder.build();
        post.setEntity(entity);
        response = client.execute(post);
        final int statusCode = response.getStatusLine()
          .getStatusCode();
        final String responseString = getContent();
        final String contentTypeInHeader = getContentTypeHeader();
        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        // assertTrue(responseString.contains("Content-Type: multipart/form-data;"));
        assertTrue(contentTypeInHeader.contains("Content-Type: multipart/form-data;"));
        System.out.println(responseString);
        System.out.println("POST Content Type: " + contentTypeInHeader);
    }

    // UTIL

    private String getContent() throws IOException {
        rd = new BufferedReader(new InputStreamReader(response.getEntity()
          .getContent()));
        String body = "";
        StringBuilder content = new StringBuilder();
        while ((body = rd.readLine()) != null) {
            content.append(body).append("\n");
        }
        return content.toString().trim();
    }

    private String getContentTypeHeader() throws IOException {
        return post.getEntity()
          .getContentType()
          .toString();
    }

}
