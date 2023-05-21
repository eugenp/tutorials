package com.baeldung.jooby;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.jooby.JoobyTest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@JoobyTest(value = App.class, port = 8080)
class AppLiveTest {

    static OkHttpClient client = new OkHttpClient();

    @Test
    void given_defaultUrl_expect_fixedString() {
        Request request = new Request.Builder().url("http://localhost:8080")
            .build();
        try (Response response = client.newCall(request)
            .execute()) {
            assertEquals("Hello World!", response.body()
                .string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
