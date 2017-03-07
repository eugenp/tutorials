package com.baeldung.list.flattennestedlist;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FlattenNestedListTest {

    private FlattenNestedList flol;
    
    @Before
    public void setup() {
        flol = new FlattenNestedList();
    }
    
    @Test
    public void givenListOfListOfString_flattenNestedList() {

        // create the list to flatten
        List<String> ls1 = Arrays.asList("one:one", "one:two", "one:three");
        List<String> ls2 = Arrays.asList("two:one", "two:two", "two:three");
        List<String> ls3 = Arrays.asList("three:one", "three:two", "three:three");

        List<List<String>> lol = new ArrayList<>();
        lol.addAll(Arrays.asList(ls1, ls2, ls3));
        
        // show nested list
        System.out.println("Nested list: ");
        lol.forEach((nl) -> System.out.println(nl));

        // flatten it
        List<String> ls = flol.flattenListOfLists(lol);

        assertNotNull(ls);
        assertTrue(ls.size() == 9);
        
        // show flattened list
        System.out.println("\nFlattened list:");
        ls.forEach((l) -> System.out.println(l));

    }

}
