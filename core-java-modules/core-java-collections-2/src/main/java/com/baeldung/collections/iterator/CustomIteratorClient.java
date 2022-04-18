package com.baeldung.collections.iterator;

class CustomIteratorClient {

    public static void main(String[] args) {
        CustomIterator<Integer> iterator = Numbers.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
