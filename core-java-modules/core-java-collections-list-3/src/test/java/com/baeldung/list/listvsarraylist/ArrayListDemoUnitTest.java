package com.baeldung.list.listvsarraylist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayListDemoUnitTest {

    ArrayListDemo application = new ArrayListDemo();

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
        ArrayList<Passenger> list = application.addPassenger(new Passenger("David", 54, "Milan", "Paris"));

        assertNotNull(list);
        assertThat(list).hasSize(4);
    }

    @Test
    public void givenPassengerList_whenRemovedPassenger_thenReturnCurrentPassengerList() {
        ArrayList<Passenger> list = application.removePassenger(passenger3);

        assertNotNull(list);
        assertThat(list).hasSize(2);
    }

    @Test
    public void givenPassengerList_whenPassedWithSourceCity_thenReturnMatchingPassengerList() {
        ArrayList<Passenger> list = application.getPassengersBySource("Singapore");
        ArrayList<Passenger> list2 = application.getPassengersBySource("London");

        assertThat(list).isEmpty();
        assertThat(list2.get(0)).isEqualTo(passenger1);
    }

    @Test
    public void givenPassengerList_whenPassedWithDestinationCity_thenReturnMatchingPassengerList() {
        ArrayList<Passenger> list = application.getPassengersByDestination("Singapore");
        ArrayList<Passenger> list2 = application.getPassengersByDestination("London");

        assertThat(list).isEmpty();
        assertThat(list2.get(0)).isEqualTo(passenger2);
    }

    @Test
    public void givenPassengerList_whenFindKidsByAge_thenReturnKidsList() {
        ArrayList<Passenger> list = new ArrayList<>();
        list.add(passenger1);
        list.add(passenger2);
        list.add(passenger3);
        long count = application.getKidsCount(list);

        assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenPassengerList_whenCalledWithCollectionsFunction_thenReturnsListType() {
        ArrayList<Passenger> list = application.getFinalPassengersList();

        assertNotNull(list);
        assertThat(list).hasSize(3);
    }

    @Test
    public void givenCurrentLocale_whenUsingStreams_thenReturnsListType() {
        ArrayList<String> servicedCountries = application.getServicedCountries();

        assertNotNull(servicedCountries);
        assertThat(servicedCountries).hasSize(Locale.getISOCountries().length);
    }

}
