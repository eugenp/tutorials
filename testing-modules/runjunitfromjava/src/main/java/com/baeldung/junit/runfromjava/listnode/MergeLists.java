package com.baeldung.junit.runfromjava.listnode;

import com.baeldung.junit.runfromjava.listnode.ListNode;

public class MergeLists {

    public ListNode merge(ListNode list1, ListNode list2) {

        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        if (list1.getValue() <= list2.getValue()) {
            list1.setNext(merge(list1.getNext(), list2));
            return list1;
        } else {
            list2.setNext(merge(list2.getNext(), list1));
            return list2;
        }
    }
}