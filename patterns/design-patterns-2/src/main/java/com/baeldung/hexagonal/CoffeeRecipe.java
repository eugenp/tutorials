package com.baeldung.hexagonal;

public class CoffeeRecipe {

    private Integer id;
    private Integer sugerInGrams;
    private Integer coffeeInGrams;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSugerInGrams() {
        return sugerInGrams;
    }
    public void setSugerInGrams(Integer sugerInGrams) {
        this.sugerInGrams = sugerInGrams;
    }
    public Integer getCoffeeInGrams() {
        return coffeeInGrams;
    }
    public void setCoffeeInGrams(Integer coffeeInGrams) {
        this.coffeeInGrams = coffeeInGrams;
    }
    @Override
    public String toString() {
        return "CoffeeRecipe [id=" + id + ", sugerInGrams=" + sugerInGrams + ", coffeeInGrams=" + coffeeInGrams + "]";
    }
        
}
