package com.baeldung.priorityqueueiterator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class PriorityQueueMain {

    public static void main(String[] args) {

        // Creating a PriorityQueue with a custom comparator
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>(new PersonAgeComparator());

        // Adding persons to the PriorityQueue
        priorityQueue.add(new Person("Alice", 25));
        priorityQueue.add(new Person("Bob", 30));
        priorityQueue.add(new Person("Charlie", 22));

        // Getting an iterator
        Iterator<Person> iterator = priorityQueue.iterator();

        // Iterating over the elements using the iterator
        System.out.println("PriorityQueue elements using custom comparator:");
        while (iterator.hasNext()) {
            Person person = iterator.next();
            System.out.println("Name: " + person.name + ", Age: " + person.age);
        }
//        System.out.println("People in ascending order of age:");
//        while (!priorityQueue.isEmpty()) {
//            Person person = priorityQueue.poll(); // Retrieves and removes the first (lowest-age) person
//            System.out.println("Name: " + person.name + ", Age: " + person.age);
//        }
    }

    public static void iterateAndPrintPriorityQueue() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        queue.add(3);
        queue.add(1);
        queue.add(2);

        Iterator<Integer> iterator = queue.iterator();

        while (iterator.hasNext()) {
            Integer element = iterator.next();
            System.out.println(element);
        }
    }

}
