package baeldung.adapter;

import baeldung.port.input.AnimalInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AnimalInfoConsoleAdapter {

        private AnimalInfo animalInfo;

        public AnimalInfoConsoleAdapter(AnimalInfo animalInfo) {
                this.animalInfo = animalInfo;
        }

        public void printAnimalSpecies() throws IOException {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String name = reader.readLine();
                String animalSpecies = animalInfo.getClassByAnimalName(name);
                System.out.println(String.format("The specie of the animal : %s is %s", name, animalSpecies));
        }
}
