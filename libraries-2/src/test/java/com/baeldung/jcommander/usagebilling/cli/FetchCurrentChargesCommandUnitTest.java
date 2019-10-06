package com.baeldung.jcommander.usagebilling.cli;

import com.beust.jcommander.JCommander;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class FetchCurrentChargesCommandUnitTest {

    private JCommander jc;

    @Before
    public void setup() {
        jc = JCommander
          .newBuilder()
          .addObject(new FetchCurrentChargesCommand())
          .build();
    }

    @Test
    public void whenParsedMultipleSubscriptionsParameter_thenParameterSubscriptionsIsPopulated() {
        FetchCurrentChargesCommand cmd = (FetchCurrentChargesCommand) jc
          .getObjects()
          .get(0);
        
        jc.parse(new String[] {
          "-C", "cb898e7a-f2a0-46d2-9a09-531f1cee1839",
          "-S", "subscriptionA001",
          "-S", "subscriptionA002",
          "-S", "subscriptionA003",
        });
        
        assertEquals(
          asList("subscriptionA001", "subscriptionA002", "subscriptionA003"),
          cmd.getSubscriptionIds()
        );
    }

    @Test
    public void whenParsedSubscriptionsColonSeparatedParameter_thenParameterSubscriptionsIsPopulated() {
        FetchCurrentChargesCommand cmd = (FetchCurrentChargesCommand) jc
          .getObjects()
          .get(0);

        jc.parse(new String[] {
          "-C", "cb898e7a-f2a0-46d2-9a09-531f1cee1839",
          "-S", "subscriptionA001:subscriptionA002:subscriptionA003",
        });

        assertEquals(
          asList("subscriptionA001", "subscriptionA002", "subscriptionA003"),
          cmd.getSubscriptionIds()
        );
    }

    @Test
    public void whenParsedSubscriptionsWithVariableArity_thenParameterSubscriptionsIsPopulated() {
        FetchCurrentChargesCommand cmd = (FetchCurrentChargesCommand) jc
          .getObjects()
          .get(0);

        jc.parse(new String[] {
          "-C", "cb898e7a-f2a0-46d2-9a09-531f1cee1839",
          "-S", "subscriptionA001", "subscriptionA002", "subscriptionA003",
        });

        assertEquals(
          asList("subscriptionA001", "subscriptionA002", "subscriptionA003"),
          cmd.getSubscriptionIds()
        );
    }
}
