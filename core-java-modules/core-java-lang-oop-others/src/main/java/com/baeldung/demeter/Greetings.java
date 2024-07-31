package com.baeldung.demeter;

public class Greetings {

    HelloCountries helloCountries = new HelloCountries();

    private static HelloCountries helloCountriesStatic = new HelloCountries();

    public String generalGreeting() {
        return "Welcome" + world();
    }

    public String world() {
        return "Hello World";
    }

    public String getHelloBrazil() {
        HelloCountries helloCountries = new HelloCountries();
        return helloCountries.helloBrazil();
    }

    public String getHelloIndia(HelloCountries helloCountries) {
        return helloCountries.helloIndia();
    }

    public String getHelloJapan() {
        return helloCountries.helloJapan();
    }

    public String getHellStaticWorld() {
        return helloCountriesStatic.helloStaticWorld();
    }
}
