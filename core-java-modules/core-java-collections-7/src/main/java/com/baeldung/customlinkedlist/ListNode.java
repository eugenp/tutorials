package com.baeldung.customlinkedlist;

public class ListNode {

    int value;
    ListNode next;

    public ListNode(int value) {
        this.value = value;
    }

    public void insert(ListNode node0, ListNode newNode) {
        ListNode node1 = node0.next;
        newNode.next = node1;
        node0.next = newNode;
    }

    public void remove(ListNode node0) {
        if (node0.next == null)
            return;

        ListNode previous = node0.next;
        ListNode node1 = previous.next;
        node0.next = node1;
    }

    public ListNode get(ListNode head, int index) {
        for (int i = 0; i < index; i++) {
            if (head == null)
                return null;
            head = head.next;
        }

        return head;
    }

    public int find(ListNode head, int target) {
        int index = 0;
        while (head != null) {
            if (head.value == target)
                return index;
            head = head.next;
            index++;
        }

        return -1;
    }

}
