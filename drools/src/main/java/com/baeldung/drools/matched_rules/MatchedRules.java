package com.baeldung.drools.matched_rules;

import org.kie.api.runtime.KieSession;
import com.baeldung.drools.config.DroolsBeanFactory;

public class MatchedRules {

    public static void main(String[] args) {
        KieSession kieSession = new DroolsBeanFactory().getKieSession();

        TrackingAgendaEventListener listener = new TrackingAgendaEventListener();
        kieSession.addEventListener(listener);

        // RuleTracker tracker = new RuleTracker();
        // kieSession.insert(tracker);

        Person person1 = new Person("Alice", 20);
        Person person2 = new Person("Bob", 16);
        Person person3 = new Person("Baeldung", 65);
        kieSession.insert(person1);
        kieSession.insert(person2);
        kieSession.insert(person3);

        kieSession.fireAllRules();

        System.out.println("Fired rules: " + listener.getFiredRuleNames());
        // System.out.println("Fired rules: " + tracker.getFiredRules());

        kieSession.dispose();
    }

}
