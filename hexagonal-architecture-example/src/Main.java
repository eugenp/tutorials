import java.io.File;

import application.ConsoleAdapter;
import datastore.FileAdapter;
import domain.FactsReader;
import domain.ObtainFacts;
import domain.RequestFacts;

public class Main {

	public static void main(String[] args) {
		String filePath = new File("").getAbsolutePath();
		// 1. Instantiate file adapter
        ObtainFacts fileAdapter = new FileAdapter(filePath+"/src/input.txt");

        // 2. Instantiate the core
        RequestFacts factsReader = new FactsReader(fileAdapter);

        // 3. Instantiate the application console adapter
        ConsoleAdapter consoleAdapter = new ConsoleAdapter(factsReader);

        System.out.println("Fact of the day is ");
        System.out.println(consoleAdapter.askForFacts());

	}

}
