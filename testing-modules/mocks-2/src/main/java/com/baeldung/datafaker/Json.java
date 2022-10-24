package com.baeldung.datafaker;

import net.datafaker.Faker;

public class Json {
    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        System.out.println(getExpression());
    }

    static String getExpression() {
        return faker.expression(
                "#{json 'person'," +
                        "'#{json ''first_name'',''#{Name.first_name}'',''last_name'',''#{Name.last_name}''}'," +
                "'address'," +
                        "'#{json ''country'',''#{Address.country}'',''city'',''#{Address.city}''}'}");
    }
}
