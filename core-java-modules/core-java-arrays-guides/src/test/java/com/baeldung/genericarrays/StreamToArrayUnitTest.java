package com.baeldung.genericarrays;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StreamToArrayUnitTest {

    @Test
    public void givenAStream_thenCanGetArrayOfObject() {
        Object[] strings = Stream.of("A", "AAA", "B", "AAB", "C")
          .filter(string -> string.startsWith("A"))
          .toArray();

        assertThat(strings).containsExactly("A", "AAA", "AAB");
        assertThat(strings).isNotInstanceOf(String[].class);
    }

    @Test
    public void givenAStream_thenCanGetArrayOfString() {
        String[] strings = Stream.of("A", "AAA", "B", "AAB", "C")
          .filter(string -> string.startsWith("A"))
          .toArray(String[]::new);

        assertThat(strings).containsExactly("A", "AAA", "AAB");
        assertThat(strings).isInstanceOf(String[].class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void givenAStream_whenConvertToOptional_thenCanGetArrayOfOptional() {
        Stream<Optional<String>> stream = Stream.of("A", "AAA", "B", "AAB", "C")
                .filter(string -> string.startsWith("A"))
                .map(Optional::of);
        Optional<String>[] strings = stream
          .toArray(Optional[]::new);

        assertThat(strings).containsExactly(Optional.of("A"),
          Optional.of("AAA"),
          Optional.of("AAB"));
        assertThat(strings).isInstanceOf(Optional[].class);
    }

    @Test
    public void givenAStream_whenConvertToOptional_thenCanGetArrayOfOptionalWithHelper() {
        Optional<String>[] strings = Stream.of("A", "AAA", "B", "AAB", "C")
          .filter(string -> string.startsWith("A"))
          .map(Optional::of)
          .toArray(genericArray(Optional[]::new));

        assertThat(strings).containsExactly(Optional.of("A"),
          Optional.of("AAA"),
          Optional.of("AAB"));
        assertThat(strings).isInstanceOf(Optional[].class);
    }

    @Test
    public void whenInvalidUseOfGenericArray_thenIllegalCast() {
        assertThatThrownBy(() -> {
            ArrayList<String>[] lists = Stream.of(singletonList("A"))
                    .toArray(genericArray(List[]::new));
        }).isInstanceOf(ClassCastException.class);
    }

    @SuppressWarnings("unchecked")
    private static <T, R extends T> IntFunction<R[]> genericArray(IntFunction<T[]> arrayCreator) {
        return size -> (R[])arrayCreator.apply(size);
    }
}
