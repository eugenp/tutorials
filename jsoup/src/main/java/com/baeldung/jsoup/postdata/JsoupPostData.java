package com.baeldung.jsoup.postdata;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.Map;

public class JsoupPostData {
    public String sendPost(String url, Map<String, String> headers, String jsonPayload) throws Exception {
        Connection.Response response = Jsoup.connect(url)
                .headers(headers)
                .requestBody(jsonPayload)
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .execute();

        return response.body();
    }
}
    