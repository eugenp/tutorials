package com.baeldung.listtostring;

public class DoublyLinkedList {
    DoublyLinkedListNode head;
    DoublyLinkedListNode tail;

    public void add(Integer data) {
        DoublyLinkedListNode newNode = new DoublyLinkedListNode(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public static String customToString(DoublyLinkedList list) {
        StringBuilder sb = new StringBuilder();
        sb.append("Custom Doubly LinkedList: ");
        if (list.head == null) {
            sb.append("Empty List");
        } else {
            DoublyLinkedListNode currentNode = list.head;
            while (currentNode != null) {
                sb.append(currentNode.data).append(" - ");
                currentNode = currentNode.next;
            }
            sb.delete(sb.length() - 3, sb.length());  
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList idsList = new DoublyLinkedList();
        idsList.add(101);
        idsList.add(102);
        idsList.add(103);

        System.out.println(customToString(idsList));

        DoublyLinkedList emptyList = new DoublyLinkedList();
        System.out.println(customToString(emptyList)); 
    }
}
