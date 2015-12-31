package org.baeldung.java8;

import org.baeldung.Foo;
import org.baeldung.FooExtended;
import org.baeldung.UseFoo;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Java8FunctionalInteracesLambdasTest {

    private UseFoo useFoo;

    @Before
    public void init() {
        useFoo = new UseFoo();
    }

    @Test
    public void functionalInterfaceInstantiation_whenReturnDefiniteString_thenCorrect() {

        Foo foo = parameter -> parameter + "from lambda";
        String result = useFoo.add("Message ", foo);

        assertEquals("Message from lambda", result);
    }

    @Test
    public void standardFIParameter_whenReturnDefiniteString_thenCorrect() {

        Function<String, String> fn = parameter -> parameter + "from lambda";
        String result = useFoo.addWithStandardFI("Message ", fn);

        assertEquals("Message from lambda", result);
    }

    @Test
    public void defaultMethodFromExtendedInterface_whenReturnDefiniteString_thenCorrect() {

        FooExtended fooExtended = string -> string;
        String result = fooExtended.defaultMethod();

        assertEquals("String from Bar", result);
    }

    @Test
    public void lambdaAndInnerClassInstantiation_whenReturnSameString_thenCorrect() {

        Foo foo = parameter -> parameter + "from Foo";

        Foo fooByIC = new Foo() {
            @Override
            public String method(String string) {
                return string + "from Foo";
            }
        };

        assertEquals(foo.method("Something "), fooByIC.method("Something "));
    }

    @Test
    public void accessVariablesFromDifferentScopes_whenReturnPredefinedString_thenCorrect() {

        assertEquals("Results: resultIC = Inner class value, resultLambda = Enclosing scope value",
                useFoo.scopeExperiment());
    }

    @Test
    public void shorteningLambdas_whenReturnEqualsResults_thenCorrect() {

        Foo foo = parameter -> buildString(parameter);

        Foo fooHuge = parameter -> { String result = "Something " + parameter;
            //many lines of code
            return result;
        };

        assertEquals(foo.method("Something"), fooHuge.method("Something"));
    }

    private String buildString(String parameter) {
        String result = "Something " + parameter;
        //many lines of code
        return result;
    }

    @Test
    public void mutatingOfEffectivelyFinalVariable_whenNotEquals_thenCorrect() {

        int[] total = new int[1];
        Runnable r = () -> total[0]++;
        r.run();

        assertNotEquals(0, total[0]);
    }

}
