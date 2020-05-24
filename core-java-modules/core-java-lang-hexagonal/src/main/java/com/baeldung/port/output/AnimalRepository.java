package baeldung.port.output;

import baeldung.core.model.Animal;

import java.util.Optional;

public interface AnimalRepository {
        Optional<Animal> getAnimalByName(String name);
}
