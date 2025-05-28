package com.baeldung.interfacesingleimpl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class InterfaceSingleImplUnitTest {
    @Test
    public void whenUsingMockAnimal_thenAnimalSoundIsCorrect() {
        MockAnimal mockAnimal = new MockAnimal();
        String expected = "Mock animal sound!";
        AnimalCare animalCare = new AnimalCare(mockAnimal);
        assertThat(animalCare.animalSound()).isEqualTo(expected);
    }

    @Test
    public void whenCreatingDog_thenDogMakesWoofSound() {
        Dog dog = new Dog("Buddy");
        String expected = "Woof! My name is Buddy";
        assertThat(dog.makeSound()).isEqualTo(expected);
    }

    @Test
    public void whenCreatingCat_thenCatMakesMeowSound() {
        Cat cat = new Cat("FuzzBall");
        String expected = "Meow! My name is FuzzBall";
        assertThat(cat.makeSound()).isEqualTo(expected);
    }

    @Test
    public void whenCreatingAnimalCareWithDog_thenDogMakesWoofSound() {
        Animal dog = new Dog("Ham");
        AnimalCare animalCare = new AnimalCare(dog);
        String expected = "Woof! My name is Ham";
        assertThat(animalCare.animalSound()).isEqualTo(expected);
    }

    @Test
    public void whenCreatingCatCareWithCat_thenCatMakesMeowSound() {
        Cat cat = new Cat("Grumpy");
        String expected = "Meow! My name is Grumpy";
        assertThat(cat.makeSound()).isEqualTo(expected);
    }

    @Test
    public void whenCreatingMockAnimal_thenMockAnimalMakesMockAnimalSound() {
        MockAnimal mockAnimal = new MockAnimal();
        String expected = "Mock animal sound!";
        assertThat(mockAnimal.makeSound()).isEqualTo(expected);
    }
}
