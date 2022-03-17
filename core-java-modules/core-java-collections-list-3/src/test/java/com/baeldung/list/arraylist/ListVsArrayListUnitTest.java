package com.baeldung.list.arraylist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListVsArrayListUnitTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private ArrayList<String> passengerList = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outputStreamCaptor));

        passengerList.add("Anna");
        passengerList.add("Binny");
        passengerList.add("Chandra");
    }

    @Test
    void givenArrayList_whenCalledUsingArrayListTypeParam_thenPrintsValues() {
        ListVsArrayList.printNames(passengerList);
        String actual = outputStreamCaptor.toString().trim();

        assertNotNull(actual);
        assertThat(actual).isEqualTo(passengerList.toString());
    }

    @Test
    void givenArrayList_whenCalledUsingListTypeParam_thenPrintsValues() {
        List<String> list = new ArrayList<>(passengerList);
        ListVsArrayList.printNamesListType(list);
        String actual = outputStreamCaptor.toString().trim();

        assertNotNull(actual);
        assertThat(actual).isEqualTo(passengerList.toString());
    }

    @Test
    void givenArrayList_whenCalledWithCollectionsFunction_thenReturnsListType() {
        List<String> actual = ListVsArrayList.getNames(passengerList);

        assertNotNull(actual);
        assertThat(actual.toString()).isEqualTo(passengerList.toString());
    }

    @Test
    void givenString_whenCalledWithCollectionsFunction_thenReturnsListType() {
        List<String> actual = ListVsArrayList.copyName(passengerList.get(0));

        assertNotNull(actual);
        assertThat(actual.get(0)).isEqualTo(passengerList.get(0));
    }

    @Test
    void givenLocale_whenUsingStreams_thenReturnsListType() {
        List<String> actual = ListVsArrayList.getCountries();

        assertNotNull(actual);
        assertThat(actual.size()).isEqualTo(Locale.getISOCountries().length);
    }

}
