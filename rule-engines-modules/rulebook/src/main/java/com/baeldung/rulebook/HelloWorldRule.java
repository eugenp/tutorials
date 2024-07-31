package com.baeldung.rulebook;

import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder;
import com.deliveredtechnologies.rulebook.model.RuleBook;

public class HelloWorldRule {
    public RuleBook<Object> defineHelloWorldRules() {

        return RuleBookBuilder.create()
            .addRule(rule -> rule.withNoSpecifiedFactType()
                .then(f -> System.out.print("Hello ")))
            .addRule(rule -> rule.withNoSpecifiedFactType()
                .then(f -> System.out.println("World")))
            .build();

    }
}
