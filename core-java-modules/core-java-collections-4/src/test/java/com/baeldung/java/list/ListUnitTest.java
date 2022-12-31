package com.baeldung.java.list;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

public class ListUnitTest {

    @Test
    public void givenAFruitList_whenBasicOperations_thenSuccess(){
        List<String> fruits = new ArrayList<>();
        
        fruits.add("Apple");
        fruits.add("Orange");
        fruits.add("Banana");
        
        assertEquals("Unexpected number of fruits in the list", 3, fruits.size());
        
        fruits.remove("Banana");
        
        assertEquals("Unexpected number of fruits in the list", 2, fruits.size());
        
        for (Iterator<String> it = fruits.listIterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }
    }
}
