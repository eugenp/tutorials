package com.baeldung;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.Filter;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringSecurityConfig.class, WebConfig.class})
public class LogoutClearSiteDataControllerUnitTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilters(springSecurityFilterChain).build();
    }

    @Test
    void whenResponseBody_thenReturnCacheHeader() throws Exception {
        this.mockMvc
          .perform(MockMvcRequestBuilders
          .get("/baeldung/logout").secure(true))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().is(302))
          .andExpect(MockMvcResultMatchers.header()
            .string("Clear-Site-Data", "\"cache\", \"cookies\", \"storage\""));
    }

}
