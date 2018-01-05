package com.baeldung.java9;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.Test;

public class Java9OptionalTest {
    @Test
    public void givenOptionalOfSome_whenToStream_thenShouldTreatItAsOneElementStream() {
        //given
        Optional<String> value = Optional.of("a");

        //when
        List<String> collect = value.stream().map(String::toUpperCase).collect(Collectors.toList());

        //then
        //assertThat(collect).hasSameElementsAs(List.of("A"));
        assertThat(collect,contains("A"));
        
    }

    @Test
    public void givenOptionalOfNone_whenToStream_thenShouldTreatItAsZeroElementStream() {
        //given
        Optional<String> value = Optional.empty();

        //when
        List<String> collect = value.stream().map(String::toUpperCase).collect(Collectors.toList());

        //then
        //assertThat(collect).isEmpty();
        assertThat(collect, empty());
    }

    @Test
    public void givenOptional_whenPresent_thenShouldExecuteProperCallback() {
        //given
        Optional<String> value = Optional.of("properValue");
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger onEmptyOptionalCounter = new AtomicInteger(0);

        //when
        value.ifPresentOrElse((v) -> successCounter.incrementAndGet(), onEmptyOptionalCounter::incrementAndGet);

        //then
        //assertThat(successCounter.get()).isEqualTo(1);
        //assertThat(onEmptyOptionalCounter.get()).isEqualTo(0);
        assertThat(successCounter.get(),equalTo(1));
        assertThat(onEmptyOptionalCounter.get(),equalTo(0));
    }

    @Test
    public void givenOptional_whenNotPresent_thenShouldExecuteProperCallback() {
        //given
        Optional<String> value = Optional.empty();
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger onEmptyOptionalCounter = new AtomicInteger(0);

        //when
        value.ifPresentOrElse((v) -> successCounter.incrementAndGet(), onEmptyOptionalCounter::incrementAndGet);

        //then
        //assertThat(successCounter.get()).isEqualTo(0);
       // assertThat(onEmptyOptionalCounter.get()).isEqualTo(1);
        assertThat(successCounter.get(),equalTo(0));
        assertThat(onEmptyOptionalCounter.get(),equalTo(1));
    }

    @Test
    public void givenOptional_whenPresent_thenShouldTakeAValueFromIt() {
        //given
        String expected = "properValue";
        Optional<String> value = Optional.of(expected);
        Optional<String> defaultValue = Optional.of("default");

        //when
        Optional<String> result = value.or(() -> defaultValue);

        //then
        //assertThat(result.get()).isEqualTo(expected);
        assertThat(result.get(),equalTo(expected));
    }

    @Test
    public void givenOptional_whenEmpty_thenShouldTakeAValueFromOr() {
        //given
        String defaultString = "default";
        Optional<String> value = Optional.empty();
        Optional<String> defaultValue = Optional.of(defaultString);

        //when
        Optional<String> result = value.or(() -> defaultValue);

        //then
        //assertThat(result.get()).isEqualTo(defaultString);
        assertThat(result.get(),equalTo(defaultString));
    }
}