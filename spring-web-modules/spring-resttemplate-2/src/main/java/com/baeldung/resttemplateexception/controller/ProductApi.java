package com.baeldung.resttemplateexception.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.resttemplateexception.model.Criterion;
import com.baeldung.resttemplateexception.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ProductApi {

    private List<Product> productList = new ArrayList<>(Arrays.asList(new Product(1, "Acer Aspire 5", 437), new Product(2, "ASUS VivoBook", 650), new Product(3, "Lenovo Legion", 990)));

    @GetMapping("/get")
    public Product get(@RequestParam String criterion) throws JsonMappingException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Criterion crt;

        crt = objectMapper.readValue(criterion, Criterion.class);
        if (crt.getProp().equals("name"))
            return findByName(crt.getValue());

        // Search by other properties (id,price)

        return null;
    }

    private Product findByName(String name) {
        for (Product product : this.productList) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    // Other methods

}
