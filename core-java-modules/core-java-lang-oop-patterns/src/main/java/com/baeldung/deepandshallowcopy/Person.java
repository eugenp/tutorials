package com.baeldung.deepandshallowcopy;

public class Person implements Cloneable {
    String name;
    int age;
    FavoriteFood favoriteFood;

    public Person(String name, int age, FavoriteFood favoriteFood) {
        this.name = name;
        this.age = age;
        this.favoriteFood = favoriteFood;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Person deepCopy() {
        FavoriteFood newFavoriteFood = new FavoriteFood(this.favoriteFood.food);
        return new Person(this.name, this.age, newFavoriteFood);
    }
}
