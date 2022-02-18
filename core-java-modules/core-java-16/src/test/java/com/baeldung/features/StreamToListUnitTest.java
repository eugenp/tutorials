package com.baeldung.features;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamToListUnitTest {

    @Test
    void givenAStream_whenCreatingANewListFromStream_thenCollectorsOrInbuiltFunctionAreEquivalent() {
        List<String> integersAsString = Arrays.asList("1", "2", "3");
        List<Integer> ints = integersAsString.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> intsEquivalent = integersAsString.stream().map(Integer::parseInt).toList();
        assertThat(ints).isEqualTo(intsEquivalent);
    }
}
