package com.baeldung.caching.ttl.controller;

import com.baeldung.caching.ttl.model.Hotel;
import com.baeldung.caching.ttl.repository.CityRepository;
import com.baeldung.caching.ttl.repository.HotelRepository;
import com.booking.testing.SlowTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
  @Autowired private ObjectMapper mapper;

  @Autowired private HotelRepository repository;
  @Autowired private CityRepository cityRepository;

  @Test
  @DisplayName("When all hotels are requested then they are all returned")
  void whenAllHotelsRequested_thenReturnAllHotels() throws Exception {
    mockMvc
            .perform(get("/hotel"))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$", hasSize((int) repository.findAll().stream().count())));
  }

  @Test
  @DisplayName("When a hotel is requested by id then the hotel is returned")
  void whenAGivenHotelsRequested_thenReturnTheHotel() throws Exception {
    Long hotelId = 1L;
    Hotel hotel =
            mapper
                    .readValue(
                            mockMvc
                                    .perform(
                                            get("/hotel/" + hotelId)
                                                    .contentType(MediaType.APPLICATION_JSON))
                                    .andExpect(status().isOk())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString(),
                            Hotel.class);

    assertThat(
            repository
                    .findById(hotelId)
                    .orElseThrow(() -> new IllegalStateException(String
                            .format("Hotel with id %s does not exist even in repository", hotelId))),
            equalTo(hotel));
  }
}