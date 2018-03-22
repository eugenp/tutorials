package com.baeldung.springamqpjavarx.restaurant;

public class Kitchen {

    public ChefGreeting processOrder(Order order) {

        String greeting;

        if (order == Order.SODA) {
            cookFor(1);
            greeting = "Sugar-free!";

        } else if (order == Order.ICE_CREAM) {
            cookFor(5);
            greeting = "Enjoy the sorbet!";

        } else if (order == Order.PIZZA) {
            cookFor(20);
            greeting = "Pepperoni special!";

        } else {
            throw new RuntimeException("Oops, my apologies!");
        }

        return new ChefGreeting(greeting);
    }

    private static void cookFor(int cookingTime) {
        try {
            Thread.sleep(1_000 * cookingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ChefGreeting {

        private final String message;

        ChefGreeting(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
