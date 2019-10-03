package com.baeldung.jcommander.usagebilling;

import com.baeldung.jcommander.usagebilling.cli.UsageBasedBilling;
import java.util.UUID;

public class UsageBasedBillingApp {

  public static void main(String[] args) {
//    args = new String[]{
//        "fetch", 
//        "--customer", UUID.randomUUID().toString()
//    };
    
    args = new String[]{
        "submit", 
        "--customer", UUID.randomUUID().toString(),
        "--subscription", UUID.randomUUID().toString(),
        "--pricing-type", "PRE_RATED",
        "--quantity", "7",
        "--timestamp", "2019-10-03T10:58:00",
        "--price", "24.56"
    };
    new UsageBasedBilling().run(args);
  }
}
