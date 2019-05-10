package com.baeldung.picocli.git.commands.subcommands;

import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

import static picocli.CommandLine.*;

@Command(
  name = "add"
)
@Component
public class GitAddCommand implements Runnable {
    @Option(names = "-A")
    private boolean allFiles;

    @Parameters(index = "0..*")
    private List<Path> files;

    @Override
    public void run() {
        if (allFiles) {
            System.out.println("Adding all files to the staging area");
        }

        if (files != null) {
            files.forEach(path -> System.out.println("Adding " + path + " to the staging area"));
        }
    }
}
