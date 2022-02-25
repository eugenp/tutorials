package com.baeldung.swaggerresponseapi;

import com.baeldung.swaggerresponseapi.controller.ProductController;
import com.baeldung.swaggerresponseapi.model.Product;
import com.baeldung.swaggerresponseapi.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductIntegrationTest {

    @Autowired
    MockMvc mock;

    @MockBean
    private ProductService productService;

    @Test
    public void givenProduct_whenAddNewProduct_thenReturnValidStatusCode() throws Exception {
        Product product = new Product("1001", "Milk");
        given(productService.addProducts(any(Product.class))).willReturn(product);

        mock.perform(post("/create").contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(product)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("1001"));

        reset(productService);
    }

    @Test
    public void givenProductsList_whenGetAllProducts_thenReturnValidProducts() throws Exception {
        Product product1 = new Product("1001", "Milk");
        Product product2 = new Product("2002", "Butter");

        given(productService.getProductsList()).willReturn(Arrays.asList(product1, product2));

        mock.perform(get("/products").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$[0].code", is(product1.getCode())))
            .andExpect(jsonPath("$[1].name", is(product2.getName())));

        reset(productService);
    }

    public String toJsonString(Product product) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(product);
        return json;
    }
}
