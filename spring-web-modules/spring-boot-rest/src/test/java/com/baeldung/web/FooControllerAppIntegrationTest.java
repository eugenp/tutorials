package com.baeldung.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baeldung.persistence.dao.IFooDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * We'll start the whole context, but not the server. We'll mock the REST calls instead.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class FooControllerAppIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IFooDao fooDao;

    @BeforeEach
    public void setup() {
        this.fooDao.deleteAll();
    }

    @Test
    public void whenFindPaginatedRequest_thenEmptyResponse() throws Exception {
        this.mockMvc.perform(get("/foos")
          .param("page", "0")
          .param("size", "2")
          .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().json("[]"));
    }

}
