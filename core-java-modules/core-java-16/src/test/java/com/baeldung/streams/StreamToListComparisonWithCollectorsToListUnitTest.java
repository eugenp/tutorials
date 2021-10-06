package com.baeldung.streams;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StreamToListComparisonWithCollectorsToListUnitTest {

    @Test
    public void whenAddingtoCollectToList_thenSuccess() {

        List<String> result = Stream.of(Locale.getISOCountries())
          .collect(Collectors.toList());

        Assertions.assertDoesNotThrow(() -> {
            result.add("test");
        });
    }

    @Test
    public void whenSortingtoCollectToList_thenSuccess() {

        List<String> result = Stream.of(Locale.getISOCountries())
          .collect(Collectors.toList());

        Assertions.assertDoesNotThrow(() -> {
            result.sort(String::compareToIgnoreCase);
        });
    }

    @Test
    public void whenAddingCollectUnmodifiableToList_thenException() {

        List<String> result = Stream.of(Locale.getISOCountries())
          .collect(Collectors.toUnmodifiableList());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            result.add("test");
        });
    }

    @Test
    public void whenSortingCollectUnmodifiableToList_thenSortException() {

        List<String> result = Stream.of(Locale.getISOCountries())
          .collect(Collectors.toUnmodifiableList());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            result.sort(String::compareToIgnoreCase);
        });
    }

    @Test
    public void whenAddingStreamToList_thenException() {

        List<String> result = Stream.of(Locale.getISOCountries())
          .toList();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            result.add("test");
        });
    }

    @Test
    public void whenSortingStreamToList_thenException() {

        List<String> result = Stream.of(Locale.getISOCountries())
          .toList();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            result.sort(String::compareToIgnoreCase);
        });
    }

    @Test
    public void whenComparingStreamToList_withCollectToList_thenEqual() {

        List<String> streamToList = Stream.of(Locale.getISOCountries())
          .toList();
        List<String> collectToList = Stream.of(Locale.getISOCountries())
          .collect(Collectors.toList());

        Assertions.assertEquals(streamToList, collectToList);
    }

    @Test
    public void whenComparingStreamToList_withUnmodifiedCollectToList_thenEqual() {

        List<String> streamToList = Stream.of(Locale.getISOCountries())
          .toList();
        List<String> collectToUnmodifiableList = Stream.of(Locale.getISOCountries())
          .collect(Collectors.toUnmodifiableList());

        Assertions.assertEquals(streamToList, collectToUnmodifiableList);
    }

    @Test
    public void whenNullCollectorsToList_thenSuccess() {

        Assertions.assertDoesNotThrow(() -> {
            Stream.of(null, null)
              .collect(Collectors.toList());
        });
    }

    @Test
    public void whenNullCollectorsUnmodifiableToList_thenException() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            Stream.of(null, null)
              .collect(Collectors.toUnmodifiableList());
        });
    }

    @Test
    public void whenNullStreamToList_thenSuccess() {

        Assertions.assertDoesNotThrow(() -> {
            Stream.of(null, null)
              .toList();
        });
    }

}
