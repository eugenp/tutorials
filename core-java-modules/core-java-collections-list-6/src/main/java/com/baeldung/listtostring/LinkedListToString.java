package com.baeldung.listtostring;
import java.util.LinkedList;

public class LinkedListToString {
    public static void main(String[] args) {
        LinkedList<Integer> idsList = new LinkedList<>();
        idsList.add(101);
        idsList.add(102);
        idsList.add(103);
        System.out.println("LinkedList: " + idsList.toString());
        LinkedList<Integer> emptyList = new LinkedList<>();
        System.out.println("LinkedList: " + emptyList.toString());
    }
}
