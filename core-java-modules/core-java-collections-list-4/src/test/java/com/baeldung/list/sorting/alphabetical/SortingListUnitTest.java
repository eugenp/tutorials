package com.baeldung.list.sorting.alphabetical;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.Collator;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class SortingListUnitTest {

    private static List<String> INPUT_NAMES = Arrays.asList("john", "mike", "usmon", "ken", "harry");
    private static List<String> EXPECTED_NATURAL_ORDER = Arrays.asList("harry", "john", "ken", "mike", "usmon");
    private static List<String> EXPECTED_REVERSE_ORDER = Arrays.asList("usmon", "mike", "ken", "john", "harry");

    @Test
    void givenListOfStrings_whenUsingCollections_thenListIsSorted() {

        Collections.sort(INPUT_NAMES);

        assertThat(INPUT_NAMES).isEqualTo(EXPECTED_NATURAL_ORDER);
    }

    @Test
    void givenListOfStrings_whenUsingCollections_thenListIsSortedInReverse() {
        Comparator<String> reverseComparator = (element1, element2) -> element2.compareTo(element1);

        Collections.sort(INPUT_NAMES, reverseComparator);

        assertThat(INPUT_NAMES).isEqualTo(EXPECTED_REVERSE_ORDER);
    }

    @Test
    void givenListOfStringsWithUpperAndLowerCaseMixed_whenCustomComparator_thenListIsSortedCorrectly() {
        List<String> movieNames = Arrays.asList("amazing SpiderMan", "Godzilla", "Sing", "Minions");
        List<String> naturalSortOrder = Arrays.asList("Godzilla", "Minions", "Sing", "amazing SpiderMan");
        List<String> comparatorSortOrder = Arrays.asList("amazing SpiderMan", "Godzilla", "Minions", "Sing");

        Collections.sort(movieNames);

        assertThat(movieNames).isEqualTo(naturalSortOrder);

        Collections.sort(movieNames, Comparator.comparing(s -> s.toLowerCase()));

        assertThat(movieNames).isEqualTo(comparatorSortOrder);
    }

    @Test
    void givenListOfStringsIncludingSomeWithSpecialCharacter_whenCustomComparator_thenListIsSortedWithSpecialCharacterLast() {
        List<String> listWithSpecialCharacters = Arrays.asList("@laska", "blah", "jo", "@sk", "foo");

        List<String> sortedNaturalOrder = Arrays.asList("@laska", "@sk", "blah", "foo", "jo");
        List<String> sortedSpecialCharacterLast = Arrays.asList("blah", "foo", "jo", "@laska", "@sk");

        Collections.sort(listWithSpecialCharacters);

        assertThat(listWithSpecialCharacters).isEqualTo(sortedNaturalOrder);

        Comparator<String> specialSignComparator = Comparator.<String, Boolean>comparing(s -> s.startsWith("@"));
        Comparator<String> specialCharacterComparator = specialSignComparator.thenComparing(Comparator.naturalOrder());

        listWithSpecialCharacters.sort(specialCharacterComparator);

        assertThat(listWithSpecialCharacters).isEqualTo(sortedSpecialCharacterLast);
    }

    @Test
    void givenListOfStrings_whenUsingStreamsAndSort_thenListIsSorted() {
        List<String> sortedList = INPUT_NAMES.stream()
            .sorted()
            .collect(Collectors.toList());

        assertThat(sortedList).isEqualTo(EXPECTED_NATURAL_ORDER);
    }

    @Test
    void givenListOfStrings_whenUsingStreamsWithComparator_thenListIsSortedInReverseOrder() {
        List<String> sortedList = INPUT_NAMES.stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());

        assertThat(sortedList).isEqualTo(EXPECTED_REVERSE_ORDER);
    }

    @Test
    void givenListOfStrings_whenUsingTreeSet_thenListIsSorted() {
        SortedSet<String> sortedSet = new TreeSet<>(INPUT_NAMES);
        List<String> sortedList = new ArrayList<>(sortedSet);

        assertThat(sortedList).isEqualTo(EXPECTED_NATURAL_ORDER);
    }

    @Test
    void givenListOfStrings_whenSortOnList_thenListIsSorted() {

        INPUT_NAMES.sort(Comparator.reverseOrder());

        assertThat(INPUT_NAMES).isEqualTo(EXPECTED_REVERSE_ORDER);
    }

    @Test
    void givenListOfStringsWithAccent_whenUseCollatorWithLocaleSet_thenListIsSortedAccordingToLocaleRules() {
        List<String> accentedStrings = Arrays.asList("único", "árbol", "cosas", "fútbol");
        List<String> sortedNaturalOrder = Arrays.asList("cosas", "fútbol", "árbol", "único");
        List<String> sortedLocaleSensitive = Arrays.asList("árbol", "cosas", "fútbol", "único");

        Collections.sort(accentedStrings);
        assertThat(accentedStrings).isEqualTo(sortedNaturalOrder);

        Collator esCollator = Collator.getInstance(new Locale("es"));

        accentedStrings.sort((s1, s2) -> {
            return esCollator.compare(s1, s2);
        });

        assertThat(accentedStrings).isEqualTo(sortedLocaleSensitive);
    }

    @Test
    void givenListOfStringsWithAccentedCharacters_whenComparatorWithNormalizer_thenListIsNormalizeAndSorted() {
        List<String> accentedStrings = Arrays.asList("único", "árbol", "cosas", "fútbol");

        List<String> naturalOrderSorted = Arrays.asList("cosas", "fútbol", "árbol", "único");
        List<String> stripAccentSorted = Arrays.asList("árbol", "cosas", "fútbol", "único");

        Collections.sort(accentedStrings);
        assertThat(accentedStrings).isEqualTo(naturalOrderSorted);

        accentedStrings.sort((o1, o2) -> {
            o1 = Normalizer.normalize(o1, Normalizer.Form.NFD);
            o2 = Normalizer.normalize(o2, Normalizer.Form.NFD);
            return o1.compareTo(o2);
        });

        assertThat(accentedStrings).isEqualTo(stripAccentSorted);
    }

    @Test
    void givenListOfStringsWithAccentedCharacters_whenComparatorWithStripAccents_canStripAccentsAndSort() {
        List<String> accentedStrings = Arrays.asList("único", "árbol", "cosas", "fútbol");

        List<String> naturalOrderSorted = Arrays.asList("cosas", "fútbol", "árbol", "único");
        List<String> stripAccentSorted = Arrays.asList("árbol", "cosas", "fútbol", "único");

        Collections.sort(accentedStrings);

        assertThat(accentedStrings).isEqualTo(naturalOrderSorted);

        accentedStrings.sort(Comparator.comparing(input -> StringUtils.stripAccents(input)));

        assertThat(accentedStrings).isEqualTo(stripAccentSorted);
    }

    @Test
    void givenListofStrings_whenProvidedTheRuleBasedCollator_thenListIsSortedUsingRuleBasedCollator() throws ParseException {

        List<String> movieNames = Arrays.asList("Godzilla", "AmazingSpiderMan", "Smurfs", "Minions");

        List<String> naturalOrderExpected = Arrays.asList("AmazingSpiderMan", "Godzilla", "Minions", "Smurfs");

        List<String> rulesBasedExpected = Arrays.asList("Smurfs", "Minions", "AmazingSpiderMan", "Godzilla");

        Collections.sort(movieNames);

        assertThat(movieNames).isEqualTo(naturalOrderExpected);

        String rule = "< s, S < m, M < a, A < g, G";

        RuleBasedCollator collator = new RuleBasedCollator(rule);
        movieNames.sort(collator);

        assertThat(movieNames).isEqualTo(rulesBasedExpected);
    }
}
