package baeldung.adapter;

import baeldung.core.model.Animal;
import baeldung.port.output.AnimalRepository;

import java.util.List;
import java.util.Optional;

public class AnimalRepositoryAdapter implements AnimalRepository {

        private List<Animal> animals;

        public AnimalRepositoryAdapter() {
                animals = List.of(new Animal("baboon", "primate"), new Animal("spider", "arachnid"));
        }

        public Optional<Animal> getAnimalByName(String name) {
                return animals.stream().filter(animal -> animal.getName().equals(name)).findAny();
        }
}
