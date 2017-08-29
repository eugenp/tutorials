package com.baeldung.algorithms.linkedlist;

public class CycleRemovalByCountingLoopNodes {
    
    public static <T> boolean detectAndRemoveCycle(Node<T> head) {
        if(head == null) {
            return false;
        }
        
        Node<T> slow = head;
        Node<T> fast = head;
        
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if(slow == fast) {
                int cycleLength = calculateCycleLength(slow);
                removeCycle(head, cycleLength);
                return true;
            }
        }
        
        return false;
    }

    private static <T> void removeCycle(Node<T> head, int cycleLength) {
        Node<T> cycleLengthAdvancedIterator = head;
        Node<T> it = head;
        
        for(int i=0; i<cycleLength; i++) {
            cycleLengthAdvancedIterator = cycleLengthAdvancedIterator.next;
        }
        
        while(it.next != cycleLengthAdvancedIterator.next) {
            it = it.next;
            cycleLengthAdvancedIterator = cycleLengthAdvancedIterator.next;
        }
        
        cycleLengthAdvancedIterator.next = null;
    }

    private static <T> int calculateCycleLength(Node<T> loopNodeParam) {
        Node<T> loopNode = loopNodeParam;
        int length = 1;
        
        while(loopNode.next != loopNodeParam) {
            length++;
            loopNode = loopNode.next;
        }
        
        return length;
    }

}
