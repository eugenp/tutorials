package com.baeldung.manuallogout;

import static org.junit.Assert.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(SimpleSecurityConfiguration.class)
public class ManualLogoutIntegrationTest {

    private static final String CLEAR_SITE_DATA_HEADER = "Clear-Site-Data";
    public static final int EXPIRY = 60 * 10;
    public static final String COOKIE_NAME = "customerName";
    public static final String COOKIE_VALUE = "myName";
    public static final String ATTRIBUTE_NAME = "att";
    public static final String ATTRIBUTE_VALUE = "attvalue";

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(value = "spring")
    @Test
    public void givenLoggedUserWhenUserLogoutThenSessionClearedAndNecessaryCookieCleared() throws Exception {

        this.mockMvc.perform(post("/basic/basiclogout").secure(true)
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(unauthenticated())
            .andReturn();
    }

    @WithMockUser(value = "spring")
    @Test
    public void givenLoggedUserWhenUserLogoutThenSessionClearedAndAllCookiesCleared() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);

        Cookie randomCookie = new Cookie(COOKIE_NAME, COOKIE_VALUE);
        randomCookie.setMaxAge(EXPIRY); // 10 minutes

        MockHttpServletRequest requestStateAfterLogout = this.mockMvc.perform(post("/cookies/cookielogout").secure(true)
            .with(csrf())
            .session(session)
            .cookie(randomCookie))
            .andExpect(status().is3xxRedirection())
            .andExpect(unauthenticated())
            .andExpect(cookie().maxAge(COOKIE_NAME, 0))
            .andReturn()
            .getRequest();

        HttpSession sessionStateAfterLogout = requestStateAfterLogout.getSession();
        assertNull(sessionStateAfterLogout.getAttribute(ATTRIBUTE_NAME));
    }

    @WithMockUser(value = "spring")
    @Test
    public void givenLoggedUserWhenUserLogoutThenClearDataSiteHeaderPresent() throws Exception {

        this.mockMvc.perform(post("/csd/csdlogout").secure(true)
            .with(csrf()))
            .andDo(print())
            .andExpect(status().is3xxRedirection())
            .andExpect(header().exists(CLEAR_SITE_DATA_HEADER))
            .andReturn();
    }

    @WithMockUser(value = "spring")
    @Test
    public void givenLoggedUserWhenUserLogoutOnRequestThenSessionCleared() throws Exception {

        this.mockMvc.perform(post("/request/logout").secure(true)
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(unauthenticated())
            .andReturn();
    }
}