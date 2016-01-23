package org.baeldung.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.baeldung.spring.ClientWebConfig;
import org.baeldung.spring.SecurityJavaConfig;
import org.baeldung.spring.SwaggerConfig;
import org.baeldung.spring.WebConfig;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = { SecurityJavaConfig.class, ClientWebConfig.class, WebConfig.class, SwaggerConfig.class })
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
