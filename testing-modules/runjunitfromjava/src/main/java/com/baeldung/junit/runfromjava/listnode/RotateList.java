package com.baeldung.junit.runfromjava.listnode;

public class RotateList {
    public ListNode rotateRight(ListNode list, int n) {

        if (list == null || list.getNext() == null) {
            return list;
        }

        ListNode tmpList = new ListNode(0);
        tmpList.setNext(list);
        ListNode fast = tmpList;
        ListNode slow = tmpList;

        int listLength;
        for (listLength = 0; fast.getNext() != null; listLength++) {
            fast = fast.getNext();
        }

        for (int j = listLength - n % listLength; j > 0; j--) {
            slow = slow.getNext();
        }

        fast.setNext(tmpList.getNext());
        tmpList.setNext(slow.getNext());
        slow.setNext(null);

        return tmpList.getNext();
    }
}
