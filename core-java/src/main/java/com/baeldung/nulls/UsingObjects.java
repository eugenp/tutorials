package com.baeldung.nulls;

import java.util.Objects;

public class UsingObjects {

    private String checked;

    public void accept(Object param) {
        try {
            Objects.requireNonNull(param);
        } catch (NullPointerException e) {
            //doSomethingElseToo
            e.printStackTrace();
        }
    }

    public void caller() throws Exception {
        if (Objects.nonNull(checked))
            accept(checked);
        else
            throw new Exception();
    }
}
