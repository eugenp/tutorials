package org.example;

public class Food {
    String fav;

    public Food(String fav) {
        this.fav = fav;
    }

    public Food(Food other) {
        this.fav = other.fav;
    }
}