package com.baeldung.java8;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Java8FindAnyFindFirstTest {

    @Test
    public void createStream_whenFindAnyResultIsPresent_thenCorrect() {

        List<String> list = Arrays.asList("A","B","C","D");

        Optional<String> result = list.stream().findAny();

        if(result.isPresent()){
            System.out.println(result.get());
        }

    }

    @Test
    public void createStream_whenFindFirstResultIsPresent_thenCorrect() {

        List<String> list = Arrays.asList("A","B","C","D");

        Optional<String> result = list.stream().findFirst();

        if(result.isPresent()){
            System.out.println(result.get());
        }

    }
}
