package com.baeldung.linkedhashset;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;


public class LinkedHashSetUnitTest{


    @Test
    void whenCreatingLinkedHashSet_shouldBeEmpty(){
        Set<String> linkedHashSet = new LinkedHashSet<>();
        assertTrue(linkedHashSet.isEmpty());
    }

    @Test
    void whenCreatingLinkedHashSetWithInitialCapacity_shouldBeEmpty(){
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(20);
        assertTrue(linkedHashSet.isEmpty());
    }

    @Test
    void whenCreatingLinkedHashSetWithExistingCollection_shouldContainAllElementOfCollection(){
        Collection<String> data = Arrays.asList("first", "second", "third", "fourth", "fifth");
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(data);

        assertFalse(linkedHashSet.isEmpty());
        assertEquals(data.size(), linkedHashSet.size());
        assertTrue(linkedHashSet.containsAll(data) && data.containsAll(linkedHashSet));
    }

    @Test
    void whenCreatingLinkedHashSetWithInitialCapacityAndLoadFactor_shouldBeEmpty(){
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(20, 3);
        assertTrue(linkedHashSet.isEmpty());
    }

    @Test
    void whenAddingElement_shouldAddElement(){
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        assertTrue(linkedHashSet.add(0));
        assertFalse(linkedHashSet.add(0));
        assertTrue(linkedHashSet.contains(0));

    }

    @Test
    void whenAddingCollection_shouldAddAllContentOfCollection(){
        Collection<Integer> data = Arrays.asList(1,2,3);
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();

        assertTrue(linkedHashSet.addAll(data));
        assertTrue(data.containsAll(linkedHashSet) && linkedHashSet.containsAll(data));
    }

    @Test
    void whenAddingCollectionWithDuplicateElements_shouldMaintainUniqueValuesInSet(){
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(2);
        Collection<Integer> data = Arrays.asList(1, 1, 2, 3);

        assertTrue(linkedHashSet.addAll(data));
        assertEquals(3, linkedHashSet.size());
        assertTrue(data.containsAll(linkedHashSet) && linkedHashSet.containsAll(data));
    }

    @Test
    void whenIteratingWithIterator_assertThatElementIsPresent(){
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(0);
        linkedHashSet.add(1);
        linkedHashSet.add(2);

        Iterator<Integer> iterator = linkedHashSet.iterator();
        for (int i = 0; i < linkedHashSet.size(); i++) {
            int nextData = iterator.next();
            assertEquals(i, nextData);
        }
    }

    @Test
    void whenIteratingWithSpliterator_assertThatElementIsPresent(){
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(0);
        linkedHashSet.add(1);
        linkedHashSet.add(2);

        Spliterator<Integer> spliterator = linkedHashSet.spliterator();
        AtomicInteger counter = new AtomicInteger();
        spliterator.forEachRemaining(data -> {
        assertEquals(counter.get(), (int)data);
        counter.getAndIncrement();
        });
    }

    @Test
    void whenRemovingAnElement_shouldRemoveElement(){
        Collection<String> data = Arrays.asList("first", "second", "third", "fourth", "fifth");
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(data);

        assertTrue(linkedHashSet.remove("second"));
        assertFalse(linkedHashSet.contains("second"));
    }

    @Test
    void whenRemovingAnElementGreaterThanTwo_shouldRemoveElement(){
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(0);
        linkedHashSet.add(1);
        linkedHashSet.add(2);
        linkedHashSet.add(3);
        linkedHashSet.add(4);

        linkedHashSet.removeIf(data -> data > 2);
        assertFalse(linkedHashSet.contains(3));
        assertFalse(linkedHashSet.contains(4));
    }

    @Test
    void whenRemovingAnElementWithIterator_shouldRemoveElement(){
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(0);
        linkedHashSet.add(1);
        linkedHashSet.add(2);

        Iterator<Integer> iterator = linkedHashSet.iterator();
        int elementToRemove = 1;
        assertTrue(linkedHashSet.contains(elementToRemove));
        while(iterator.hasNext()){
            if(elementToRemove == iterator.next()){
                iterator.remove();
            }
        }
        assertFalse(linkedHashSet.contains(elementToRemove));
    }

}
