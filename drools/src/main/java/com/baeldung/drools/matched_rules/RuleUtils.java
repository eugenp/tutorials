package com.baeldung.drools.matched_rules;

import org.kie.api.runtime.rule.RuleContext;

public class RuleUtils {

    public static void track(RuleContext ctx, RuleTracker tracker) {
        String ruleName = ctx.getRule().getName();
        tracker.add(ruleName);
    }
}
