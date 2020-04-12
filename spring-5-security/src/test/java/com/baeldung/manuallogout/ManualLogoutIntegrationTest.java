package com.baeldung.manuallogout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest()
public class ManualLogoutIntegrationTest {

    private static final String CLEAR_SITE_DATA_HEADER = "Clear-Site-Data";

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(value = "spring")
    @Test
    public void logoutWhenRequestPost() throws Exception {

        MockHttpSession session = new MockHttpSession();
        Cookie randomCookie = new Cookie("customerName", "myName");

        session.setAttribute("att", "attvalue");
        this.mockMvc.perform(post("/basiclogout").secure(true).with(csrf()).session(session).cookie(randomCookie))
                .andExpect(status().is3xxRedirection())
                .andExpect(unauthenticated())
                .andReturn();
    }

    @WithMockUser(value = "spring")
    @Test
    public void logoutWithClearDataSiteHeaderWhenRequestPost() throws Exception {

        this.mockMvc.perform(post("/csdlogout").secure(true).with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().exists(CLEAR_SITE_DATA_HEADER))
                .andReturn();
    }
}