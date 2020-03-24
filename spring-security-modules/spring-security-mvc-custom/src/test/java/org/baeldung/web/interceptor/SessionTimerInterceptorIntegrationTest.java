package org.baeldung.web.interceptor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.baeldung.security.spring.SecurityWithoutCsrfConfig;
import org.baeldung.spring.MvcConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SecurityWithoutCsrfConfig.class, MvcConfig.class })
@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
public class SessionTimerInterceptorIntegrationTest {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * After execution of HTTP GET logs from interceptor will be displayed in
     * the console
     */
    @Test
    public void testInterceptors() throws Exception {
        HttpSession session = mockMvc.perform(get("/auth/foos"))
            .andExpect(status().is2xxSuccessful())
            .andReturn()
            .getRequest()
            .getSession();
        Thread.sleep(51000);
        mockMvc.perform(get("/auth/foos").session((MockHttpSession) session))
            .andExpect(status().is2xxSuccessful());
    }

}
