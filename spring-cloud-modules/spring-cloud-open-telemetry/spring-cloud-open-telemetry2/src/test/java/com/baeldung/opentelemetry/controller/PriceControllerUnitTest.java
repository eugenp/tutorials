package com.baeldung.opentelemetry.controller;


import com.baeldung.opentelemetry.exception.PriceNotFoundException;
import com.baeldung.opentelemetry.model.Price;
import com.baeldung.opentelemetry.repository.PriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(PriceController.class)
@EnableWebMvc
public class PriceControllerUnitTest {

    @MockBean
    private PriceRepository priceRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenProductandPriceAvailable_whenGetProductCalled_thenReturnProductDetails() throws Exception {
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
    public void givenProductNotFound_whenGetProductCalled_thenReturnInternalServerError() throws Exception {
        long productId = 100000L;

        when(priceRepository.getPrice(productId)).thenThrow(PriceNotFoundException.class);

        mockMvc.perform(get("/price/" + productId))
          .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

}
