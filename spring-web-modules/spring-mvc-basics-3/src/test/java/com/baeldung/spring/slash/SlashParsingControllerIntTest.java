package com.baeldung.spring.slash;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SlashParsingControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenUsingPathVariablemWithoutSlashes_thenStatusOk() throws Exception {
        final String stringWithoutSlashes = "noslash";

        MvcResult mvcResult = mockMvc.perform(get("/slash/mypaths/" + stringWithoutSlashes))
            .andExpect(status().isOk())
            .andReturn();

        assertEquals(stringWithoutSlashes, mvcResult.getResponse()
            .getContentAsString());
    }

    @Test
    public void whenUsingPathVariableWithSlashes_thenStatusNotFound() throws Exception {
        final String stringWithSlashes = "url/with/slashes";

        mockMvc.perform(get("/slash/mypaths/" + stringWithSlashes))
            .andExpect(status().isNotFound());
    }

    @Test
    public void givenAllFallbackEndpoint_whenUsingPathWithSlashes_thenStatusOk() throws Exception {
        final String stringWithSlashes = "url/for/testing/purposes";

        MvcResult mvcResult = mockMvc.perform(get("/slash/all/" + stringWithSlashes))
            .andExpect(status().isOk())
            .andReturn();

        assertEquals(stringWithSlashes, mvcResult.getResponse()
            .getContentAsString());
    }

    @Test
    public void givenAllFallbackEndpoint_whenUsingConsecutiveSlashes_thenPathNormalized() throws Exception {
        final String stringWithSlashes = "http://myurl.com";

        MvcResult mvcResult = mockMvc.perform(get("/slash/all/" + stringWithSlashes))
            .andExpect(status().isOk())
            .andReturn();

        String stringWithSlashesNormalized = URI.create("/slash/all/" + stringWithSlashes)
            .normalize()
            .toString()
            .split("/slash/all/")[1];

        assertEquals(stringWithSlashesNormalized, mvcResult.getResponse()
            .getContentAsString());
    }

    @Test
    public void whenUsingSlashesInQueryParam_thenParameterAccepted() throws Exception {
        final String stringWithSlashes = "url/for////testing/purposes";

        MvcResult mvcResult = mockMvc.perform(get("/slash/all").param("param", stringWithSlashes))
            .andExpect(status().isOk())
            .andReturn();

        assertEquals(stringWithSlashes, mvcResult.getResponse()
            .getContentAsString());
    }

}
