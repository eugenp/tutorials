package baeldung;

import baeldung.adapter.AnimalInfoConsoleAdapter;

import java.io.IOException;

public class ApplicationRunner {

        public static void main(String[] args) throws IOException {
                AnimalInfoConsoleAdapter animalInfoConsoleAdapter = new AnimalInfoConsoleAdapter(AnimalInfoFactory.instance());
                animalInfoConsoleAdapter.printAnimalSpecies();
        }
}
