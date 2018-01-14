package com.baeldung.di.example.store;

public class Store {
    private Item item;

    public Store() {
    }

    public Store(Item item) {
        this.item = item;
    }

    public Item getSell() {
        return item;
    }

    public void setSell(Item item) {
        this.item = item;
    }

    public String getItemDescription() {

        String description;

        if (item != null)
            description = "This is a " + item.getType() + ", with the title " + item.getTitle();
        else
            description = "There is not Item yet";

        return description;
    }
}
