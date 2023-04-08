package com.baeldung.functional.functors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

class FunctorsUnitTest {
    @Test
    public void whenProvideAValue_ShouldMapTheValue() {
        Functor<Integer> functor = new Functor<>(5);
        Function<Integer, Integer> addThree = (num) -> num + 3;
        Functor<Integer> mappedFunctor = functor.map(addThree);
        assertEquals(8, mappedFunctor.getValue());
    }

    @Test
    public void whenApplyAnIdentityToAFunctor_thenResultIsEqualsToInitialValue() {
        String value = "baeldung";
        //Identity
        Functor<String> identity = new Functor<>(value).map(Function.identity());
        assertTrue(identity.eq(value));
    }

    @Test
    public void whenApplyAFunctionToOtherFunction_thenResultIsEqualsBetweenBoth() {
        int value = 100;
        long expected = 100;
        // Composition/Associativity
        Function<Integer, String> f = Object::toString;
        Function<String, Long> g = Long::valueOf;

        Functor<Long> left = new Functor<>(value).map(f)
            .map(g);
        Functor<Long> right = new Functor<>(value).map(f.andThen(g));

        assertTrue(left.eq(expected));
        assertTrue(right.eq(expected));
    }

    @Test
    public void whenApplyOperationsToEnumFunctors_thenGetTheProperResult() {
        assertEquals(15, EnumFunctor.PLUS.apply(10, 5));
        assertEquals(5, EnumFunctor.MINUS.apply(10, 5));
        assertEquals(50, EnumFunctor.MULTIPLY.apply(10, 5));
        assertEquals(2, EnumFunctor.DIVIDE.apply(10, 5));
    }
}