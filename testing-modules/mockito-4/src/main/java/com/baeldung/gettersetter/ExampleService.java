package com.baeldung.gettersetter;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExampleService {

    public <T> T getField(Supplier<T> getter) {
        return getter.get();
    }

    public <T> void setField(Consumer<T> setter, T value) {
        setter.accept(value);
    }

}
