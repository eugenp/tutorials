package com.baeldung.java8;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Java8FindAnyFindFirstTest {

    @Test
    public void createStream_whenFindAnyResultIsPresent_thenCorrect() {

        List<String> list = Arrays.asList("A","B","C","D");

        Optional<String> result = list.stream().findAny();

        assertTrue(result.isPresent());
        assertThat(result.get(), anyOf(is("A"), is("B"), is("C"), is("D")));
    }

    @Test
    public void createStream_whenFindFirstResultIsPresent_thenCorrect() {

        List<String> list = Arrays.asList("A","B","C","D");

        Optional<String> result = list.stream().findFirst();

        assertTrue(result.isPresent());
        assertThat(result.get(),is("A"));
    }
}
