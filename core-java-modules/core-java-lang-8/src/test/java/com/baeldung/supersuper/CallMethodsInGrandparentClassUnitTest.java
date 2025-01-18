package com.baeldung.supersuper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Person {

    String sayHello() {
        return "Person: How are you?";
    }
}

class Child extends Person {

    @Override
    String sayHello() {
        return "Child: How are you?";
    }

    String mySuperSayHello() {
        return super.sayHello();
    }
}

class Grandchild extends Child {

    @Override
    String sayHello() {
        return "Grandchild: How are you?";
    }

    String childSayHello() {
        return super.sayHello();
    }

    // super.super.sayHello() doesn't compile
    // String personSayHello() {
    //     return super.super.sayHello();
    // }

    String personSayHello() {
        return super.mySuperSayHello();
    }
}

public class CallMethodsInGrandparentClassUnitTest {

    @Test
    void whenCallMethodsInGrandparentClass_thenViolateEncapsulation() {
        Player liam = new Player("Liam", "football", 9);
        Player eric = new Player("Eric", "football", 99);
        Player kai = new Player("Kai", "tennis", 7);

        TopFootballPlayers topFootballPlayers = new TopFootballPlayers();
        assertTrue(topFootballPlayers.add(liam));

        Exception exEric = assertThrows(IllegalArgumentException.class, () -> topFootballPlayers.add(eric));
        assertEquals("Not a top player", exEric.getMessage());

        Exception exKai = assertThrows(IllegalArgumentException.class, () -> topFootballPlayers.add(kai));
        assertEquals("Not a football player", exKai.getMessage());

    }

    @Test
    void whenCallFathersMySuperSayHelloMethod_thenCalledGrandpaSayHelloMethod() {
        Grandchild aGrandchild = new Grandchild();
        assertEquals("Grandchild: How are you?", aGrandchild.sayHello());
        assertEquals("Child: How are you?", aGrandchild.childSayHello());
        assertEquals("Person: How are you?", aGrandchild.personSayHello());
    }
}