package com.baeldung.pattern.hexagonal.product.adapter.inbound.web;

import com.baeldung.pattern.hexagonal.product.application.port.inbound.GetPriceUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.math.BigDecimal.TEN;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetPriceUseCase getPriceUseCase;

    @Test
    void givenPriceForProduct_whenQueryingForProductPrice_thenOKResponseReturned() throws Exception {
        given(getPriceUseCase.getPrice("id-123"))
          .willReturn(TEN);

        mockMvc.perform(get("/products/id-123/price"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.productId").value("id-123"))
          .andExpect(jsonPath("$.price").value("10"));

        then(getPriceUseCase)
          .should()
          .getPrice(eq("id-123"));
    }

}