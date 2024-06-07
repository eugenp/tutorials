package com.baeldung.parameterfunction;

import java.util.function.BiFunction;

interface Operation { int execute(int a, int b); }

public class ParameterFunctionDemo {

    public static int performOperation(int a, int b, Operation operation) { return operation.execute(a, b); }

    public static int passFunctionUsingAnnonymousClass() {
        return performOperation(5, 3, new Operation() {
            @Override
            public int execute(int a, int b) {
                return a + b;
            }
        });
    }

    public static int passFunctionUsingLambda() {
        return performOperation(5, 3, (a, b) -> a + b);
    }

    private static int add(int a, int b) { return a + b; }

    public static int passFunctionUsingMethodReferrences() {
        return performOperation(5, 3, ParameterFunctionDemo::add);
    }

    private static int executeFunction(BiFunction<Integer, Integer, Integer> function, int a, int b) { return function.apply(a, b);  }

    public static int passFunctionUsingFunction() {
        return executeFunction((a, b) -> a + b, 5, 3);
    }

    public static void main (String args []) {
        System.out.println(passFunctionUsingAnnonymousClass());
        System.out.println(passFunctionUsingLambda());
        System.out.println(passFunctionUsingMethodReferrences());
        System.out.println(passFunctionUsingFunction());
    }
}
