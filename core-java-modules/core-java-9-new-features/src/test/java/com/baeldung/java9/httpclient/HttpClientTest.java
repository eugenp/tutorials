package com.baeldung.java9.httpclient;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by adam.
 */
public class HttpClientTest {

    @Test
    public void shouldReturnSampleDataContentWhenConnectViaSystemProxy() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://postman-echo.com/post"))
            .headers("Content-Type", "text/plain;charset=UTF-8")
            .POST(HttpRequest.BodyProcessor.fromString("Sample body"))
            .build();

        HttpResponse<String> response = HttpClient.newBuilder()
            .proxy(ProxySelector.getDefault())
            .build()
            .send(request, HttpResponse.BodyHandler.asString());

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
            .send(request, HttpResponse.BodyHandler.asString());

        assertThat(response.statusCode(), equalTo(HttpURLConnection.HTTP_MOVED_PERM));
        assertThat(response.body(), containsString("https://stackoverflow.com/"));
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
            .send(request, HttpResponse.BodyHandler.asString());

        assertThat(response.statusCode(), equalTo(HttpURLConnection.HTTP_OK));
        assertThat(response.finalRequest()
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
            .send(request, HttpResponse.BodyHandler.asString());

        assertThat(response.statusCode(), equalTo(HttpURLConnection.HTTP_OK));
    }

    @Test
    public void shouldSendRequestAsync() throws URISyntaxException, InterruptedException, ExecutionException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://postman-echo.com/post"))
            .headers("Content-Type", "text/plain;charset=UTF-8")
            .POST(HttpRequest.BodyProcessor.fromString("Sample body"))
            .build();
        CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
            .build()
            .sendAsync(request, HttpResponse.BodyHandler.asString());

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
            .sendAsync(request, HttpResponse.BodyHandler.asString());

        CompletableFuture<HttpResponse<String>> response2 = HttpClient.newBuilder()
            .executor(executorService)
            .build()
            .sendAsync(request, HttpResponse.BodyHandler.asString());

        CompletableFuture<HttpResponse<String>> response3 = HttpClient.newBuilder()
            .executor(executorService)
            .build()
            .sendAsync(request, HttpResponse.BodyHandler.asString());

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
            .cookieManager(new CookieManager(null, CookiePolicy.ACCEPT_NONE))
            .build();

        httpClient.send(request, HttpResponse.BodyHandler.asString());

        assertThat(httpClient.cookieManager()
            .get()
            .getCookieStore()
            .getCookies(), empty());
    }

    @Test
    public void shouldStoreCookieWhenPolicyAcceptAll() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://postman-echo.com/get"))
            .GET()
            .build();

        HttpClient httpClient = HttpClient.newBuilder()
            .cookieManager(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
            .build();

        httpClient.send(request, HttpResponse.BodyHandler.asString());

        assertThat(httpClient.cookieManager()
            .get()
            .getCookieStore()
            .getCookies(), not(empty()));
    }

    @Test
    public void shouldProcessMultipleRequestViaStream() throws URISyntaxException, ExecutionException, InterruptedException {
        List<URI> targets = Arrays.asList(new URI("https://postman-echo.com/get?foo1=bar1"), new URI("https://postman-echo.com/get?foo2=bar2"));

        HttpClient client = HttpClient.newHttpClient();

        List<CompletableFuture<String>> futures = targets.stream()
            .map(target -> client.sendAsync(HttpRequest.newBuilder(target)
                .GET()
                .build(), HttpResponse.BodyHandler.asString())
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

}
