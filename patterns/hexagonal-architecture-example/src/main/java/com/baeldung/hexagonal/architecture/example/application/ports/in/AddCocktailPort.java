package com.baeldung.hexagonal.architecture.example.application.ports.in;

public interface AddCocktailPort {

    void addCocktail(AddCocktailCommand command);

    class AddCocktailCommand {
        private String name;
        private double price;

        public AddCocktailCommand(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }
}
