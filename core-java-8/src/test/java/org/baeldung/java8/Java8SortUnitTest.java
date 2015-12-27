package org.baeldung.java8;

import static org.hamcrest.Matchers.equalTo;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.baeldung.java8.entity.ComparableHuman;
import org.baeldung.java8.entity.Human;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

public class Java8SortUnitTest {

    Map<Human, String> humanMap;
    Map<ComparableHuman, String> comparableHumanMap;

    // tests -

    @Test
    public final void givenPreLambda_whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        Collections.sort(humans, new Comparator<Human>() {
            @Override
            public final int compare(final Human h1, final Human h2) {
                return h1.getName().compareTo(h2.getName());
            }
        });

        Assert.assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        humans.sort((final Human h1, final Human h2) -> h1.getName().compareTo(h2.getName()));

        Assert.assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void givenLambdaShortForm_whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        humans.sort((h1, h2) -> h1.getName().compareTo(h2.getName()));

        Assert.assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 12), new Human("Sarah", 10), new Human("Zack", 12));
        humans.sort((lhs, rhs) -> {
            if (lhs.getName().equals(rhs.getName())) {
                return lhs.getAge() - rhs.getAge();
            } else {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        Assert.assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void givenCompositionVerbose_whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 12), new Human("Sarah", 10), new Human("Zack", 12));
        final Comparator<Human> byName = (h1, h2) -> h1.getName().compareTo(h2.getName());
        final Comparator<Human> byAge = (h1, h2) -> Ints.compare(h1.getAge(), h2.getAge());

        humans.sort(byName.thenComparing(byAge));
        Assert.assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void givenComposition_whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 12), new Human("Sarah", 10), new Human("Zack", 12));

        humans.sort(Comparator.comparing(Human::getName).thenComparing(Human::getAge));
        Assert.assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void whenSortingEntitiesByAge_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        humans.sort((h1, h2) -> Ints.compare(h1.getAge(), h2.getAge()));
        Assert.assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));
        final Comparator<Human> comparator = (h1, h2) -> h1.getName().compareTo(h2.getName());

        humans.sort(comparator.reversed());
        Assert.assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void givenMethodDefinition_whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        humans.sort(Human::compareByNameThenAge);
        Assert.assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void givenInstanceMethod_whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        humans.sort(Comparator.comparing(Human::getName));
        Assert.assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void givenHashMap_whenSortingByVals_thenCorrectlySorted() {
        humanMap = buildHumanMap();
        final List<Human> humans = humanMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue)).map(Map.Entry::getKey).collect(Collectors.toList());
        final Map<Human, String> orderedMap = new LinkedHashMap<>();

        humans.stream().forEach((human) -> {
            orderedMap.put(human, humanMap.get(human));
        });
        Assert.assertThat(orderedMap.get(new Human("David", 30)), equalTo("Australian"));
    }

    @Test
    public final void givenHashMap_whenSortingByValsOnlyJava8_thenCorrectlySorted() {
        humanMap = buildHumanMap();
        final Map<Human, String> orderedMap = humanMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> {
            throw new AssertionError();
        }, LinkedHashMap::new));
        Assert.assertThat(orderedMap.get(new Human("David", 30)), equalTo("Australian"));
    }

    @Test
    public final void givenHashMap_whenSortingByVals_thenReverseSorted() {
        humanMap = buildHumanMap();
        final Map<Human, String> reverseOrderedMap = humanMap.entrySet().stream().sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> {
            throw new AssertionError();
        }, LinkedHashMap::new));
        Assert.assertEquals(reverseOrderedMap.keySet().stream().findFirst().get(), new Human("Ramos", 170));
    }

    @Test
    public final void givenHashMap_whenSortingByComparableKeys_thenCorrectlySortNaturally() {
        comparableHumanMap = buildComparableHumanMap();

        final List<ComparableHuman> comparableHumanList = comparableHumanMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).map(Map.Entry::getKey).collect(Collectors.toList());
        Assert.assertEquals(comparableHumanList.get(0), new ComparableHuman("Sarah", 10));
    }

    private Map<Human, String> buildHumanMap() {
        humanMap = new HashMap<>();
        humanMap.put(new Human("Sarah", 10), "Indian");
        humanMap.put(new Human("John", 120), "European");
        humanMap.put(new Human("David", 30), "Australian");
        humanMap.put(new Human("James", 40), "American");
        humanMap.put(new Human("James", 140), "Canadian");
        humanMap.put(new Human("Ramos", 170), "SouthAmerican");
        humanMap.put(new Human("Carlos", 20), "Brazillian");
        return humanMap;
    }

    private Map<ComparableHuman, String> buildComparableHumanMap() {
        comparableHumanMap = new HashMap<>();
        comparableHumanMap.put(new ComparableHuman("Sarah", 10), "Indian");
        comparableHumanMap.put(new ComparableHuman("John", 120), "European");
        comparableHumanMap.put(new ComparableHuman("David", 30), "Australian");
        comparableHumanMap.put(new ComparableHuman("James", 40), "American");
        comparableHumanMap.put(new ComparableHuman("James", 140), "Canadian");
        comparableHumanMap.put(new ComparableHuman("Ramos", 170), "SouthAmerican");
        comparableHumanMap.put(new ComparableHuman("Carlos", 20), "Brazillian");
        return comparableHumanMap;
    }

}
