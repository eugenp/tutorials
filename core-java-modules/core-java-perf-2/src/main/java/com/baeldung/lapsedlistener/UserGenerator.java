package com.baeldung.lapsedlistener;

import net.datafaker.Faker;

public class UserGenerator {

    private UserGenerator() {
    }

    private static final Faker faker = new Faker();

    public static User generateUser() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String phone = faker.phoneNumber().cellPhone();
        String street = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String zipCode = faker.address().zipCode();
        return new User(name, email, phone, street, city, state, zipCode);
    }

}