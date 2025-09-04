package com.baeldung.customstatefulvalidation.controllers;

import com.baeldung.customstatefulvalidation.configuration.TenantChannels;
import com.baeldung.customstatefulvalidation.repository.WarehouseRouteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.baeldung.customstatefulvalidation.model.PurchaseOrderItemFactory.createValidPurchaseOrderItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest({PurchaseOrderController.class, TenantChannels.class, WarehouseRouteRepository.class})
class PurchaseOrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenSendBlankRequestThenInvalid() throws Exception {
        mockMvc.perform(post("/api/purchasing/")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenSendValidRequestThenAccepted() throws Exception {
        mockMvc.perform(post("/api/purchasing/")
                .content(objectMapper.writeValueAsString(createValidPurchaseOrderItem()))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());
    }
}