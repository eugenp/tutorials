package org.baeldung.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.baeldung.custom.Application;
import org.baeldung.custom.persistence.model.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(classes = { Application.class })
@AutoConfigureMockMvc
public class CustomUserDetailsServiceIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithUserDetails("john")
    public void givenUserWithReadPermissions_whenRequestUserInfo_thenRetrieveUserData() throws Exception {
        this.mvc.perform(get("/user"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user.privileges[0].name").value("FOO_READ_PRIVILEGE"))
            .andExpect(jsonPath("$.user.organization.name").value("FirstOrg"))
            .andExpect(jsonPath("$.user.username").value("john"));
    }
    
    @Test
    @WithUserDetails("tom")
    public void givenUserWithWritePermissions_whenRequestUserInfo_thenRetrieveUserData() throws Exception {
        this.mvc.perform(get("/user"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user.privileges[0].name").value("FOO_READ_PRIVILEGE"))
            .andExpect(jsonPath("$.user.organization.name").value("FirstOrg"))
            .andExpect(jsonPath("$.user.username").value("john"));
    }

    @Test
    @WithUserDetails("john")
    public void givenUserWithReadPermissions_whenRequestFoo_thenRetrieveSampleFoo() throws Exception {
        this.mvc.perform(get("/foos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Sample"));
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_whenRequestFoo_thenRetrieveUnauthorized() throws Exception {
        this.mvc.perform(get("/foos/1"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("john")
    public void givenUserWithReadPermissions_whenCreateNewFoo_thenForbiddenStatusRetrieved() throws Exception {
        this.mvc.perform(post("/foos").content(asJsonString(new Foo())))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("tom")
    public void givenUserWithWritePermissions_whenCreateNewFoo_thenOkStatusRetrieved() throws Exception {
        this.mvc.perform(post("/foos").content(asJsonString(new Foo())))
            .andExpect(status().isOk());
    }
    
    @Test
    public void givenUserWithWritePrivilegeAndHasPermission_whenPostFoo_thenOk() {
        Response response = givenAuth("tom", "111").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                   .body(new Foo("sample"))
                                                   .post("http://localhost:8082/foos");
        assertEquals(201, response.getStatusCode());
        assertTrue(response.asString().contains("id"));
    }
    
    private RequestSpecification givenAuth(String username, String password) {
        FormAuthConfig formAuthConfig = 
          new FormAuthConfig("http://localhost:8082/login", "username", "password");
         
        return RestAssured.given().auth().form(username, password, formAuthConfig);
    }

    private static String asJsonString(final Object obj) throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);
        return jsonContent;
    }

}
