package com.baeldung.hexagonal;

public class CoreApplication {

    private DataPort dataSource;

    public boolean makeCoffee(String coffeeType) {

        boolean coffeeMadeSuccessfully = false;
        int recipeId = -1;
        
        switch (coffeeType) {
            case "latte":
                recipeId = 1;
                break;
            case "espresso":
                recipeId = 2;
                break;
            case "cappuccino":
                recipeId = 3;
                break;
        }

        CoffeeRecipe recipe = dataSource.getCoffeeRecipeById(recipeId);

        if (recipe != null)
            coffeeMadeSuccessfully = true;

        return coffeeMadeSuccessfully;
    }

    public DataPort getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataPort dataSource) {
        this.dataSource = dataSource;
    }
    
}