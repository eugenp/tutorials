package com.baeldung.collections.iterator;

import java.util.Iterator;

class CustomIteratorClient {

    public static void main(String[] args) {
        Iterator<Integer> iterator = Numbers.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
