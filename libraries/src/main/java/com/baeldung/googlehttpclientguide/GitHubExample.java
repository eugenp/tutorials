package com.baeldung.googlehttpclientguide;

import com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandler;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GitHubExample {
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    // static final HttpTransport HTTP_TRANSPORT = new ApacheHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    // static final JsonFactory JSON_FACTORY = new GsonFactory();

    private static void run() throws Exception {
        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory((HttpRequest request) -> {
            request.setParser(new JsonObjectParser(JSON_FACTORY));
        });
        GitHubUrl url = new GitHubUrl("https://api.github.com/users");
        url.per_page = 10;
        url.page = 1;
        HttpRequest request = requestFactory.buildGetRequest(url);
        ExponentialBackOff backoff = new ExponentialBackOff.Builder().setInitialIntervalMillis(500).setMaxElapsedTimeMillis(900000).setMaxIntervalMillis(6000).setMultiplier(1.5).setRandomizationFactor(0.5).build();
        request.setUnsuccessfulResponseHandler(new HttpBackOffUnsuccessfulResponseHandler(backoff));
        Type type = new TypeToken<List<User>>() {
        }.getType();
        List<User> users = (List<User>) request.execute().parseAs(type);
        System.out.println(users);
        url.appendRawPath("/eugenp");
        request = requestFactory.buildGetRequest(url);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<HttpResponse> responseFuture = request.executeAsync(executor);
        User eugen = responseFuture.get().parseAs(User.class);
        System.out.println(eugen);
        executor.shutdown();
    }

    public static void main(String[] args) {
        try {
            run();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
