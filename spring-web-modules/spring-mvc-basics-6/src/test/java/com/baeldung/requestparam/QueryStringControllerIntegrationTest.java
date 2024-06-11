package com.baeldung.requestparam;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class QueryStringControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(new QueryStringController()).build();
    }

    @Test
    public void whenInvokeGetQueryString_thenReturnTheOriginQueryString() throws Exception {
        this.mockMvc.perform(get("/api/byGetQueryString?username=bob&roles=admin&roles=stuff"))
            .andExpect(status().isOk())
            .andExpect(content().string("username=bob&roles=admin&roles=stuff"));
    }

    @Test
    public void whenInvokeGetQueryParameter_thenReturnOneParameterValue() throws Exception {
        this.mockMvc.perform(get("/api/byGetParameter?username=bob"))
            .andExpect(status().isOk())
            .andExpect(content().string("username:bob"));
    }

    @Test
    public void whenInvokeGetParameterValues_thenReturnParameterAsArray() throws Exception {
        this.mockMvc.perform(get("/api/byGetParameterValues?roles=admin&roles=stuff"))
            .andExpect(status().isOk())
            .andExpect(content().string("roles:[admin, stuff]"));
    }

    @ParameterizedTest
    @CsvSource({
        "/api/byGetParameterMap",
        "/api/byParameterName",
        "/api/byAnnoRequestParam",
        "/api/byPojo"
    })
    public void whenPassParameters_thenReturnResolvedModel(String path) throws Exception {
        this.mockMvc.perform(get(path + "?username=bob&roles=admin&roles=stuff"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("bob"))
            .andExpect(jsonPath("$.roles").value(containsInRelativeOrder("admin", "stuff")));
    }
}