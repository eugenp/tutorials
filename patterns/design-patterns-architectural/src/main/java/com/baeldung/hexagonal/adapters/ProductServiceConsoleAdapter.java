package com.baeldung.hexagonal.adapters;

import java.util.List;
import java.util.Scanner;

import com.baeldung.hexagonal.domain.Product;
import com.baeldung.hexagonal.service.ProductService;

public class ProductServiceConsoleAdapter {

    private ProductService productService;

    public ProductServiceConsoleAdapter(ProductService productService) {
        this.productService = productService;
    }

    public void createProduct(Scanner scanner) {

        System.out.println("What is the name of the product ? ");
        String name = CommandConsoleUtils.readString(scanner);

        System.out.println("What is the category of the product ? ");
        String category = CommandConsoleUtils.readString(scanner);

        System.out.println("Specify the stock available:");
        int stock = Integer.parseInt(CommandConsoleUtils.readString(scanner));

        System.out.println("Specify the price:");
        double price = Double.parseDouble(CommandConsoleUtils.readString(scanner));

        productService.createProduct(name, category, stock, price);

    }

    public void applyDiscount(Scanner scanner) {

        System.out.println("What is the amount of discount ? ");
        String discount = CommandConsoleUtils.readString(scanner);

        System.out.println("Apply to all products (Y/N) ? ");
        String applyAll = CommandConsoleUtils.readString(scanner);

        if (applyAll.equals("N")) {
            System.out.println("Which is the product to update ?");
            String productId = CommandConsoleUtils.readString(scanner);
            productService.applyDiscount(Integer.parseInt(discount), Integer.parseInt(productId));
            System.out.println("Product " + productId + " successfully updated with the following discount: " + discount);
        } else {
            List<Product> allProduct = productService.getAllProducts();
            for (Product p : allProduct) {
                productService.applyDiscount(Integer.parseInt(discount), (p.getProductId()));
            }

            System.out.println("All products successfully updated with the following discount: " + discount);
        }

    }

}
