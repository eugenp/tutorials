package com.baeldung.algorithms.permutation;

import java.util.Arrays;
import java.util.Collections;

public  class Permutation {

    public static <T> void printAllRecursive(T[] elements, char delimiter) {
        printAllRecursive(elements.length, elements, delimiter);
    }

    public static <T> void printAllRecursive(int n, T[] elements, char delimiter) {

        if(n == 1) {
            printArray(elements, delimiter);
        } else {
            for(int i = 0; i < n-1; i++) {
                printAllRecursive(n - 1, elements, delimiter);
                if(n % 2 == 0) {
                    swap(elements, i, n-1);
                } else {
                    swap(elements, 0, n-1);
                }
            }
            printAllRecursive(n - 1, elements, delimiter);
        }
    }

    public static <T> void printAllIterative(int n, T[] elements, char delimiter) {

        int[] indexes = new int[n];
        for (int i = 0; i < n; i++) {
            indexes[i] = 0;
        }

        printArray(elements, delimiter);

        int i = 0;
        while (i < n) {
            if (indexes[i] < i) {
                swap(elements, i % 2 == 0 ?  0: indexes[i], i);
                printArray(elements, delimiter);
                indexes[i]++;
                i = 0;
            }
            else {
                indexes[i] = 0;
                i++;
            }
        }
    }

    public static <T extends Comparable<T>> void printAllOrdered(T[] elements, char delimiter) {

        Arrays.sort(elements);
        boolean hasNext = true;

        while(hasNext) {
            printArray(elements, delimiter);
            int k = 0, l = 0;
            hasNext = false;
            for (int i = elements.length - 1; i > 0; i--) {
                if (elements[i].compareTo(elements[i - 1]) > 0) {
                    k = i - 1;
                    hasNext = true;
                    break;
                }
            }

            for (int i = elements.length - 1; i > k; i--) {
                if (elements[i].compareTo(elements[k]) > 0) {
                    l = i;
                    break;
                }
            }

            swap(elements, k, l);
            Collections.reverse(Arrays.asList(elements).subList(k + 1, elements.length));
        }
    }

    public static <T> void printRandom(T[] elements, char delimiter) {

        Collections.shuffle(Arrays.asList(elements));
        printArray(elements, delimiter);
    }

    private static <T> void swap(T[] elements, int a, int b) {

        T tmp = elements[a];
        elements[a] = elements[b];
        elements[b] = tmp;
    }

    private static <T> void printArray(T[] elements, char delimiter) {

        String delimiterSpace = delimiter + " ";
        for(int i = 0; i < elements.length; i++) {
            System.out.print(elements[i] + delimiterSpace);
        }
        System.out.print('\n');
    }

    public static void main(String[] argv) {

        Integer[] elements = {1,2,3,4};

        System.out.println("Rec:");
        printAllRecursive(elements, ';');

        System.out.println("Iter:");
        printAllIterative(elements.length, elements, ';');

        System.out.println("Orderes:");
        printAllOrdered(elements, ';');

        System.out.println("Random:");
        printRandom(elements, ';');

        System.out.println("Random:");
        printRandom(elements, ';');
    }
}
