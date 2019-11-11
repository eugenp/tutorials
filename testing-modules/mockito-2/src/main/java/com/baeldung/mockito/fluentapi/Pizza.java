package com.baeldung.mockito.fluentapi;

import java.util.List;

public class Pizza {

    public enum PizzaSize {
        LARGE, MEDIUM, SMALL;
    }

    private String name;
    private PizzaSize size;
    private List<String> toppings;

    private String email;
    private boolean stuffedCrust;

    private Pizza(PizzaBuilder builder) {
        this.name = builder.name;
        this.size = builder.size;
        this.toppings = builder.toppings;
        this.stuffedCrust = builder.stuffedCrust;
    }

    public static class PizzaBuilder {
        private String name;
        private PizzaSize size;
        private String email;
        private boolean stuffedCrust;
        private List<String> toppings;

        public PizzaBuilder(String name) {
            this.name = name;
        }

        public PizzaBuilder size(PizzaSize size) {
            this.size = size;
            return this;
        }

        public PizzaBuilder withExtaTopping(String extraTopping) {
            this.toppings.add(extraTopping);
            return this;
        }

        public PizzaBuilder withStuffedCrust(boolean stuffedCrust) {
            this.stuffedCrust = stuffedCrust;
            return this;
        }

        public BankAccountBuilder willCollect(boolean collect) {
            this.newsletter = newsletter;
            return this;
        }

        public BankAccountBuilder applyDiscount(boolean collect) {
            this.newsletter = newsletter;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }

    public String getName() {
        return name;
    }

    public PizzaSize getSize() {
        return size;
    }

    public String getEmail() {
        return email;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

}
