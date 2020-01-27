package com.baeldung;

import org.baeldung.boot.Application;
import org.baeldung.boot.domain.Modes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SpringBootApplicationIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenRequestHasBeenMade_whenMeetsAllOfGivenConditions_thenCorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/entity/all")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void givenRequestHasBeenMade_whenMeetsFindByDateOfGivenConditions_thenCorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/entity/findbydate/{date}", "2011-12-03T10:15:30")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void givenRequestHasBeenMade_whenMeetsFindByModeOfGivenConditions_thenCorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/entity/findbymode/{mode}", Modes.ALPHA.name())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void givenRequestHasBeenMade_whenMeetsFindByVersionOfGivenConditions_thenCorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/entity/findbyversion").header("Version", "1.0.0")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }
}