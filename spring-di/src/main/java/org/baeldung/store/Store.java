package org.baeldung.store;

import org.springframework.beans.factory.annotation.Autowired;

public class Store {
    
    @Autowired
    private Item item;
    
    public Store() {}
    
    public Store(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
