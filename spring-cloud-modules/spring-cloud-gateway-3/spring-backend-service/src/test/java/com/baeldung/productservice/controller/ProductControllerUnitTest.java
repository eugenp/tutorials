package com.baeldung.productservice.controller;

import com.baeldung.productservice.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void givenProductandPriceDataAvailable_whenGetProductCalled_thenReturnProductDetails() throws Exception {
        long productId = 100000L;

        Product product = createProduct(productId);

        mockMvc.perform(get("/product/" + productId))
            .andExpect(status().is(HttpStatus.OK.value()));
    }

    private static Product createProduct(long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setName("test");
        return product;
    }

}
