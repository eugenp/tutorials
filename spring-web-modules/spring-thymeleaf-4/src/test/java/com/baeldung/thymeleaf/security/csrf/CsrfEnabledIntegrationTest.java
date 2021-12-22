package com.baeldung.thymeleaf.security.csrf;

import com.baeldung.thymeleaf.config.InitSecurity;
import com.baeldung.thymeleaf.config.WebApp;
import com.baeldung.thymeleaf.config.WebMVCConfig;
import com.baeldung.thymeleaf.config.WebMVCSecurity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebApp.class, WebMVCConfig.class, WebMVCSecurity.class, InitSecurity.class })
public class CsrfEnabledIntegrationTest {

    @Autowired
    WebApplicationContext wac;
    @Autowired
    MockHttpSession session;

    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;

    private RequestPostProcessor testUser() {
        return user("user1").password("user1Pass").roles("USER");
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(springSecurityFilterChain).build();
    }

    @Test
    public void addStudentWithoutCSRF() throws Exception {
        mockMvc.perform(post("/saveStudent").contentType(MediaType.APPLICATION_JSON).param("id", "1234567").param("name", "Joe").param("gender", "M").with(testUser())).andExpect(status().isForbidden());
    }

    @Test
    public void addStudentWithCSRF() throws Exception {
        mockMvc.perform(post("/saveStudent").contentType(MediaType.APPLICATION_JSON).param("id", "1234567").param("name", "Joe").param("gender", "M").with(testUser()).with(csrf())).andExpect(status().isOk());
    }

}
