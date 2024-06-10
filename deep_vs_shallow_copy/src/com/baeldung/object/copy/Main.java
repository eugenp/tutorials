package com.baeldung.object.copy;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {

        /* Initialize ShoppingCart */
        ShoppingCart shoppingCart = new ShoppingCart();
        List<String> items = new ArrayList<String>();

        items.add("Bread");

        shoppingCart.setItems(items);
        shoppingCart.setCartName("MyCart");

        print("Initialise Shopping Cart:...");
        print(String.format("> Initialized Shopping Cart : %s", shoppingCart));

        ObjectCopyUtils objectCopyUtils = new ObjectCopyUtils();

        /* Deep Copy ShoppingCart */
        System.out.println("Deep Copy Of Shopping Cart:...");

        ShoppingCart deepCopy = objectCopyUtils.deepCopy(shoppingCart);

        deepCopy.setCartName("MyCartWithDeepCopy");
        deepCopy.getItems()
            .add("Cheese");

        print(String.format("-> Initial Shopping Cart : %s", shoppingCart));
        print(String.format("-> Deep Copy of Shopping Cart : %s", deepCopy));

        /* Copy By Cloning ShoppingCart */
        print("Copy Of Shopping Cart By Cloning:...");

        ShoppingCart copyByClone = objectCopyUtils.cloneCopy(shoppingCart);

        copyByClone.setCartName("MyCartWithDeepCopyClone");
        copyByClone.getItems()
            .add("Apple");

        print(String.format("-> Initial Shopping Cart : %s", shoppingCart));
        print(String.format("-> Deep Copy By Cloning : %s", copyByClone));

        /* Deep Copy By Cloning ShoppingCart */
        print("Deep Copy Of Shopping Cart By Cloning:...");

        ShoppingCart deepCopyByClone = objectCopyUtils.deepCloneCopy(shoppingCart);

        deepCopyByClone.setCartName("MyCartWithDeepCopyClone");
        deepCopyByClone.getItems()
            .add("Orange");

        print(String.format("-> Initial Shopping Cart : %s", shoppingCart));
        print(String.format("-> Deep Copy By Cloning : %s", deepCopyByClone));
        
        /* Shallow Copy ShoppingCart */
        print("Shallow Copy Of Shopping Cart:...");

        ShoppingCart shallowCopy = objectCopyUtils.shallowCopy(shoppingCart);

        shallowCopy.setCartName("MyCartWithShallowCopy");
        shallowCopy.getItems()
            .add("Butter");

        print(String.format("-> Initial Shopping Cart : %s", shoppingCart));
        print(String.format("-> Shallow Copy : %s", shallowCopy));

    }

    private static void print(String printText) {
        System.out.println(printText);
    }
}
