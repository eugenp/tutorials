package com.example.hexagonalarchitecture.kitchenassistant.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Getter
    private final Long id;

    @Getter
    private final Address address;

    @Getter
    private final Wallet wallet;

    public static User withId(Long id, Address address, Wallet wallet) {
        return new User(id, address, wallet);
    }

}
