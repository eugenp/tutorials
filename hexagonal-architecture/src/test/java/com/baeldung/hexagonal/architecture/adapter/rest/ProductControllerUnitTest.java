package com.baeldung.hexagonal.architecture.adapter.rest;

import com.baeldung.hexagonal.architecture.domain.model.Product;
import com.baeldung.hexagonal.architecture.port.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService productService;

    @Test
    public void givenProducts_whenGetProducts_thenReturnJsonArray() throws Exception {
        Product mobilePhone = new Product(1, "Mobile Phone", "Samsung Galaxy S9");
        Product book = new Product(2, "Book", "Kite Runner");
        Product laptop = new Product(3, "Laptop", "Macbook Pro");

        List<Product> products = Arrays.asList(mobilePhone, book, laptop);

        given(productService.getProducts()).willReturn(products);

        mvc.perform(get("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].type", is(mobilePhone.getType())))
                .andExpect(jsonPath("$[1].description", is(book.getDescription())));

        verify(productService, VerificationModeFactory.times(1)).getProducts();
        reset(productService);
    }

    @Test
    public void givenProduct_whenGetProductById_thenReturnValidProduct() throws Exception {
        Product mobilePhone = new Product(1, "Mobile Phone", "Samsung Galaxy S9");
        given(productService.getProductById(Mockito.anyInt())).willReturn(mobilePhone);

        mvc.perform(get("/api/v1/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(mobilePhone.getProductId())));

        verify(productService, VerificationModeFactory.times(1)).getProductById(Mockito.anyInt());
        reset(productService);
    }

    @Test
    public void whenPostProduct_thenCreateProduct() throws Exception {
        Product book = new Product(1, "Book", "Kite Runner");
        given(productService.addProduct(Mockito.any())).willReturn(book);

        mvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type", is(book.getType())));

        verify(productService, VerificationModeFactory.times(1)).addProduct(Mockito.any());
        reset(productService);
    }

    @Test
    public void whenDeleteProduct_thenRemoveValidProduct() throws Exception {
        Product book = new Product(1, "Book", "Kite Runner");
        given(productService.removeProduct(Mockito.anyInt())).willReturn(book);

        mvc.perform(delete("/api/v1/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(book.getProductId())));

        verify(productService, VerificationModeFactory.times(1)).removeProduct(Mockito.anyInt());
        reset(productService);
    }
}
