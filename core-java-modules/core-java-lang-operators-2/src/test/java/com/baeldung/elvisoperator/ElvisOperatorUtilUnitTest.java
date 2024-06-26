package com.baeldung.elvisoperator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ElvisOperatorUtilUnitTest {

    @Test
    public void givenAString_whenUsingOptional_thenReturnExpectedValue() {
        User user = new User("Baeldung");

        String greeting = ElvisOperatorUtil.usingOptional(user);
        assertEquals("BAELDUNG", greeting);
    }

    @Test
    public void givenAnEmptyString_whenUsingOptional_thenReturnDefaultValue() {
        User user = new User(null);

        String greeting = ElvisOperatorUtil.usingOptional(user);
        assertEquals("Hello Stranger", greeting);
    }

    @Test
    public void givenAString_whenUsingTernaryOperator_thenReturnExpectedValue() {
        User user = new User("Baeldung");

        String greeting = ElvisOperatorUtil.usingTernaryOperator(user);
        assertEquals("Baeldung", greeting);
    }

    @Test
    public void givenAnEmptyString_whenUsingTernaryOperator_thenReturnDefaultValue() {
        User user = new User(null);

        String greeting = ElvisOperatorUtil.usingOptional(user);
        assertEquals("Hello Stranger", greeting);
    }

    @Test
    public void givenAString_whenUsingCustomMethod_thenReturnExpectedValue() {
        User user = new User("Baeldung");
        String greeting = ElvisOperatorUtil.elvis(user.getName(), "Hello Stranger");
        assertEquals("Baeldung", greeting);
    }

    @Test
    public void givenAnEmptyString_whenUsingCustomMethod_thenReturnDefaultValue() {
        User user = new User(null);

        String greeting = ElvisOperatorUtil.usingOptional(user);
        assertEquals("Hello Stranger", greeting);
    }

    @Test
    public void givenAUserWithCity_whenUsingChaningCustomMethod_thenReturnExpectedValue() {
        User user = new User("Baeldung");
        user.setAddress(new Address("Singapore"));

        String cityName = ElvisOperatorUtil.elvis(ElvisOperatorUtil.elvis(user, new User("Stranger"))
                .getAddress(), new Address("Default City"))
            .getCity();

        assertEquals("Singapore", cityName);
    }

    @Test
    public void givenAUserWithEmptyCity_whenUsingChaningCustomMethod_thenReturnDefaultValue() {
        User user = new User("Baeldung");
        user.setAddress(null);

        String cityName = ElvisOperatorUtil.elvis(ElvisOperatorUtil.elvis(user, new User("Stranger"))
                .getAddress(), new Address("Default City"))
            .getCity();

        assertEquals("Default City", cityName);
    }
}
