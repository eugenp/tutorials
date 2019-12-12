package com.baeldung.hexagonal.architecture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArtistDemoApplicationLauncherTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void givenArtisId_whenCallingGetArtistById_thenReturnsArtist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/artist/1")
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.name").value("Billie Eilish"));
    }

    @Test
    void givenUnexistingArtisId_whenCallingGetArtistById_thenIsNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/artist/7")
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.name").doesNotExist());
    }
}