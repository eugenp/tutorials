package com.baeldung.java8.lambda.tips;

import com.baeldung.java8.lambda.tips.Foo;
import com.baeldung.java8.lambda.tips.FooExtended;
import com.baeldung.java8.lambda.tips.UseFoo;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Java8FunctionalInteracesLambdasUnitTest {

    private UseFoo useFoo;

    @Before
    public void init() {
        useFoo = new UseFoo();
    }

    @Test
    public void functionalInterfaceInstantiation_whenReturnDefiniteString_thenCorrect() {
        final Foo foo = parameter -> parameter + "from lambda";
        final String result = useFoo.add("Message ", foo);

        assertEquals("Message from lambda", result);
    }

    @Test
    public void standardFIParameter_whenReturnDefiniteString_thenCorrect() {
        final Function<String, String> fn = parameter -> parameter + "from lambda";
        final String result = useFoo.addWithStandardFI("Message ", fn);

        assertEquals("Message from lambda", result);
    }

    @Test
    public void defaultMethodFromExtendedInterface_whenReturnDefiniteString_thenCorrect() {
        final FooExtended fooExtended = string -> string;
        final String result = fooExtended.defaultCommon();

        assertEquals("Default Common from Bar", result);
    }

    @Test
    public void lambdaAndInnerClassInstantiation_whenReturnSameString_thenCorrect() {
        final Foo foo = parameter -> parameter + "from Foo";

        final Foo fooByIC = new Foo() {
            @Override
            public String method(final String string) {
                return string + "from Foo";
            }
        };

        assertEquals(foo.method("Something "), fooByIC.method("Something "));
    }

    @Test
    public void accessVariablesFromDifferentScopes_whenReturnPredefinedString_thenCorrect() {
        assertEquals("Results: resultIC = Inner class value, resultLambda = Enclosing scope value", useFoo.scopeExperiment());
    }

    @Test
    public void shorteningLambdas_whenReturnEqualsResults_thenCorrect() {
        final Foo foo = parameter -> buildString(parameter);

        final Foo fooHuge = parameter -> {
            final String result = "Something " + parameter;
            // many lines of code
            return result;
        };

        assertEquals(foo.method("Something"), fooHuge.method("Something"));
    }

    private String buildString(final String parameter) {
        final String result = "Something " + parameter;
        // many lines of code
        return result;
    }

    @Test
    public void mutatingOfEffectivelyFinalVariable_whenNotEquals_thenCorrect() {
        final int[] total = new int[1];
        final Runnable r = () -> total[0]++;
        r.run();

        assertNotEquals(0, total[0]);
    }

}
