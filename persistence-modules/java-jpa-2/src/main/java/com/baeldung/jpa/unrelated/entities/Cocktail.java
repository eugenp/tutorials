package com.baeldung.jpa.unrelated.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "menu")
public class Cocktail {
    @Id
    @Column(name = "cocktail_name")
    private String name;

    @Column
    private double price;

    @Column(name = "category")
    private String category;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "cocktail_name",
        referencedColumnName = "cocktail", 
        insertable = false, updatable = false, 
        foreignKey = @javax.persistence
          .ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Recipe recipe;

    @OneToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(
        name = "cocktail", 
        referencedColumnName = "cocktail_name", 
        insertable = false, 
        updatable = false, 
        foreignKey = @javax.persistence
          .ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<MultipleRecipe> recipeList;

    public Cocktail() {
    }

    public Cocktail(String name, double price, String baseIngredient) {
        this.name = name;
        this.price = price;
        this.category = baseIngredient;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<MultipleRecipe> getRecipeList() {
        return recipeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cocktail cocktail = (Cocktail) o;
        return Double.compare(cocktail.price, price) == 0 && 
            Objects.equals(name, cocktail.name) && 
            Objects.equals(category, cocktail.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, category);
    }
}
