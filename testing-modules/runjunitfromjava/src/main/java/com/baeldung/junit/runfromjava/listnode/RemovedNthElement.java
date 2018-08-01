package com.baeldung.junit.runfromjava.listnode;

public class RemovedNthElement {
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode start = new ListNode(0);
        start.setNext(head);

        ListNode fast = start;
        ListNode slow = start;

        for (int i = 0; i < n + 1 && fast != null; i++) {
            fast = fast.getNext();
        }

        while (fast != null) {
            fast = fast.getNext();
            slow = slow.getNext();
        }

        slow.setNext(slow.getNext()
            .getNext());

        return start.getNext();
    }
}
