package com.baeldung.airline;

import com.github.rvesse.airline.annotations.Cli;
import com.github.rvesse.airline.help.Help;

@Cli(name = "baeldung-cli", 
description = "Baeldung Airline Tutorial",
defaultCommand = Help.class, 
commands = { DatabaseSetupCommand.class, LoggingCommand.class, Help.class })
public class CommandLine {

    public static void main(String[] args) {
        com.github.rvesse.airline.Cli<Runnable> cli = new com.github.rvesse.airline.Cli<>(CommandLine.class);
        Runnable cmd = cli.parse(args);
        cmd.run();
    }
}
