package com.bealdung.java9.finalizers;

import java.lang.ref.Cleaner;

class CleaningDemo {

    public static void main(String[] args) {
        final Cleaner cleaner = Cleaner.create();
        try (Order order = new Order(cleaner)) {
            for (int i = 0; i < 10; i++) {
                order.register(new Product(i), i);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }
}
