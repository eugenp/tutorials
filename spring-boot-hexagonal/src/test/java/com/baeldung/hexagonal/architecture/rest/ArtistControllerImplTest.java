package com.baeldung.hexagonal.architecture.rest;

import com.baeldung.hexagonal.architecture.core.port.ArtistServicePort;
import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ArtistControllerImpl.class)
@ContextConfiguration(classes = { ArtistController.class, ArtistControllerImpl.class })
public class ArtistControllerImplTest {

    private static final String TEST_ARTIST_NAME = "Ed Sheeran";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArtistServicePort artistServicePort;

    @Test
    void givenArtisId_whenCallingGetArtistById_thenFindByIdToService() throws Exception {
        final ArtistDto testArtistDto = ArtistDto.builder()
            .name(TEST_ARTIST_NAME)
            .build();
        when(artistServicePort.getArtistById(1L)).thenReturn(testArtistDto);

        mvc.perform(MockMvcRequestBuilders.get("/artist/1")
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                .exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                .value(TEST_ARTIST_NAME));

        verify(artistServicePort, only()).getArtistById(1L);
    }

    @Test
    void givenUnexistingArtisId_whenCallingGetArtistById_thenFindByIdToServiceFails() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/artist/1")
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                .doesNotExist());

        verify(artistServicePort, only()).getArtistById(1L);
    }
}