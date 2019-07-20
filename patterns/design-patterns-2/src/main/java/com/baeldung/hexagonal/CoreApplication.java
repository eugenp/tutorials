package com.baeldung.hexagonal;

public class CoreApplication {

    private DataPort dataSource;

    public CoreApplication(DataPort dataSource) {
        this.dataSource = dataSource;
    }

    public boolean makeCoffee(String coffeeType) {

        boolean coffeeMadeSuccessfully = false;
        int recepieId = -1;
        
        switch (coffeeType) {
            case "latte":
                recepieId = 1;
                break;
            case "espresso":
                recepieId = 2;
                break;
            case "cappuccino":
                recepieId = 3;
                break;
        }

        CoffeeRecepie recepie = dataSource.getCoffeeRecepieById(recepieId);

        if (recepie != null)
            coffeeMadeSuccessfully = true;

        return coffeeMadeSuccessfully;
    }

}