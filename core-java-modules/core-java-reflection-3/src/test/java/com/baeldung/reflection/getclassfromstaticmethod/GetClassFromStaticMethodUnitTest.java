package com.baeldung.reflection.getclassfromstaticmethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.Test;

class Player {

    private String name;
    private int age;

    public Player(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Class<?> currentClass() {
        return getClass();
    }

    // the method below doesn't compile
    //  public static Class<?> getClassInStatic() {
    //      return getClass();
    //  }

    public static Class<?> currentClassByClassLiteral() {
        return Player.class;
    }

    public static Class<?> currentClassByMethodHandles() {
        return MethodHandles.lookup()
            .lookupClass();
    }
}

public class GetClassFromStaticMethodUnitTest {

    @Test
    void whenUsingGetClass_thenGetExpectedClass() {
        Player kai = new Player("Kai", 25);
        assertSame(Player.class, kai.currentClass());
    }

    @Test
    void whenUsingClassName_thenGetExpectedClass() {
        assertSame(Player.class, Player.currentClassByClassLiteral());
    }

    @Test
    void whenUsingMethodHandlers_thenGetExpectedClass() {
        assertSame(Player.class, Player.currentClassByMethodHandles());
    }

}