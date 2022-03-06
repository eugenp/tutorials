package com.baeldung.hexagonal.architecture.rest;

import com.baeldung.hexagonal.architecture.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@RunWith(SpringRunner.class)
class ProductControllerUnitTest {

    @MockBean
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenGetProductById_thenReturnProduct() throws Exception {
        Product product = Product.builder()
                .productId(1)
                .type("Technology")
                .description("Last generation mobile phone")
                .build();
        when(productController.getProductById(anyInt())).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/product/{productId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddProduct_thenCorrectStatusCode() throws Exception {
        Product product = Product.builder()
                .productId(1)
                .type("Technology")
                .description("Last generation mobile phone")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteProduct_thenRemoveProduct() throws Exception {
        Product product = Product.builder()
                .productId(1)
                .type("Technology")
                .description("Last generation mobile phone")
                .build();
        when(productController.removeProduct(anyInt())).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/product/{productId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}