package org.baeldung.boot.jsoncomponent;

import javafx.scene.paint.Color;

public class User {
    private final Color favoriteColor;

    public User(Color favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public Color getFavoriteColor() {
        return favoriteColor;
    }
}
