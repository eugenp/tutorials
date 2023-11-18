package com.baeldung.returnfirstnonnull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ReturnFirstNonNullLazyEvaluateUnitTest {

    private final LazyEvaluate spy = Mockito.spy(new LazyEvaluate());

    @Test
    void givenChainOfMethods_whenUsingIfStatements_thenLazilyEvaluateMethodsUntilFirstNonNull() {
        String object = spy.methodA();
        if (object == null) {
            object = spy.methodB();
        }

        if (object == null) {
            object = spy.methodC();
        }

        assertEquals("first non null", object);
        verify(spy, times(1)).methodA();
        verify(spy, times(1)).methodB();
        verify(spy, times(0)).methodC();
    }

    @Test
    void givenChainOfMethods_whenUsingApacheCommonsLang3_thenReturnFirstNonNull() {
        String object = ObjectUtils.getFirstNonNull(spy::methodA, spy::methodB, spy::methodC);

        assertEquals("first non null", object);
        verify(spy, times(1)).methodA();
        verify(spy, times(1)).methodB();
        verify(spy, times(0)).methodC();
    }

    @Test
    void givenChainOfMethods_whenUsingSupplierInterface_thenLazilyEvaluateMethodsUntilFirstNonNull() {
        Optional<String> object = Stream.<Supplier<String>> of(spy::methodA, spy::methodB, spy::methodC)
            .map(Supplier::get)
            .filter(Objects::nonNull)
            .findFirst();

        assertThat(object).contains("first non null");
        verify(spy, times(1)).methodA();
        verify(spy, times(1)).methodB();
        verify(spy, times(0)).methodC();
    }

    @Test
    void givenNonNullObjectAndFallbackMethod_whenUsingApacheCommonsLang3_thenReturnFirstNonNull() {
        String nonNullObject = spy.methodB();
        String object = ObjectUtils.getIfNull(nonNullObject, spy::methodC);

        assertEquals("first non null", object);
        verify(spy, times(0)).methodC();
    }

    @Test
    void givenNullObjectAndFallbackMethod_whenUsingApacheCommonsLang3_thenReturnFirstNonNull() {
        String nullObject = null;
        String object = ObjectUtils.getIfNull(nullObject, spy::methodB);

        assertEquals("first non null", object);
    }
}