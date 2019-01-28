package com.baeldung.http;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * http request测试
 */
public class HttpRequestLiveTest {

    /**
     * 注意：下例只能访问http协议
     * GET访问
     * @throws IOException
     */
    @Test
    public void whenGetRequest_thenOk() throws IOException {
        URL url = new URL("http://example.com");
        //URL url = new URL("http://www.runoob.com");
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
        System.out.println("content.toString():{}" + content.toString());
        assertTrue("content incorrect", content.toString().contains("Example Domain"));
    }

    /**
     * POST访问
     * @throws IOException
     */
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
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream())
        );
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        assertEquals("status code incorrect", status, 200);
        System.out.println("content.toString():{}" + content.toString());
    }

    /**
     * 获取Cookies
     * @throws IOException
     */
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
            for (HttpCookie httpCookie : cookies) {
                cookieManager.getCookieStore().add(null, httpCookie);
            }
            usernameCookie = cookies.stream().findAny().filter(new Predicate<HttpCookie>() {
                @Override
                public boolean test(HttpCookie cookie) {
                    return cookie.getName().equals("username");
                }
            });
        }

        if (usernameCookie == null) {
            cookieManager.getCookieStore().add(null, new HttpCookie("username", "john"));
        }

        con.disconnect();

        con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Cookie", StringUtils.join(cookieManager.getCookieStore().getCookies(), ";"));

        int status = con.getResponseCode();
        assertEquals("status code incorrect", status, 200);
    }

    /**
     * 重定向测试
     * @throws IOException
     */
    @Test
    public void whenRedirect_thenOk() throws IOException {
        //URL url = new URL("http://example.com");
        URL url = new URL("https://www.baidu.com");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setInstanceFollowRedirects(true);
        int status = con.getResponseCode();
        System.out.println("status:{}" + status);

        if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
            String location = con.getHeaderField("Location");
            System.out.println("location:{}" + location);
            URL newUrl = new URL(location);
            con = (HttpURLConnection) newUrl.openConnection();
        }

        assertEquals("status code incorrect", con.getResponseCode(), 200);
    }

}
