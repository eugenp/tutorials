package com.baeldung.hexagonalspringboot;

import java.util.List;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.hexagonalspringboot.domain.Product;
import com.baeldung.hexagonalspringboot.port.IProductSearchService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetProducts_thenStatus200() throws Exception {
        mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void whenGetByCategory_thenStatus200() throws Exception {

        mockMvc.perform(get("/products/tablet").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

}
