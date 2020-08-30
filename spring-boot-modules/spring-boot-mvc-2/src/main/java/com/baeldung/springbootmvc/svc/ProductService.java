package com.baeldung.springbootmvc.svc;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baeldung.springbootmvc.model.Product;

@Service
public class ProductService {

    private final Set<Product> products = new HashSet<>();

    {
        products.add(new Product("Book", 23.90, 1));
        products.add(new Product("Pen", 44.34, 2));
    }

    public Product findById(int id) {
        return products.stream()
            .filter(obj -> obj.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ItemNotFoundException("Product not found"));
    }

    public Product findByName(String name) {
        return products.stream()
            .filter(obj -> obj.getName()
                .equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> new ItemNotFoundException("Product not found"));
    }

    public Set<Product> findAll() {
        return products;
    }

    public Product save(Product product) {
        if (StringUtils.isEmpty(product.getName()) || product.getPrice() == 0.0) {
            throw new IllegalArgumentException();
        }
        int newId = products.stream()
            .mapToInt(Product::getId)
            .max()
            .getAsInt() + 1;
        product.setId(newId);
        products.add(product);
        return product;
    }

    public static class ItemNotFoundException extends RuntimeException {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public ItemNotFoundException(String msg) {
            super(msg);
        }
    }
}
