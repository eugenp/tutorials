package com.baeldung.jupiter;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Spring5Java8NewFeaturesIntegrationTest {

    @FunctionalInterface
    public interface FunctionalInterfaceExample<Input, Result> {
        Result reverseString(Input input);
    }

    public class StringUtils {
        FunctionalInterfaceExample<String, String> functionLambdaString = s -> Pattern.compile(" +")
            .splitAsStream(s)
            .map(word -> new StringBuilder(word).reverse())
            .collect(Collectors.joining(" "));
    }

    @Test
    void givenStringUtil_whenSupplierCall_thenFunctionalInterfaceReverseString() throws Exception {
        Supplier<StringUtils> stringUtilsSupplier = StringUtils::new;

        assertEquals(stringUtilsSupplier.get().functionLambdaString.reverseString("hello"), "olleh");
    }
}
