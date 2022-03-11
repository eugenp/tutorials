package com.baeldung.maxhttpheadersize.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.maxhttpheadersize.config.MaxHTTPHeaderSizeConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MaxHTTPHeaderSizeConfig.class)
@WebAppConfiguration
public class MaxHttpHeaderSizeControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .build();
    }

    @Test
    public void givenTokenWithLessThan8KBLegth_whenSendGetRequest_thenReturnsOK() throws Exception {
        mockMvc.perform(get("/request-header-test").contentType(MediaType.APPLICATION_JSON_VALUE)
            .with(httpBasic("user", "password"))
            .header("token", "token"))
            .andExpect(status().isOk());
    }

    @Test
    public void givenTokenIsMissingInHeader_whenSendGetRequest_thenThrowsBadRequest() throws Exception {
        mockMvc.perform(get("/request-header-test").contentType(MediaType.APPLICATION_JSON_VALUE)
            .with(httpBasic("user", "password")))
            .andExpect(status().isBadRequest());
    }

}
