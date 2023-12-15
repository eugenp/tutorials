package com.baeldung.picocli.git.commands.programmative;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.RunLast;

import org.springframework.stereotype.Component;

import com.baeldung.picocli.git.commands.subcommands.GitAddCommand;
import com.baeldung.picocli.git.commands.subcommands.GitCommitCommand;

import picocli.CommandLine;

@Command(name = "git")
@Component
public class GitCommand implements Runnable {
    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new GitCommand());
        commandLine.addSubcommand("add", new GitAddCommand());
        commandLine.addSubcommand("commit", new GitCommitCommand());

        commandLine.parseWithHandler(new RunLast(), args);
    }

    @Override
    public void run() {
        System.out.println("The popular git command");
    }
}
