package com.baeldung;

import java.util.function.Consumer;
import java.util.function.Function;

public class AdderImpl implements Adder {

    @Override
    public String addWithFunction(Function<String, String> f) {
        return f.apply("Something ");
    }

    @Override
    public void addWithConsumer(Consumer<Integer> f) {
    }

}
