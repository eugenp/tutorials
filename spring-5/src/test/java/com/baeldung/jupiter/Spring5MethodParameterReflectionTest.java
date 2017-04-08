package com.baeldung.jupiter;

import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Spring5MethodParameterReflectionTest {

    @Test
    void givenMethodName_whenReflectionEnhancement_thenMethodValid()
      throws Exception {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("reverseString"
          , StringUtils.class.getDeclaredMethod("reverseString"
          , new Class[]{String.class}));
        String helloWorldReversed = parser.parseExpression("#reverseString('hello')")
          .getValue(context, String.class);

        assertEquals(helloWorldReversed, "olleh");
    }
}
