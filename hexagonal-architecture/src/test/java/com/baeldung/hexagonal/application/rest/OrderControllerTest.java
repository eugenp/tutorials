package com.baeldung.hexagonal.application.rest;

import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.domain.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void givenExistingOrder_whenFindingOrder_thenReturnCorrectOrder() throws Exception {
        Order order = new Order();
        order.setPrice(new BigDecimal(1000));
        Long orderId = this.orderRepository.save(order).getId();

        this.mockMvc.perform(get("/orders/{id}", orderId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(1000));
    }

    @Test
    public void whenSavingOrder_thenShoudBeSuccessful() throws Exception {
        Order order = new Order();
        order.setPrice(new BigDecimal(1000));

        this.mockMvc.perform(post("/orders")
                .content(asJsonString(order))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) // if I need to be specific
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.price").value(1000));
    }

    private String asJsonString(Order order) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(order);
        return jsonContent;
    }
}
