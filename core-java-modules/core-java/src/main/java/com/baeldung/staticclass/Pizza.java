package com.baeldung.staticclass;

public class Pizza {

    private static String cookedCount;
    private boolean isThinCrust;

    // Accessible globally
    public static class PizzaSalesCounter {

        private static String orderedCount;
        public static String deliveredCount;

        PizzaSalesCounter() {
            System.out.println("Static field of enclosing class is "
              + Pizza.cookedCount);
            System.out.println("Non-static field of enclosing class is "
              + new Pizza().isThinCrust);
        }
    }

    Pizza() {
        System.out.println("Non private static field of static class is "
          + PizzaSalesCounter.deliveredCount);
        System.out.println("Private static field of static class is "
          + PizzaSalesCounter.orderedCount);
    }

    public static void main(String[] a) {
        // Create instance of the static class without an instance of enclosing class
        new Pizza.PizzaSalesCounter();
    }
}
