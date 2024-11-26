package com.baeldung.java.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class ListUnitTest {
    
    @Test
    public void givenAFruitList_whenAddNewFruit_thenFruitIsAdded(){
        List<String> fruits = new ArrayList<>();
        assertEquals("Unexpected number of fruits in the list, should have been 0", 0, fruits.size());
        
        fruits.add("Apple");
        assertEquals("Unexpected number of fruits in the list, should have been 1", 1, fruits.size());
    }
    
    @Test
    public void givenAFruitList_whenContainsFruit_thenFruitIsInTheList(){
        List<String> fruits = new ArrayList<>();
        
        fruits.add("Apple");
        assertTrue("Apple should be in the fruit list", fruits.contains("Apple"));
        assertFalse("Banana should not be in the fruit list", fruits.contains("Banana"));
    }
    
    @Test
    public void givenAnEmptyFruitList_whenEmptyCheck_thenListIsEmpty(){
        List<String> fruits = new ArrayList<>();
        assertTrue("Fruit list should be empty", fruits.isEmpty());
        
        fruits.add("Apple");
        assertFalse("Fruit list should not be empty", fruits.isEmpty());
    }
    
    @Test
    public void givenAFruitList_whenIterateOverIt_thenFruitsAreInOrder(){
        List<String> fruits = new ArrayList<>();
        
        fruits.add("Apple"); // fruit at index 0
        fruits.add("Orange");// fruit at index 1
        fruits.add("Banana");// fruit at index 2
        int index = 0;
        for (Iterator<String> it = fruits.listIterator(); it.hasNext(); ) {
            String fruit = it.next();
            assertEquals("Fruits should be in order", fruits.get(index++), fruit);
        }
    }
    
    @Test
    public void givenAFruitList_whenRemoveFruit_thenFruitIsRemoved(){
        List<String> fruits = new ArrayList<>();
        
        fruits.add("Apple"); 
        fruits.add("Orange");
        assertEquals("Unexpected number of fruits in the list, should have been 2", 2, fruits.size());
        
        fruits.remove("Apple");
        assertEquals("Unexpected number of fruits in the list, should have been 1", 1, fruits.size());
    }
    
    @Test
    public void givenAFruitList_whenSetFruit_thenFruitIsUpdated(){
        List<String> fruits = new ArrayList<>();
        
        fruits.add("Apple"); 
        fruits.add("Orange");
        
        fruits.set(0, "Banana");
        assertEquals("Fruit at index 0 should be Banana", "Banana", fruits.get(0));
    }
    
    @Test
    public void givenAFruitList_whenSort_thenFruitsAreSorted(){
        List<String> fruits = new ArrayList<>();
        
        fruits.add("Apple"); 
        fruits.add("Orange");
        fruits.add("Banana");
        
        fruits.sort(Comparator.naturalOrder());
        
        assertEquals("Fruit at index 0 should be Apple", "Apple", fruits.get(0));
        assertEquals("Fruit at index 1 should be Banana", "Banana", fruits.get(1));
        assertEquals("Fruit at index 2 should be Orange", "Orange", fruits.get(2));
    }
    
    @Test
    public void givenAFruitList_whenSublist_thenWeGetASublist(){
        List<String> fruits = new ArrayList<>();
        
        fruits.add("Apple"); 
        fruits.add("Orange");
        fruits.add("Banana");
        
        List<String> fruitsSublist = fruits.subList(0, 2);
        assertEquals("Unexpected number of fruits in the sublist, should have been 2", 2, fruitsSublist.size());
        
        assertEquals("Fruit at index 0 should be Apple", "Apple", fruitsSublist.get(0));
        assertEquals("Fruit at index 1 should be Orange", "Orange", fruitsSublist.get(1));
    }
    
    @Test
    public void givenAFruitList_whenToArray_thenWeGetAnArray(){
        List<String> fruits = new ArrayList<>();
        
        fruits.add("Apple"); 
        fruits.add("Orange");
        fruits.add("Banana");
        
        String[] fruitsArray = fruits.toArray(new String[0]);
        assertEquals("Unexpected number of fruits in the array, should have been 3", 3, fruitsArray.length);
        
        assertEquals("Fruit at index 0 should be Apple", "Apple", fruitsArray[0]);
        assertEquals("Fruit at index 1 should be Orange", "Orange", fruitsArray[1]);
        assertEquals("Fruit at index 2 should be Banana", "Banana", fruitsArray[2]);
    }
    
}
