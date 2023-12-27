package com.baeldung.picocli.helloworld;

import static picocli.CommandLine.Command;

import picocli.CommandLine;

@Command(
  name = "hello",
  description = "Says hello"
)
public class HelloWorldCommand implements Runnable {
    public static void main(String[] args) {
        CommandLine.run(new HelloWorldCommand(), args);
    }

    @Override
    public void run() {
        System.out.println("Hello World!");
    }
}
