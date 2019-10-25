package com.baeldung.web.controller.redirect;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/api-servlet.xml")
@WebAppConfiguration
public class RedirectControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void whenRedirectOnUrlWithUsingXMLConfig_thenStatusRedirectionAndRedirectedOnUrl() throws Exception {
        mockMvc.perform(get("/redirectWithXMLConfig")).andExpect(status().is3xxRedirection()).andExpect(view().name("RedirectedUrl")).andExpect(model().attribute("attribute", equalTo("redirectWithXMLConfig")))
                .andExpect(redirectedUrl("redirectedUrl?attribute=redirectWithXMLConfig"));
    }

    @Test
    public void whenRedirectOnUrlWithUsingRedirectPrefix_thenStatusRedirectionAndRedirectedOnUrl() throws Exception {
        mockMvc.perform(get("/redirectWithRedirectPrefix")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/redirectedUrl")).andExpect(model().attribute("attribute", equalTo("redirectWithRedirectPrefix")))
                .andExpect(redirectedUrl("/redirectedUrl?attribute=redirectWithRedirectPrefix"));
    }

    @Test
    public void whenRedirectOnUrlWithUsingRedirectAttributes_thenStatusRedirectionAndRedirectedOnUrlAndAddedAttributeToFlashScope() throws Exception {
        mockMvc.perform(get("/redirectWithRedirectAttributes")).andExpect(status().is3xxRedirection()).andExpect(flash().attribute("flashAttribute", equalTo("redirectWithRedirectAttributes")))
                .andExpect(model().attribute("attribute", equalTo("redirectWithRedirectAttributes"))).andExpect(model().attribute("flashAttribute", equalTo(null))).andExpect(redirectedUrl("redirectedUrl?attribute=redirectWithRedirectAttributes"));
    }

    @Test
    public void whenRedirectOnUrlWithUsingRedirectView_thenStatusRedirectionAndRedirectedOnUrlAndAddedAttributeToFlashScope() throws Exception {
        mockMvc.perform(get("/redirectWithRedirectView")).andExpect(status().is3xxRedirection()).andExpect(model().attribute("attribute", equalTo("redirectWithRedirectView"))).andExpect(redirectedUrl("redirectedUrl?attribute=redirectWithRedirectView"));
    }

    @Test
    public void whenRedirectOnUrlWithUsingForwardPrefix_thenStatusOkAndForwardedOnUrl() throws Exception {
        mockMvc.perform(get("/forwardWithForwardPrefix")).andExpect(status().isOk()).andExpect(view().name("forward:/redirectedUrl")).andExpect(model().attribute("attribute", equalTo("redirectWithForwardPrefix"))).andExpect(forwardedUrl("/redirectedUrl"));
    }

}
