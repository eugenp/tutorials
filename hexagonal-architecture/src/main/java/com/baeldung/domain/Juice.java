package com.baeldung.domain;

import java.io.Serializable;

public class Juice implements Serializable {
    private String name;

    private String[] ingredients;

    public Juice(String juiceName, String[] juiceIngredients) {
        this.name = juiceName;
        this.ingredients = juiceIngredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
