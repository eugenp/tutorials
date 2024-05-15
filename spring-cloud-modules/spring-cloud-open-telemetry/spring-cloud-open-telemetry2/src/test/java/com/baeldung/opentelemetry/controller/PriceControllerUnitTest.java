package com.baeldung.opentelemetry.controller;


import com.baeldung.opentelemetry.exception.PriceNotFoundException;
import com.baeldung.opentelemetry.model.Price;
import com.baeldung.opentelemetry.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PriceController.class)
class PriceControllerUnitTest {

    @MockBean
    private PriceRepository priceRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenProductandPriceAvailable_whenGetProductCalled_thenReturnProductDetails() throws Exception {
        long productId = 100000L;
        Price price = new Price();
        price.setProductId(productId);
        price.setPriceAmount(12.00);
        price.setDiscount(2.5);

        when(priceRepository.getPrice(productId)).thenReturn(price);

        mockMvc.perform(get("/price/" + productId))
          .andExpect(status().is(HttpStatus.OK.value()));
    }


    @Test
    void givenProductNotFound_whenGetProductCalled_thenReturnInternalServerError() throws Exception {
        long productId = 100000L;

        when(priceRepository.getPrice(productId)).thenThrow(PriceNotFoundException.class);

        mockMvc.perform(get("/price/" + productId))
          .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

}
