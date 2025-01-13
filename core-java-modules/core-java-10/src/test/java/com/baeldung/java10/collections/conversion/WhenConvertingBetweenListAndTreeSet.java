package com.baeldung.java10.collections.conversion;

import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
 
import java.util.ArrayList;
import java.util.TreeSet;
 

public class WhenConvertingBetweenListAndTreeSet {
    
    @Test
    public void givenComparableObject_whenConvertingToTreeSet_thenNoExceptionThrown() {

        ArrayList&lt;Employee&gt; arrayList = new ArrayList&lt;Employee&gt;();

        arrayList.add(new Employee(3, "John"));
        arrayList.add(new Employee(5, "Mike"));
        arrayList.add(new Employee(2, "Bob"));
        arrayList.add(new Employee(1, "Tom"));
        arrayList.add(new Employee(4, "Johnny"));
        
        assertDoesNotThrow(()-&gt;{
        TreeSet&lt;Employee&gt; treeSet=new TreeSet&lt;Employee&gt;(arrayList);
        });
    }

    @Test
    public void givenTreeSet_whenConvertingToList_thenNoExceptionThrown() {

        TreeSet&lt;Employee&gt; treeSet = new TreeSet&lt;Employee&gt;();

        treeSet.add(new Employee(3, "John"));
        treeSet.add(new Employee(5, "Mike"));
        treeSet.add(new Employee(2, "Bob"));
        treeSet.add(new Employee(1, "Tom"));
        treeSet.add(new Employee(4, "Johnny"));
        
        assertDoesNotThrow(()-&gt;{
        ArrayList&lt;Employee&gt; arrayList=new ArrayList&lt;Employee&gt;(treeSet);
        });
    }

