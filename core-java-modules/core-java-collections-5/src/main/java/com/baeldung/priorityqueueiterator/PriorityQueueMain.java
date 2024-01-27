package com.baeldung.priorityqueueiterator;

import java.util.Iterator;
import java.util.PriorityQueue;

public class PriorityQueueMain {

    public static void main(String[] args) {
        showPriorityQueueWithIterator();
        showCustomOrderingWithIterator();
        showCustomOrderingWithPoll();
        useIteratorToCapitalizeNames();
        showFailFastIteratorBehavior();
    }

    public static void showPriorityQueueWithIterator() {
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

    public static void showFailFastIteratorBehavior() {
        PriorityQueue<Integer> numbers = new PriorityQueue<>();
        numbers.add(3);
        numbers.add(1);
        numbers.add(2);

        Iterator<Integer> iterator = numbers.iterator();
        numbers.add(4);

        try {
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException occurred during iteration.");
        }
    }

    public static void showCustomOrderingWithIterator() {
        // Creating a PriorityQueue with a custom comparator
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>(new PersonAgeComparator());

        // Adding persons to the PriorityQueue
        priorityQueue.add(new Person("Alice", 25));
        priorityQueue.add(new Person("Bob", 30));
        priorityQueue.add(new Person("Charlie", 22));

        Iterator<Person> iterator = priorityQueue.iterator();

        while (iterator.hasNext()) {
            Person person = iterator.next();
            System.out.println("Name: " + person.name + ", Age: " + person.age);
        }
    }

    public static void showCustomOrderingWithPoll() {
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>(new PersonAgeComparator());

        priorityQueue.add(new Person("Alice", 25));
        priorityQueue.add(new Person("Bob", 30));
        priorityQueue.add(new Person("Charlie", 22));

        Iterator<Person> iterator = priorityQueue.iterator();

        while (!priorityQueue.isEmpty()) {
            Person person = priorityQueue.poll();
            System.out.println("Name: " + person.name + ", Age: " + person.age);
        }
    }

    public static void useIteratorToCapitalizeNames() {
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>(new PersonAgeComparator());

        priorityQueue.add(new Person("Alice", 25));
        priorityQueue.add(new Person("Bob", 30));
        priorityQueue.add(new Person("Charlie", 22));

        Iterator<Person> iterator = priorityQueue.iterator();

        while (iterator.hasNext()) {
            Person person = iterator.next();
            person.setName(person.getName()
                .toUpperCase());
            System.out.println("Name: " + person.name + ", Age: " + person.age);
        }
    }

}
