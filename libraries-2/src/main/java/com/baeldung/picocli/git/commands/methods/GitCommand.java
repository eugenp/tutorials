package com.baeldung.picocli.git.commands.methods;

import picocli.CommandLine;

import static picocli.CommandLine.Command;

@Command(name = "git")
public class GitCommand implements Runnable {
    public static void main(String[] args) {
        CommandLine.run(new GitCommand(), args);
    }

    @Override
    public void run() {
        System.out.println("The popular git command");
    }

    @Command(name = "add")
    public void addCommand() {
        System.out.println("Adding some files to the staging area");
    }

    @Command(name = "commit")
    public void commitCommand() {
        System.out.println("Committing files in the staging area, how wonderful?");
    }
}
