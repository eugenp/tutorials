package com.baeldung.javaslang.exception.handling;

import com.baeldung.javaslang.exception.handling.client.ClientException;
import com.baeldung.javaslang.exception.handling.client.HttpClient;
import com.baeldung.javaslang.exception.handling.client.Response;
import javaslang.control.Try;
import org.junit.Test;

import java.util.function.Function;

import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;
import static org.junit.Assert.*;

public class JavaslangTryTest {

    @Test
    public void givenHttpClientThatSuccess_whenMakeACall_shouldReturnTryOfSuccess() {
        //given
        Integer defaultChainedResult = 1;
        String id = "a";
        HttpClient httpClient = () -> new Response(id);

        //when
        Try<Response> response = new JavaslangTry(httpClient).getResponse();
        Integer chainedResult = response
                .map(this::actionThatTakesResponse)
                .getOrElse(defaultChainedResult);

        //then
        assertTrue(response.isSuccess());
        response.onSuccess(r -> assertEquals(id, r.id));
        assertNotEquals(defaultChainedResult, chainedResult);
    }

    @Test
    public void givenHttpClientThatFailure_whenMakeACall_shouldReturnTryOfFailure() {
        //given
        Integer defaultChainedResult = 1;
        HttpClient httpClient = () -> {
            throw new ClientException("problem");
        };

        //when
        Try<Response> response = new JavaslangTry(httpClient).getResponse();
        Integer chainedResult = response
                .map(this::actionThatTakesResponse)
                .getOrElse(defaultChainedResult);

        //then
        assertTrue(response.isFailure());
        response.onFailure(ex -> assertTrue(ex instanceof ClientException));
        assertEquals(defaultChainedResult, chainedResult);
    }

    @Test
    public void givenHttpClientThatFailure_whenMakeACall_shouldReturnTryOfFailureAndNotRecoverFromCriticalProblem() {
        //given
        Response defaultResponse = new Response("b");
        HttpClient httpClient = () -> {
            throw new RuntimeException("critical problem");
        };

        //when
        Try<Response> recovered = new JavaslangTry(httpClient).getResponse()
                .recover(r -> Match(r).of(
                        Case(instanceOf(ClientException.class), defaultResponse)
                ));

        //then
        assertTrue(recovered.isFailure());

//        recovered.getOrElseThrow(throwable -> {
//            throw new RuntimeException(throwable);
//        });
    }

    @Test
    public void givenHttpClientThatFailure_whenMakeACall_shouldReturnTryOfFailureAndNotRecoverFromNonCriticalProblem() {
        //given
        Response defaultResponse = new Response("b");
        HttpClient httpClient = () -> {
            throw new ClientException("non critical problem");
        };

        //when
        Try<Response> recovered = new JavaslangTry(httpClient).getResponse()
                .recover(r -> Match(r).of(
                        Case(instanceOf(ClientException.class), defaultResponse)
                ));

        //then
        assertTrue(recovered.isSuccess());
    }


    public int actionThatTakesResponse(Response response) {
        return response.id.hashCode();
    }

}