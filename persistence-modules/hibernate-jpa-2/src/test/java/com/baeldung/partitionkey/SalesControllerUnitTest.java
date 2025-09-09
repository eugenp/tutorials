package com.baeldung.partitionkey;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = Controller.class, excludeAutoConfiguration = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class SalesControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalesRepository salesRepository;

    @Test
    void givenValidSalesObject_whenAddingSales_thenPersistsAndReturnsCorrectly() throws Exception {
        Sales sales = new Sales(104L, LocalDate.of(2024, 2, 1), BigDecimal.valueOf(8476.34d));
        when(salesRepository.save(ArgumentMatchers.any(Sales.class))).thenReturn(sales);
        mockMvc.perform(get("/add").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.id").value(104L))
          .andExpect(jsonPath("$.saleDate").value("2024-02-01"))
          .andExpect(jsonPath("$.amount").value(8476.34));
    }
}
