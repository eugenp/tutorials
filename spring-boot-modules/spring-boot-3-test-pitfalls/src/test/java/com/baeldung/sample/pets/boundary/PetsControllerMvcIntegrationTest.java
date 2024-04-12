package com.baeldung.sample.pets.boundary;

import com.baeldung.sample.pets.domain.PetService;
import com.baeldung.sample.test.slices.PetsBoundaryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@PetsBoundaryTest
class PetsControllerMvcIntegrationTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    PetService service;

    @Test
    void shouldReturnEmptyArrayWhenGetPets() throws Exception {
        when(service.getPets()).thenReturn(Collections.emptyList());
        mvc.perform(
            get("/pets")
              .accept(MediaType.APPLICATION_JSON)
          )
          .andExpect(status().isOk())
          .andExpect(content().string("[]"));
    }

}
