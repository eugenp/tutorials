package com.baeldung.http;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpClientRequestBuilder {

    public HttpResponseWrapper sendGetRequest(String url, Map<String, String> parameters) throws UnsupportedEncodingException {
        HttpClient client = HttpClientBuilder.create()
            .build();
        if (parameters != null) {
            url += "?" + ParameterStringBuilder.getParamsString(parameters);
        }
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);

            HttpResponseWrapper responseWrapper = new HttpResponseWrapper();
            responseWrapper.setStatus(response.getStatusLine()
                .getStatusCode());
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity()
                .getContent()));

            String line, content = "";
            while ((line = in.readLine()) != null) {
                content += line;
            }
            responseWrapper.setContent(content);
            return responseWrapper;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpResponseWrapper sendPostRequestWithParameters(String url, Map<String, String> parameters) {
        HttpClient client = HttpClientBuilder.create()
            .build();
        HttpPost request = new HttpPost(url);

        try {
            if (parameters != null) {
                List<NameValuePair> nameValuePairs = parameters.entrySet().stream()
                  .map(e -> new BasicNameValuePair(e.getKey(), e.getValue()))
                  .collect(Collectors.toList());
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }

            HttpResponse response = client.execute(request);

            HttpResponseWrapper responseWrapper = new HttpResponseWrapper();
            responseWrapper.setStatus(response.getStatusLine()
                .getStatusCode());
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity()
                .getContent()));

            String line, content = "";
            while ((line = in.readLine()) != null) {
                content += line;
            }
            responseWrapper.setContent(content);
            return responseWrapper;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public HttpResponseWrapper sendPostRequestWithJson(String url, String json) {
        HttpClient client = HttpClientBuilder.create()
            .build();
        HttpPost request = new HttpPost(url);

        try {
            request.addHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(json));

            HttpResponse response = client.execute(request);

            HttpResponseWrapper responseWrapper = new HttpResponseWrapper();
            responseWrapper.setStatus(response.getStatusLine()
                .getStatusCode());
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity()
                .getContent()));

            String line, content = "";
            while ((line = in.readLine()) != null) {
                content += line;
            }
            responseWrapper.setContent(content);
            return responseWrapper;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
