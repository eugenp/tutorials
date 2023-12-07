package com.baeldung.micronaut.vs.springboot;

import com.baeldung.micronaut.vs.springboot.client.ArithmeticClientImpl;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class ArithmeticClientUnitTest {
    @Inject
    private EmbeddedApplication<?> server;
    @Inject
    private ArithmeticClientImpl client;

    @Test
    public void givenTwoNumbers_whenAdd_thenCorrectAnswerReturned() {
        String expected = Float.valueOf(10 + 20).toString();
        assertEquals(expected, client.sum(10, 20));
    }

    @Test
    public void givenTwoNumbers_whenSubtract_thenCorrectAnswerReturned() {
        String expected = Float.valueOf(20 - 10).toString();
        assertEquals(expected, client.subtract(20, 10));
    }

    @Test
    public void givenTwoNumbers_whenMultiply_thenCorrectAnswerReturned() {
        String expected = Float.valueOf(10 * 20).toString();
        assertEquals(expected, client.multiply(10, 20));
    }

    @Test
    public void givenTwoNumbers_whenDivide_thenCorrectAnswerReturned() {
        String expected = Float.valueOf(30 / 10).toString();
        assertEquals(expected, client.divide(30, 10));
    }

    @Test
    public void whenMemory_thenCorrectAnswerReturned() {
        String expected = "Initial:";
        assertThat(client.memory()).contains(expected);
    }
}
