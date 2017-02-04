package com.baeldung.javaslang.exception.handling;

import com.baeldung.javaslang.exception.handling.client.ClientException;
import com.baeldung.javaslang.exception.handling.client.HttpClient;
import com.baeldung.javaslang.exception.handling.client.Response;
import javaslang.collection.Stream;
import javaslang.control.Option;
import javaslang.control.Try;
import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Collectors;

import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;
import static org.junit.Assert.*;

public class JavaslangTryTest {

    @Test
    public void givenHttpClient_whenMakeACall_shouldReturnSuccess() {
        //given
        Integer defaultChainedResult = 1;
        String id = "a";
        HttpClient httpClient = () -> new Response(id);

        //when
        Try<Response> response = new JavaslangTry(httpClient).getResponse();
        Integer chainedResult = response
                .map(this::actionThatTakesResponse)
                .getOrElse(defaultChainedResult);
        Stream<String> stream = response.toStream().map(it -> it.id);

        //then
        assertTrue(!stream.isEmpty());
        assertTrue(response.isSuccess());
        response.onSuccess(r -> assertEquals(id, r.id));
        response.andThen(r -> assertEquals(id, r.id));
        assertNotEquals(defaultChainedResult, chainedResult);
    }

    @Test
    public void givenHttpClientFailure_whenMakeACall_shouldReturnFailure() {
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
        Option<Response> optionalResponse = response.toOption();

        //then
        assertTrue(optionalResponse.isEmpty());
        assertTrue(response.isFailure());
        response.onFailure(ex -> assertTrue(ex instanceof ClientException));
        assertEquals(defaultChainedResult, chainedResult);
    }

    @Test
    public void givenHttpClientThatFailure_whenMakeACall_shouldReturnFailureAndNotRecover() {
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
    public void givenHttpClientThatFailure_whenMakeACall_shouldReturnFailureAndRecover() {
        //given
        Response defaultResponse = new Response("b");
        HttpClient httpClient = () -> {
            throw new ClientException("non critical problem");
        };

        //when
        Try<Response> recovered = new JavaslangTry(httpClient).getResponse()
                .recover(r -> Match(r).of(
                        Case(instanceOf(ClientException.class), defaultResponse),
                        Case(instanceOf(IllegalArgumentException.class), defaultResponse)
                ));

        //then
        assertTrue(recovered.isSuccess());
    }


    public int actionThatTakesResponse(Response response) {
        return response.id.hashCode();
    }

    public int actionThatTakesTryResponse(Try<Response> response, int defaultTransformation){
        return response.transform(responses -> response.map(it -> it.id.hashCode()).getOrElse(defaultTransformation));
    }

}