package com.baeldung.hexagonal.port.web.test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test //
    public void shouldPassA() throws Exception {
        this.mockMvc.perform(get("/showBalance"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("0")));
    }

    @Test //
    public void shouldPassB() throws Exception {
        this.mockMvc.perform(post("/postDeposit").param("amount", "15"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("true")));
    }

    @Test //
    public void shouldPassC() throws Exception {
        this.mockMvc.perform(get("/showBalance"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("15")));
    }

    @Test //
    public void shouldPassD() throws Exception {
        this.mockMvc.perform(get("/renderStatement"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect((jsonPath("$[0].type", is("deposit"))))
            .andExpect((jsonPath("$[0].amount", is(15))));
    }
}