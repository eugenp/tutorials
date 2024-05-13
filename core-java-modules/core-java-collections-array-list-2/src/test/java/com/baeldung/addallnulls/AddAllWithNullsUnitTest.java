package com.baeldung.addallnulls;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

class AddAllWithNullsUnitTest {

    @ParameterizedTest
    @NullSource
    void givenNull_whenAddAll_thenAddThrowsNPE(List<String> list) {
        ArrayList<String> strings = new ArrayList<>();
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> strings.addAll(list));
    }

    @ParameterizedTest
    @NullSource
    void givenNull_whenAddAllWithCheck_thenNoNPE(List<String> list) {
        ArrayList<String> strings = new ArrayList<>();
        assertThatNoException().isThrownBy(() -> {
            if (list != null) {
                strings.addAll(list);
            }
        });
    }

    @ParameterizedTest
    @NullSource
    void givenNull_whenAddAllWithCollectionCheck_thenNoNPE(List<String> list) {
        ArrayList<String> strings = new ArrayList<>();
        assertThatNoException().isThrownBy(() -> {
            strings.addAll(CollectionUtils.emptyIfNull(list));
        });
    }

    @ParameterizedTest
    @NullSource
    void givenNull_whenAddAllWithExternalizedCheck_thenNoNPE(List<String> list) {
        ArrayList<String> strings = new ArrayList<>();
        assertThatNoException().isThrownBy(() -> {
            addIfNonNull(list, strings);
        });
    }

    private static void addIfNonNull(List<String> list, ArrayList<String> strings) {
        if (list != null) {
            strings.addAll(list);
        }
    }

    @ParameterizedTest
    @NullSource
    void givenNull_whenAddAllWithOptional_thenNoNPE(List<String> list) {
        ArrayList<String> strings = new ArrayList<>();
        assertThatNoException().isThrownBy(() -> {
            Optional.ofNullable(list).ifPresent(strings::addAll);
        });
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    void givenCollectionOfNullableLists_whenFilter_thenNoNPE(List<List<String>> listOfLists) {
        ArrayList<String> strings = new ArrayList<>();
        assertThatNoException().isThrownBy(() -> {
            listOfLists.stream().filter(Objects::nonNull).forEach(strings::addAll);
        });
    }

    static Stream<List<List<String>>> listProvider() {
        ArrayList<List<String>> lists = new ArrayList<>();
        lists.add(null);
        lists.add(List.of("Hello"));
        lists.add(null);
        lists.add(List.of("World"));
        lists.add(null);
        return Stream.of(
          lists
        );
    }
}
