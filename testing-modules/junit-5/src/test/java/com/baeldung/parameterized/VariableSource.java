package com.baeldung.parameterized;

import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(VariableArgumentsProvider.class)
public @interface VariableSource {

    /**
     * Represents the name of the static variable to load the test arguments from.
     *
     * @return Static variable name.
     */
    String value();
}
