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

class Woman extends Person {

    @Override
    String sayHello() {
        return "Woman: How are you?";
    }

    String mySuperSayHello() {
        return super.sayHello();
    }
}

class Girl extends Woman {

    @Override
    String sayHello() {
        return "Girl: How are you?";
    }

    String womanSayHello() {
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
        Girl aGirl = new Girl();
        assertEquals("Girl: How are you?", aGirl.sayHello());
        assertEquals("Woman: How are you?", aGirl.womanSayHello());
        assertEquals("Person: How are you?", aGirl.personSayHello());
    }
}