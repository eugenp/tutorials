package org.example.deepvsshallowcopy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import dto.Animal;
import dto.Car;
import dto.EatType;
import dto.Person;

@SpringBootTest
class DeepVsShallowCopyApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void shallowCopy() {
        Car car1 = new Car("BMW");
        Person person1 = new Person("oscar", car1);
        Person person2 = person1.clone();
        System.out.println("Person 1 name: " + person1.name);
        System.out.println("Person 1 car model: " + person1.car.model);
        System.out.println("Person 2 name: " + person2.name);
        System.out.println("Person 2 car model: " + person2.car.model);

        System.out.println("==============");
        person2.car.model = "Audi";
        System.out.println("Person 1 name: " + person1.name);
        System.out.println("Person 1 car model: " + person1.car.model);
        System.out.println("Person 2 name: " + person2.name);
        System.out.println("Person 2 car model: " + person2.car.model);
    }

    @Test
    void deepCopy() {
        EatType eatType1 = new EatType("carnivore");
        Animal animal1 = new Animal("lion", eatType1);
        Animal animal2 = animal1.clone();
        System.out.println("Animal 1 name: " + animal1.name);
        System.out.println("Animal 1 food-type: " + animal1.food.foodType);
        System.out.println("Animal 2 name: " + animal2.name);
        System.out.println("Animal 2 food-type: " + animal2.food.foodType);

        System.out.println("==============");
        animal2.food.foodType = "omnivore";
        System.out.println("Animal 1 name: " + animal1.name);
        System.out.println("Animal 1 food-type: " + animal1.food.foodType);
        System.out.println("Animal 2 name: " + animal2.name);
        System.out.println("Animal 2 food-type: " + animal2.food.foodType);
    }
}
