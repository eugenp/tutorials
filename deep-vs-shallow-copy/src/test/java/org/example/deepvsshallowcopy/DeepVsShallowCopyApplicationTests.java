package org.example.deepvsshallowcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import dto.Animal;
import dto.Car;
import dto.EatType;
import dto.Person;

@SpringBootTest
class DeepVsShallowCopyApplicationTests {
    @Test
    void shallowCopy() {
        Car car1 = new Car("BMW");
        Person person1 = new Person("oscar", car1);
        Person person2 = person1.clone();
        Assertions.assertEquals(person1.car.model, person2.car.model);
        person2.car.model = "Audi";
        Assertions.assertEquals(person1.car.model, person2.car.model);
}

    @Test
    void deepCopy() {
        EatType eatType1 = new EatType("carnivore");
        Animal animal1 = new Animal("lion", eatType1);
        Animal animal2 = animal1.clone();
        Assertions.assertEquals(animal1.food.foodType, animal2.food.foodType);
        animal2.food.foodType = "omnivore";
        Assertions.assertNotEquals(animal1.food.foodType, animal2.food.foodType);
    }
}
