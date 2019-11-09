package com.baeldung.reactive.webclient.simultaneous;

public class UserWithItem {
    private User user;
    private Item item;

    public UserWithItem(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public User user() {
        return user;
    }

    public Item item() {
        return item;
    }
}
