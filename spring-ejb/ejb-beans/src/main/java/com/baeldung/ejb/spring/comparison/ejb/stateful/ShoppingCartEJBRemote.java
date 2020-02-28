package com.baeldung.ejb.spring.comparison.ejb.stateful;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ShoppingCartEJBRemote {

    void addItem(String item);

    List<String> getItems();
    
    void setName(String name);
    
    String getName();
}
