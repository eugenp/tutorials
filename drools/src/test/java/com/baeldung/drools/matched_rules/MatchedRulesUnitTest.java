package com.baeldung.drools.matched_rules;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;

import com.baeldung.drools.config.DroolsBeanFactory;

public class MatchedRulesUnitTest {

    private KieSession kieSession;

    @BeforeEach
    public void before() {
        kieSession = new DroolsBeanFactory().getKieSession();
    }

    @Test
    public void givenPerson_whenListenerAttached_thenRuleIsTracked() {
        // Given
        Person person = new Person("Bob", 65);

        TrackingAgendaEventListener listener = new TrackingAgendaEventListener();
        kieSession.addEventListener(listener);

        // When
        kieSession.insert(person);
        kieSession.fireAllRules();
        kieSession.dispose();

        // Then
        assertFalse(listener.getFiredRuleNames().isEmpty());
        assertTrue(listener.getFiredRuleNames().contains("Check Voting Eligibility Event"));
        assertTrue(listener.getFiredRuleNames().contains("Senior Priority Voting Event"));
    }

    @Test
    public void givenPerson_whenRulesFire_thenContextTracksFiredRules() {
        // Given
        Person person = new Person("John", 70);
        RuleTracker tracker = new RuleTracker();

        // When
        kieSession.insert(person);
        kieSession.insert(tracker);

        kieSession.fireAllRules();
        kieSession.dispose();

        // Then
        List<String> fired = tracker.getFiredRules();

        assertTrue(fired.contains("Check Voting Eligibility"));
        assertTrue(fired.contains("Senior Priority Voting"));

        assertTrue(person.isEligibleToVote());
        assertTrue(person.isPriorityVoter());
    }

}
