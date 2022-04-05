package com.baeldung.java8.lambda.tips;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public interface Processor {

    String processWithCallable(Callable<String> c) throws Exception;

    String processWithSupplier(Supplier<String> s);

}
