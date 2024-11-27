package com.baeldung;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.context.Context;

public class TriviaService {

    private final OkHttpClient client;
    private TextMapPropagator textMapPropagator;

    public TriviaService(OkHttpClient client, TextMapPropagator textMapPropagator) {
        this.client = client;
        this.textMapPropagator = textMapPropagator;
    }
    
    public WordResponse requestWordFromSource(Context context, String wordServiceUrl) throws IOException {
        Request request = new Request.Builder()
            .url(wordServiceUrl)
            .build();

        Request.Builder requestBuilder = request.newBuilder();
        textMapPropagator.inject(context, requestBuilder, Request.Builder::addHeader);

        okhttp3.Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected service call error: " + response);
        }

        return new WordResponse(response.body().string(), response.code());
    }
}
