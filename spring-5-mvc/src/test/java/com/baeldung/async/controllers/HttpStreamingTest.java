package com.baeldung.async.controllers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.baeldung.async.Spring5Async;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5Async.class)
public class HttpStreamingTest {

    private MockMvc mockMvc;

    @Autowired
    AsyncProcessingController asyncController;

    @Autowired
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    @Autowired
    StringHttpMessageConverter stringHttpMessageConverter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(asyncController)
                .setMessageConverters(this.jackson2HttpMessageConverter, this.stringHttpMessageConverter)
                .build();
    }

    @Test
    public void whenCallResBodyEmitter_thenReturnStringValue() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/resBodyEmitter")).andReturn();

        mockMvc
          .perform(asyncDispatch(result))
          .andExpect(status().isOk())
          .andExpect(content().string(EXPECTED_RES_BODY_RESPONSE));
    }

    @Test
    public void whenCallResEntityResBodyEmitter_thenReturnStringValue() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/resEntityResBodyEmitter")).andReturn();

        mockMvc
          .perform(asyncDispatch(result))
          .andExpect(status().isOk())
          .andExpect(content().string(EXPECTED_RES_BODY_RESPONSE));
    }

    @Test
    public void whenCallSseEmitter_thenReturnStringValue() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/sseEmitter")).andReturn();

        mockMvc
          .perform(asyncDispatch(result))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.parseMediaType("text/event-stream;charset=UTF-8")));

        String[] streamResponse = result.getResponse().getContentAsString().split("\\n\\n");

        assertEquals(EXPECTED_SSE_RESPONSE_0, streamResponse[0]);
        assertEquals(EXPECTED_SSE_RESPONSE_1, streamResponse[1]);
    }
    
    @Test
    public void whenCallResEntitySseEmitter_thenReturnStringValue() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/resEntitySseEmitter")).andReturn();

        mockMvc
          .perform(asyncDispatch(result))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.parseMediaType("text/event-stream;charset=UTF-8")));

        String[] streamResponse = result.getResponse().getContentAsString().split("\\n\\n");

        assertEquals(EXPECTED_SSE_RESPONSE_0, streamResponse[0]);
        assertEquals(EXPECTED_SSE_RESPONSE_1, streamResponse[1]);
    }
    
    @Test
    public void whenCallStreamingResponseBody_thenReturnStringValue() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/streamingResponseBody")).andReturn();

        mockMvc
          .perform(asyncDispatch(result))
          .andExpect(status().isOk())
          .andExpect(content().bytes("StreamingResponseBody".getBytes()));
    }
    
    @Test
    public void whenCallResStreamingResponseBody_thenReturnFile() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/resStreamingResponseBody")).andReturn();

        mockMvc
          .perform(asyncDispatch(result))
          .andExpect(status().isOk())
          .andExpect(header().string("Content-Disposition", "form-data; name=\"filename\"; filename=\"streamingResponseBody.txt\""))
          .andExpect(content().contentType(MediaType.valueOf("application/force-download")))
          .andExpect(content().bytes("StreamingResponseBody".getBytes()));
    }
    
    static final String EXPECTED_RES_BODY_RESPONSE = "{\"status\":\"OK\",\"value\":0}{\"status\":\"OK\",\"value\":1}";
    static final String EXPECTED_SSE_RESPONSE_0 = "data:{\"status\":\"OK\",\"value\":0}";
    static final String EXPECTED_SSE_RESPONSE_1 = "data:{\"status\":\"OK\",\"value\":1}";

}
