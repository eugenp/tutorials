package com.baeldung.cookiemanagement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.baeldung.cookiemanagement.config.CsvUserDetailsUtils;

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTest {

    @Autowired
    MockMvc mvc;
    Random random = new Random();

    UserDetails randomUser() throws IOException {
        List<UserDetails> list = CsvUserDetailsUtils.read(new ClassPathResource("users.csv", CsvUserDetailsUtils.class));
        return list.get(random.nextInt(list.size()));
    }

    @Test
    void givenRandomuser_whenLoggedIn_thenProtectedResourceAccessWithSession() throws Exception {
        UserDetails user = randomUser();
        String username = user.getUsername();

        MvcResult loginResult = mvc.perform(post("/login").param("username", username)
            .param("password", user.getPassword()
                .replace("{noop}", "")))
            .andExpect(status().is3xxRedirection())
            .andReturn();

        MockHttpSession session = (MockHttpSession) loginResult.getRequest()
            .getSession();

        mvc.perform(get("/secured/api/me").session(session))
            .andExpect(status().isOk())
            .andExpect(content().string(username));
    }
}
