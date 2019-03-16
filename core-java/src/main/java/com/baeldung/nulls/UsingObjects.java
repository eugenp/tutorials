package com.baeldung.nulls;

import java.util.Objects;

public class UsingObjects {

    public void accept(Object param) throws Exception {
        try {
            Objects.requireNonNull(param);
        } catch (NullPointerException e) {
            throw new Exception();
        }

        //doSomething()
    }
}
