package com.baeldung.commons.collections.orderedmap;

import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.OrderedMapIterator;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderedMapUnitTest {

    private String[] names = { "Emily", "Mathew", "Rose", "John", "Anna" };
    private Integer[] ages = { 37, 28, 40, 36, 21 };

    private int RUNNERS_COUNT = names.length;

    private OrderedMap<String, Integer> runnersLinkedMap;
    private OrderedMap<String, Integer> runnersListOrderedMap;

    @Before
    public void createRunners() {
        // First implementation: ListOrderedMap
        this.runnersListOrderedMap = new ListOrderedMap<>();
        this.loadOrderedMapOfRunners(this.runnersListOrderedMap);

        // Second implementation: LinkedMap
        this.runnersLinkedMap = new LinkedMap<>();
        this.loadOrderedMapOfRunners(this.runnersLinkedMap);
    }

    private void loadOrderedMapOfRunners(OrderedMap<String, Integer> runners) {
        for (int i = 0; i < RUNNERS_COUNT; i++) {
            runners.put(this.names[i], this.ages[i]);
        }
    }

    @Test
    public void givenALinkedMap_whenIteratedWithMapIterator_thenPreservesOrder() {
        // Tests that the order in map iterator is the same
        // as defined in the constant arrays of names and ages:

        OrderedMapIterator<String, Integer> runnersIterator = this.runnersLinkedMap.mapIterator();
        int i = 0;
        while (runnersIterator.hasNext()) {
            runnersIterator.next();
            assertEquals(runnersIterator.getKey(), this.names[i]);
            assertEquals(runnersIterator.getValue(), this.ages[i]);
            i++;
        }
    }

    @Test
    public void givenAListOrderedMap_whenIteratedWithMapIterator_thenPreservesOrder() {
        // Tests that the order in map iterator is the same
        // as defined in the constant arrays of names and ages:

        OrderedMapIterator<String, Integer> runnersIterator = this.runnersListOrderedMap.mapIterator();
        int i = 0;
        while (runnersIterator.hasNext()) {
            runnersIterator.next();
            assertEquals(runnersIterator.getKey(), this.names[i]);
            assertEquals(runnersIterator.getValue(), this.ages[i]);
            i++;
        }
    }

    @Test
    public void givenALinkedMap_whenIteratedForwards_thenPreservesOrder() {
        // Tests that the order in the forward iteration is the same
        // as defined in the constant arrays of names and ages

        String name = this.runnersLinkedMap.firstKey();
        int i = 0;
        while (name != null) {
            assertEquals(name, this.names[i]);
            name = this.runnersLinkedMap.nextKey(name);
            i++;
        }
    }

    @Test
    public void givenAListOrderedMap_whenIteratedForwards_thenPreservesOrder() {
        // Tests that the order in the forward iteration is the same
        // as defined in the constant arrays of names and ages

        String name = this.runnersListOrderedMap.firstKey();
        int i = 0;
        while (name != null) {
            assertEquals(name, this.names[i]);
            name = this.runnersListOrderedMap.nextKey(name);
            i++;
        }
    }

    @Test
    public void givenALinkedMap_whenIteratedBackwards_thenPreservesOrder() {
        // Tests that the order in the backwards iteration is the same
        // as defined in the constant arrays of names and ages

        String name = this.runnersLinkedMap.lastKey();
        int i = RUNNERS_COUNT - 1;
        while (name != null) {
            assertEquals(name, this.names[i]);
            name = this.runnersLinkedMap.previousKey(name);
            i--;
        }
    }

    @Test
    public void givenAListOrderedMap_whenIteratedBackwards_thenPreservesOrder() {
        // Tests that the order in the backwards iteration is the same
        // as defined in the constant arrays of names and ages

        String name = this.runnersListOrderedMap.lastKey();
        int i = RUNNERS_COUNT - 1;
        while (name != null) {
            assertEquals(name, this.names[i]);
            name = this.runnersListOrderedMap.previousKey(name);
            i--;
        }
    }

    @Test
    public void givenALinkedMap_whenObjectIsSearched_thenMatchesConstantArray() {
        assertEquals(ages[4], this.runnersLinkedMap.get("Anna"));
    }

    @Test
    public void givenALinkedMap_whenConvertedToList_thenMatchesKeySet() {
        // Casting the OrderedMap to a LinkedMap we can use asList() method

        LinkedMap<String, Integer> lmap = (LinkedMap<String, Integer>) this.runnersLinkedMap;
        List<String> listKeys = new ArrayList<>();
        listKeys.addAll(this.runnersLinkedMap.keySet());
        List<String> linkedMap = lmap.asList();
        assertEquals(listKeys, linkedMap);
    }

    @Test
    public void givenALinkedMap_whenSearchByIndexIsUsed_thenMatchesConstantArray() {
        LinkedMap<String, Integer> lmap = (LinkedMap<String, Integer>) this.runnersLinkedMap;

        for (int i = 0; i < RUNNERS_COUNT; i++) {
            // accessed by index:
            String name = lmap.get(i);
            assertEquals(name, this.names[i]);

            // index of key concides with position in array
            assertEquals(lmap.indexOf(this.names[i]), i);
        }
    }

    @Test
    public void givenALinkedMap_whenElementRemoved_thenSizeDecrease() {
        LinkedMap<String, Integer> lmap = (LinkedMap<String, Integer>) this.runnersLinkedMap;
        Integer johnAge = lmap.remove("John");// by object
        assertEquals(johnAge, new Integer(36));
        assertEquals(lmap.size(), RUNNERS_COUNT - 1);

        Integer emilyAge = lmap.remove(0);// by index
        assertEquals(emilyAge, new Integer(37));
        assertEquals(lmap.size(), RUNNERS_COUNT - 2);
    }

    @Test
    public void givenAListOrderedMap_whenObjectIsSearched_thenMatchesConstantArray() {
        assertEquals(ages[4], this.runnersListOrderedMap.get("Anna"));
    }

    @Test
    public void givenAListOrderedMap_whenConvertedToList_thenMatchesKeySet() {
        ListOrderedMap<String, Integer> lomap = (ListOrderedMap<String, Integer>) this.runnersListOrderedMap;
        List<String> listKeys = new ArrayList<>();
        listKeys.addAll(this.runnersListOrderedMap.keySet());
        List<String> lomapList = lomap.asList();
        assertEquals(listKeys, lomapList);
    }

    @Test
    public void givenAListOrderedMap_whenSearchByIndexIsUsed_thenMatchesConstantArray() {
        ListOrderedMap<String, Integer> lomap = (ListOrderedMap<String, Integer>) this.runnersListOrderedMap;

        for (int i = 0; i < RUNNERS_COUNT; i++) {
            // accessed by index:
            String name = lomap.get(i);
            assertEquals(name, this.names[i]);

            // index of key concides with position in array
            assertEquals(lomap.indexOf(this.names[i]), i);
        }
    }

    @Test
    public void givenAListOrderedMap_whenElementRemoved_thenSizeDecrease() {
        ListOrderedMap<String, Integer> lomap = (ListOrderedMap<String, Integer>) this.runnersListOrderedMap;

        Integer johnAge = lomap.remove("John");// by object

        assertEquals(johnAge, new Integer(36));
        assertEquals(lomap.size(), RUNNERS_COUNT - 1);

        Integer emilyAge = lomap.remove(0);// by index
        assertEquals(emilyAge, new Integer(37));
        assertEquals(lomap.size(), RUNNERS_COUNT - 2);
    }
}
