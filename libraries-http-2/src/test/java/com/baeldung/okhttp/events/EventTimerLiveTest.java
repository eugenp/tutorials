package com.baeldung.okhttp.events;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EventTimerLiveTest {

    @Test
    public void givenSimpleEventTimer_whenRequestSent_thenCallsLogged() throws IOException {
        
        OkHttpClient client = new OkHttpClient.Builder()
            .eventListener(new EventTimer())
            .build();

        Request request = new Request.Builder()
            .url("https://www.baeldung.com/")
            .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals("Response code should be: ", 200, response.code());
        }
    }

}
