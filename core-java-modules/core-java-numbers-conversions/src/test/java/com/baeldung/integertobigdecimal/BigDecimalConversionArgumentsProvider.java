package com.baeldung.integertobigdecimal;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class BigDecimalConversionArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
          Arguments.of(Integer.valueOf(0), BigDecimal.valueOf(0)),
          Arguments.of(Integer.valueOf(1), BigDecimal.valueOf(1)),
          Arguments.of(Integer.valueOf(2), BigDecimal.valueOf(2)),
          Arguments.of(Integer.valueOf(3), BigDecimal.valueOf(3)),
          Arguments.of(Integer.valueOf(4), BigDecimal.valueOf(4)),
          Arguments.of(Integer.valueOf(5), BigDecimal.valueOf(5)),
          Arguments.of(Integer.valueOf(6), BigDecimal.valueOf(6)),
          Arguments.of(Integer.valueOf(7), BigDecimal.valueOf(7)),
          Arguments.of(Integer.valueOf(8), BigDecimal.valueOf(8)),
          Arguments.of(Integer.valueOf(9), BigDecimal.valueOf(9)),
          Arguments.of(Integer.valueOf(10), BigDecimal.valueOf(10)),
          Arguments.of(Integer.valueOf(11), BigDecimal.valueOf(11)),
          Arguments.of(Integer.valueOf(12), BigDecimal.valueOf(12)),
          Arguments.of(Integer.valueOf(13), BigDecimal.valueOf(13)),
          Arguments.of(Integer.valueOf(14), BigDecimal.valueOf(14)),
          Arguments.of(Integer.valueOf(15), BigDecimal.valueOf(15))
        );
    }

}
