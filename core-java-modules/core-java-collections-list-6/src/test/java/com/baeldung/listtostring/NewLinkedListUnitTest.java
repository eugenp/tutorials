package com.baeldung.listtostring;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

public class NewLinkedListTest {

    // Test for custom toString() when the list is not empty
    @Test
    void givenList_customToString_formattedOutput() {
        LinkedList<Integer> idsList = new LinkedList<>();
        idsList.add(101);
        idsList.add(102);
        idsList.add(103);

        String expected = "Custom LinkedList: 101 - 102 - 103";
        assertEquals(expected, NewLinkedList.customToString(idsList), 
            "Custom toString() should format the list elements with ' - ' separator.");
    }

    // Test for custom toString() when the list is empty
    @Test
    void emptyList_customToString_formattedOutput() {
        LinkedList<Integer> emptyList = new LinkedList<>();
        
        String expected = "Custom LinkedList: Empty List";
        assertEquals(expected, NewLinkedList.customToString(emptyList), 
            "Empty list should return 'Empty List' in custom toString().");
    }
}
