package com.baeldung.okhttp.timeout;

import okhttp3.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class OkHttpTimeoutLiveTest {

    private static Logger logger = LoggerFactory.getLogger(OkHttpTimeoutLiveTest.class);

    private static final String HTTP_NON_ROUTABLE_ADDRESS = "http://203.0.113.1";
    private static final String HTTPS_ADDRESS_DELAY_2 = "https://httpbin.org/delay/2";

    @Test
    public void whenConnectTimeoutExceededThenSocketTimeoutException() {
        // Given
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url(HTTP_NON_ROUTABLE_ADDRESS)
                .build();

        // When
        Throwable thrown = catchThrowable(() -> client.newCall(request).execute());

        // Then
        assertThat(thrown).isInstanceOf(SocketTimeoutException.class);

        logThrown(thrown);
    }

    @Test
    public void whenReadTimeoutExceededThenSocketTimeoutException() {
        // Given
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url(HTTPS_ADDRESS_DELAY_2)
                .build();

        // When
        Throwable thrown = catchThrowable(() -> client.newCall(request).execute());

        // Then
        assertThat(thrown).isInstanceOf(SocketTimeoutException.class);

        logThrown(thrown);
    }

    @Test
    public void whenWriteTimeoutExceededThenSocketTimeoutException() {
        // Given
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url(HTTPS_ADDRESS_DELAY_2)
                .post(RequestBody.create(MediaType.parse("text/plain"), create1MBString()))
                .build();

        // When
        Throwable thrown = catchThrowable(() -> client.newCall(request).execute());

        // Then
        assertThat(thrown).isInstanceOf(SocketTimeoutException.class);

        logThrown(thrown);
    }

    @Test
    public void whenCallTimeoutExceededThenInterruptedIOException() {
        // Given
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(1, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(HTTPS_ADDRESS_DELAY_2)
                .build();

        // When
        Throwable thrown = catchThrowable(() -> client.newCall(request).execute());

        // Then
        assertThat(thrown).isInstanceOf(InterruptedIOException.class);

        logThrown(thrown);
    }

    @Test
    public void whenPerRequestTimeoutExtendedThenResponseSuccess() throws IOException {
        // Given
        OkHttpClient defaultClient = new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(HTTPS_ADDRESS_DELAY_2)
                .build();

        Throwable thrown = catchThrowable(() -> defaultClient.newCall(request).execute());

        assertThat(thrown).isInstanceOf(InterruptedIOException.class);

        // When
        OkHttpClient extendedTimeoutClient = defaultClient.newBuilder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        // Then
        Response response = extendedTimeoutClient.newCall(request).execute();
        assertThat(response.code()).isEqualTo(200);
    }

    private void logThrown(Throwable thrown) {
        logger.info("Thrown: ", thrown);
    }

    private String create1MBString() {
        return new String(new char[512 * 1024]);
    }
}
