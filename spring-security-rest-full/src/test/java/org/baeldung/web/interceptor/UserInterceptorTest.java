package org.baeldung.web.interceptor;

import org.baeldung.spring.PersistenceConfig;
import org.baeldung.spring.SecurityWithoutCsrfConfig;
import org.baeldung.spring.WebConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
@ContextConfiguration(classes = { SecurityWithoutCsrfConfig.class, PersistenceConfig.class, WebConfig.class })
@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
public class UserInterceptorTest {

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
     */
    @Test
    public void testInterceptors() throws Exception {
        mockMvc.perform(get("/auth/admin")).andExpect(status().is2xxSuccessful());
    }

}
