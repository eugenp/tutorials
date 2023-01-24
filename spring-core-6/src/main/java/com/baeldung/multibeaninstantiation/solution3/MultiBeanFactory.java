package com.baeldung.multibeaninstantiation.solution3;

import java.util.List;

public interface MultiBeanFactory<T> {
    List<T> getObject(String name) throws Exception;

    Class<?> getObjectType();
}