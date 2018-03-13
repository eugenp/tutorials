package com.baeldung.flips.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"feature.flips.by.id=Y",
                              "feature.movie.statistics=Y"
                             })
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class FlipControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldLoadAllThings() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/things"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(6)));
    }

    @Test
    public void shouldGetThingById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/things/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Thing1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)));
    }

    @Test
    public void getNewThing() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/thing/new"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("New Thing!")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(99)));
    }
}