
package com.baeldung.requestmapping;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.config.MvcConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@AutoConfigureWebClient
public class BazzNewMappingsExampleIntegrationTest {
    
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void whenGettingAllBazz_thenSuccess() throws Exception{
        mockMvc.perform(get("/bazz"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(4)))
            .andExpect(jsonPath("$[1].id", is("2")))
            .andExpect(jsonPath("$[1].name", is("Bazz2")));
    }
    
    @Test
    public void whenGettingABazz_thenSuccess() throws Exception{
        mockMvc.perform(get("/bazz/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is("1")))
            .andExpect(jsonPath("$.name", is("Bazz1")));
    }
    
    @Test
    public void whenAddingABazz_thenSuccess() throws Exception{
        mockMvc.perform(post("/bazz").param("name", "Bazz5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is("5")))
            .andExpect(jsonPath("$.name", is("Bazz5")));
    }
    
    @Test
    public void whenUpdatingABazz_thenSuccess() throws Exception{
        mockMvc.perform(put("/bazz/5").param("name", "Bazz6"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is("5")))
            .andExpect(jsonPath("$.name", is("Bazz6")));
    }
    
    @Test
    public void whenDeletingABazz_thenSuccess() throws Exception{
        mockMvc.perform(delete("/bazz/5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is("5")));
    }
}
