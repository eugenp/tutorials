package com.baeldung.algorithms.linkedlist;

import java.util.HashSet;
import java.util.Set;

public class CycleDetectionByHashing {
 
    public static <T> boolean detectCycle(Node<T> head) {
        if(head == null) {
            return false;
        }
        
        Set<Node<T>> set = new HashSet<>();
        Node<T> node = head; 
        
        while(node != null) {
            if(set.contains(node)) {
                return true;
            }
            set.add(node);
            node = node.next;
        }
        
        return false;
    }
    
}
