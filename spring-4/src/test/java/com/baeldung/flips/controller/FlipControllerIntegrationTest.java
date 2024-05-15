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
@SpringBootTest(properties = {
    "feature.foo.by.id=Y",
    "feature.new.foo=Y",
    "last.active.after=2018-03-14T00:00:00Z",
    "first.active.after=2999-03-15T00:00:00Z",
    "logging.level.org.flips=info"

}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class FlipControllerIntegrationTest {

    @Autowired private MockMvc mvc;

    @Test
    public void givenValidDayOfWeek_APIAvailable() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/foo/1"))
            .andExpect(MockMvcResultMatchers.status().is(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Foo1")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)));
    }

    @Test
    public void givenValidDate_APIAvailable() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/foo/last"))
            .andExpect(MockMvcResultMatchers.status().is(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Foo6")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(6)));
    }

    @Test
    public void givenInvalidDate_APINotAvailable() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/foo/first"))
            .andExpect(MockMvcResultMatchers.status().is(501));
    }

    @Test
    public void givenCorrectProfile_APIAvailable() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/foos"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(6)));
    }

    @Test
    public void givenPropertySet_APIAvailable() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/foos/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Foo1")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)));
    }

    @Test
    public void getValidExpression_FlipBean() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/foo/new"))
            .andExpect(MockMvcResultMatchers.status().is(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Shiny New Foo!")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(100)));
    }
}