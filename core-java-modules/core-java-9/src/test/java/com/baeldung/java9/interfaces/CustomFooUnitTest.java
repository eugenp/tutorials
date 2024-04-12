package com.baeldung.java9.interfaces;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class CustomFooUnitTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void givenACustomFooObject_whenCallingDefaultMethodBar_thenExpectedStringIsWrittenToSystemOut() {
        CustomFoo customFoo = new CustomFoo();
        customFoo.bar();
        assertThat(outContent.toString()).isEqualTo("Hello world!");
    }

    @Test
    void givenAFooInterface_whenCallingStaticMethodBuzz_thenExpectedStringIsWrittenToSystemOut() {
        Foo.buzz();
        assertThat(outContent.toString()).isEqualTo("Hello static world!");
    }
}