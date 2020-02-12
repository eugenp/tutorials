package com.baeldung.picocli.git.commands.subcommands;

import org.springframework.stereotype.Component;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(
  name = "commit"
)
@Component
public class GitCommitCommand implements Runnable {
    @Option(names = {"-m", "--message"}, required = true)
    private String[] messages;

    @Override
    public void run() {
        System.out.println("Committing files in the staging area, how wonderful?");
        if (messages != null) {
            System.out.println("The commit message is");
            for (String message : messages) {
                System.out.println(message);
            }
        }
    }
}
