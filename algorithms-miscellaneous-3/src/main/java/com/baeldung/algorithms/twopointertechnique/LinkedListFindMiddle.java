package com.baeldung.algorithms.twopointertechnique;

public class LinkedListFindMiddle {

    public <T> T findMiddle(MyNode<T> head) {
        MyNode<T> slowPointer = head;
        MyNode<T> fastPointer = head;

        while (fastPointer.next != null && fastPointer.next.next != null) {
            fastPointer = fastPointer.next.next;
            slowPointer = slowPointer.next;
        }
        return slowPointer.data;
    }

}
