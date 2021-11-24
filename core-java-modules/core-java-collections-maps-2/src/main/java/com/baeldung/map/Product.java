package com.baeldung.map;

import java.util.*;

public class Product {

    private String name;
    private String description;
    private List<String> tags;

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
        this.tags = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public Product addTagsOfOtherProduct(Product product) {
        this.tags.addAll(product.getTags());
        return this;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    public static void forEach() {

        HashMap<String, Product> productsByName = new HashMap<>();
        productsByName.forEach( (key, product)
                        -> System.out.println("Key: " + key + " Product:" + product.getDescription())
                //do something with the key and value
        );

        //Prior to Java 8:
        for(Map.Entry<String, Product> entry : productsByName.entrySet()) {
            Product product =  entry.getValue();
            String key = entry.getKey();
            //do something with the key and value
        }
    }

    public static void getOrDefault() {

        HashMap<String, Product> productsByName = new HashMap<>();
        Product chocolate = new Product("chocolate", "something sweet");
        Product defaultProduct = productsByName.getOrDefault("horse carriage", chocolate);
        Product bike = productsByName.getOrDefault("E-Bike", chocolate);

        //Prior to Java 8:
        Product bike2 = productsByName.containsKey("E-Bike")
                ? productsByName.get("E-Bike")
                : chocolate;
        Product defaultProduct2 = productsByName.containsKey("horse carriage")
                ? productsByName.get("horse carriage")
                : chocolate;
    }

    public static void putIfAbsent() {

        HashMap<String, Product> productsByName = new HashMap<>();
        Product chocolate = new Product("chocolate", "something sweet");
        productsByName.putIfAbsent("E-Bike", chocolate);

        //Prior to Java 8:
        if(productsByName.containsKey("E-Bike")) {
            productsByName.put("E-Bike", chocolate);
        }
    }

    public static void merge() {

        HashMap<String, Product> productsByName = new HashMap<>();
        Product eBike2 = new Product("E-Bike", "A bike with a battery");
        eBike2.getTags().add("sport");
        productsByName.merge("E-Bike", eBike2, Product::addTagsOfOtherProduct);

        //Prior to Java 8:
        if(productsByName.containsKey("E-Bike")) {
            productsByName.get("E-Bike").addTagsOfOtherProduct(eBike2);
        } else {
            productsByName.put("E-Bike", eBike2);
        }
    }

    public static void compute() {

        HashMap<String, Product> productsByName = new HashMap<>();
        Product eBike2 = new Product("E-Bike", "A bike with a battery");

        productsByName.compute("E-Bike", (k,v) -> {
            if(v != null) {
                return v.addTagsOfOtherProduct(eBike2);
            } else {
                return eBike2;
            }
        });

        //Prior to Java 8:
        if(productsByName.containsKey("E-Bike")) {
            productsByName.get("E-Bike").addTagsOfOtherProduct(eBike2);
        } else {
            productsByName.put("E-Bike", eBike2);
        }
    }
}
