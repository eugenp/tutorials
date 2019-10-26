package com.baeldung.nulls;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class FindBugsAnnotations {

    public void accept(@NotNull Object param) {
        System.out.println(param.toString());
    }

    public void print(@Nullable Object param) {
        System.out.println("Printing " + param);
    }

    @NotNull
    public Object process() throws Exception {
        Object result = doSomething();
        if (result == null) {
            throw new Exception("Processing fail. Got a null response");
        } else {
            return result;
        }
    }

    private Object doSomething() {
        return null;
    }
}
