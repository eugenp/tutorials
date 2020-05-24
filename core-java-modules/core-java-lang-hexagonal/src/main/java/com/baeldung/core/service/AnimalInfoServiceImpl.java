package baeldung.core.service;


import baeldung.core.model.Animal;
import baeldung.port.input.AnimalInfo;
import baeldung.port.output.AnimalRepository;

import java.util.Optional;

public class AnimalInfoServiceImpl implements AnimalInfo {

    private AnimalRepository animalRepository;

    public AnimalInfoServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public String getClassByAnimalName(String animalName) {
        Optional<Animal> animalOptional = animalRepository.getAnimalByName(animalName);
        return animalOptional.map(Animal::getClassName).orElse("Animal not found, sorry!!");
    }
}
