package org.cloud.app.rest;

import org.cloud.app.core.service.CloudTechService;
import org.cloud.app.domain.data.CloudTechDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Collections;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CloudTechControllerImpl.class)
@ContextConfiguration(classes = {CloudTechController.class, CloudTechControllerImpl.class})
public class CloudTechControllerImplTest {

    private static final String TEST_CLOUD = "AWS";
    private static final String TEST_CLOUDTECH = "AWS Athena";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CloudTechService cloudTechService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenCloudTech_whenAddCloudTech_thenEntityIsPortedToService() throws Exception {
        final CloudTechDto testCloudTechDto = CloudTechDto.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH)
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/cloudTech")
                .content(objectMapper.writeValueAsString(testCloudTechDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(cloudTechService, only()).addCloudTech(testCloudTechDto);
    }


    @Test
    void givenCloudTech_whenUpdateCloudTech_thenEntityUpdateIsPortedToService() throws Exception {
        final CloudTechDto testCloudTechDto = CloudTechDto.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH)
                .build();

        mvc.perform(MockMvcRequestBuilders.put("/cloudTech")
                .content(objectMapper.writeValueAsString(testCloudTechDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cloudTechService, only()).updateCloudTech(testCloudTechDto);
    }

    @Test
    void givenCloudTech_whenRemoveCloudTech_thenEntityRemovalIsPortedToService() throws Exception {
        final CloudTechDto testCloudTechDto = CloudTechDto.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH)
                .build();

        mvc.perform(MockMvcRequestBuilders.delete("/cloudTech")
                .content(objectMapper.writeValueAsString(testCloudTechDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cloudTechService, only()).removeCloudTech(testCloudTechDto);
    }

    @Test
    void givenCallToAllCloudTechs_whenNoParams_thenFindAllIsPortedToService() throws Exception {
        final CloudTechDto testCloudTechDto = CloudTechDto.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH)
                .build();
        when(cloudTechService.getAllCloudTech()).thenReturn(Collections.singletonList(testCloudTechDto));

        mvc.perform(MockMvcRequestBuilders.get("/cloudTech")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cloud")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cloud")
                        .value(TEST_CLOUD))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cloudTech")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cloudTech")
                        .value(TEST_CLOUDTECH));

        verify(cloudTechService, only()).getAllCloudTech();
    }

    @Test
    void givenArtisId_whenCallingGetCloudTechById_thenFindByIdToService() throws Exception {
        final CloudTechDto testCloudTechDto = CloudTechDto.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH)
                .build();
        when(cloudTechService.getCloudTechById(1L)).thenReturn(testCloudTechDto);

        mvc.perform(MockMvcRequestBuilders.get("/cloudTech/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cloud")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cloud")
                        .value(TEST_CLOUD))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cloudTech")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cloudTech")
                        .value(TEST_CLOUDTECH));

        verify(cloudTechService, only()).getCloudTechById(1L);
    }

    @Test
    void givenUnexistingArtisId_whenCallingGetCloudTechById_thenFindByIdToServiceFails() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cloudTech/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cloud")
                        .doesNotExist());

        verify(cloudTechService, only()).getCloudTechById(1L);
    }

}