package com.baeldung.object.copy;

import java.util.ArrayList;
import java.util.List;

public class ObjectCopyUtils {

    public ShoppingCart shallowCopy(ShoppingCart shoppingCart) {

        ShoppingCart shallowCopyOfObject = shoppingCart;

        return shallowCopyOfObject;
    }

    public ShoppingCart deepCopy(ShoppingCart shoppingCart) {

        ShoppingCart deepCopyOfObject = new ShoppingCart();

        List<String> itemList = new ArrayList<String>();

        shoppingCart.setCartName(shoppingCart.getCartName());
        shoppingCart.getItems()
            .stream()
            .forEach(t -> itemList.add(t));

        deepCopyOfObject.setItems(itemList);

        return deepCopyOfObject;
    }

    public ShoppingCart cloneCopy(ShoppingCart shoppingCart) throws CloneNotSupportedException {

        ShoppingCart deepCopyOfObject = (ShoppingCart) shoppingCart.clone();
        
        return deepCopyOfObject;
    }
    
    @SuppressWarnings("unchecked")
    public ShoppingCart deepCloneCopy(ShoppingCart shoppingCart) throws CloneNotSupportedException {

        ShoppingCart deepCopyOfObject = (ShoppingCart) shoppingCart.clone();
        
        ArrayList<String> itemsList = (ArrayList<String>) shoppingCart.getItems();

        deepCopyOfObject.setItems((ArrayList<String>)itemsList.clone());
        
        return deepCopyOfObject;
    }
}
