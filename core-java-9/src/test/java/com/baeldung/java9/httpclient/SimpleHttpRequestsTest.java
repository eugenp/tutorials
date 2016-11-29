package com.baeldung.java9.httpclient;         



import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

import org.junit.Before;
import org.junit.Test;

public class SimpleHttpRequestsTest {

    private URI httpURI; 
    private static final String jsonBody = "{  \"object\": { \"a\": \"b\", \"c\": \"d\" }," +
  "\"array\": [ 1, 2 ], \"string\": \"Hello World\" }";
    @Before
    public void init() throws URISyntaxException {
        httpURI = new URI("http://www.baeldung.com/");
    }

    @Test
    public void quickGet() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.create( httpURI ).GET();
        HttpResponse response = request.response();
        int responseStatusCode = response.statusCode();
        String responseBody = response.body(HttpResponse.asString());
        assertTrue("Get response status code is bigger then 400",  responseStatusCode < 400);
    }
    
    @Test
    public void postMehtod() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.create(httpURI);
        requestBuilder.body(HttpRequest.fromString("param1=foo,param2=bar")).followRedirects(HttpClient.Redirect.SECURE);
        HttpRequest request = requestBuilder.POST();
        HttpResponse response = request.response();
        int statusCode = response.statusCode();
        assertTrue("HTTP return code", statusCode == HTTP_OK);
    }
    
    @Test
    public void putMehtod() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.create(httpURI);
        requestBuilder.setHeader("content-type", "application/json")
                      .body(HttpRequest.fromFile(
                              (new File("target/test-classes/request_body.json")).toPath()));
        HttpRequest request = requestBuilder.PUT();
        HttpResponse response = request.response();
        int statusCode = response.statusCode();
        assertTrue("HTTP return code", statusCode < 500);
    }
    
    @Test
    public void customeHeaderRequest() throws IOException, InterruptedException {
        Builder requestBuilder = HttpRequest.create(httpURI).
                header("Foo", "Bar").
                headers("Header1", "Value1", "Header2", "Value2");
        HttpRequest request = requestBuilder.GET();

        HttpResponse response = request.response();
        int responseStatusCode = response.statusCode();
        String responseBody = response.body(HttpResponse.asString());
        assertTrue("Get response status code is bigger then 400", responseStatusCode < 400);
    }
    
    String printHeaders(HttpHeaders h){
        if(h == null){
            return null;
        }
        
       StringBuilder sb = new StringBuilder();
       Map<String, List<String>> hMap = h.map();
       for(String k : hMap.keySet()){
           sb.append(k).append(":");
           List<String> l =  hMap.get(k);
           if( l != null ){
               l.forEach( s -> { sb.append(s).append(","); } );
           }
           sb.append("\n");
       }
       return sb.toString();
    }
}
