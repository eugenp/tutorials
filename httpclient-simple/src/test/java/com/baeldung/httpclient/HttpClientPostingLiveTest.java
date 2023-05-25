package com.baeldung.httpclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.fluent.Form;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.handler.CustomHttpClientResponseHandler;

/*
 * NOTE : Need module spring-rest to be running
 */
class HttpClientPostingLiveTest {
    private static final String SAMPLE_URL = "http://www.example.com";
    private static final String URL_SECURED_BY_BASIC_AUTHENTICATION = "http://browserspy.dk/password-ok.php";
    private static final String DEFAULT_USER = "test";
    private static final String DEFAULT_PASS = "test";

    @Test
    void whenSendPostRequestUsingHttpClient_thenCorrect() throws IOException {
        final HttpPost httpPost = new HttpPost(SAMPLE_URL);
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", DEFAULT_USER));
        params.add(new BasicNameValuePair("password", DEFAULT_PASS));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        try (CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(httpPost, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    void whenSendPostRequestWithAuthorizationUsingHttpClient_thenCorrect() throws IOException {
        final HttpPost httpPost = new HttpPost(URL_SECURED_BY_BASIC_AUTHENTICATION);
        httpPost.setEntity(new StringEntity("test post"));

        final BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        final UsernamePasswordCredentials credentials =
            new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS.toCharArray());

        credsProvider.setCredentials(new AuthScope(URL_SECURED_BY_BASIC_AUTHENTICATION, 80) ,credentials);

        try (CloseableHttpClient client = HttpClients.custom()
            .setDefaultCredentialsProvider(credsProvider)
            .build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(httpPost, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    void whenPostJsonUsingHttpClient_thenCorrect() throws IOException {
        final HttpPost httpPost = new HttpPost(SAMPLE_URL);

        final String json = "{\"id\":1,\"name\":\"John\"}";
        final StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try (CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(httpPost, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    void whenPostFormUsingHttpClientFluentAPI_thenCorrect() throws IOException {
        Request request = Request.post(SAMPLE_URL)
            .bodyForm(Form.form()
                .add("username", DEFAULT_USER)
                .add("password", DEFAULT_PASS)
                .build());

        HttpResponse response = request.execute()
            .returnResponse();
        assertThat(response.getCode(), equalTo(HttpStatus.SC_OK));
    }

    @Test
    void whenSendMultipartRequestUsingHttpClient_thenCorrect() throws IOException {
        final HttpPost httpPost = new HttpPost(SAMPLE_URL);

        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("username", DEFAULT_USER);
        builder.addTextBody("password", DEFAULT_PASS);
        builder.addBinaryBody(
            "file", new File("src/test/resources/test.in"), ContentType.APPLICATION_OCTET_STREAM, "file.ext");

        final HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);

        try (CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(httpPost, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    void whenUploadFileUsingHttpClient_thenCorrect() throws IOException {
        final HttpPost httpPost = new HttpPost(SAMPLE_URL);

        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody(
            "file", new File("src/test/resources/test.in"), ContentType.APPLICATION_OCTET_STREAM, "file.ext");
        final HttpEntity multipart = builder.build();

        httpPost.setEntity(multipart);

        try (CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(httpPost, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    void whenGetUploadFileProgressUsingHttpClient_thenCorrect() throws IOException {
        final HttpPost httpPost = new HttpPost(SAMPLE_URL);

        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody(
            "file", new File("src/test/resources/test.in"), ContentType.APPLICATION_OCTET_STREAM, "file.ext");
        final HttpEntity multipart = builder.build();

        final ProgressEntityWrapper.ProgressListener pListener =
            percentage -> assertFalse(Float.compare(percentage, 100) > 0);

        httpPost.setEntity(new ProgressEntityWrapper(multipart, pListener));

        try (CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(httpPost, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

}