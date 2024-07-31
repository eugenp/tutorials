package com.baeldung.jcommander.usagebilling;

import com.baeldung.jcommander.usagebilling.cli.UsageBasedBilling;

public class UsageBasedBillingApp {

  /*
   *  Entry-point: invokes the cli passing the command-line args
   *  
   *  Invoking "Submit" sub-command:
   *  mvn exec:java \
        -Dexec.mainClass=com.baeldung.jcommander.usagebilling.UsageBasedBillingApp -q \
        -Dexec.args="submit --customer cb898e7a-f2a0-46d2-9a09-531f1cee1839 --subscription subscriptionPQRMN001 --pricing-type PRE_RATED --timestamp 2019-10-03T10:58:00 --quantity 7 --price 24.56"
   *     
   *  Invoking "Fetch" sub-command:
   *  mvn exec:java \
        -Dexec.mainClass=com.baeldung.jcommander.usagebilling.UsageBasedBillingApp -q \
        -Dexec.args="fetch --customer cb898e7a-f2a0-46d2-9a09-531f1cee1839 --subscription subscriptionPQRMN001 subscriptionPQRMN002 subscriptionPQRMN003 --itemized"
   */
  public static void main(String[] args) {
    new UsageBasedBilling().run(args);
  }
}
