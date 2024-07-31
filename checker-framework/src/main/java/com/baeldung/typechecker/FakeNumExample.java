package com.baeldung.typechecker;

import org.checkerframework.checker.fenum.qual.Fenum;

//@SuppressWarnings("fenum:assignment.type.incompatible")
public class FakeNumExample {

    // Here we use some String constants to represents countries.
    static final @Fenum("country") String ITALY = "IT";
    static final @Fenum("country") String US = "US";
    static final @Fenum("country") String UNITED_KINGDOM = "UK";

    // Here we use other String constants to represent planets instead.
    static final @Fenum("planet") String MARS = "Mars";
    static final @Fenum("planet") String EARTH = "Earth";
    static final @Fenum("planet") String VENUS = "Venus";

    // Now we write this method and we want to be sure that
    // the String parameter has a value of a country, not that is just a String.
    void greetCountries(@Fenum("country") String country) {
        System.out.println("Hello " + country);
    }

    // Similarly we're enforcing here that the provided
    // parameter is a String that represent a planet.
    void greetPlanets(@Fenum("planet") String planet) {
        System.out.println("Hello " + planet);
    }

    public static void main(String[] args) {

        FakeNumExample obj = new FakeNumExample();

        // This will fail because we pass a planet-String to a method that
        // accept a country-String.
        obj.greetCountries(MARS);

        // Here the opposite happens.
        obj.greetPlanets(US);
    }

}
