package com.baeldung.rules;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Verifier;

public class VerifierRuleUnitTest {

    private List<String> messageLog = new ArrayList<String>();

    @Rule
    public Verifier verifier = new Verifier() {
        @Override
        public void verify() {
            assertFalse("Message Log is not Empty!", messageLog.isEmpty());
        }
    };

    @Test
    public void givenNewMessage_whenVerified_thenMessageLogNotEmpty() {
        // ...
        messageLog.add("There is a new message!");
    }

}
