package org.baeldung.security.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.baeldung.spring.PersistenceConfig;
import org.baeldung.spring.SecurityWithoutCsrfConfig;
import org.baeldung.spring.WebConfig;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = { SecurityWithoutCsrfConfig.class, PersistenceConfig.class, WebConfig.class })
public class CsrfDisabledIntegrationTest extends CsrfAbstractIntegrationTest {

	@Test
	public void givenNotAuth_whenAddFoo_thenUnauthorized() throws Exception {
		mvc.perform(post("/auth/foos").contentType(MediaType.APPLICATION_JSON).content(createFoo())).andExpect(status().isUnauthorized());
	}

	@Test
	public void givenAuth_whenAddFoo_thenCreated() throws Exception {
		mvc.perform(post("/auth/foos").contentType(MediaType.APPLICATION_JSON).content(createFoo()).with(testUser())).andExpect(status().isCreated());
	}

	@Test
	public void accessMainPageWithoutAuthorization() throws Exception {
		mvc.perform(get("/graph.html").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void accessOtherPages() throws Exception {
		mvc.perform(get("/auth/transfer").contentType(MediaType.APPLICATION_JSON).param("accountNo", "1").param("amount", "100"))
				.andExpect(status().isUnauthorized()); // without authorization
		mvc.perform(get("/auth/transfer").contentType(MediaType.APPLICATION_JSON).param("accountNo", "1").param("amount", "100").with(testUser()))
				.andExpect(status().isOk()); // with authorization
	}

	@Test
	public void accessAdminPage() throws Exception {
		mvc.perform(get("/auth/admin/x").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()); //without authorization
		mvc.perform(get("/auth/admin/x").contentType(MediaType.APPLICATION_JSON).with(testAdmin())).andExpect(status().isOk()); //with authorization
	}

}
