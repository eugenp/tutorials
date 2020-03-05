package com.baeldung.ejbspringcomparison.spring.stateful;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ShoppingCartBean {

    private String name;
    private List<String> shoppingCart;

    public ShoppingCartBean() {
        shoppingCart = new ArrayList<String>();
    }

    public void addItem(String item) {
        shoppingCart.add(item);
    }

    public List<String> getItems() {
        return shoppingCart;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
