package com.baeldung.typechecker;

import org.checkerframework.checker.fenum.qual.Fenum;

@SuppressWarnings("fenum:assignment.type.incompatible")
public class FakeNumExample {

    static final @Fenum("country") String ITALY = "IT";
    static final @Fenum("country") String US = "US";
    static final @Fenum("country") String UNITED_KINGDOM = "UK";

    static final @Fenum("planet") String MARS = "Mars";
    static final @Fenum("planet") String EARTH = "Earth";
    static final @Fenum("planet") String VENUS = "Venus";

    @SuppressWarnings("fenum:argument.type.incompatible")
    void greetCountries(@Fenum("country") String country){
        System.out.println("Hello " + country);
    }

    @SuppressWarnings("fenum:argument.type.incompatible")
    void greetPlanets(@Fenum("planet") String planet){
        System.out.println("Hello " + planet);
    }

    public static void main(String[] args) {
        FakeNumExample obj = new FakeNumExample();
        obj.greetCountries(MARS);
        obj.greetPlanets(US);
    }

}
