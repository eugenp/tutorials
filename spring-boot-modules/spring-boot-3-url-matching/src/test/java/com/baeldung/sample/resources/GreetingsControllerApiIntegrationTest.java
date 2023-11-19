package com.baeldung.sample.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = GreetingsController.class)
class GreetingsControllerApiIntegrationTest {

    private static final String BASEURL = "/some";
    private static final String DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

    @Autowired
    MockMvc mvc;

    @Test
    public void testGreeting() throws Exception {
        mvc.perform(get(BASEURL + "/greeting").accept(DEFAULT_MEDIA_TYPE))
          .andExpect(status().isOk())
          .andExpect(content().string("Hello"));
    }

    @Test
    @Disabled("Disabled while TrailingSlashRedirectFilter is in use.")
    public void testGreetingTrailingSlash() throws Exception {
        mvc.perform(get(BASEURL + "/greeting/").accept(DEFAULT_MEDIA_TYPE))
          .andExpect(status().isOk())
          .andExpect(content().string("Hello with slash"));
    }

    @Test
    public void testGreetingTrailingSlashWithFilter() throws Exception {
        mvc.perform(get(BASEURL + "/greeting/").accept(DEFAULT_MEDIA_TYPE))
          .andExpect(status().isOk())
          .andExpect(content().string("Hello"));
    }

}
