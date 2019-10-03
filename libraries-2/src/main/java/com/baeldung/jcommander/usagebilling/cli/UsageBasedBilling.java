package com.baeldung.jcommander.usagebilling.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "")
public class UsageBasedBilling {

  private SubmitUsageCommand createOrderCmd;
  private FetchCurrentChargesCommand fetchTradesCmd;

  public UsageBasedBilling() {
    this.createOrderCmd = new SubmitUsageCommand();
    this.fetchTradesCmd = new FetchCurrentChargesCommand();
  }

  public void run(String[] args) {
    JCommander jc = JCommander.newBuilder()
        .addObject(this)
        .addCommand(createOrderCmd)
        .addCommand(fetchTradesCmd)
        .build();

    jc.parse(args);

    String cmd = jc.getParsedCommand();
    if (Constants.SUBMIT_CMD.equalsIgnoreCase(cmd)) {
      System.out.println("Parsing usage request...");
      this.createOrderCmd.submit();

    } else if (Constants.FETCH_CMD.equalsIgnoreCase(cmd)) {
      System.out.println("Preparing query...");
      this.fetchTradesCmd.fetch();

    } else {
      System.err.println("Invalid command: " + cmd);
    }
  }

  static class Constants {

    static final String SUBMIT_CMD = "submit";
    static final String FETCH_CMD = "fetch";
  }
}
