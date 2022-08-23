package com.baeldung.user.check;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserCheckServletLiveTest {
    private static final String BASE_URL = "http://localhost:8080/javax-servlets-2/user-check";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    private CloseableHttpClient buildClient() {
        return HttpClientBuilder.create()
            .setRedirectStrategy(new LaxRedirectStrategy())
            .build();
    }

    @Test
    public void whenCorrectCredentials_thenLoginSucceeds() throws Exception {
        try (CloseableHttpClient client = buildClient()) {
            HttpPost post = new HttpPost(BASE_URL + "/login");

            List<BasicNameValuePair> form = new ArrayList<>();
            form.add(new BasicNameValuePair("name", "admin"));
            form.add(new BasicNameValuePair("password", "password"));

            post.setEntity(new UrlEncodedFormEntity(form));
            try (CloseableHttpResponse response = client.execute(post)) {
                String body = EntityUtils.toString(response.getEntity());

                assertTrue(response.getStatusLine()
                    .getStatusCode() == 200);

                assertTrue(body.contains("login success"));
            }
        }
    }

    @Test
    public void whenIncorrectCredentials_thenLoginFails() throws Exception {
        try (CloseableHttpClient client = buildClient()) {
            HttpPost post = new HttpPost(BASE_URL + "/login");

            List<BasicNameValuePair> form = new ArrayList<>();
            form.add(new BasicNameValuePair("name", "admin"));
            form.add(new BasicNameValuePair("password", "invalid"));

            post.setEntity(new UrlEncodedFormEntity(form));
            try (CloseableHttpResponse response = client.execute(post)) {
                String body = EntityUtils.toString(response.getEntity());

                assertTrue(response.getStatusLine()
                    .getStatusCode() == 401);

                assertTrue(body.contains("invalid login"));
            }
        }
    }

    @Test
    public void whenNotLoggedIn_thenRedirectedToLoginPage() throws Exception {
        try (CloseableHttpClient client = buildClient()) {
            HttpGet get = new HttpGet(BASE_URL + "/home");

            try (CloseableHttpResponse response = client.execute(get)) {
                String body = EntityUtils.toString(response.getEntity());

                assertTrue(response.getStatusLine()
                    .getStatusCode() == 401);

                assertTrue(body.contains("redirected to login"));
            }
        }
    }
}
