package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArtistDemoApplicationLauncherTest {
    private static final String TEST_ARTIST_NAME = "Ed Sheeran";

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenArtist_whenAddAndThenRemoveArtist_thenEntity() throws Exception {
        final ArtistDto testArtistDto = ArtistDto.builder()
            .name(TEST_ARTIST_NAME)
            .build();

        mvc.perform(MockMvcRequestBuilders.post("/artist")
            .content(objectMapper.writeValueAsString(testArtistDto))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isCreated());

        mvc.perform(MockMvcRequestBuilders.get("/artist/6")
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.name").value(TEST_ARTIST_NAME));

        mvc.perform(MockMvcRequestBuilders.delete("/artist")
            .content(objectMapper.writeValueAsString(testArtistDto))
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/artist/6")
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.name").doesNotExist());
    }

    @Test
    void givenCallToAllArtists_whenNoParams_thenFindAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/artist")
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$[0].name").exists())
            .andExpect(jsonPath("$[0].name").value("Billie Eilish"))
            .andExpect(jsonPath("$[1].name").exists())
            .andExpect(jsonPath("$[1].name").value("Fatboy Slim"))
            .andExpect(jsonPath("$[2].name").exists())
            .andExpect(jsonPath("$[2].name").value("Maroon 5"))
            .andExpect(jsonPath("$[3].name").exists())
            .andExpect(jsonPath("$[3].name").value("Moby"))
            .andExpect(jsonPath("$[4].name").exists())
            .andExpect(jsonPath("$[4].name").value("The Pogues"));
    }

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