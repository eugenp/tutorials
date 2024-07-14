package com.baeldung.functionparameter;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class FunctionParameterUnitTest {

    @Test
    void givenAddFunction_whenUsingAnnonymousClass_thenReturnSumValue() {
        assertEquals(8, FunctionParameter.passFunctionUsingAnnonymousClass(5, 3));
        assertEquals(0, FunctionParameter.passFunctionUsingAnnonymousClass(100, -100));
    }

    @Test
    void givenAddFunction_whenUsingLambda_thenReturnSumValue() {
        assertEquals(8, FunctionParameter.passFunctionUsingLambda(5, 3));
        assertEquals(0, FunctionParameter.passFunctionUsingLambda(100, -100));
    }

    @Test
    void givenAddFunction_whenUsingMethodReferrences_thenReturnSumValue() {
        assertEquals(8, FunctionParameter.passFunctionUsingMethodReferrences(5, 3));
        assertEquals(0, FunctionParameter.passFunctionUsingMethodReferrences(100, -100));
    }

    @Test
    void givenAddFunction_whenUsingFunction_thenReturnSumValue() {
        assertEquals(8, FunctionParameter.passFunctionUsingFunction(5, 3));
        assertEquals(0, FunctionParameter.passFunctionUsingFunction(100, -100));
    }

    @Test
    void givenAddFunction_whenUsingCallable_thenReturnSumValue() throws Exception {
        assertEquals(8, FunctionParameter.passFunctionUsingCallable(5, 3));
        assertEquals(0, FunctionParameter.passFunctionUsingCallable(100, -100));
    }
}
