package com.baeldung.adapter;

import com.baeldung.domain.Juice;
import com.baeldung.port.inbound.JuiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
public class JuiceControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private JuiceService juiceService;

    private MockMvc mockMvc;

    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenSendGetRequestWithName_thenCorrectJuiceRetrieved() throws Exception {

        String kiwiJuiceName = "kiwi";
        String[] kiwiJuiceIngredients = new String[]{"water", "kiwi", "sugar"};
        Juice kiwiTestJuice = new Juice(kiwiJuiceName, kiwiJuiceIngredients);

        Mockito.when(juiceService.retrieveJuice(kiwiJuiceName)).thenReturn(kiwiTestJuice);

        mockMvc.perform(MockMvcRequestBuilders.get("/juice/kiwi"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(jsonPath("$.name").value(kiwiJuiceName))
                .andExpect(jsonPath("$.ingredients[0]").value(kiwiJuiceIngredients[0]))
                .andExpect(jsonPath("$.ingredients[1]").value(kiwiJuiceIngredients[1]))
                .andExpect(jsonPath("$.ingredients[2]").value(kiwiJuiceIngredients[2]));
    }

    @Test
    public void whenSendGetRequestToJuieEndpoint_thenAllJuicesAreRetrieved() throws Exception {

        String kiwiJuiceName = "kiwi";
        String[] kiwiJuiceIngredients = new String[]{"water", "kiwi", "sugar"};
        Juice kiwiTestJuice = new Juice(kiwiJuiceName, kiwiJuiceIngredients);

        String appleJuiceName = "apple";
        String[] appleJuiceIngredients = new String[]{"water", "apple", "sugar"};
        Juice appleTestJuice = new Juice(appleJuiceName, appleJuiceIngredients);

        Mockito.when(juiceService.listJuices()).thenReturn(new ArrayList<>(Arrays.asList(kiwiTestJuice, appleTestJuice)));

        mockMvc.perform(MockMvcRequestBuilders.get("/juice"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(jsonPath("$[0].name").value(kiwiJuiceName))
                .andExpect(jsonPath("$[1].name").value(appleJuiceName))
                .andExpect(jsonPath("$[0].ingredients[0]").value(kiwiJuiceIngredients[0]))
                .andExpect(jsonPath("$[0].ingredients[1]").value(kiwiJuiceIngredients[1]))
                .andExpect(jsonPath("$[0].ingredients[2]").value(kiwiJuiceIngredients[2]))
                .andExpect(jsonPath("$[1].ingredients[0]").value(appleJuiceIngredients[0]))
                .andExpect(jsonPath("$[1].ingredients[1]").value(appleJuiceIngredients[1]))
                .andExpect(jsonPath("$[1].ingredients[2]").value(appleJuiceIngredients[2]));
    }

    @Test
    public void whenSendingPostRequestWithJuiceAsJson_thenJuiceIsAdded() throws Exception {
        String appleJuiceName = "apple";
        String[] appleJuiceIngredients = new String[]{"water", "apple", "sugar"};
        Juice appleTestJuiceToAdd = new Juice(appleJuiceName, appleJuiceIngredients);

        mockMvc.perform(MockMvcRequestBuilders.post("/juice")
                .content(asJsonString(appleTestJuiceToAdd))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
