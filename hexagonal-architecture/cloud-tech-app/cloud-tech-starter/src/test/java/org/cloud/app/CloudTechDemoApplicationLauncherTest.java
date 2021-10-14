package org.cloud.tech;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cloud.tech.domain.data.CloudTechDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CloudTechDemoApplicationLauncherTest {
    private static final String TEST_CLOUD = "GCP";
    private static final String TEST_CLOUDTECH = "GCP ML";
    private static final String TEST_CLOUDTECH_2 = "GCP COMPUTE";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    void givenCloudTech_whenAddAndUpdateAndThenRemoveCloudTech_thenEntity() throws Exception {
        final CloudTechDto testCloudTechDto = CloudTechDto.builder()
                .cloud(TEST_CLOUD)
                .cloudTech(TEST_CLOUDTECH)
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/cloudTech")
                .content(objectMapper.writeValueAsString(testCloudTechDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());

        mvc.perform(MockMvcRequestBuilders.get("/cloudTech/6")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cloud").exists())
                .andExpect(jsonPath("$.cloud").value(TEST_CLOUD))
                .andExpect(jsonPath("$.cloudTech").exists())
                .andExpect(jsonPath("$.cloudTech").value(TEST_CLOUDTECH));

        testCloudTechDto.setCloudTech(TEST_CLOUDTECH_2);

        mvc.perform(MockMvcRequestBuilders.put("/cloudTech")
                .content(objectMapper.writeValueAsString(testCloudTechDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/cloudTech/6")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cloud").exists())
                .andExpect(jsonPath("$.cloud").value(TEST_CLOUD))
                .andExpect(jsonPath("$.cloudTech").exists())
                .andExpect(jsonPath("$.cloudTech").value(TEST_CLOUDTECH_2));

        mvc.perform(MockMvcRequestBuilders.delete("/cloudTech")
                .content(objectMapper.writeValueAsString(testCloudTechDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/cloudTech/6")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.cloud").doesNotExist())
                .andExpect(jsonPath("$.cloudTech").doesNotExist());
    }

    @Test
    void givenCallToAllCloudTech_whenNoParams_thenFindAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cloudTech")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].cloud").exists())
                .andExpect(jsonPath("$[0].cloud").value("AWS"))
                .andExpect(jsonPath("$[0].cloudTech").exists())
                .andExpect(jsonPath("$[0].cloudTech").value("AWS Ec2"))
                .andExpect(jsonPath("$[1].cloud").exists())
                .andExpect(jsonPath("$[1].cloud").value("Azure"))
                .andExpect(jsonPath("$[1].cloudTech").exists())
                .andExpect(jsonPath("$[1].cloudTech").value("Azure AI"))
                .andExpect(jsonPath("$[2].cloud").exists())
                .andExpect(jsonPath("$[2].cloud").value("GCP"))
                .andExpect(jsonPath("$[2].cloudTech").exists())
                .andExpect(jsonPath("$[2].cloudTech").value("GCP ML"))
                .andExpect(jsonPath("$[3].cloud").exists())
                .andExpect(jsonPath("$[3].cloud").value("AWS"))
                .andExpect(jsonPath("$[3].cloudTech").exists())
                .andExpect(jsonPath("$[3].cloudTech").value("AWS RDS"))
                .andExpect(jsonPath("$[4].cloud").exists())
                .andExpect(jsonPath("$[4].cloud").value("Azure"))
                .andExpect(jsonPath("$[4].cloudTech").exists())
                .andExpect(jsonPath("$[4].cloudTech").value("Azure ML"));
    }

    @Test
    void givenArtisId_whenCallingGetCloudTechById_thenReturnsCloudTech() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cloudTech/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cloud").exists())
                .andExpect(jsonPath("$.cloud").value("AWS"))
                .andExpect(jsonPath("$.cloudTech").exists())
                .andExpect(jsonPath("$.cloudTech").value("Compute"));
    }

    @Test
    void givenUnexistingArtisId_whenCallingGetCloudTechById_thenIsNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cloudTech/7")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.cloud").doesNotExist())
                .andExpect(jsonPath("$.cloudTech").doesNotExist());
    }
}