package com.baeldung;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.context.Context;

class TriviaServiceUnitTest {

    @Mock
    private OkHttpClient mockHttpClient;

    @Mock
    private okhttp3.Call mockHttpCall;

    @Mock
    private Response mockHttpResponse;

    @Mock
    private ResponseBody mockHttpResponseBody;

    private TriviaService triviaService;

    private String wordServiceUrl;

    @Mock
    private TextMapPropagator mockTextMapPropagator;

    @Mock
    private Context mockContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        triviaService = new TriviaService(mockHttpClient, mockTextMapPropagator);
        wordServiceUrl = "https://localhost:8081/api/words/random";
    }

    @Test
    void whenRequestingAWordFromSource_thenReturnAWordWithDefinition() throws IOException {
        String responseBody = "{\"meaning\":\"Fluent or persuasive in speaking or writing.\",\"word\":\"eloquent\"}";

        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockHttpCall);
        when(mockHttpCall.execute()).thenReturn(mockHttpResponse);
        when(mockHttpResponse.isSuccessful()).thenReturn(true);
        when(mockHttpResponse.body()).thenReturn(mockHttpResponseBody);
        when(mockHttpResponseBody.string()).thenReturn(responseBody);
        when(mockHttpResponse.code()).thenReturn(200);

        WordResponse result = triviaService.requestWordFromSource(mockContext, wordServiceUrl);

        assertEquals(responseBody, result.wordWithDefinition());
        assertEquals(200, result.httpResponseCode());
    }
}