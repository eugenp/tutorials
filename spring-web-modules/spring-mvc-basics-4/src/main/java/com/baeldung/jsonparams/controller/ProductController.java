package com.baeldung.jsonparams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.jsonparams.model.Product;
import com.baeldung.jsonparams.propertyeditor.ProductEditor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Product.class, new ProductEditor(objectMapper));
    }

    @PostMapping("/create")
    @ResponseBody
    public Product createProduct(@RequestBody Product product) {
        // custom logic
        return product;
    }

    @GetMapping("/get")
    @ResponseBody
    public Product getProduct(@RequestParam String product) throws JsonMappingException, JsonProcessingException {
        final Product prod = objectMapper.readValue(product, Product.class);
        return prod;
    }

    @GetMapping("/get2")
    @ResponseBody
    public Product get2Product(@RequestParam Product product) {
        // custom logic
        return product;
    }

}
