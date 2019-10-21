package com.baeldung.jcommander.usagebilling.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.UnixStyleUsageFormatter;

public class UsageBasedBilling {

    static final String SUBMIT_CMD = "submit";
    static final String FETCH_CMD = "fetch";

    private JCommander jCommander;
    private SubmitUsageCommand submitUsageCmd;
    private FetchCurrentChargesCommand fetchChargesCmd;

    public UsageBasedBilling() {
        this.submitUsageCmd = new SubmitUsageCommand();
        this.fetchChargesCmd = new FetchCurrentChargesCommand();
        jCommander = JCommander.newBuilder()
          .addObject(this)
          .addCommand(submitUsageCmd)
          .addCommand(fetchChargesCmd)
          .build();

        setUsageFormatter(SUBMIT_CMD);
        setUsageFormatter(FETCH_CMD);
    }

    public void run(String[] args) {
        String parsedCmdStr;
        try {
            jCommander.parse(args);
            parsedCmdStr = jCommander.getParsedCommand();
            
            switch (parsedCmdStr) {
                case SUBMIT_CMD:
                    if (submitUsageCmd.isHelp()) {
                        getSubCommandHandle(SUBMIT_CMD).usage();
                    }
                    System.out.println("Parsing usage request...");
                    submitUsageCmd.submit();
                    break;

                case FETCH_CMD:
                    if (fetchChargesCmd.isHelp()) {
                        getSubCommandHandle(SUBMIT_CMD).usage();
                    }
                    System.out.println("Preparing fetch query...");
                    fetchChargesCmd.fetch();
                    
                    break;
                    
                default:
                    System.err.println("Invalid command: " + parsedCmdStr);
            }
        } catch (ParameterException e) {
            System.err.println(e.getLocalizedMessage());
            parsedCmdStr = jCommander.getParsedCommand();
            if (parsedCmdStr != null) {
                getSubCommandHandle(parsedCmdStr).usage();
            } else {
                jCommander.usage();
            }
        }
    }

    private JCommander getSubCommandHandle(String command) {
        JCommander cmd = jCommander.getCommands().get(command);

        if (cmd == null) {
            System.err.println("Invalid command: " + command);
        }
        return cmd;
    }

    private void setUsageFormatter(String subCommand) {
        JCommander cmd = getSubCommandHandle(subCommand);
        cmd.setUsageFormatter(new UnixStyleUsageFormatter(cmd));
    }
}
