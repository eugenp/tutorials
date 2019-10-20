package com.baeldung.jcommander.usagebilling.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.UnixStyleUsageFormatter;

public class UsageBasedBilling {

    private JCommander main;
    private SubmitUsageCommand submitUsageCmd;
    private FetchCurrentChargesCommand fetchChargesCmd;

    public UsageBasedBilling() {
        this.submitUsageCmd = new SubmitUsageCommand();
        this.fetchChargesCmd = new FetchCurrentChargesCommand();
        main = JCommander
          .newBuilder()
          .addObject(this)
          .addCommand(submitUsageCmd)
          .addCommand(fetchChargesCmd)
          .build();

        setUsageFormatter(Constants.SUBMIT_CMD);
        setUsageFormatter(Constants.FETCH_CMD);
    }

    public void run(String[] args) {
        String parsedCmdStr;
        try {
            main.parse(args);
            parsedCmdStr = main.getParsedCommand();
            
            switch (parsedCmdStr) {
                case Constants.SUBMIT_CMD:
                    if (this.submitUsageCmd.isHelp()) {
                        getSubCommandHandle(Constants.SUBMIT_CMD).usage();
                    }
                    System.out.println("Parsing usage request...");
                    this.submitUsageCmd.submit();
                    break;

                case Constants.FETCH_CMD:
                    if (this.fetchChargesCmd.isHelp()) {
                        getSubCommandHandle(Constants.SUBMIT_CMD).usage();
                    }
                    System.out.println("Preparing fetch query...");
                    this.fetchChargesCmd.fetch();
                    
                    break;
                    
                default:
                    System.err.println("Invalid command: " + parsedCmdStr);
            }
        } catch (ParameterException e) {
            System.err.println(e.getLocalizedMessage());
            parsedCmdStr = main.getParsedCommand();
            if (parsedCmdStr != null) {
                getSubCommandHandle(parsedCmdStr).usage();
            } else {
                main.usage();
            }
        }
    }

    private JCommander getSubCommandHandle(String command) {
        JCommander cmd = main
          .getCommands()
          .get(command);

        if (cmd == null) {
            System.err.println("Invalid command: " + command);
        }
        return cmd;
    }

    private void setUsageFormatter(String subCommand) {
        JCommander cmd = getSubCommandHandle(subCommand);
        cmd.setUsageFormatter(new UnixStyleUsageFormatter(cmd));
    }

    static class Constants {

        static final String SUBMIT_CMD = "submit";
        static final String FETCH_CMD = "fetch";
    }
}
