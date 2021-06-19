package com.baeldung;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        MinMaxHeap<Integer> minMaxHeap = new MinMaxHeap(8);
        minMaxHeap.insert(34);
        minMaxHeap.insert(12);
        minMaxHeap.insert(28);
        minMaxHeap.insert(9);
        minMaxHeap.insert(30);
        minMaxHeap.insert(19);
        minMaxHeap.insert(1);
        minMaxHeap.insert(40);
        System.out.println(minMaxHeap.getMinMaxHeap());
        minMaxHeap.removeMin();
        System.out.println(minMaxHeap.getMinMaxHeap());
        /*List<Integer> list = new ArrayList<>();
        list.add(34);
        list.add(12);
        list.add(28);
        list.add(9);
        list.add(30);
        list.add(19);
        list.add(1);
        list.add(40);
        MinMaxHeap<Integer> minMaxHeap = new MinMaxHeap<>(list);
        minMaxHeap.create();
        System.out.println(list);*/
    }
}
