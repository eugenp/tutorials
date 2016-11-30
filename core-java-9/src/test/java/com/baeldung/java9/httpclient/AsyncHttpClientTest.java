package com.baeldung.java9.httpclient;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.security.auth.login.LoginException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;

public class AsyncHttpClientTest {

    private static final int size = 3;
    private URI [] URIs = new URI[size] ;
    private String status;
    private HttpRequest [] requests = new HttpRequest[size];
    private CompletableFuture<HttpResponse>[] futureResponses = new CompletableFuture[size];
    private static final AtomicInteger counter = new AtomicInteger(0);

    @Before
    public void init() throws URISyntaxException {
        URIs[0] = new URI("http://www.baeldung.com/");
        URIs[1] = new URI("http://www.baeldung.com/category/java/");
        URIs[2] = new URI("http://www.baeldung.com/category/http/");
    }

    @Test
    public void asynchronousGet() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        long before = System.currentTimeMillis();
        CompletableFuture<HttpResponse> futureResponses0 = HttpRequest.create(URIs[0]).GET().responseAsync();
        CompletableFuture<HttpResponse> futureResponses1 = futureResponses0.thenCompose(this::checkResponseAndFireRequest);
        futureResponses1.thenAccept(this::processResponse);
        
        futureResponses1.get();
        System.out.println("End.");
        
        
        
//        for(int i=0; i< URIs.length; i++){
//            futureResponses[i] = HttpRequest.create(URIs[i]).GET().responseAsync().exceptionally(this::processException);
//                    
//            //futureResponses[i].get();
//        }
        //futureResponses[0].thenAccept(this::processResponse).
        
//        futureResponses[0].exceptionally(this::processException)
//            .thenAccept(this::processResponse);
        
        
//        HttpResponse resp = futureResponses[0].get();
//        HttpHeaders hs = resp.headers();
//        assertTrue("There should be more then 1 header.", hs.map().size() > 1);
    }

    
    private CompletableFuture<HttpResponse> checkResponseAndFireRequest(HttpResponse response){
    	if(response.statusCode() == 200){
    		System.out.println("Success");
    		int c = counter.incrementAndGet();
    		return HttpRequest.create(URIs[c]).GET().responseAsync();
    	}else
    		return null;
    }
    
    
   private void processResponse(HttpResponse response) {
       String responseBody = response.body(HttpResponse.asString());
       if(! responseBody.contains("Login Success")){
           status = "FAIL";
       }else{
           status = "SUCCESS";
       }
       System.out.println("Status is "+ status);
   } 
   
   private HttpResponse processException(Throwable th){
       System.out.println("Exception Caught");
       th.printStackTrace();
       return null;
   }
}
