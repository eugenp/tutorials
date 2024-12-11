package com.baeldung.collection;

import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
 

public class WhenUsingHashSet {

    private static HashSet<Employee> hashSet;
    private static TreeSet<Employee> treeSet;
    
    @Test
    public void whenAddingElement_shouldAddElement() {
        Set<String> hashset = new HashSet<>();
        assertTrue(hashset.add("String Added"));
    }

    @Test
    public void whenCheckingForElement_shouldSearchForElement() {
        Set<String> hashsetContains = new HashSet<>();
        hashsetContains.add("String Added");
        assertTrue(hashsetContains.contains("String Added"));
    }

    @Test
    public void whenCheckingTheSizeOfHashSet_shouldReturnThesize() {
        Set<String> hashSetSize = new HashSet<>();
        hashSetSize.add("String Added");
        assertEquals(1, hashSetSize.size());
    }

    @Test
    public void whenCheckingForEmptyHashSet_shouldCheckForEmpty() {
        Set<String> emptyHashSet = new HashSet<>();
        assertTrue(emptyHashSet.isEmpty());
    }

    @Test
    public void whenRemovingElement_shouldRemoveElement() {
        Set<String> removeFromHashSet = new HashSet<>();
        removeFromHashSet.add("String Added");
        assertTrue(removeFromHashSet.remove("String Added"));
    }

    @Test
    public void whenClearingHashSet_shouldClearHashSet() {
        Set<String> clearHashSet = new HashSet<>();
        clearHashSet.add("String Added");
        clearHashSet.clear();
        assertTrue(clearHashSet.isEmpty());
    }

    @Test
    public void whenIteratingHashSet_shouldIterateHashSet() {
        Set<String> hashset = new HashSet<>();
        hashset.add("First");
        hashset.add("Second");
        hashset.add("Third");
        Iterator<String> itr = hashset.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenModifyingHashSetWhileIterating_shouldThrowException() {
        Set<String> hashset = new HashSet<>();
        hashset.add("First");
        hashset.add("Second");
        hashset.add("Third");
        Iterator<String> itr = hashset.iterator();
        while (itr.hasNext()) {
            itr.next();
            hashset.remove("Second");
        }
    }

    @Test
    public void whenRemovingElementUsingIterator_shouldRemoveElement() {
        Set<String> hashset = new HashSet<>();
        hashset.add("First");
        hashset.add("Second");
        hashset.add("Third");
        Iterator<String> itr = hashset.iterator();
        while (itr.hasNext()) {
            String element = itr.next();
            if (element.equals("Second"))
                itr.remove();
        }
        assertEquals(2, hashset.size());
    }

    @Test
    public void givenNonComparableObject_whenConvertingToTreeSet_thenExceptionThrown() {

        HashSet<Employee> hashSet = new HashSet<Employee>();
        
        hashSet.add(new Employee(3, "John"));
        hashSet.add(new Employee(5, "Mike"));
        hashSet.add(new Employee(2, "Bob"));
        hashSet.add(new Employee(1, "Tom"));
        hashSet.add(new Employee(4, "Johnny"));  
        
        assertThrows(ClassCastException.class,() -> { 
          TreeSet<Employee> treeSet = new TreeSet<Employee>(hashSet); 
        });
    }

    @Test
    public void givenComparableObject_whenConvertingToTreeSet_thenNoExceptionThrown() {

        HashSet<Employee> hashSet = new HashSet<Employee>();
        
        hashSet.add(new Employee(3, "John"));
        hashSet.add(new Employee(5, "Mike"));
        hashSet.add(new Employee(2, "Bob"));
        hashSet.add(new Employee(1, "Tom"));
        hashSet.add(new Employee(4, "Johnny"));  
        
        assertDoesNotThrow(()->{
            TreeSet<Employee> treeSet=new TreeSet<Employee>(hashSet);
        });
    }
}
