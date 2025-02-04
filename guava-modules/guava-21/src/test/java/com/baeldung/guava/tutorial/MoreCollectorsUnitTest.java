package com.baeldung.guava.tutorial;

import com.google.common.collect.MoreCollectors;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MoreCollectorsUnitTest {

    @Test
    public void toOptionalTest() {

        List<Integer> numbers = Arrays.asList(1);

        Optional<Integer> number = numbers
          .stream()
          .map(e -> e * 2)
          .collect(MoreCollectors.toOptional());

        Assert.assertEquals(number.get(), Integer.valueOf(2));
    }

    @Test
    public void onlyElementTest() {
        List<Integer> numbers = Arrays.asList(1);

        Integer number = numbers
          .stream()
          .map(e -> e * 2)
          .collect(MoreCollectors.onlyElement());

        Assert.assertEquals(number, Integer.valueOf(2));
    }

}
