package com.baeldung.jcommander.usagebilling.cli;

import org.junit.Test;

public class UsageBasedBillingIntegrationTest {

    @Test
    public void whenInvokeSubmitCommand_thenProcessUsage() {
        String[] argv = {
          "submit",
          "--customer", "cb898e7a-f2a0-46d2-9a09-531f1cee1839",
          "--subscription", "subscriptionPQRMN001",
          "--pricing-type", "PRE_RATED",
          "--timestamp", "2019-10-03T10:58:00",
          "--quantity", "7",
          "--price", "24.56"
        };
        new UsageBasedBilling().run(argv);
    }

    @Test
    public void whenInvokeFetchCommand_thenFetchChargesForCustomer() {
        String[] argv = {
          "fetch",
          "-C", "ac787e6b-d1b0-45d1-8c18-352e2dff2948",
          "-S", "subscriptionA001", "subscriptionA002", "subscriptionA003",
          "--itemized"
        };
        new UsageBasedBilling().run(argv);
    }
}
