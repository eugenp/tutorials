package com.baeldung.hexagonal;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CoffeeRecipeData {

    @XmlElement(name = "coffeeRecipe")
    List<CoffeeRecipe> coffeeRecipes;

    public List<CoffeeRecipe> getCoffeeRecipes() {
        return coffeeRecipes;
    }

    public void setCoffeeRecipes(List<CoffeeRecipe> coffeeRecipes) {
        this.coffeeRecipes = coffeeRecipes;
    }

    @Override
    public String toString() {
        return "CoffeeRecipeData [coffeeRecipes=" + coffeeRecipes + "]";
    }

}