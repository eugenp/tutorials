package com.baeldung.listvsset;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

public abstract class ParametrizationAware<T> {

    public List<Class<T>> getParametrizationClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return Arrays.stream(type.getActualTypeArguments())
          .map(s -> ((Class<T>) s)).toList();
    }
}
