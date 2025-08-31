package com.baeldung.ruleengine;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.baeldung.ruleengine.model.Order;


public class SpelRule {

    private final String expression;
    private final String description;

    public SpelRule(String expression, String description) {
        this.expression = expression;
        this.description = description;
    }

    public boolean evaluate(Order order) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext(order);
        context.setVariable("order", order);
        return parser.parseExpression(expression)
            .getValue(context, Boolean.class);
    }

    public String getDescription() {
        return description;
    }
}
