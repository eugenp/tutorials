package com.baeldung.nplusone;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ParametrizationAware<T> {

    public List<Class<T>> getParametrizationClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return Arrays.stream(type.getActualTypeArguments())
          .map(s -> ((Class<T>) s)).collect(Collectors.toList());
    }
}
