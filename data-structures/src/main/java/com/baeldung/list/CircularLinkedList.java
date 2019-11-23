package com.baeldung.list;

public class CircularLinkedList {

    Node head = null;
    Node tail = null;

    public void addNode(int value) {

        Node newNode = new Node(value);

        // If no elements are present, make the newly addNodeed node as head
        if (head == null) {
            head = newNode;
        }
        // If there are elements already present, the existing tail should point to new node
        else {
            tail.nextNode = newNode;
        }

        // Irrespective of whether or not elements are added, assign the
        // tail to newNode and the nextNode for tail as head
        tail = newNode;
        tail.nextNode = head;
    }

    public boolean containsNode(int searchValue) {

        // Start traversing from the head
        Node currentNode = head;

        // If list is empty no need of traversal and can return false
        if (head == null) {
            return false;
        } else {
            do {
                // Compares the search value with each node value present in the list
                if (currentNode.value == searchValue) {
                    return true;
                }
                currentNode = currentNode.nextNode;
            } while (currentNode != head);
            return false;
        }
    }

    public void deleteNode(int valueToDelete) {

        // Start traversing from the head
        Node currentNode = head;

        // If list is non empty
        if (head != null) {
            // If the node to delete is the head node itself,
            // update the head as the next node of current head
            // and the nextNode of tail as new head
            if (currentNode.value == valueToDelete) {
                head = head.nextNode;
                tail.nextNode = head;
                currentNode = null;
            } else {
                do {
                    // Fetch the next node of current node
                    Node nextNode = currentNode.nextNode;
                    // If the value to delete matches the next node's value,
                    // update the next node of current node as the next node of present next node
                    if (nextNode.value == valueToDelete) {
                        currentNode.nextNode = nextNode.nextNode;
                        nextNode = null;
                        break;
                    }
                    currentNode = currentNode.nextNode;
                } while (currentNode != head);
            }
        }
    }

    public void traverseList() {

        // Start traversing from the head
        Node currentNode = head;

        if (head != null) {
            do {
                System.out.print(currentNode.value + " ");
                currentNode = currentNode.nextNode;
            } while (currentNode != head);
        }
    }

}

class Node {

    int value;
    Node nextNode;

    public Node(int value) {
        this.value = value;
    }

}