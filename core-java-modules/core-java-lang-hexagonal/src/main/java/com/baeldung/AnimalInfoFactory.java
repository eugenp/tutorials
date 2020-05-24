package baeldung;

import baeldung.adapter.AnimalRepositoryInMemoryImpl;
import baeldung.core.service.AnimalInfoServiceImpl;
import baeldung.port.input.AnimalInfo;
import baeldung.port.output.AnimalRepository;

public class AnimalInfoFactory {

        public static AnimalInfo instance() {
                AnimalRepository animalRepository = new AnimalRepositoryInMemoryImpl();
                AnimalInfo animalInfo = new AnimalInfoServiceImpl(animalRepository);
                return animalInfo;
        }
}
