package com.baeldung.linkedlist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList; 
import java.util.LinkedList;

public class LinkedListJUnitTest {

    @Test
    public void whenInitializingLinkedList_ShouldReturnEmptyList() throws Exception {
        LinkedList<String> linkedList=new LinkedList<String>();
        
        Assertions.assertTrue(linkedList.isEmpty());
    }

    @Test
    public void whenInitializingListFromCollection_ShouldReturnCollectionsElements() throws Exception {
        ArrayList<Integer> arrayList=new ArrayList<Integer>(3);
        
        arrayList.add(Integer.valueOf(1));
        arrayList.add(Integer.valueOf(2));
        arrayList.add(Integer.valueOf(3));
 
        LinkedList<Integer> linkedList=new LinkedList<Integer>(arrayList);

        Object[] linkedListElements = linkedList.toArray();
        Object[] collectionElements = arrayList.toArray();

        Assertions.assertArrayEquals(linkedListElements,collectionElements);
    }

    @Test
    public void whenAddingElementsInLinkedListAtSpecificPosition_ShouldReturnElementsInProperSequence() throws Exception {
        LinkedList<String> linkedList=new LinkedList<String>();
        
        linkedList.addFirst("one");
        linkedList.addLast("three");
        linkedList.add("four");
        linkedList.add(1,"two");

        Object[] linkedListElements = linkedList.toArray();
        Object[] expectedListElements = {"one","two","three","four"};

        Assertions.assertArrayEquals(linkedListElements,expectedListElements);
    }
}
