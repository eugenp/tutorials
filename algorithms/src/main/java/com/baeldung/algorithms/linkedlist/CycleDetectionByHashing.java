package com.baeldung.algorithms.linkedlist;

import java.util.HashSet;
import java.util.Set;

public class CycleDetectionByHashing {
    
    public static void main(String[] args) {
        Node<Integer> last = Node.createNewNode(10, null);
        
        for(int i=9; i>=1; --i) {
            Node<Integer> current = Node.createNewNode(i, last);
            last = current;
        }
        
        Node<Integer> root = last;
        Node.traverseList(root);
        System.out.println(detectCycle(root));
    }
    
    public static <T> boolean detectCycle(Node<T> root) {
        if(root == null) {
            return false;
        }
        
        Set<Node<T>> set = new HashSet<>();
        Node<T> node = root; 
        
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
