package com.baeldung.ternaryoperator;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class TernaryOperatorUnitTest {

    @Test
    public void whenUsingTernaryOperator_thenConditionIsEvaluatedAndValueReturned() {
        int number = 10;
        String msg = number > 10 ? "Number is greater than 10" : "Number is less than or equal to 10";

        assertThat(msg).isEqualTo("Number is less than or equal to 10");
    }

    @Test
    public void whenConditionIsTrue_thenOnlyFirstExpressionIsEvaluated() {
        int exp1 = 0, exp2 = 0;
        int result = 12 > 10 ? ++exp1 : ++exp2;

        assertThat(exp1).isEqualTo(1);
        assertThat(exp2).isEqualTo(0);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void whenConditionIsFalse_thenOnlySecondExpressionIsEvaluated() {
        int exp1 = 0, exp2 = 0;
        int result = 8 > 10 ? ++exp1 : ++exp2;

        assertThat(exp1).isEqualTo(0);
        assertThat(exp2).isEqualTo(1);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void givenANestedCondition_whenUsingTernaryOperator_thenCorrectValueIsReturned() {
        int number = 6;
        String msg = number > 10 ? "Number is greater than 10" : number > 5 ? "Number is greater than 5" : "Number is less than or equal to 5";

        assertThat(msg).isEqualTo("Number is greater than 5");
    }
}
