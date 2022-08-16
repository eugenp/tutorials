package com.baeldung.list.creation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListOfUnitTest {

    @Test
    void givenAnArrayOfStringWhenCreatingAListUsingItsValuesThenItWillHaveTheSameElementsInTheSameOrder() {
        String[] array = new String[]{"one", "two", "three"};
        List<String> list = List.of(array);
        assertThat(list).containsExactly("one", "two", "three");
    }

    @Test
    void givenAnListOfStringCreatedUsingListOfWhenChangingTheOriginalArrayTheReturnListWontChange() {
        String[] array = new String[]{"one", "two", "three"};
        List<String> list = List.of(array);
        array[0] = "thousand";
        assertThat(list.get(0)).isEqualTo("one");
    }

    @Test
    void givenAnArrayCreatedWithListOfWhenAddingANewElementThenUnsupportedOperationExceptionIsThrown() {
        List<String> list = List.of("one", "two", "three");
        assertThrows(UnsupportedOperationException.class, () -> list.add("four"));
    }

    @Test
    void givenAnArrayCreatedWithListOfWhenRemovingAnExistingElementThenUnsupportedOperationExceptionIsThrown() {
        List<String> list = List.of("one", "two", "three");
        assertThrows(UnsupportedOperationException.class, () -> list.remove("two"));
    }

    @Test
    void givenAnArrayCreatedWithListOfWhenModifyingAnExistingElementThenUnsupportedOperationExceptionIsThrown() {
        List<String> list = List.of("one", "two", "three");
        assertThrows(UnsupportedOperationException.class, () -> list.set(1, "four"));
    }

    @Test
    void givenAnArrayContainingNullElementWhenUsingItToCreateListThenNullPointerExceptionIsThrown() {
        assertThrows(NullPointerException.class, () -> List.of("one", null, "two"));
    }


}
