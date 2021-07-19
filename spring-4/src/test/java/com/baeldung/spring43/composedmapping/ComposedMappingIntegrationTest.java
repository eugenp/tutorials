package com.baeldung.spring43.composedmapping;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.easymock.EasyMock.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = ComposedMappingConfiguration.class)
@WebAppConfiguration
public class ComposedMappingIntegrationTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private AppointmentService appointmentService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenRequestingMethodWithGetMapping_thenReceiving200Answer() throws Exception {
        this.mockMvc.perform(get("/appointments").accept(MediaType.ALL)).andExpect(status().isOk());
        verify(appointmentService);
    }

}
