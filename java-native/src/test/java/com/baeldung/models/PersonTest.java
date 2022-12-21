package com.baeldung.models;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class PersonTest{
    @Test
    public void shallowCopy() {
        Car car1 = new Car("Tesla");
        Person p = new Person("Christophe", 24, car1);
        Person shallowCopyP = new Person(p.getName(), p.getAge(), p.getCar());
        shallowCopyP.getCar().setModel("Audi");
        shallowCopyP.setAge(18);
        Assertions.assertNotEquals(p, shallowCopyP);
        Assertions.assertEquals(p.getCar(), shallowCopyP.getCar());
        Assertions.assertNotEquals(p.getAge(), shallowCopyP.getAge());
    }

    @Test
    public void deepCopy() {
        Car car1 = new Car("Tesla");
        Person p = new Person("Christophe", 24, car1);
        Person deepCopy = new Person(p);
        deepCopy.getCar().setModel("Audi");
        Assertions.assertNotEquals(p, deepCopy);
        Assertions.assertNotEquals(p.getCar(), deepCopy.getCar());
    }
}