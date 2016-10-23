package org.baeldung.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class) @ContextConfiguration @WebAppConfiguration @SpringBootTest public class LiveTest {
        @Autowired private WebApplicationContext context;

        private MockMvc mvc;

        @Before public void setup() {
                mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        }

        @Test public void givenUnauthenticatedUser_whenAccessingMainPage_thenRedirect() throws Exception {
                mvc.perform(get("/")).andExpect(status().is3xxRedirection());
        }

        @Test public void givenValidUsernameAndPassword_whenLogin_thenOK() throws Exception {
                mvc.perform(formLogin("/login").user("user").password("pass")).andExpect(authenticated());
        }

        @Test public void givenAuthenticatedAdmin_whenAccessingMainPage_thenOK() throws Exception {
                mvc.perform(get("/").with(user("admin").password("pass").roles("ADMIN"))).andExpect(status().isOk());
        }
}
