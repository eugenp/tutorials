package com.baeldung.java8.lambda.serialization;

import java.io.Serializable;

public class SerializableLambdaExpression {
    public static Object getLambdaExpressionObject() {
        Runnable r = (Runnable & Serializable) () -> System.out.println("please serialize this message");
        return r;
    }
}
