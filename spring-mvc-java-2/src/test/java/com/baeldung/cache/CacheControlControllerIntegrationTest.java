package com.baeldung.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.HttpHeaders.IF_UNMODIFIED_SINCE;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, WebConfig.class})
public class CacheControlControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void whenResponseBody_thenReturnCacheHeader() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/hello/baeldung"))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.header().string("Cache-Control","max-age=60, must-revalidate, no-transform"));
    }

    @Test
    void whenViewName_thenReturnCacheHeader() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/home/baeldung"))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.header().string("Cache-Control","max-age=60, must-revalidate, no-transform"))
          .andExpect(MockMvcResultMatchers.view().name("home"));
    }

    @Test
    void whenStaticResources_thenReturnCacheHeader() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/resources/hello.css"))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.header().string("Cache-Control","max-age=60, must-revalidate, no-transform"));
    }

    @Test
    void whenInterceptor_thenReturnCacheHeader() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cache/baeldung"))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.header().string("Cache-Control","max-age=60, must-revalidate, no-transform"));
    }

    @Test
    void whenValidate_thenReturnCacheHeader() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(IF_UNMODIFIED_SINCE, "Tue, 04 Feb 2020 19:57:25 GMT");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/validate/baeldung").headers(headers))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().is(304));
    }




}
