package com.baeldung.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/*
 * NOTE : Need module spring-rest to be running
 */
public class HttpClientPostingLiveTest {
    private static final String SAMPLE_URL = "http://www.example.com";
    private static final String URL_SECURED_BY_BASIC_AUTHENTICATION = "http://browserspy.dk/password-ok.php";
    private static final String DEFAULT_USER = "test";
    private static final String DEFAULT_PASS = "test";

    @Test
    public void whenSendPostRequestUsingHttpClient_thenCorrect() throws IOException {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(SAMPLE_URL);

        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", DEFAULT_USER));
        params.add(new BasicNameValuePair("password", DEFAULT_PASS));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        final CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    public void whenSendPostRequestWithAuthorizationUsingHttpClient_thenCorrect() throws IOException, AuthenticationException {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(URL_SECURED_BY_BASIC_AUTHENTICATION);

        httpPost.setEntity(new StringEntity("test post"));
        final UsernamePasswordCredentials creds = new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS);
        httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));

        final CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    public void whenPostJsonUsingHttpClient_thenCorrect() throws IOException {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(SAMPLE_URL);

        final String json = "{\"id\":1,\"name\":\"John\"}";
        final StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        final CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    public void whenPostFormUsingHttpClientFluentAPI_thenCorrect() throws IOException {
        final HttpResponse response = Request.Post(SAMPLE_URL).bodyForm(Form.form().add("username", DEFAULT_USER).add("password", DEFAULT_PASS).build()).execute().returnResponse();

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public void whenSendMultipartRequestUsingHttpClient_thenCorrect() throws IOException {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(SAMPLE_URL);

        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("username", DEFAULT_USER);
        builder.addTextBody("password", DEFAULT_PASS);
        builder.addBinaryBody("file", new File("src/test/resources/test.in"), ContentType.APPLICATION_OCTET_STREAM, "file.ext");
        final HttpEntity multipart = builder.build();

        httpPost.setEntity(multipart);

        final CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    public void whenUploadFileUsingHttpClient_thenCorrect() throws IOException {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(SAMPLE_URL);

        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", new File("src/test/resources/test.in"), ContentType.APPLICATION_OCTET_STREAM, "file.ext");
        final HttpEntity multipart = builder.build();

        httpPost.setEntity(multipart);

        final CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    public void whenGetUploadFileProgressUsingHttpClient_thenCorrect() throws IOException {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(SAMPLE_URL);

        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", new File("src/test/resources/test.in"), ContentType.APPLICATION_OCTET_STREAM, "file.ext");
        final HttpEntity multipart = builder.build();

        final ProgressEntityWrapper.ProgressListener pListener = percentage -> assertFalse(Float.compare(percentage, 100) > 0);

        httpPost.setEntity(new ProgressEntityWrapper(multipart, pListener));

        final CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

}