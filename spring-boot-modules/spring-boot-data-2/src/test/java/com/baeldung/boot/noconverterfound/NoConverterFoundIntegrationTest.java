package com.baeldung.boot.noconverterfound;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.boot.noconverterfound.controller.StudentRestController;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentRestController.class)
public class NoConverterFoundIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGettersNotDefined_thenThrowException() throws Exception {

        String url = "/api/student/1";

        this.mockMvc.perform(get(url))
          .andExpect(status().isInternalServerError())
          .andExpect(result -> assertThat(result.getResolvedException())
            .isInstanceOf(HttpMessageNotWritableException.class))
          .andExpect(result -> assertThat(result.getResolvedException().getMessage())
            .contains("No converter found for return value of type"));

    }

    @Test
    public void whenGettersAreDefined_thenReturnObject() throws Exception {

        String url = "/api/student/2";

        this.mockMvc.perform(get(url))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.firstName").value("John"));

    }

}
