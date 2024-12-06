package com.baeldung.cats;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class ItemControllerIntegrationTest {

    static final String ITEM_API = "/api/v2/item";

    MockMvc mvc;

    public ItemControllerIntegrationTest(WebApplicationContext wac) {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac)
            .build();
    }

    @Test
    void whenPostingItem_thenReturnContainsUuid() throws Exception {
        mvc.perform(post(ITEM_API).contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"foo\", \"value\": 1}"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                .isNotEmpty());
    }
}
