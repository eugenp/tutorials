package com.baeldung.list.listvsarraylist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListDemoUnitTest {

    ListDemo application = new ListDemo();

    Passenger passenger1;
    Passenger passenger2;
    Passenger passenger3;

    @BeforeEach
    protected void setUp() {
        passenger1 = new Passenger("Anna", 25, "London", "New York");
        passenger2 = new Passenger("Binny", 35, "New York", "London");
        passenger3 = new Passenger("Chandra", 8, "Paris", "New Delhi");
        application.addPassenger(passenger1);
        application.addPassenger(passenger2);
        application.addPassenger(passenger3);
    }

    @Test
    public void givenEmptyList_whenAddedPassenger_thenReturnCurrentPassengerList() {
        List<Passenger> list = application.addPassenger(new Passenger("David", 54, "Milan", "Paris"));

        assertNotNull(list);
        assertThat(list).hasSize(4);
    }

    @Test
    public void givenPresentList_whenRemovedPassenger_thenReturnCurrentPassengerList() {
        List<Passenger> list = application.removePassenger(passenger3);

        assertNotNull(list);
        assertThat(list).hasSize(2);
    }

    @Test
    public void givenPresentList_whenPassedWithSourceCity_thenReturnMatchingPassengerList() {
        List<Passenger> list = application.getPassengersBySource("Singapore");
        List<Passenger> list2 = application.getPassengersBySource("London");

        assertThat(list).isEmpty();
        assertThat(list2.get(0)).isEqualTo(passenger1);
    }

    @Test
    public void givenPresentList_whenPassedWithDestinationCity_thenReturnMatchingPassengerList() {
        List<Passenger> list = application.getPassengersByDestination("Singapore");
        List<Passenger> list2 = application.getPassengersByDestination("London");

        assertThat(list).isEmpty();
        assertThat(list2.get(0)).isEqualTo(passenger2);
    }

    @Test
    public void givenPassengerList_whenFindKidsByAge_thenReturnKidsList() {
        List<Passenger> list = new ArrayList<>();
        list.add(passenger1);
        list.add(passenger2);
        list.add(passenger3);
        long count = application.getKidsCount(list);

        assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenPresentList_whenCalledWithCollectionsFunction_thenReturnsListType() {
        List<Passenger> list = application.getFinalPassengersList();

        assertNotNull(list);
        assertThat(list).hasSize(3);
    }

    @Test
    public void givenCurrentLocale_whenUsingStreams_thenReturnsListType() {
        List<String> servicedCountries = application.getServicedCountries();

        assertNotNull(servicedCountries);
        assertThat(servicedCountries).hasSize(Locale.getISOCountries().length);
    }

}
