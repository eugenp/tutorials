package com.baeldung.casting;

import java.util.List;

/**
 * 动物饲养员
 * @author zn.wang
 */
public class AnimalFeeder {

    public void feed(List<Animal> animals) {
        for (Animal animal : animals) {
            animal.eat();
            if (animal instanceof Cat) {
                ((Cat) animal).meow();
            }
        }
    }

    /**
     * 无节制的饲料
     * @param animals
     */
    public void uncheckedFeed(List<Animal> animals) {
        for (Animal animal : animals) {
            animal.eat();
            ((Cat) animal).meow();
        }
    }

}
