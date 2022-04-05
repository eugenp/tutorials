package com.baeldung.java11.httpclient.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class HttpClientUnitTest {

    @Test
    public void shouldReturnSampleDataContentWhenConnectViaSystemProxy() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://postman-echo.com/post"))
            .headers("Content-Type", "text/plain;charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofString("Sample body"))
            .build();
      
        
        HttpResponse<String> response = HttpClient.newBuilder()
            .proxy(ProxySelector.getDefault())
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
        
        assertThat(response.statusCode(), equalTo(HttpURLConnection.HTTP_OK));
        assertThat(response.body(), containsString("Sample body"));
    }

    @Test
    public void shouldNotFollowRedirectWhenSetToDefaultNever() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://stackoverflow.com"))
            .version(HttpClient.Version.HTTP_1_1)
            .GET()
            .build();
        HttpResponse<String> response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode(), equalTo(HttpURLConnection.HTTP_MOVED_PERM));
        assertTrue(response.headers().map().get("location").stream().anyMatch("https://stackoverflow.com/"::equals));
    }

    @Test
    public void shouldFollowRedirectWhenSetToAlways() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://stackoverflow.com"))
            .version(HttpClient.Version.HTTP_1_1)
            .GET()
            .build();
        HttpResponse<String> response = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode(), equalTo(HttpURLConnection.HTTP_OK));
        assertThat(response.request()
            .uri()
            .toString(), equalTo("https://stackoverflow.com/"));
    }

    @Test
    public void shouldReturnOKStatusForAuthenticatedAccess() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://postman-echo.com/basic-auth"))
            .GET()
            .build();
        HttpResponse<String> response = HttpClient.newBuilder()
            .authenticator(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("postman", "password".toCharArray());
                }
            })
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode(), equalTo(HttpURLConnection.HTTP_OK));
    }

    @Test
    public void shouldSendRequestAsync() throws URISyntaxException, InterruptedException, ExecutionException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://postman-echo.com/post"))
            .headers("Content-Type", "text/plain;charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofString("Sample body"))
            .build();
        CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
            .build()
            .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.get()
            .statusCode(), equalTo(HttpURLConnection.HTTP_OK));
    }

    @Test
    public void shouldUseJustTwoThreadWhenProcessingSendAsyncRequest() throws URISyntaxException, InterruptedException, ExecutionException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://postman-echo.com/get"))
            .GET()
            .build();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CompletableFuture<HttpResponse<String>> response1 = HttpClient.newBuilder()
            .executor(executorService)
            .build()
            .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        CompletableFuture<HttpResponse<String>> response2 = HttpClient.newBuilder()
            .executor(executorService)
            .build()
            .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        CompletableFuture<HttpResponse<String>> response3 = HttpClient.newBuilder()
            .executor(executorService)
            .build()
            .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        CompletableFuture.allOf(response1, response2, response3)
            .join();

        assertThat(response1.get()
            .statusCode(), equalTo(HttpURLConnection.HTTP_OK));
        assertThat(response2.get()
            .statusCode(), equalTo(HttpURLConnection.HTTP_OK));
        assertThat(response3.get()
            .statusCode(), equalTo(HttpURLConnection.HTTP_OK));
    }

    @Test
    public void shouldNotStoreCookieWhenPolicyAcceptNone() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://postman-echo.com/get"))
            .GET()
            .build();

        HttpClient httpClient = HttpClient.newBuilder()
            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE))
            .build();

        httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(httpClient.cookieHandler()
            .isPresent());
    }

    @Test
    public void shouldStoreCookieWhenPolicyAcceptAll() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://postman-echo.com/get"))
            .GET()
            .build();

        HttpClient httpClient = HttpClient.newBuilder()
            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
            .build();

        httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(httpClient.cookieHandler()
            .isPresent());
    }

    @Test
    public void shouldProcessMultipleRequestViaStream() throws URISyntaxException, ExecutionException, InterruptedException {
        List<URI> targets = Arrays.asList(new URI("https://postman-echo.com/get?foo1=bar1"), new URI("https://postman-echo.com/get?foo2=bar2"));

        HttpClient client = HttpClient.newHttpClient();

        List<CompletableFuture<String>> futures = targets.stream()
            .map(target -> client.sendAsync(HttpRequest.newBuilder(target)
                .GET()
                .build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> response.body()))
            .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .join();

        if (futures.get(0)
            .get()
            .contains("foo1")) {
            assertThat(futures.get(0)
                .get(), containsString("bar1"));
            assertThat(futures.get(1)
                .get(), containsString("bar2"));
        } else {
            assertThat(futures.get(1)
                .get(), containsString("bar2"));
            assertThat(futures.get(1)
                .get(), containsString("bar1"));
        }

    }
    
    @Test
    public void completeExceptionallyExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
                CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
        CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> { return (th != null) ? "message upon cancel" : ""; });
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        try {
            cf.join();
            fail("Should have thrown an exception");
        } catch (CompletionException ex) { // just for testing
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }

        assertEquals("message upon cancel", exceptionHandler.join());
    }

}
