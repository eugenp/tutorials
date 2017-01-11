package com.baeldung.java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by Steve on 1/10/2017.
 */
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
