package com.baeldung.java8.lambda.tips;


import java.util.function.Function;

public class UseFoo {

    private String value = "Enclosing scope value";

    public String add(final String string, final Foo foo) {
        return foo.method(string);
    }

    public String addWithStandardFI(final String string, final Function<String, String> fn) {
        return fn.apply(string);
    }

    public String scopeExperiment() {
        final Foo fooIC = new Foo() {
            String value = "Inner class value";

            @Override
            public String method(final String string) {
                return value;
            }
        };
        final String resultIC = fooIC.method("");

        final Foo fooLambda = parameter -> {
            final String value = "Lambda value";
            return this.value;
        };
        final String resultLambda = fooLambda.method("");

        return "Results: resultIC = " + resultIC + ", resultLambda = " + resultLambda;
    }

}
