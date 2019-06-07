package com.baeldung.hexagonal_architecture;

import java.io.File;

import com.baeldung.hexagonal_architecture.driver.ConsoleAdapter;
import com.baeldung.hexagonal_architecture.driven.FileAdapter;
import com.baeldung.hexagonal_architecture.core.FactsReader;
import com.baeldung.hexagonal_architecture.core.ObtainFacts;
import com.baeldung.hexagonal_architecture.core.RequestFacts;

public class App {
    public static void main(String[] args) {
        String filePath = new File("src/main/java/com/baeldung/hexagonal_architecture").getAbsolutePath();
        // 1. Instantiate file adapter
        ObtainFacts fileAdapter = new FileAdapter(filePath + "/input.txt");

        // 2. Instantiate the core
        RequestFacts factsReader = new FactsReader(fileAdapter);

        // 3. Instantiate the application console adapter
        ConsoleAdapter consoleAdapter = new ConsoleAdapter(factsReader);

        System.out.println("Fact of the day is ");
        System.out.println(consoleAdapter.askForFacts());

    }
}
