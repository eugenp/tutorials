package com.baeldung.linkedlistremove;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class CreateListProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
          Arguments.of(List.of("January"), 1),
          Arguments.of(List.of("January", "February"), 2),
          Arguments.of(List.of("January", "February", "March"), 3),
          Arguments.of(List.of("January","February","March","April"), 4),
          Arguments.of(List.of("January","February","March","April","May"), 5),
          Arguments.of(List.of("January","February","March","April","May","June"), 6),
          Arguments.of(List.of("January","February","March","April","May","June","July"), 7),
          Arguments.of(List.of("January","February","March","April","May","June","July","August"), 8),
          Arguments.of(List.of("January","February","March","April","May","June","July","August","September"), 9),
          Arguments.of(List.of("January","February","March","April","May","June","July","August","September","October"), 10),
          Arguments.of(List.of("January","February","March","April","May","June","July","August","September","October","November"), 11),
          Arguments.of(List.of("January","February","March","April","May","June","July","August","September","October","November","December"), 12)
        );
    }
}
