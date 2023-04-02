package com.baeldung.deepandshallowcopy;

public class Person {
    String name;
    int age;
    FavoriteFood favoriteFood;

    public Person(String name, int age, FavoriteFood favoriteFood) {
        this.name = name;
        this.age = age;
        this.favoriteFood = favoriteFood;
    }

    // Deep copy method
    public Person deepCopy() {
        FavoriteFood copiedFavoriteFood = new FavoriteFood(this.favoriteFood.food);
        return new Person(this.name, this.age, copiedFavoriteFood);
    }
}