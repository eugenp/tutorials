package com.baeldung.priorityqueuewithpair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Stream;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class PriorityQueueWithComplexObjectUnitTest {

    @Test
    void givenMeetings_whenUseWithPriorityQueue_thenSortByStartDateTime() {
        Meeting projectDiscussion = new Meeting(
          LocalDateTime.parse("2025-11-10T19:00:00"),
          LocalDateTime.parse("2025-11-10T20:00:00"),
          "Project Discussion"
        );
        Meeting businessMeeting = new Meeting(
          LocalDateTime.parse("2025-11-15T14:00:00"),
          LocalDateTime.parse("2025-11-15T16:00:00"),
          "Business Meeting"
        );
        PriorityQueue<Meeting> meetings = new PriorityQueue<>();
        meetings.add(projectDiscussion);
        meetings.add(businessMeeting);

        assertThat(meetings.poll()).isEqualTo(projectDiscussion);
        assertThat(meetings.poll()).isEqualTo(businessMeeting);
    }

    @ParameterizedTest
    @MethodSource("pairProvider")
    void givenPairs_whenUsePriorityQueue_thenSortThemBySecondElement(List<Pair<String, Integer>> pairs) {
        PriorityQueue<Pair<String, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Pair::getSecond));

        queue.addAll(pairs);
        Pair<String, Integer> previousEntry = queue.poll();
        while (!queue.isEmpty()) {
            Pair<String, Integer> currentEntry = queue.poll();
            assertThat(previousEntry.getSecond()).isLessThanOrEqualTo(currentEntry.getSecond());
            previousEntry = currentEntry;
        }
    }

    @ParameterizedTest
    @MethodSource("mapEntryProvider")
    void givenMapEntries_whenUsePriorityQueue_thenSortThemBySecondElement(List<Map.Entry<String, Integer>> pairs) {
        PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        queue.addAll(pairs);
        Map.Entry<String, Integer> previousEntry = queue.poll();
        while (!queue.isEmpty()) {
            Map.Entry<String, Integer> currentEntry = queue.poll();
            assertThat(previousEntry.getValue()).isLessThanOrEqualTo(currentEntry.getValue());
            previousEntry = currentEntry;
        }
    }

    @ParameterizedTest
    @MethodSource("bookProvider")
    void givenBooks_whenUsePriorityQueue_thenSortThemBySecondElement(List<Book> books) {
        PriorityQueue<Book> queue = new PriorityQueue<>(Comparator.comparingInt(Book::getPublicationYear));
        queue.addAll(books);
        Book previousBook = queue.poll();
        while (!queue.isEmpty()) {
            Book currentBook = queue.poll();
            assertThat(previousBook.getPublicationYear())
              .isLessThanOrEqualTo(currentBook.getPublicationYear());
            previousBook = currentBook;
        }
    }

    @ParameterizedTest
    @MethodSource("bookProvider")
    void givenBooks_whenUsePriorityQueueWithoutComparator_thenThrowClassCastExcetption(List<Book> books) {
        PriorityQueue<Book> queue = new PriorityQueue<>();
        assertThatExceptionOfType(ClassCastException.class).isThrownBy(() -> queue.addAll(books));
    }

    @ParameterizedTest
    @MethodSource("bookProvider")
    void givenBooks_whenUsePriorityQueueWithoutComparatorWithoutAddingElements_thenNoExcetption(List<Book> books) {
        PriorityQueue<Book> queue = new PriorityQueue<>();
        assertThat(queue).isNotNull();
    }

    static Stream<List> pairProvider() {
        return Stream.of(
          List.of(
            new Pair<>("Dune", 1965),
            new Pair<>("Foundation", 1951),
            new Pair<>("2001: A Space Odyssey", 1968),
            new Pair<>("Do Androids Dream of Electric Sheep?", 1968),
            new Pair<>("Ender's Game", 1985),
            new Pair<>("The Hitchhiker's Guide to the Galaxy", 1979),
            new Pair<>("1984", 1949),
            new Pair<>("Brave New World", 1932),
            new Pair<>("Fahrenheit 451", 1953),
            new Pair<>("Neuromancer", 1984),
            new Pair<>("Stranger in a Strange Land", 1961),
            new Pair<>("The Handmaid's Tale", 1985),
            new Pair<>("Ubik", 1969),
            new Pair<>("Solaris", 1961),
            new Pair<>("Slaughterhouse-Five", 1969),
            new Pair<>("The War of the Worlds", 1898),
            new Pair<>("Childhood's End", 1953),
            new Pair<>("Snow Crash", 1992),
            new Pair<>("The Moon is a Harsh Mistress", 1966),
            new Pair<>("Hyperion", 1989),
            new Pair<>("The Left Hand of Darkness", 1969),
            new Pair<>("The Gods Themselves", 1972),
            new Pair<>("The Stars My Destination", 1956),
            new Pair<>("Rendezvous with Rama", 1973),
            new Pair<>("The Forever War", 1974)
          )
        );
    }

    static Stream<List> mapEntryProvider() {
        return Stream.of(
          List.of(
            new AbstractMap.SimpleEntry<>("Dune", 1965),
            new AbstractMap.SimpleEntry<>("Foundation", 1951),
            new AbstractMap.SimpleEntry<>("2001: A Space Odyssey", 1968),
            new AbstractMap.SimpleEntry<>("Do Androids Dream of Electric Sheep?", 1968),
            new AbstractMap.SimpleEntry<>("Ender's Game", 1985),
            new AbstractMap.SimpleEntry<>("The Hitchhiker's Guide to the Galaxy", 1979),
            new AbstractMap.SimpleEntry<>("1984", 1949),
            new AbstractMap.SimpleEntry<>("Brave New World", 1932),
            new AbstractMap.SimpleEntry<>("Fahrenheit 451", 1953),
            new AbstractMap.SimpleEntry<>("Neuromancer", 1984),
            new AbstractMap.SimpleEntry<>("Stranger in a Strange Land", 1961),
            new AbstractMap.SimpleEntry<>("The Handmaid's Tale", 1985),
            new AbstractMap.SimpleEntry<>("Ubik", 1969),
            new AbstractMap.SimpleEntry<>("Solaris", 1961),
            new AbstractMap.SimpleEntry<>("Slaughterhouse-Five", 1969),
            new AbstractMap.SimpleEntry<>("The War of the Worlds", 1898),
            new AbstractMap.SimpleEntry<>("Childhood's End", 1953),
            new AbstractMap.SimpleEntry<>("Snow Crash", 1992),
            new AbstractMap.SimpleEntry<>("The Moon is a Harsh Mistress", 1966),
            new AbstractMap.SimpleEntry<>("Hyperion", 1989),
            new AbstractMap.SimpleEntry<>("The Left Hand of Darkness", 1969),
            new AbstractMap.SimpleEntry<>("The Gods Themselves", 1972),
            new AbstractMap.SimpleEntry<>("The Stars My Destination", 1956),
            new AbstractMap.SimpleEntry<>("Rendezvous with Rama", 1973),
            new AbstractMap.SimpleEntry<>("The Forever War", 1974)
          )
        );
    }

    static Stream<List<Book>> bookProvider() {
        return Stream.of(
          List.of(
            new Book("Frank Herbert", "Dune", 1965),
            new Book("Isaac Asimov", "Foundation", 1951),
            new Book("Arthur C. Clarke", "2001: A Space Odyssey", 1968),
            new Book("Philip K. Dick", "Do Androids Dream of Electric Sheep?", 1968),
            new Book("Orson Scott Card", "Ender's Game", 1985),
            new Book("Douglas Adams", "The Hitchhiker's Guide to the Galaxy", 1979),
            new Book("George Orwell", "1984", 1949),
            new Book("Aldous Huxley", "Brave New World", 1932),
            new Book("Ray Bradbury", "Fahrenheit 451", 1953),
            new Book("William Gibson", "Neuromancer", 1984),
            new Book("Robert A. Heinlein", "Stranger in a Strange Land", 1961),
            new Book("Margaret Atwood", "The Handmaid's Tale", 1985),
            new Book("Philip K. Dick", "Ubik", 1969),
            new Book("Stanislaw Lem", "Solaris", 1961),
            new Book("Kurt Vonnegut", "Slaughterhouse-Five", 1969),
            new Book("H.G. Wells", "The War of the Worlds", 1898),
            new Book("Arthur C. Clarke", "Childhood's End", 1953),
            new Book("Neal Stephenson", "Snow Crash", 1992),
            new Book("Robert A. Heinlein", "The Moon is a Harsh Mistress", 1966),
            new Book("Dan Simmons", "Hyperion", 1989),
            new Book("Ursula K. Le Guin", "The Left Hand of Darkness", 1969),
            new Book("Isaac Asimov", "The Gods Themselves", 1972),
            new Book("Alfred Bester", "The Stars My Destination", 1956),
            new Book("Arthur C. Clarke", "Rendezvous with Rama", 1973),
            new Book("Joe Haldeman", "The Forever War", 1974)
          )
        );
    }
}
