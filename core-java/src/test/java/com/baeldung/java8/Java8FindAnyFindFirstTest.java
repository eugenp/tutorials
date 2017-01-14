package com.baeldung.java8;


import org.assertj.core.condition.AnyOf;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Java8FindAnyFindFirstTest {

    @Test
    public void createStream_whenFindAnyResultIsPresent_thenCorrect() {

        List<String> list = Arrays.asList("A","B","C","D");

        Optional<String> result = list.stream().findAny();

        assert result.isPresent();
        assertThat(result.get(), anyOf(is("A"), is("B"), is("C"), is("D")));
    }

    @Test
    public void createStream_whenFindFirstResultIsPresent_thenCorrect() {

        List<String> list = Arrays.asList("A","B","C","D");

        Optional<String> result = list.stream().findFirst();

        assert result.isPresent();
        assertThat(result.get(),is("A"));
    }
}
