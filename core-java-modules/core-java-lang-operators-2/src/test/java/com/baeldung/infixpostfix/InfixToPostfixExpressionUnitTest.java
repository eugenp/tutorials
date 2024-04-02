package com.baeldung.infixpostfix;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class InfixToPostfixExpressionUnitTest {

    @Test
    public void givenSimpleOp_whenNoParenthesis_thenProduceValidPostfix() {
        String infix = "a+b*c-d";
        String postfix = "abc*+d-";

        InfixToPostFixExpressionConversion obj = new InfixToPostFixExpressionConversion();

        Assertions.assertEquals(postfix, obj.infixToPostfix(infix));
    }

    @Test
    public void givenSimpleOp_whenWithParenthesis_thenProduceValidPostfix() {
        String infix = "(a+b)*(c-d)";
        String postfix = "ab+cd-*";

        InfixToPostFixExpressionConversion obj = new InfixToPostFixExpressionConversion();

        Assertions.assertEquals(postfix, obj.infixToPostfix(infix));
    }

    @Test
    public void givenComplexOp_whenInputIsInfix_thenProduceValidPostfix() {
        String infix = "a^b*(c^d-e)^(f+g*h)-i";
        String postfix = "ab^cd^e-fgh*+^*i-";

        InfixToPostFixExpressionConversion obj = new InfixToPostFixExpressionConversion();

        Assertions.assertEquals(postfix, obj.infixToPostfix(infix));
    }
}
