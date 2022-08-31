package com.baeldung.handlermethodargumentresolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenSendingAPostJSON_thenReturnFirstNameAndCity() throws Exception {

        String jsonString = "{\"firstName\":\"John\",\"lastName\":\"Smith\",\"age\":10,\"address\":{\"streetName\":\"Example Street\",\"streetNumber\":\"10A\",\"postalCode\":\"1QW34\",\"city\":\"Timisoara\",\"country\":\"Romania\"}}";
        mockMvc.perform(post("/user/process/custom").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName")
                .value("John"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.city")
                .value("Timisoara"));
    }

    @Test
    void whenSendingAPostJSON_thenReturnUserAndAddress() throws Exception {

        String jsonString = "{\"firstName\":\"John\",\"lastName\":\"Smith\",\"age\":10,\"address\":{\"streetName\":\"Example Street\",\"streetNumber\":\"10A\",\"postalCode\":\"1QW34\",\"city\":\"Timisoara\",\"country\":\"Romania\"}}";
        ObjectMapper mapper = new ObjectMapper();
        UserDto user = mapper.readValue(jsonString, UserDto.class);

        MvcResult mvcResult = mockMvc.perform(post("/user/create/custom").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("", content);
 //           .andExpect(MockMvcResultMatchers.jsonPath("$.user")
 //               .value(user.toString()));
//            .andExpect(MockMvcResultMatchers.jsonPath("$.address")
//                .value("Timisoara"));
    }
}