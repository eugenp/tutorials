package com.baeldung.web.interceptor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baeldung.security.spring.SecurityWithoutCsrfConfig;
import com.baeldung.spring.MvcConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SecurityWithoutCsrfConfig.class, MvcConfig.class })
public class LoggerInterceptorIntegrationTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    MockHttpSession session;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * After execution of HTTP GET logs from interceptor will be displayed in
     * the console
     *
     * @throws Exception
     */
    @Test
    public void testInterceptors() throws Exception {
        mockMvc.perform(get("/login.html"))
            .andExpect(status().isOk());
    }

}
