package com.baeldung.list.listvsarraylist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListDemoUnitTest {

    ListDemo application = new ListDemo();

    Passenger passenger1;
    Passenger passenger2;
    Passenger passenger3;
    Passenger passenger4;
    Passenger passenger5;
    Passenger passenger6;

    @BeforeEach
    protected void setUp() {
        passenger1 = new Passenger("Anna", 25, "London", "New York");
        passenger2 = new Passenger("Binny", 35, "New York", "London");
        passenger3 = new Passenger("Chandra", 8, "Paris", "New Delhi");
        passenger4 = new Passenger("Amanda", 32, "Paris", "Hamburg");
        passenger5 = new Passenger("Dora", 8, "Paris", "Hamburg");
        passenger6 = new Passenger("Kent", 48, "Paris", "Munich");

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

    @Test
    void whenUsingSingletonListUtilMethod_thenGetExpectedResult() {
        List<Passenger> singletonList = Collections.singletonList(passenger1);

        assertThat(singletonList).hasSize(1);
        assertThat(singletonList.get(0)).isEqualTo(passenger1);
        assertThrows(UnsupportedOperationException.class, () -> singletonList.add(passenger2));
    }

    @Test
    void whenUsingUnmodifiableListUtilMethod_thenGetExpectedResult() {
        List<Passenger> originalList = new ArrayList<>();
        originalList.add(passenger1);
        originalList.add(passenger2);

        List<Passenger> unmodifiableList = Collections.unmodifiableList(originalList);
        assertThat(unmodifiableList).isEqualTo(originalList);

        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add(passenger3));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.remove(passenger2));

        originalList.add(passenger3);
        assertThat(unmodifiableList).hasSize(3);
    }

    @Test
    void whenUsingArraysAsList_thenGetExpectedResult() {
        Passenger[] array = { passenger1, passenger2 };
        List<Passenger> list = Arrays.asList(array);

        assertThat(list).hasSize(2);
        assertThat(list.get(0)).isEqualTo(passenger1);
        assertThat(list.get(1)).isEqualTo(passenger2);

        // We can update elements (reflected in the array too)
        list.set(1, passenger3);
        assertThat(array[1]).isEqualTo(passenger3);

        // Verify immutability of size: adding/removing throws UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> list.add(passenger2));
        assertThrows(UnsupportedOperationException.class, () -> list.remove(passenger1));
    }

    @Test
    void whenUsingArraysSubList_thenGetExpectedResult() {
        List<Passenger> originalList = Arrays.asList(passenger1, passenger2, passenger3, passenger4, passenger5);

        List<Passenger> subList = originalList.subList(1, 3);

        assertThat(subList).isEqualTo(Arrays.asList(passenger2, passenger3));

        // Changes in sublist reflect in the original list
        subList.set(1, passenger6);
        assertThat(originalList.get(2)).isEqualTo(passenger6);

        // immutability of size: adding/removing throws UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> subList.add(passenger4));
        assertThrows(UnsupportedOperationException.class, () -> subList.remove(passenger2));
    }

    @Test
    void whenUsingListOf_thenGetExpectedResult() {
        List<Passenger> list = List.of(passenger1, passenger2, passenger3);

        assertThat(list).isEqualTo(Arrays.asList(passenger1, passenger2, passenger3));

        assertThrows(UnsupportedOperationException.class, () -> list.add(passenger4));
        assertThrows(UnsupportedOperationException.class, () -> list.set(0, passenger4));
        assertThrows(UnsupportedOperationException.class, () -> list.remove(passenger2));
    }

    @Test
    void whenUsingListCopyOf_thenGetExpectedResult() {
        List<Passenger> originalList = new ArrayList<>();
        originalList.add(passenger1);
        originalList.add(passenger2);
        originalList.add(passenger3);

        List<Passenger> copy = List.copyOf(originalList);

        assertThat(copy).isEqualTo(originalList);

        assertThrows(UnsupportedOperationException.class, () -> copy.add(passenger4));
        assertThrows(UnsupportedOperationException.class, () -> copy.set(0, passenger4));
        assertThrows(UnsupportedOperationException.class, () -> copy.remove(passenger2));

        // Changes to the original list do NOT affect the copy
        originalList.add(passenger6);
        assertThat(copy).hasSize(3);
    }
}