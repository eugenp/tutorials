package com.baeldung.spring.data.persistence.findvsget;


import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class UserProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        return userSource();
    }

    static Stream<Arguments> userSource() {
        return Stream.of(
            Arguments.of(1L, "Saundra", "Krystek"),
            Arguments.of(2L, "Korey", "Venners"),
            Arguments.of(3L, "Lory", "Daffey"),
            Arguments.of(4L, "Michail", "Spinella"),
            Arguments.of(5L, "Emanuel", "Geertje"),
            Arguments.of(6L, "Jervis", "Waugh"),
            Arguments.of(7L, "Chantal", "Soldan"),
            Arguments.of(8L, "Darnall", "Fanner"),
            Arguments.of(9L, "Cordelia", "Hindge"),
            Arguments.of(10L, "Lem", "Pitcock")
        );
    }
}
