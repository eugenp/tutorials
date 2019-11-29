package com.baeldung.circularlinkedlist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CircularLinkedListUnitTest {

    @Test
    public void givenACircularLinkedList_WhenAddingElements_ThenListContainsThoseElements() {
        CircularLinkedList cll = createCircularLinkedList();

        assertTrue(cll.containsNode(8));
        assertTrue(cll.containsNode(37));
    }

    @Test
    public void givenACircularLinkedList_WhenLookingForNonExistingElement_ThenReturnsFalse() {
        CircularLinkedList cll = createCircularLinkedList();

        assertFalse(cll.containsNode(11));
    }

    @Test
    public void givenACircularLinkedList_WhenDeletingElements_ThenListDoesNotContainThoseElements() {
        CircularLinkedList cll = createCircularLinkedList();

        assertTrue(cll.containsNode(13));
        cll.deleteNode(13);
        assertFalse(cll.containsNode(13));

        assertTrue(cll.containsNode(1));
        cll.deleteNode(1);
        assertFalse(cll.containsNode(1));

        assertTrue(cll.containsNode(46));
        cll.deleteNode(46);
        assertFalse(cll.containsNode(46));
    }

    private CircularLinkedList createCircularLinkedList() {
        CircularLinkedList cll = new CircularLinkedList();

        cll.addNode(13);
        cll.addNode(7);
        cll.addNode(24);
        cll.addNode(1);
        cll.addNode(8);
        cll.addNode(37);
        cll.addNode(46);

        return cll;
    }

}
