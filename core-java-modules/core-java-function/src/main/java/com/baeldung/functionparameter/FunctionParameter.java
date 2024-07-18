package com.baeldung.functionparameter;

import java.util.function.BiFunction;
import java.util.concurrent.Callable;

interface Operation { int execute(int a, int b); }

public class FunctionParameter {

    public static int performOperation(int a, int b, Operation operation) { return operation.execute(a, b); }

    public static int passFunctionUsingAnnonymousClass(int a, int b) {
        return performOperation(a, b, new Operation() {
            @Override
            public int execute(int a, int b) {
                return a + b;
            }
        });
    }

    public static int passFunctionUsingLambda(int a, int b) {
        return performOperation(a, b, (i, k) -> i + k);
    }

    private static int add(int a, int b) { return a + b; }

    public static int passFunctionUsingMethodReferrences(int a, int b) {
        return performOperation(a, b, FunctionParameter::add);
    }

    private static int executeFunction(BiFunction<Integer, Integer, Integer> function, int a, int b) { return function.apply(a, b);  }

    public static int passFunctionUsingFunction(int a, int b) {
        return executeFunction((i, k) -> i + k, a, b);
    }

    private static int executeCallable(Callable<Integer> task) throws Exception {
        return task.call();
    }

    public static int passFunctionUsingCallable(int a, int b) throws Exception {
        Callable<Integer> task = () -> a + b;
        return executeCallable(task);
    }
}
