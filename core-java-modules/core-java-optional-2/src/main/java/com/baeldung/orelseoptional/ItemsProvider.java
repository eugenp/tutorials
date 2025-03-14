package com.baeldung.orelseoptional;

import java.util.Optional;

public class ItemsProvider {

    Optional<String> getEmptyItem() {
        System.out.println("Returning an empty item");
        return Optional.empty();
    }

    Optional<String> getNail() {
        System.out.println("Returning a nail");
        return Optional.of("nail");
    }

    Optional<String> getHammer() {
        System.out.println("Returning a hammer");
        return Optional.of("hammer");
    }
}