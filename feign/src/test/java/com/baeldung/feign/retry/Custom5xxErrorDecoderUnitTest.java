package com.baeldung.feign.retry;

import feign.*;
import feign.codec.ErrorDecoder;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Custom5xxErrorDecoderUnitTest {
    @Test
    public void given5xxResponse_whenDecode_thenReturnRetryableException() {
        // given
        ErrorDecoder decoder = new Custom5xxErrorDecoder();
        Response response = responseStub(500);

        // when
        Exception exception = decoder.decode("GET", response);

        // then
        assertTrue(exception instanceof RetryableException);
    }

    @Test
    public void given4xxResponse_whenDecode_thenReturnFeignException() {
        // given
        ErrorDecoder decoder = new Custom5xxErrorDecoder();
        Response response = responseStub(400);

        // when
        Exception exception = decoder.decode("GET", response);

        // then
        assertTrue(exception instanceof FeignException);
        assertFalse(exception instanceof RetryableException);
    }

    @NotNull
    private Response responseStub(int status) {
        return Response.builder()
                .request(Request.create(
                        Request.HttpMethod.GET, "url", new HashMap<String, Collection<String>>(), new byte[0], Charset.defaultCharset(), new RequestTemplate()))
                .status(status)
                .build();
    }
}
