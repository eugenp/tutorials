package com.baeldung.thymeleaf.methods;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.thymeleaf.config.WebApp;
import com.baeldung.thymeleaf.config.WebMVCConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebApp.class, WebMVCConfig.class })
public class MethodsControllerIntegrationTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .build();
    }

    @Test
    public void whenCallingControllerThenViewIsRendered() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/methods"))
            .andExpect(status().isOk())
            .andExpect(view().name("methods.html"))
            .andExpect(content().string(containsString("Calling an Object’s Method From Thymeleaf")));
    }

    @Test
    public void whenCallingGetterThenValueIsRendered() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/methods"))
            .andExpect(status().isOk())
            .andExpect(view().name("methods.html"))
            .andExpect(content().string(containsString("getName = Baeldung")));
    }

    @Test
    public void whenCallingModelMethodThenValueIsRendered() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/methods"))
            .andExpect(status().isOk())
            .andExpect(view().name("methods.html"))
            .andExpect(content().string(containsString("buildUppercaseName = BAELDUNG")))
            .andExpect(content().string(containsString("getNameSubstring = ldung")));
    }

    @Test
    public void whenCallingStaticMethodThenValueIsRendered() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/methods"))
            .andExpect(status().isOk())
            .andExpect(view().name("methods.html"))
            .andExpect(content().string(containsString("defaultDateFormat = YYYY-MM-DD hh:mm:ss")))
            .andExpect(content().string(containsString("formatNow = 2026-01-29 12:34:56")));
    }

    @Test
    public void whenCallingSpringBeanMethodThenValueIsRendered() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/methods"))
            .andExpect(status().isOk())
            .andExpect(view().name("methods.html"))
            .andExpect(content().string(containsString("methodsBean = Hello, Baeldung!")));
    }

}
