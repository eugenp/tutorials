package com.baeldung.jpa.unrelated.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "multiple_recipes")
public class MultipleRecipe {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "cocktail")
    private String cocktail;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "base_ingredient")
    private String baseIngredient;

    public MultipleRecipe() {
    }

    public MultipleRecipe(Long id, String cocktail, 
        String instructions, String baseIngredient) {
        this.id = id;
        this.cocktail = cocktail;
        this.instructions = instructions;
        this.baseIngredient = baseIngredient;
    }

    public Long getId() {
        return id;
    }

    public String getCocktail() {
        return cocktail;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getBaseIngredient() {
        return baseIngredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MultipleRecipe that = (MultipleRecipe) o;
        
        return Objects.equals(id, that.id) && 
          Objects.equals(cocktail, that.cocktail) && 
          Objects.equals(instructions, that.instructions) && 
          Objects.equals(baseIngredient, that.baseIngredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cocktail, 
          instructions, baseIngredient);
    }
}
