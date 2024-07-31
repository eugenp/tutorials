package com.baeldung.collections.iterable;

class CustomIterableClient {

    public static void main(String[] args) {

        ShoppingCart<Product> shoppingCart = new ShoppingCart<>();
        shoppingCart.add(new Product("Tuna", 42));
        shoppingCart.add(new Product("Eggplant", 65));
        shoppingCart.add(new Product("Salad", 45));
        shoppingCart.add(new Product("Banana", 29));

        for (Product product : shoppingCart) {
            System.out.println(product.getName());
        }
    }
}
