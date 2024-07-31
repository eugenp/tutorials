package com.baeldung.list.listoflists;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class ListOfListsUnitTest {

    private List<List<String>> getListOfListsFromCsv() throws URISyntaxException, IOException {

        List<String> lines = Files.readAllLines(Paths.get(getClass().getResource("/listoflists/example.csv")
            .toURI()));
        List<List<String>> listOfLists = new ArrayList<>();

        lines.forEach(line -> {
            List<String> innerList = new ArrayList<>(Arrays.asList(line.split(", ")));
            listOfLists.add(innerList);
        });
        return listOfLists;
    }

    private void printListOfLists(List<List<String>> listOfLists) {
        System.out.println("\n           List of Lists           ");
        System.out.println("-------------------------------------");
        listOfLists.forEach(innerList -> {
            String line = String.join(", ", innerList);
            System.out.println(line);
        });
    }

    @Test
    void givenCsv_whenInitListOfLists_thenGetExpectedResults() throws URISyntaxException, IOException {
        List<List<String>> listOfLists = getListOfListsFromCsv();
        assertThat(listOfLists).hasSize(3);
        assertThat(listOfLists.stream()
            .map(List::size)
            .collect(Collectors.toSet())).hasSize(1)
            .containsExactly(4);
        printListOfLists(listOfLists);
    }

    @Test
    void givenListOfLists_whenRemoveElementFromInnerLists_thenGetExpectedResults() throws URISyntaxException, IOException {
        List<List<String>> listOfLists = getListOfListsFromCsv();

        listOfLists.forEach(innerList -> innerList.remove("Delete Me"));
        assertThat(listOfLists.stream()
            .map(List::size)
            .collect(Collectors.toSet())).hasSize(1)
            .containsExactly(3);

        printListOfLists(listOfLists);
    }

    @Test
    void givenListOfLists_whenAddNewInnerList_thenGetExpectedResults() throws URISyntaxException, IOException {
        List<List<String>> listOfLists = getListOfListsFromCsv();
        List<String> newList = new ArrayList<>(Arrays.asList("Slack", "Zoom", "Microsoft Teams", "Telegram"));
        listOfLists.add(2, newList);

        assertThat(listOfLists).hasSize(4);
        assertThat(listOfLists.get(2)).containsExactly("Slack", "Zoom", "Microsoft Teams", "Telegram");
        printListOfLists(listOfLists);
    }

    @Test
    void givenListOfLists_whenGettingSizeOfSubListsAndSizeOfElements_thenGetExpectedResults() throws URISyntaxException, IOException {
        List<List<String>> listOfLists = getListOfListsFromCsv();
        // size of inner lists
        assertThat(listOfLists).hasSize(3);

        // size of all elements in subLists
        int totalElements = listOfLists.stream().mapToInt(List::size).sum();
        assertThat(totalElements).isEqualTo(12);
    }
}