package com.baeldung.thymeleaf.lists;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class ListsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCalledToList_ThenConvertsToList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lists/toList"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("converted list size: <span>4</span>")));
    }

    @Test
    public void whenCalledContains_ThenChecksMembership() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lists/contains"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("myList contains red: <span>true</span>")))
          .andExpect(content().string(containsString("myList contains red and green: <span>true</span>")));
    }

    @Test
    public void whenCalledSize_ThenReturnsSize() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lists/size"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("size: <span>4</span>")));
    }

    @Test
    public void whenCalledSort_ThenSortsItems() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lists/sort"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("sort: <span>[blue, green, red, yellow]</span>")))
          .andExpect(content().string(containsString("sort with Comparator: <span>[yellow, red, green, blue]</span>")));
    }

    @Test
    public void whenCalledIsEmpty_ThenChecksAnyMembers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lists/isEmpty"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("isEmpty Check : <span>false</span>")));
    }
}