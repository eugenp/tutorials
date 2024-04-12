package com.baeldung.functional;

import java.util.function.Function;

public class FunctionComposition {

    private static Function<Double, Double> log = (value) -> Math.log(value);
    private static Function<Double, Double> sqrt = (value) -> Math.sqrt(value);

    public static Double logThenSqrt(Double number) {
        Function<Double, Double> logThenSqrt = sqrt.compose(log);
        return (logThenSqrt.apply(3.14));
    }

    public static Double sqrtThenLog(Double number) {
        Function<Double, Double> sqrtThenLog = sqrt.andThen(log);
        return (sqrtThenLog.apply(3.14));
    }

}
