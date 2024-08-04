package com.baeldung.problemdetails.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.baeldung.problemdetails.model.OperationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
class SalesControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenValidInput_whenSellingPriceIsCalculated_thenReturnResult() throws Exception {
        OperationRequest operationRequest = new OperationRequest(100.0, 20.0);
        mockMvc.perform(MockMvcRequestBuilders.post("/sales/calculate")
                .content(toJson(operationRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpectAll(status().isOk(), jsonPath("$.basePrice").value(operationRequest.basePrice()),
                jsonPath("$.discount").value(operationRequest.discount()), jsonPath("$.sellingPrice").value(80.0))
            .andReturn();
    }

    @Test
    void givenNullBasePrice_whenSellingPriceIsCalculated_thenReturnError() throws Exception {
        OperationRequest operationRequest = new OperationRequest(null, 32.0);
        mockMvc.perform(MockMvcRequestBuilders.post("/sales/calculate")
                .content(toJson(operationRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpectAll(status().isBadRequest(), jsonPath("$.title").value(HttpStatus.BAD_REQUEST.getReasonPhrase()),
                jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()), jsonPath("$.detail").value("Invalid request content."),
                jsonPath("$.instance").value("/sales/calculate"))
            .andReturn();
    }

    @Test
    void givenInvalidDiscount_whenSellingPriceIsCalculated_thenReturnError() throws Exception {
        OperationRequest operationRequest = new OperationRequest(100.0, 32.0);
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/sales/calculate")
                    .content(toJson(operationRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        } catch (ServletException e) {
            final Throwable cause = e.getCause();
            assertInstanceOf(IllegalArgumentException.class, cause);
            assertEquals("Discount greater than 30% not allowed.", cause.getMessage());
        }
    }

    @Test
    void givenFreeSale_whenSellingPriceIsCalculated_thenReturnError() throws Exception {
        OperationRequest operationRequest = new OperationRequest(100.0, 140.0);
        mockMvc.perform(MockMvcRequestBuilders.post("/sales/calculate")
                .content(toJson(operationRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpectAll(status().isBadRequest(), jsonPath("$.title").value(HttpStatus.BAD_REQUEST.getReasonPhrase()),
                jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()), jsonPath("$.detail").value("Free sale is not allowed."),
                jsonPath("$.instance").value("discount"))
            .andReturn();
    }

    private String toJson(OperationRequest operationRequest) {
        try {
            return objectMapper.writeValueAsString(operationRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
