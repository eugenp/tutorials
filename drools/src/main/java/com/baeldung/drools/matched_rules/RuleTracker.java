package com.baeldung.drools.matched_rules;

import java.util.ArrayList;
import java.util.List;

public class RuleTracker {

    private final List<String> firedRules = new ArrayList<>();

    public void add(String ruleName) {
        firedRules.add(ruleName);
    }

    public List<String> getFiredRules() {
        return firedRules;
    }
}
