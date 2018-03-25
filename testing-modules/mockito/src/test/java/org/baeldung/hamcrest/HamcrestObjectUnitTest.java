package org.baeldung.hamcrest;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.hamcrest.Matchers.not;

public class HamcrestObjectUnitTest {

    @Test
    public void givenACity_whenHasToString_thenCorrect() {
        City city = new City("San Francisco", "CA");

        assertThat(city, hasToString("[Name: San Francisco, State: CA]"));
    }

    @Test
    public void givenACity_whenHasToStringEqualToIgnoringCase_thenCorrect() {
        City city = new City("San Francisco", "CA");

        assertThat(city, hasToString(equalToIgnoringCase("[NAME: SAN FRANCISCO, STATE: CA]")));
    }

    @Test
    public void givenACity_whenHasToStringEmptyOrNullString_thenCorrect() {
        City city = new City(null, null);

        assertThat(city, hasToString(emptyOrNullString()));
    }

    @Test
    public void givenACity_whenTypeCompatibleWithLocation_thenCorrect() {
        City city = new City("San Francisco", "CA");

        assertThat(city.getClass(), is(typeCompatibleWith(Location.class)));
    }

    @Test
    public void givenACity_whenTypeNotCompatibleWithString_thenCorrect() {
        City city = new City("San Francisco", "CA");

        assertThat(city.getClass(), is(not(typeCompatibleWith(String.class))));
    }

    @Test
    public void givenACity_whenTypeCompatibleWithObject_thenCorrect() {
        City city = new City("San Francisco", "CA");

        assertThat(city.getClass(), is(typeCompatibleWith(Object.class)));
    }

}
