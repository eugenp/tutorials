package com.baeldung.hexagonal;

//secondary port
interface HatPersistence {

    void save(String name, String hat);

    String findByName(String name);
}
