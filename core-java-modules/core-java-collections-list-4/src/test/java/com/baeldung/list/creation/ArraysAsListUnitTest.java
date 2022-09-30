package com.baeldung.list.creation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArraysAsListUnitTest {

    @Test
    void givenAnArrayOfIntegersWhenCreatingAListUsingArraysAsListThenItWillHaveTheSameElementsInTheSameOrder() {
        Integer[] array = new Integer[]{1, 2, 3, 4};
        List<Integer> list = Arrays.asList(array);
        assertThat(list).containsExactly(1, 2, 3, 4);
    }

    @Test
    void givenAnListOfIntegersCreatedUsingArraysAsListWhenChangingTheOriginalArrayTheReturnListWillAlsoChange() {
        Integer[] array = new Integer[]{1, 2, 3};
        List<Integer> list = Arrays.asList(array);
        array[0] = 1000;
        assertThat(list.get(0)).isEqualTo(1000);
    }

    @Test
    void givenAnListOfIntegersCreatedUsingArraysAsListWhenChangingTheReturnedListTheOriginalArrayWillAlsoChange() {
        Integer[] array = new Integer[]{1, 2, 3};
        List<Integer> list = Arrays.asList(array);
        list.set(0, 1000);
        assertThat(array[0]).isEqualTo(1000);
    }

    @Test
    void givenAnArrayOfIntegersCreatedUsingArraysAsListWhenModifyingAnExistingElementThenModifyIt() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        list.set(1, 1000);
        assertThat(list.get(1)).isEqualTo(1000);
    }

    @Test
    void givenAnArrayCreatedWithArraysAsListWhenAddingANewElementThenUnsupportedOperationExceptionIsThrown() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        assertThrows(UnsupportedOperationException.class, () -> list.add(6));
    }

    @Test
    void givenAnArrayCreatedWithArraysAsListWhenRemovingAnExistingElementThenUnsupportedOperationExceptionIsThrown() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        assertThrows(UnsupportedOperationException.class, () -> list.remove(1));
    }


}
