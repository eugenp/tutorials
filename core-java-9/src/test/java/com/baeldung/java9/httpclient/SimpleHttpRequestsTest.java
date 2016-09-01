package com.baeldung.java9.httpclient;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

import org.junit.Before;
import org.junit.Test;

public class SimpleHttpRequests {

 //   private URI httpURI =   
    
    @Before
    public void init() {

    }

    @Test
    public void quickGet() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.create(new URI("http://localhost:8080")).GET();
        HttpResponse response = request.response();
        System.out.println(printHeaders(response.headers()));
        String responseBody = response.body(HttpResponse.asString());
        assertTrue("Get response body size",  responseBody.length() > 10);
    }
    
    @Test
    public void asyncGet() throws URISyntaxException, IOException, InterruptedException, ExecutionException{
        HttpRequest request = HttpRequest.create(new URI("http://localhost:8080")).GET();
        long before = System.currentTimeMillis();
        CompletableFuture<HttpResponse> futureResponse = request.responseAsync();
        futureResponse.thenAccept( response -> {
            HttpHeaders hs = response.headers();
            System.out.println(Thread.currentThread()+"\nHeaders:----------------------\n"+ printHeaders(hs));
            String responseBody = response.body(HttpResponse.asString());
            
            
            //System.out.println(responseBody);
        });
        
        
        
                
        long after = System.currentTimeMillis();
        System.out.println(Thread.currentThread()+" waits "+ (after - before));
        assertTrue("Thread waits", (after - before) < 1500);
        
        futureResponse.join();
        
        // Calculate some other thing in this Thread
        //HttpResponse response = futureResponse.get();
        long afterAfter = System.currentTimeMillis();
        System.out.println(Thread.currentThread()+ "(afterAfter - before)"+ (afterAfter - before));
       
        //String responseBody = response.body(HttpResponse.asString());
        //HttpHeaders hs = response.headers();
        //System.out.println(responseBody);
       // assertTrue("Get response body size",  responseBody.length() > 10);
    }
    
    @Test
    public void PostMehtod() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.create(new URI("http://localhost:8080"));
        requestBuilder.body(HttpRequest.fromString("param1=foo,param2=bar")).followRedirects(HttpClient.Redirect.SECURE);
        HttpRequest request = requestBuilder.POST();
        HttpResponse response = request.response();
        int statusCode = response.statusCode();
        assertTrue("HTTP return code", statusCode == HTTP_OK);
    }
    
    @Test
    public void configureHttpClient() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException{
        CookieManager cManager = new CookieManager();
        cManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        
        SSLParameters sslParam = new SSLParameters (new String[] { "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256" }, new String[] { "TLSv1.2" });
        
        HttpClient.Builder hcBuilder = HttpClient.create();
        hcBuilder.cookieManager(cManager).sslContext(SSLContext.getDefault()).sslParameters(sslParam);
        HttpClient httpClient = hcBuilder.build();
        HttpRequest.Builder reqBuilder = httpClient.request(new URI("https://localhost:8443"));
        
        HttpRequest request = reqBuilder.followRedirects(HttpClient.Redirect.ALWAYS).GET();
        HttpResponse response = request.response();
        int statusCode = response.statusCode();
        System.out.println(response.body(HttpResponse.asString()));
        assertTrue("HTTP return code", statusCode == HTTP_OK);
    }
    
    SSLParameters getDefaultSSLParameters() throws NoSuchAlgorithmException{
        SSLParameters sslP1 = SSLContext.getDefault().getSupportedSSLParameters();
        String [] proto = sslP1.getApplicationProtocols();
        String [] cifers = sslP1.getCipherSuites();
        System.out.println(printStringArr(proto));
        System.out.println(printStringArr(cifers));
        return sslP1;
    }
    
    String printStringArr(String ... args ){
        if(args == null){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : args){
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
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
