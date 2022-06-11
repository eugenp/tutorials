package com.baeldung.apikeysecretauth.service;

import java.util.List;

import com.baeldung.apikeysecretauth.model.Product;
import com.baeldung.apikeysecretauth.model.User;

public interface ProductService {
    List<Product> getAllProducts(User user);
}
