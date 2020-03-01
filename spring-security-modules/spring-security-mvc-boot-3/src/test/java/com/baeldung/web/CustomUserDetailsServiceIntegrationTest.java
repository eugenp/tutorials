package com.baeldung.web;

import org.apache.http.HttpHeaders;
import com.baeldung.custom.Application;
import com.baeldung.custom.persistence.model.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = { Application.class })
@AutoConfigureMockMvc
public class CustomUserDetailsServiceIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithUserDetails("john")
    public void givenUserWithReadPermissions_whenRequestUserInfo_thenRetrieveUserData() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/user").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.user.privileges[0].name").value("FOO_READ_PRIVILEGE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.user.organization.name").value("FirstOrg"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value("john"));
    }

    @Test
    @WithUserDetails("tom")
    public void givenUserWithWritePermissions_whenRequestUserInfo_thenRetrieveUserData() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/user").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.user.privileges").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.user.organization.name").value("SecondOrg"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value("tom"));
    }

    @Test
    @WithUserDetails("john")
    public void givenUserWithReadPermissions_whenRequestFoo_thenRetrieveSampleFoo() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/foos/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sample"));
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenRequestFoo_thenRetrieveUnauthorized() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/foos/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithUserDetails("john")
    public void givenUserWithReadPermissions_whenCreateNewFoo_thenForbiddenStatusRetrieved() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/foos").with(SecurityMockMvcRequestPostProcessors.csrf())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .content(asJsonString(new Foo())))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithUserDetails("tom")
    public void givenUserWithWritePermissions_whenCreateNewFoo_thenOkStatusRetrieved() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/foos").with(SecurityMockMvcRequestPostProcessors.csrf())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .content(asJsonString(new Foo())))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    private static String asJsonString(final Object obj) throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);
        return jsonContent;
    }

}
