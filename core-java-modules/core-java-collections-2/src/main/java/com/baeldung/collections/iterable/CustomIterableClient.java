package com.baeldung.collections.iterable;

class CustomIterableClient {

    public static void main(String[] args) {

        CustomCollection<Product> products = new CustomCollection<>();
        products.add(new Product("Tuna", 42));
        products.add(new Product("Eggplant", 65));
        products.add(new Product("Salad", 45));
        products.add(new Product("Banana", 29));

        for (Product product : products) {
            System.out.println(product.getName());
        }
    }
}
