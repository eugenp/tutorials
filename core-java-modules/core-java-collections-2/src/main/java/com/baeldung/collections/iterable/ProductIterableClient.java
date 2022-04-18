package com.baeldung.collections.iterable;

import java.util.Arrays;
import java.util.List;

class ProductIterableClient {

    public static void main(String[] args) {
        List<Product> products = Arrays.asList(new Product("Salad", 45), new Product("Tuna", 42), new Product("Eggplant", 65), new Product("Bread", 22), new Product("Banana", 29));

        ProductIterable iterable = new ProductIterable(products);

        for (Product product : iterable) {
            System.out.println(product.getName());
        }
    }
}
