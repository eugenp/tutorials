package com.baeldung.caching.ttl;

import com.baeldung.caching.ttl.repository.HotelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
@SlowTest
class HotelControllerIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private HotelRepository repository;

    @Test
    @DisplayName("When all hotels requested then request is successful")
    void whenAllHotelsRequested_thenRequestIsSuccessful() throws Exception {
        mockMvc
                .perform(get("/hotel"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When all hotels are requested then they correct number of hotels is returned")
    void whenAllHotelsRequested_thenReturnAllHotels() throws Exception {
        mockMvc
                .perform(get("/hotel"))
                .andExpect(jsonPath("$", hasSize((int) repository.findAll().stream().count())));
    }
}