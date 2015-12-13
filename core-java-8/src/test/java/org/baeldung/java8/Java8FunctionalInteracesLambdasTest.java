package org.baeldung.java8;

import fiandlambdas.Foo;
import fiandlambdas.Foo_3Impl;
import fiandlambdas.UseFoo;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class Java8FunctionalInteracesLambdasTest {

    private UseFoo useFoo;

    private Foo_3Impl foo_3;

    private String value;

    @Before
    public void init() {
        useFoo = new UseFoo();
        foo_3 = new Foo_3Impl();
        value = "Enclosing scope value";
    }

    @Test
    public void functionalInterfaceInstantiation_whenReturnDefiniteString_thenCorrect() {

        Foo fooByAIC = new Foo() {

            @Override
            public String method(String string) {
                return string + "from Foo.method()";
            }
        };
        String resultAIC = fooByAIC.method("This is a string ");

        Foo foo = parameter -> parameter + "from lambda";
        String result = useFoo.add("Message ", foo);

        Function<String, String> fn = parameter -> parameter + "from lambda";
        String result_2 = useFoo.addWithStandardFI("Message ", fn);

        assertEquals(resultAIC, "This is a string from Foo.method()");
        assertEquals(result, "Message from lambda");
        assertEquals(result_2, "Message from lambda");
    }

    @Test
    public void givenScope_whetReturnDefiniteString_thenCorrect() {

        Foo fooAIC = new Foo() {
            String value = "Inner value";

            @Override
            public String method(String string) {
                return this.value;
            }
        };
        String resultAIC = fooAIC.method("");

        Foo fooLambda = parameter -> {
            String value = "Lambda value";
            return this.value;
        };
        String resultLambda = fooLambda.method("");

        assertEquals(resultAIC, "Inner value");
        assertEquals(resultLambda, "Enclosing scope value");
    }

    @Test
    public void givenStringBuilder_whenToStringReturnsDefiniteString_thenCorrect() {
        StringBuilder message = new StringBuilder();
        Runnable r = () -> message.append("Lambda doesn't change the message" +
                "reference, but it changes the value of the object");
        r.run();

        assertEquals(message.toString(), "Lambda doesn't change the message" +
                "reference, but it changes the value of the object");
    }

    @Test
    public void givenFunction_whenProcessParametreToDefniteString_thenCorrect() {
        String r = foo_3.addWithFunction(a -> a + "from function");

        assertEquals(r, "Something from function");
    }
}
