package org.baeldung.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.baeldung.spring.PersistenceConfig;
import org.baeldung.spring.SecSecurityConfig;
import org.baeldung.spring.WebConfig;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = { SecSecurityConfig.class, PersistenceConfig.class, WebConfig.class })
public class CsrfDisabledIntegrationTest extends CsrfAbstractIntegrationTest {

    @Test
    public void givenNotAuth_whenAddFoo_thenUnauthorized() throws Exception {
        mvc.perform(post("/foos").contentType(MediaType.APPLICATION_JSON).content(createFoo())).andExpect(status().isUnauthorized());
    }

    @Test
    public void givenAuth_whenAddFoo_thenCreated() throws Exception {
        mvc.perform(post("/foos").contentType(MediaType.APPLICATION_JSON).content(createFoo()).with(testUser())).andExpect(status().isCreated());
    }

}
