package com.baeldung.jpa.unrelated.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="recipes")
public class Recipe {
    @Id
    @Column(name = "cocktail")
    private String cocktail;

    @Column
    private String instructions;

    public Recipe() {
    }

    public Recipe(String cocktail, String instructions) {
        this.cocktail = cocktail;
        this.instructions = instructions;
    }

    public String getCocktail() {
        return cocktail;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(cocktail, recipe.cocktail) 
          && Objects.equals(instructions, recipe.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cocktail, instructions);
    }
}
