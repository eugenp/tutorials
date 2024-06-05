package com.baeldung.generics;

import com.google.common.reflect.TypeToken;

public class SenderServiceGuava<T extends Sender> {

    TypeToken<T> typeToken;

    TypeToken<T> typeTokenAnonymous = new TypeToken<T>(getClass()) {
    };

    public SenderServiceGuava() {
    }

    public SenderServiceGuava(Class<T> clazz) {
        this.typeToken = TypeToken.of(clazz);
    }

    public T createInstance() {
        try {
            return (T) typeToken.getRawType()
                .getDeclaredConstructor()
                .newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T createInstanceAnonymous() {
        try {
            return (T) typeTokenAnonymous.getRawType()
                .getDeclaredConstructor()
                .newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
