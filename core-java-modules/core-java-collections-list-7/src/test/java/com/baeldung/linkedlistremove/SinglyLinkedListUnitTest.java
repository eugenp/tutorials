package com.baeldung.linkedlistremove;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;

class SinglyLinkedListUnitTest {

    private SinglyLinkedList<String> linkedList;

    @BeforeEach
    void setup() {
        linkedList = new SinglyLinkedList<>();
    }

    @Nested
    @DisplayName("Given an empty list")
    class GivenEmptyLinkedList {


        @Test
        void whenNewListThenListEmpty() {
            assertThat(linkedList.isEmpty()).isTrue();
        }

        @Test
        void whenAddElementThenListNotEmpty() {
            linkedList.add("Hello world!");
            assertThat(linkedList.isEmpty()).isFalse();
        }

        @ParameterizedTest
        @ArgumentsSource(CreateListProvider.class)
        void whenAddingElementsThenSizeIsCorrect(List<String> toAdd, int expectedSize) {
            toAdd.forEach(linkedList::add);
            assertThat(linkedList.size()).isEqualTo(expectedSize);
            toAdd.forEach(s -> assertThat(linkedList.contains(s)).isTrue());
        }

    }

    @Nested
    @DisplayName("Given a filled list")
    class GivenFilledLinkedList {

        private final List<String> SOURCE =
          List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

        @BeforeEach
        void setup() {
            SOURCE.forEach(linkedList::add);
        }

        @ParameterizedTest
        @ArgumentsSource(DeleteListProvider.class)
        void whenDeleteElementsSizeIsCorrect(List<String> toDelete, int expectedSize) {
            toDelete.forEach(linkedList::remove);
            assertThat(linkedList.size()).isEqualTo(expectedSize);
            toDelete.forEach(s -> assertThat(linkedList.contains(s)).isFalse());
        }

        @ParameterizedTest
        @CsvSource({
          "1,6",
          "2,5",
          "3,4",
          "4,3",
        })
        void whenDeleteElementsSizeIsCorrect(int toRemove, int expectedSize) {
            for (int i = 0; i < toRemove; i++) {
                linkedList.removeLast();
            }
            assertThat(linkedList.size()).isEqualTo(expectedSize);
            SOURCE.subList(0, expectedSize).forEach(s -> assertThat(linkedList.contains(s)).isTrue());
        }
    }

}
