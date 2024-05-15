package com.baeldung.security.csrf;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baeldung.security.spring.SecurityWithCsrfCookieConfig;
import com.baeldung.spring.MvcConfig;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = { SecurityWithCsrfCookieConfig.class, MvcConfig.class })
public class CsrfCookieEnabledIntegrationTest extends CsrfAbstractIntegrationTest {

    @Test
    public void givenNoCsrf_whenAddFoo_thenForbidden() throws Exception {
        // @formatter:off
        mvc
        .perform(post("/auth/foos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createFoo())
        .with(testUser()))
        .andExpect(status().isForbidden());
        // @formatter:on
    }

    @Test
    public void givenCsrf_whenAddFoo_thenCreated() throws Exception {
        // @formatter:off
        mvc
        .perform(post("/auth/foos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createFoo())
        .with(testUser())
        .with(csrf()))
        .andExpect(status().isCreated());
        // @formatter:on
    }

}
