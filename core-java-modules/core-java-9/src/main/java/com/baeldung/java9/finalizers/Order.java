package com.bealdung.java9.finalizers;

import java.lang.ref.Cleaner;

class Order implements AutoCloseable {

    private final Cleaner cleaner;
    private Cleaner.Cleanable cleanable;

    public Order(Cleaner cleaner) {
        this.cleaner = cleaner;
    }

    public void register(Product product, int id) {
        this.cleanable = cleaner.register(product, new CleaningAction(id));
    }

    public void close() {
        cleanable.clean();
        System.out.println("Cleanable closed");
    }

    static class CleaningAction implements Runnable {

        private final int id;

        public CleaningAction(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.printf("Object with id %s is garbage collected. %n", id);
        }
    }
}
