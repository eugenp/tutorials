package com.baeldung.supersuper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Grandpa {

    String sayHello() {
        return "Grandpa: How are you?";
    }
}

class Father extends Grandpa {

    @Override
    String sayHello() {
        return "Father: How are you?";
    }

    String mySuperSayHello() {
        return super.sayHello();
    }
}

class Child extends Father {

    @Override
    String sayHello() {
        return "Child: How are you?";
    }

    String fatherSayHello() {
        return super.sayHello();
    }

    // super.super.sayHello() doesn't compile
    // String grandpaSayHello() {
    //     return super.super.sayHello();
    // }

    String grandpaSayHello() {
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
        Child aChild = new Child();
        assertEquals("Child: How are you?", aChild.sayHello());
        assertEquals("Father: How are you?", aChild.fatherSayHello());
        assertEquals("Grandpa: How are you?", aChild.grandpaSayHello());
    }
}