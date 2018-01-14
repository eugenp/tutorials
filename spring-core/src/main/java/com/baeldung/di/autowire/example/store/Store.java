package com.baeldung.di.autowire.example.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("store")
public class Store {

    private Item item;

    public Store() {
    }

    public Item getSell() {
        return item;
    }

    @Autowired
    @Qualifier("book")
    public void setSellBook(Item item) {
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
