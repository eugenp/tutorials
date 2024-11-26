package com.baeldung.linkedlistremove;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class DeleteListProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
          Arguments.of(List.of("Monday"), 6),
          Arguments.of(List.of("Monday", "Tuesday"), 5),
          Arguments.of(List.of("Monday", "Tuesday", "Wednesday"), 4),
          Arguments.of(List.of("Monday", "Tuesday", "Wednesday", "Thursday"), 3),
          Arguments.of(List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"), 2),
          Arguments.of(List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"), 1),
          Arguments.of(List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"), 0)
        );
    }
}
