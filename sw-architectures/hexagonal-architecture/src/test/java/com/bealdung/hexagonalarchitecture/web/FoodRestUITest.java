package com.bealdung.hexagonalarchitecture.web;

import com.bealdung.hexagonalarchitecture.adapter.FoodRestController;
import com.bealdung.hexagonalarchitecture.core.Food;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FoodRestUITest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FoodRestController foodRestController;

    private final String foodName = "burger";
    private final String[] ingredients = {"beef", "tomato", "lettuce", "bread"};

    @Test
    void saveNewFood() throws Exception {

        Food food = new Food(foodName, ingredients);
        String foodDtoJson = objectMapper.writeValueAsString(food);

        mockMvc.perform(post("/food")
                .contentType(MediaType.APPLICATION_JSON)
                .content(foodDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void getFoodByName() throws Exception {
        mockMvc.perform(get("/food/" + foodName)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
