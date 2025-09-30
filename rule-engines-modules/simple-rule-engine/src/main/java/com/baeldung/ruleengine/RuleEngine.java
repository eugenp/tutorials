package com.baeldung.ruleengine;

import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.ruleengine.model.Order;

public class RuleEngine {
    private final List<IRule> rules;

    public RuleEngine(List<IRule> rules) {
        this.rules = rules;
    }

    public List<String> evaluate(Order order) {
        return rules.stream()
            .filter(rule -> rule.evaluate(order))
            .map(IRule::description)
            .collect(Collectors.toList());
    }
}
