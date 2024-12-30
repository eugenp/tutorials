package com.baeldung.handlermappings;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.config.BeanNameUrlHandlerMappingConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = BeanNameUrlHandlerMappingConfig.class)
public class BeanNameMappingConfigIntegrationTest {

    @Autowired
    private WebApplicationContext webAppContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void whenBeanNameMapping_thenMappedOK() throws Exception {
        mockMvc.perform(get("/beanNameUrl")).andExpect(status().isOk()).andExpect(view().name("welcome")).andDo(print());
    }
}