package com.baeldung.algorithms.linkedlist;

public class CycleRemovalWithoutCountingLoopNodes {

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
                removeCycle(slow, head);
                return true;
            }
        }
        
        return false;
    }

    private static <T> void removeCycle(Node<T> meetingPointParam, Node<T> head) {
        Node<T> loopNode = meetingPointParam;
        Node<T> it = head;
        
        while(loopNode.next != it.next) {
            it = it.next;
            loopNode = loopNode.next;
        }
        
        loopNode.next = null;
    }

}
