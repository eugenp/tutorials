package com.baeldung.picocli.git.commands.declarative;

import com.baeldung.picocli.git.model.ConfigElement;
import com.baeldung.picocli.git.commands.subcommands.GitAddCommand;
import com.baeldung.picocli.git.commands.subcommands.GitCommitCommand;
import com.baeldung.picocli.git.commands.subcommands.GitConfigCommand;
import picocli.CommandLine;

import static picocli.CommandLine.*;
import static picocli.CommandLine.Command;

@Command(
  name = "git",
  subcommands = {
    GitAddCommand.class,
    GitCommitCommand.class,
    GitConfigCommand.class
  }
)
public class GitCommand implements Runnable {
    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new GitCommand());
        commandLine.registerConverter(ConfigElement.class, ConfigElement::from);

        commandLine.parseWithHandler(new RunLast(), args);
    }

    @Override
    public void run() {
        System.out.println("The popular git command");
    }
}
