package com.baeldung.springamqpjavarx.restaurant;

public class Kitchen {

    public ChefGreeting processOrder(Order order) {

        String greeting;

        switch (order) {
            case SODA:
                cookFor(1);
                greeting = "Sugar-free!";
                break;

            case ICE_CREAM:
                cookFor(5);
                greeting = "Enjoy the sorbet!";
                break;

            case PIZZA:
                cookFor(10);
                greeting = "Pepperoni special!";
                break;

            default:
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
