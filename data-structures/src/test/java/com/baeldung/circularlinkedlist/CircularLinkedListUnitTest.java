package com.baeldung.circularlinkedlist;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    public void givenACircularLinkedList_WhenDeletingInOrderHeadMiddleTail_ThenListDoesNotContainThoseElements() {
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

    @Test
    public void givenACircularLinkedList_WhenDeletingInOrderTailMiddleHead_ThenListDoesNotContainThoseElements() {
        CircularLinkedList cll = createCircularLinkedList();

        assertTrue(cll.containsNode(46));
        cll.deleteNode(46);
        assertFalse(cll.containsNode(46));

        assertTrue(cll.containsNode(1));
        cll.deleteNode(1);
        assertFalse(cll.containsNode(1));

        assertTrue(cll.containsNode(13));
        cll.deleteNode(13);
        assertFalse(cll.containsNode(13));
    }

    @Test
    public void givenACircularLinkedListWithOneNode_WhenDeletingElement_ThenListDoesNotContainTheElement() {
        CircularLinkedList cll = new CircularLinkedList();
        cll.addNode(1);
        cll.deleteNode(1);
        assertFalse(cll.containsNode(1));
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
