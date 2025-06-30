package baeldung.linkedlistarray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.baeldung.linkedlistarray.LinkedListArray;

public class LinkedListArrayUnitTest {

    @Test
    void givenNumbers_whenGroupedUsingRawArray_thenGroupsAreCorrect() {
        int[] input = { 3, 11, 21, 9, 19, 22 };
        LinkedList<Integer>[] arrayOfLists = LinkedListArray.groupUsingRawArray(input);

        assertEquals(2, arrayOfLists[0].size());
        assertTrue(arrayOfLists[0].contains(3));
        assertTrue(arrayOfLists[0].contains(9));
    }

    @Test
    void givenNumbers_whenGroupedUsingLinkedList_thenGroupsAreCorrect() {
        int[] input = { 3, 11, 21, 9, 19, 22 };
        List<LinkedList<Integer>> arrayOfLists = LinkedListArray.groupUsingList(input);

        assertEquals(2, arrayOfLists.get(1)
            .size());
        assertTrue(arrayOfLists.get(1)
            .contains(11));
        assertTrue(arrayOfLists.get(1)
            .contains(19));
    }

    @Test
    void givenNumbers_whenGroupedUsingStreams_thenGroupsAreCorrect() {
        int[] input = { 3, 11, 21, 9, 19, 22 };
        List<LinkedList<Integer>> arrayOfLists = LinkedListArray.groupUsingStreams(input);

        assertEquals(2, arrayOfLists.get(0)
            .size());
        assertTrue(arrayOfLists.get(0)
            .contains(3));
        assertTrue(arrayOfLists.get(0)
            .contains(9));
    }

    @Test
    void givenNumbers_whenGroupedUsingSetAll_thenGroupsAreCorrect() {
        int[] input = { 3, 11, 21, 9, 19, 22 };
        LinkedList<Integer>[] arrayOfLists = LinkedListArray.groupUsingSetAll(input);

        assertEquals(2, arrayOfLists[2].size());
        assertTrue(arrayOfLists[2].contains(21));
        assertTrue(arrayOfLists[2].contains(22));
    }
}
