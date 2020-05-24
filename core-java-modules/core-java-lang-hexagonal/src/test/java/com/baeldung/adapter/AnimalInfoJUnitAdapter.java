package baeldung.adapter;

import baeldung.core.model.Animal;
import baeldung.core.service.AnimalInfoServiceImpl;
import baeldung.port.input.AnimalInfo;
import baeldung.port.output.AnimalRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AnimalInfoJUnitAdapter {

    private AnimalInfo animalInfo;

    @Mock
    private AnimalRepository animalRepository;

    @Before
    public void setup() {
        animalInfo = new AnimalInfoServiceImpl(animalRepository);
    }

    @Test
    public void whenRunningForValidAnimalName_shouldReturnCorrectAnimalClass() {
        Animal animal = new Animal("testName", "testClass");
        Mockito.when(animalRepository.getAnimalByName(animal.getName())).thenReturn(Optional.of(animal));
        String animalClass = animalInfo.getClassByAnimalName(animal.getName());
        Assert.assertEquals(animal.getClassName(), animalClass);
    }

    @Test
    public void whenRunningForInvalidAnimalName_shouldReturnDefaultResponse() {
        Animal animal = new Animal("testName", "testClass");
        Mockito.when(animalRepository.getAnimalByName(Mockito.notNull())).thenReturn(Optional.empty());
        String animalClass = animalInfo.getClassByAnimalName(animal.getName());
        Assert.assertEquals("Animal not found, sorry!!", animalClass);
    }
}
