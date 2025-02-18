package com.baeldung.listtostring;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DoublyLinkedListUnitTest {

    @Test
    void givenList_toString_formattedOutput() {
        DoublyLinkedList list = new DoublyLinkedList();
        list.add(101);
        list.add(102);
        list.add(103);

        String expectedOutput = "Custom Doubly LinkedList: 101 - 102 - 103";
        String actualOutput = DoublyLinkedList.customToString(list);
        assertEquals(expectedOutput, actualOutput, "The list toString should match the expected format.");

        DoublyLinkedList emptyList = new DoublyLinkedList();
        String expectedEmptyOutput = "Custom Doubly LinkedList: Empty List";
        String actualEmptyOutput = DoublyLinkedList.customToString(emptyList);
        assertEquals(expectedEmptyOutput, actualEmptyOutput, "The empty list toString should show 'Empty List'.");
    }
}
