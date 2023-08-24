package com.baeldung.aggregateEx;

import com.baeldung.aggregateEx.entity.Result;

import java.util.function.Function;

public class CustomMapper {
    public static <T, R> Function<T, Result<R>> mapper(Function<T, R> func) {
        return arg -> {
            Result<R> result;
            try {
                result = new Result(func.apply(arg));
            } catch (Exception e) {
                result = new Result(e);
            }
            return result;
        };
    }
}
