package com.baeldung.streams.conversion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class EnumerationStreamConversionUnitTest {

    @Test
    public void givenEnumeration_whenConvertedToStream_thenNotNull() {
        Vector<Integer> input = new Vector<>(Arrays.asList(1, 2, 3, 4, 5));

        Stream<Integer> resultingStream = EnumerationStreamConversion.convert(input.elements());

        Assert.assertNotNull(resultingStream);
    }

    @Test
    public void whenConvertedToList_thenCorrect() {
        Vector<Integer> input = new Vector<>(Arrays.asList(1, 2, 3, 4, 5));

        Stream<Integer> stream = EnumerationStreamConversion.convert(input.elements());
        List<Integer> list = stream.filter(e -> e >= 3)
            .collect(Collectors.toList());
        assertThat(list, contains(3, 4, 5));
    }
}
