package com.baeldung.classtype;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeToken<T> {
    private Type type;

    protected TypeToken(){
        Type superclass = getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
}
