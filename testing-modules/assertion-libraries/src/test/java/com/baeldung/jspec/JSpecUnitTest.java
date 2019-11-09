package com.baeldung.jspec;

import static org.javalite.test.jspec.JSpec.$;
import static org.javalite.test.jspec.JSpec.a;
import static org.javalite.test.jspec.JSpec.expect;
import static org.javalite.test.jspec.JSpec.it;
import static org.javalite.test.jspec.JSpec.the;

import java.util.Arrays;
import java.util.List;

import org.javalite.test.jspec.ExceptionExpectation;
import org.junit.Test;

public class JSpecUnitTest {

    @Test
    public void onePlusTwo_shouldEqualThree() {
        $(1 + 2).shouldEqual(3);
        a(1 + 2).shouldEqual(3);
        the(1 + 2).shouldEqual(3);
        it(1 + 2).shouldEqual(3);
    }
    
    @Test
    public void messageShouldContainJSpec() {
        String message = "Welcome to JSpec demo";
        // The message should not be empty
        the(message).shouldNotBe("empty");
        // The message should contain JSpec
        the(message).shouldContain("JSpec");
    }

    public void colorsListShouldContainRed() {
        List<String> colorsList = Arrays.asList("red", "green", "blue");
        $(colorsList).shouldContain("red");
    }

    public void guessedNumberShouldEqualHiddenNumber() {
        Integer guessedNumber = 11;
        Integer hiddenNumber = 11;

        $(guessedNumber).shouldEqual(hiddenNumber);
        $(guessedNumber).shouldNotBeTheSameAs(hiddenNumber);
    }
    
    @Test
    public void dividingByThero_shouldThrowArithmeticException() {
        expect(new ExceptionExpectation<ArithmeticException>(ArithmeticException.class) {
            @Override
            public void exec() throws ArithmeticException {
                System.out.println(1 / 0);
            }
        } );
    }

}
