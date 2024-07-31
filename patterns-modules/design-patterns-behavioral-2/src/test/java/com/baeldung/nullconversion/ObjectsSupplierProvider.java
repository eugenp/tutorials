package com.baeldung.nullconversion;

import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class ObjectsSupplierProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Supplier<String> supplier = () -> "default";
        return Stream.of(
          Arguments.of(null, "default", supplier),
          Arguments.of("not null", "default", supplier)
        );

    }
}
