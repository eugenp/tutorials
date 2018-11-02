package com.baeldung.reduceIfelse;

import com.baeldung.reducingIfElse.Expression;
import com.baeldung.reducingIfElse.Operator;
import com.baeldung.reducingIfElse.Rule;
import com.baeldung.reducingIfElse.RuleEngine;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RuleEngineUnitTest {

    @Test
    public void whenNumbersGivenToRuleEngine_thenReturnCorrectResult() {
        Expression expression = new Expression(5, 5, Operator.ADD);
        RuleEngine engine = new RuleEngine();
        List<Rule> rules = engine.process(expression);

        assertNotNull(rules);
        assertEquals(1, rules.size());
        assertEquals(10, rules.get(0)
            .getResult());
    }
}
