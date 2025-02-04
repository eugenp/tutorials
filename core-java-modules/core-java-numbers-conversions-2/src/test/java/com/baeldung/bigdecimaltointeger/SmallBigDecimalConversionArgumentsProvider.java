package com.baeldung.bigdecimaltointeger;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class SmallBigDecimalConversionArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
          Arguments.of(BigDecimal.valueOf(0), 0),
          Arguments.of(BigDecimal.valueOf(1), 1),
          Arguments.of(BigDecimal.valueOf(2), 2),
          Arguments.of(BigDecimal.valueOf(3), 3),
          Arguments.of(BigDecimal.valueOf(4), 4),
          Arguments.of(BigDecimal.valueOf(5), 5),
          Arguments.of(BigDecimal.valueOf(6), 6),
          Arguments.of(BigDecimal.valueOf(7), 7),
          Arguments.of(BigDecimal.valueOf(8), 8),
          Arguments.of(BigDecimal.valueOf(9), 9),
          Arguments.of(BigDecimal.valueOf(10), 10),
          Arguments.of(BigDecimal.valueOf(11), 11),
          Arguments.of(BigDecimal.valueOf(12), 12),
          Arguments.of(BigDecimal.valueOf(13), 13),
          Arguments.of(BigDecimal.valueOf(14), 14),
          Arguments.of(BigDecimal.valueOf(15), 15)
        );
    }

}
