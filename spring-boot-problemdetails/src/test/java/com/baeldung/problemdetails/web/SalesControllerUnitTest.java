package com.baeldung.problemdetails.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
class CalculatorControllerUnitTest {
  @Autowired private MockMvc mockMvc;

  @Test
  void add() {
    try {
      mockMvc.perform(MockMvcRequestBuilders.post("/add")).andExpect(status().isOk()).andReturn();
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }

  @Test
  void subtract() {}

  @Test
  void multiply() {}

  @Test
  void divide() {}
}
