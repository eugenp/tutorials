package com.baeldung;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class TriviaService {

    private final OkHttpClient client;

    public TriviaService(OkHttpClient client) {
        this.client = client;
    }
    
    public WordResponse requestWordFromSource(String wordServiceUrl) throws IOException {
        Request request = new Request.Builder()
            .url(wordServiceUrl)
            .build();

        okhttp3.Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected service call error: " + response);
        }

        return new WordResponse(response.body().string(), response.code());
    }
}
