package com.baeldung.springvalidation;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserAccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenSaveBasicInfo_whenCorrectInput_thenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/saveBasicInfo")
            .accept(MediaType.TEXT_HTML)
            .param("name", "test123")
            .param("password", "pass"))
            .andExpect(view().name("success"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void givenSaveBasicInfoStep1_whenCorrectInput_thenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/saveBasicInfoStep1")
            .accept(MediaType.TEXT_HTML)
            .param("name", "test123")
            .param("password", "pass"))
            .andExpect(view().name("success"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void givenSaveBasicInfoStep1_whenIncorrectInput_thenError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/saveBasicInfoStep1")
            .accept(MediaType.TEXT_HTML))
            .andExpect(model().errorCount(2))
            .andExpect(view().name("error"))
            .andExpect(status().isOk())
            .andDo(print());
    }

}
