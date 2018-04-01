package com.example;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Based on LinkedList of Queue
 * @author lihongjie
 * methods：
 * boolean add(E e);
 * boolean offer(E e);
 * 区别：两者都是往队列尾部插入元素，不同的时候，当超出队列界限的时候，add（）方法是抛出异常让你处理，而offer（）方法是直接返回false
 * E remove(); Retrieves and removes the head of this queue.or returns {@code NoSuchElementException} if this queue is empty.
 * E poll(); Retrieves and removes the head of this queue.or returns {@code null} if this queue is empty.
 * E element(); Retrieves, but does not remove, the head of this queue. returns {@code NoSuchElementException} if this queue is empty.
 * E peek(); Retrieves, but does not remove, the head of this queue,or returns {@code null} if this queue is empty.
 *
 */
public class JdkQueue {

    public static void main(String[] args) {

        Queue<Integer> queue = new LinkedList<Integer>();
        System.out.println("queue size： " + queue.size());

        Integer arr[] = {1,2,3,4,5};

        for (int i : arr) {
            queue.offer(i);
        }

        while (!queue.isEmpty()) {
            System.out.println("queue： " + queue.peek());
            queue.poll();
        }

    }
}
