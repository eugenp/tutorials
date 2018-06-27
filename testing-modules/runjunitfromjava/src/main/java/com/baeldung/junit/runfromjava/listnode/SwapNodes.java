package com.baeldung.junit.runfromjava.listnode;

import com.baeldung.junit.runfromjava.listnode.ListNode;

public class SwapNodes {
    public ListNode swapPairs(ListNode listHead) {

        ListNode result = new ListNode(0);
        result.setNext(listHead);

        ListNode current = result;

        while (current.getNext() != null && current.getNext()
            .getNext() != null) {

            ListNode first = current.getNext();
            ListNode second = current.getNext()
                .getNext();

            first.setNext(second.getNext());
            current.setNext(second);
            current.getNext()
                .setNext(first);

            current = current.getNext()
                .getNext();
        }

        return result.getNext();
    }
}
