package com.baeldung;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Adder {

    String addWithFunction(Function<String, String> f);

    void addWithConsumer(Consumer<Integer> f);

}
