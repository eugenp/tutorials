package com.baeldung.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by madhumita.g on 25-07-2018.
 */
public class AnimalActivity {

    final static Logger logger = LoggerFactory.getLogger(AnimalActivity.class);


    public static void eat(Animal animal) {
        logger.info("Animal is eating");
    }

    public static void eat(Dog dog) {
        logger.info("Dog is eating");
    }

    public static void main(String[] args) {

        Animal animal = new Animal();

        //calling methods of animal object
        animal.makeNoise();

        animal.makeNoise(3);


        //assigning a dog object to reference of type Animal
        Animal dogAnimal = new Dog();

        dogAnimal.makeNoise();

        // calling static function
        AnimalActivity.eat(dogAnimal);

        return;

    }
}
