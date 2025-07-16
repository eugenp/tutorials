package baeldung.linkedlistarray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.baeldung.linkedlistarray.LinkedListArray;

public class LinkedListArrayUnitTest {

    int[] input = { 3, 7, 12, 15, 20, 25 };

    @Test
    void givenNumbers_whenGroupedUsingRawArray_thenGroupsAreCorrect() {
        LinkedList<Integer>[] arrayOfLists = LinkedListArray.createUsingRawArray();
        LinkedListArray.allocateNumbers(input, arrayOfLists);

        assertEquals(2, arrayOfLists[0].size());
        assertTrue(arrayOfLists[0].contains(3));
        assertTrue(arrayOfLists[0].contains(7));
    }

    @Test
    void givenNumbers_whenGroupedUsingLinkedList_thenGroupsAreCorrect() {
        ArrayList<LinkedList<Integer>> arrayOfLists = LinkedListArray.createUsingArrayList();
        LinkedListArray.allocateNumbers(input, arrayOfLists);

        assertEquals(2, arrayOfLists.get(1)
            .size());
        assertTrue(arrayOfLists.get(1)
            .contains(12));
        assertTrue(arrayOfLists.get(1)
            .contains(15));
    }

    @Test
    void givenNumbers_whenGroupedUsingStreams_thenGroupsAreCorrect() {
        ArrayList<LinkedList<Integer>> arrayOfLists = LinkedListArray.createUsingStreams();
        LinkedListArray.allocateNumbers(input, arrayOfLists);

        assertEquals(2, arrayOfLists.get(0)
            .size());
        assertTrue(arrayOfLists.get(0)
            .contains(3));
        assertTrue(arrayOfLists.get(0)
            .contains(7));
    }

    @Test
    void givenNumbers_whenGroupedUsingSetAll_thenGroupsAreCorrect() {
        LinkedList<Integer>[] arrayOfLists = LinkedListArray.createUsingSetAll();
        LinkedListArray.allocateNumbers(input, arrayOfLists);

        assertEquals(2, arrayOfLists[2].size());
        assertTrue(arrayOfLists[2].contains(20));
        assertTrue(arrayOfLists[2].contains(25));
    }
}
