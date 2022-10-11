package com.baeldung.reducingIfElse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RuleEngine {

    private static List<Rule> rules = new ArrayList<>();

    static {
        rules.add(new AddRule());
    }

    public Result process(Expression expression) {

        Rule rule = rules.stream()
            .filter(r -> r.evaluate(expression))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Expression does not matches any Rule"));
        return rule.getResult();
    }
}
