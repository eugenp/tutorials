package com.baeldung.mockito.fluentapi;

import java.util.ArrayList;
import java.util.List;

public class Pizza {

    public enum PizzaSize {
        LARGE, MEDIUM, SMALL;
    }

    private String name;
    private PizzaSize size;
    private List<String> toppings;
    private boolean stuffedCrust;
    private boolean collect;
    private Integer discount;

    private Pizza(PizzaBuilder builder) {
        this.name = builder.name;
        this.size = builder.size;
        this.toppings = builder.toppings;
        this.stuffedCrust = builder.stuffedCrust;
        this.collect = builder.collect;
        this.discount = builder.discount;
    }

    public String getName() {
        return name;
    }

    public PizzaSize getSize() {
        return size;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public boolean isStuffedCrust() {
        return stuffedCrust;
    }

    public boolean isCollecting() {
        return collect;
    }

    public Integer getDiscount() {
        return discount;
    }

    public static class PizzaBuilder {
        private String name;
        private PizzaSize size;

        private List<String> toppings;
        private boolean stuffedCrust;
        private boolean collect;
        private Integer discount = null;

        public PizzaBuilder() {
        }

        public PizzaBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PizzaBuilder size(PizzaSize size) {
            this.size = size;
            return this;
        }

        public PizzaBuilder withExtraTopping(String extraTopping) {
            if (this.toppings == null) {
                toppings = new ArrayList<>();
            }
            this.toppings.add(extraTopping);
            return this;
        }

        public PizzaBuilder withStuffedCrust(boolean stuffedCrust) {
            this.stuffedCrust = stuffedCrust;
            return this;
        }

        public PizzaBuilder willCollect(boolean collect) {
            this.collect = collect;
            return this;
        }

        public PizzaBuilder applyDiscount(Integer discount) {
            this.discount = discount;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }

}
