package com.baeldung.jcommander.usagebilling.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubmitUsageCommandUnitTest {

    private static JCommander jc;

    @BeforeClass
    public static void setup() {
        jc = JCommander
          .newBuilder()
          .addObject(new SubmitUsageCommand())
          .build();

        jc.parse(new String[] {
          "--customer", "cb898e7a-f2a0-46d2-9a09-531f1cee1839",
          "--subscription", "subscriptionPQRMN001",
          "--pricing-type", "PRE_RATED",
          "--timestamp", "2019-10-03T10:58:00",
          "--quantity", "7",
          "--price", "24.56"
        });
    }

    @Test
    public void whenParsedCustomerParameter_thenParameterOfTypeStringIsPopulated() {
        SubmitUsageCommand cmd = (SubmitUsageCommand) jc
          .getObjects()
          .get(0);
        assertEquals("cb898e7a-f2a0-46d2-9a09-531f1cee1839", cmd.getCustomerId());
    }

    @Test
    public void whenParsedTimestampParameter_thenParameterOfTypeInstantIsPopulated() {
        SubmitUsageCommand cmd = (SubmitUsageCommand) jc
          .getObjects()
          .get(0);
        assertEquals("2019-10-03T10:58:00Z", cmd
          .getTimestamp()
          .toString());
    }

    @Test(expected = ParameterException.class)
    public void whenParsedCustomerIdNotUUID_thenParameterException() {
        jc.parse(new String[] {
          "--customer", "customer001",
          "--subscription", "subscriptionPQRMN001",
          "--pricing-type", "PRE_RATED",
          "--timestamp", "2019-10-03T10:58:00",
          "--quantity", "7",
          "--price", "24.56"
        });
    }
}
