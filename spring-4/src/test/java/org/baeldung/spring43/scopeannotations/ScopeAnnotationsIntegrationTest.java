package org.baeldung.spring43.scopeannotations;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = ScopeAnnotationsConfiguration.class)
@WebAppConfiguration
public class ScopeAnnotationsIntegrationTest extends AbstractJUnit4SpringContextTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenDifferentRequests_thenDifferentInstancesOfRequestScopedBeans() throws Exception {
        MockHttpSession session = new MockHttpSession();

        String requestScopedServiceInstanceNumber1 = this.mockMvc.perform(get("/appointments/request").session(session).accept(MediaType.ALL)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        String requestScopedServiceInstanceNumber2 = this.mockMvc.perform(get("/appointments/request").session(session).accept(MediaType.ALL)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertNotEquals(requestScopedServiceInstanceNumber1, requestScopedServiceInstanceNumber2);
    }

    @Test
    public void whenDifferentSessions_thenDifferentInstancesOfSessionScopedBeans() throws Exception {

        MockHttpSession session1 = new MockHttpSession();
        MockHttpSession session2 = new MockHttpSession();

        String sessionScopedServiceInstanceNumber1 = this.mockMvc.perform(get("/appointments/session").session(session1).accept(MediaType.ALL)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String sessionScopedServiceInstanceNumber2 = this.mockMvc.perform(get("/appointments/session").session(session1).accept(MediaType.ALL)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String sessionScopedServiceInstanceNumber3 = this.mockMvc.perform(get("/appointments/session").session(session2).accept(MediaType.ALL)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(sessionScopedServiceInstanceNumber1, sessionScopedServiceInstanceNumber2);

        assertNotEquals(sessionScopedServiceInstanceNumber1, sessionScopedServiceInstanceNumber3);

    }

    @Test
    public void whenDifferentSessionsAndRequests_thenAlwaysSingleApplicationScopedBean() throws Exception {

        MockHttpSession session1 = new MockHttpSession();
        MockHttpSession session2 = new MockHttpSession();

        String applicationScopedServiceInstanceNumber1 = this.mockMvc.perform(get("/appointments/application").session(session1).accept(MediaType.ALL)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String applicationScopedServiceInstanceNumber2 = this.mockMvc.perform(get("/appointments/application").session(session2).accept(MediaType.ALL)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(applicationScopedServiceInstanceNumber1, applicationScopedServiceInstanceNumber2);

    }

}
