package com.baeldung.java8.lambda.serialization;

public class NotSerializableLambdaExpression {
    public static Object getLambdaExpressionObject() {
        Runnable r = () -> System.out.println("please serialize this message");
        return r;
    }
}
