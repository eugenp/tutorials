package com.baeldung.java9.httpclient;

import org.junit.After;
import static org.junit.Assert.assertTrue;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.security.auth.login.LoginException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.mockserver.client.server.MockServerClient;
import static org.mockserver.integration.ClientAndProxy.startClientAndProxy;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;
import static java.util.concurrent.TimeUnit.SECONDS;

public class AsyncHttpClientTest {

    private static final int size = 3;
    private ArrayList<URI> URIs = new ArrayList<>();
    private Iterator<URI> uriIterator;
    private String status;
    private HttpRequest [] requests = new HttpRequest[size];
    private CompletableFuture<HttpResponse>[] futureResponses = new CompletableFuture[size];
    private static final AtomicInteger counter = new AtomicInteger(0);
    private ClientAndProxy proxy;
    private ClientAndServer mockServer;
    private static final String serverHost = "localhost";
    private static final int serverPort = 1080;
    private static final String baseURL = "http://"+serverHost+":"+ serverPort;
    
    @Before
    public void init() throws URISyntaxException {
        URIs.add( new URI("http://www.baeldung.com/") );
        URIs.add( new URI("http://www.baeldung.com/category/java/") );
        URIs.add( new URI("http://www.baeldung.com/category/http/") );
        uriIterator = URIs.iterator();
        
        setupMockHttpServer();
    }

    @Test
    public void asynchronousGet() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        long before = System.currentTimeMillis();
        CompletableFuture<HttpResponse> futureResponses0 = HttpRequest.create(uriIterator.next()).GET().responseAsync();
        CompletableFuture<HttpResponse> futureResponses1 = futureResponses0.thenCompose(this::checkResponseAndFireRequest);
        futureResponses1.thenAccept(this::processResponseBody);
        
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
    		return HttpRequest.create(uriIterator.next()).GET().responseAsync();
    	}else
    		return null;
    }
    
    
   private void processResponseBody(HttpResponse response) {
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
   
   @After
   public void stopProxy() {
       proxy.stop();
       mockServer.stop();
   }
   
   private void setupMockHttpServer() {
	   mockServer = startClientAndServer(serverPort);
	   proxy = startClientAndProxy(serverPort + 10);
	   
	   new MockServerClient("127.0.0.1", serverPort)
       .when(
               request()
                       .withMethod("POST")
                       .withPath("/login")
                       .withQueryStringParameters(
                               new Parameter("returnUrl", "/account")
                       )
                       //.withCookies(
                       //        new Cookie("sessionId", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
                       //)
                       .withBody(exact("{username: 'foo', password: 'bar'}")),
               exactly(1)
       )
       .respond(
               response()
                       .withStatusCode(401)
                       .withHeaders(
                               new Header("Content-Type", "application/json; charset=utf-8"),
                               new Header("Cache-Control", "public, max-age=86400")
                       )
                       .withBody("{ message: 'incorrect username and password combination' }")
                       .withDelay(new Delay(SECONDS, 1))
       );
   }
   
   
}
