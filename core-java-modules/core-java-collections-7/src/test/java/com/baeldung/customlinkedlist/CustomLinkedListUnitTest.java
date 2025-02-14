package com.baeldung.customlinkedlist;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomLinkedListUnitTest {

    @Test
    public void givenLinkedList_whenBuildingReferenceBetweenNodes_thenCorrect() {
        Node<Integer> node0 = new Node<>(1);
        Node<Integer> node1 = new Node<>(2);
        Node<Integer> node2 = new Node<>(3);
        node0.next = node1;
        node1.next = node2;

        assertEquals(1, node0.value);
        assertEquals(2, node0.next.value);
        assertEquals(3, node0.next.next.value);
    }

    @Test
    public void givenAnEmptyCustomLinkedList_whenInsertingNodes_thenReturnValueOfSpecifiedIndex() {
        CustomLinkedList<String> fruit = new CustomLinkedList<>();
        fruit.insertTail("Avocado");
        fruit.insertTail("Banana");
        fruit.insertTail("Apple");
        assertEquals("Apple", fruit.get(2));
    }

    @Test
    public void givenLinkedList_whenGettingElementByIndex_thenCorrect() {
        CustomLinkedList<String> fruit = new CustomLinkedList<>();
        fruit.insertTail("Avocado");
        fruit.insertTail("Banana");
        fruit.insertTail("Apple");
        assertEquals("Avocado", fruit.get(0));
    }

    @Test
    public void givenEmptyCustomLinkedList_whenInsertingElementAtTheHead_thenReturnCorrectSize() {
        CustomLinkedList<String> fruit = new CustomLinkedList<>();
        fruit.insertTop("Avocado");
        fruit.insertTop("Banana");
        fruit.insertTop("Apple");
        assertEquals(3, fruit.size());
    }

    @Test
    public void givenCustomLinkedList_whenRetrievingTheSizeOfTheList_thenCorrect() {
        CustomLinkedList<String> fruit = new CustomLinkedList<>();
        fruit.insertTop("Avocado");
        fruit.insertTop("Banana");
        fruit.insertTop("Apple");
        assertEquals(3, fruit.size());
    }

    @Test
    public void givenCustomLinkedList_whenRemovingANodeBaseOnIndex_thenReturnNewSize() {
        CustomLinkedList<String> fruit = new CustomLinkedList<>();
        fruit.insertTop("Avocado");
        fruit.insertTop("Banana");
        fruit.insertTop("Apple");
        fruit.removeAtIndex(2);
        assertEquals(2, fruit.size());
    }
}