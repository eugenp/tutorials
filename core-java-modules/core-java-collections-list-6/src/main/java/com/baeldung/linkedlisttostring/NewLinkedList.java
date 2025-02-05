package com.baeldung.linkedlisttostring;
import java.util.LinkedList;

public class NewLinkedList {
    public static String customToString(LinkedList<Integer> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("Custom LinkedList: ");        
        if (list.isEmpty()) {
            sb.append("Empty List");
        } else {
            for (Integer item : list) {
                sb.append(item).append(" - ");
            }
            sb.delete(sb.length() - 3, sb.length());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkedList<Integer> idsList = new LinkedList<>();
        idsList.add(101);
        idsList.add(102);
        idsList.add(103);
        System.out.println(customToString(idsList)); 
        LinkedList<Integer> emptyList = new LinkedList<>();
        System.out.println(customToString(emptyList));  
    }
}
