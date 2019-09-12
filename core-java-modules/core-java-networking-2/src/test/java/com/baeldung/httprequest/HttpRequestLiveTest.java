package com.baeldung.httprequest;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpRequestLiveTest {

    @Test
    public void whenGetRequest_thenOk() throws IOException {
        URL url = new URL("http://example.com");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val");
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        assertEquals("status code incorrect", status, 200);
        assertTrue("content incorrect", content.toString()
            .contains("Example Domain"));
    }

    @Test
    public void whenPostRequest_thenOk() throws IOException {
        URL url = new URL("http://example.com");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val");
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        assertEquals("status code incorrect", status, 200);
    }

    @Test
    public void whenGetCookies_thenOk() throws IOException {
        URL url = new URL("http://example.com");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        CookieManager cookieManager = new CookieManager();
        String cookiesHeader = con.getHeaderField("Set-Cookie");
        Optional<HttpCookie> usernameCookie = null;
        if (cookiesHeader != null) {
            List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);
            cookies.forEach(cookie -> cookieManager.getCookieStore()
                .add(null, cookie));
            usernameCookie = cookies.stream()
                .findAny()
                .filter(cookie -> cookie.getName()
                    .equals("username"));
        }

        if (usernameCookie == null) {
            cookieManager.getCookieStore()
                .add(null, new HttpCookie("username", "john"));
        }

        con.disconnect();

        con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Cookie", StringUtils.join(cookieManager.getCookieStore()
            .getCookies(), ";"));

        int status = con.getResponseCode();

        assertEquals("status code incorrect", status, 200);
    }

    @Test
    public void whenRedirect_thenOk() throws IOException {
        URL url = new URL("http://example.com");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setInstanceFollowRedirects(true);
        int status = con.getResponseCode();

        if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
            String location = con.getHeaderField("Location");
            URL newUrl = new URL(location);
            con = (HttpURLConnection) newUrl.openConnection();
        }

        assertEquals("status code incorrect", con.getResponseCode(), 200);
    }

    @Test
    public void whenFailedRequest_thenOk() throws IOException {
        URL url = new URL("http://example.com");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();

        Reader streamReader = null;

        if (status > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        con.disconnect();

        assertEquals("status code incorrect", status, 411);
        assertTrue("error content", content.toString()
            .contains("411 - Length Required"));
    }

    @Test
    public void whenGetRequestFullResponse_thenOk() throws IOException {
        URL url = new URL("http://example.com");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        String fullResponse = FullResponseBuilder.getFullResponse(con);

        con.disconnect();

        assertEquals("status code incorrect", con.getResponseCode(), 200);
        assertTrue("header incorrect", fullResponse.contains("Content-Type: text/html; charset=UTF-8"));
        assertTrue("response incorrect", fullResponse.contains("<!doctype html><html><head>"));
    }

}
