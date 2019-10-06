package com.baeldung.jcommander.usagebilling;

import com.baeldung.jcommander.usagebilling.cli.UsageBasedBilling;

public class UsageBasedBillingApp {

    public static void main(String[] args) {
        new UsageBasedBilling().run(args);
    }
}
