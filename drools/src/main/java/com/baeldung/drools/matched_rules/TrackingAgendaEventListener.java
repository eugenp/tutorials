package com.baeldung.drools.matched_rules;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.rule.Match;

public class TrackingAgendaEventListener extends DefaultAgendaEventListener {
    private final List<Match> matchList = new ArrayList<>();

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        matchList.add(event.getMatch());
    }

    public List<String> getFiredRuleNames() {
        List<String> names = new ArrayList<>();
        for (Match m : matchList) {
            names.add(m.getRule().getName());
        }
        return names;
    }

}
