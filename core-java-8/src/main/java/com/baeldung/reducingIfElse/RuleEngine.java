package com.baeldung.reducingIfElse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RuleEngine {

    private static List<Rule> rules = new ArrayList<>();

    static {
        rules.add(new AddRule());
    }

    public List<Rule> process(Expression expression) {
        return rules.stream()
            .filter(r -> r.evaluate(expression))
            .collect(Collectors.toList());
    }
}
