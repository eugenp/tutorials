package com.baeldung.gson.jsontolist.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParameterizedTypeImpl implements ParameterizedType {

    private final Class<?> rawType;
    private final Type[] actualTypeArguments;

    private ParameterizedTypeImpl(Class<?> rawType, Type[] actualTypeArguments) {
        this.rawType = rawType;
        this.actualTypeArguments = actualTypeArguments;
    }

    public static ParameterizedType make(Class<?> rawType, Type ... actualTypeArguments) {
        return new ParameterizedTypeImpl(rawType, actualTypeArguments);
    }

    @Override
    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    @Override
    public Type getRawType() {
        return rawType;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
