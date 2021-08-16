package com.baeldung.examples.hexagonal;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var inMemoryAccountRepository = new InMemoryAccountRepository();
        var simpleAccountService = new SimpleAccountService(inMemoryAccountRepository);
        var commandLineInterface = new CommandLineInterface(simpleAccountService);
        commandLineInterface.start();

    }
}
