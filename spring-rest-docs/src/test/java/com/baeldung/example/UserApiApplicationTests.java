package com.baeldung.example;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.example.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserApiApplication.class)
@WebAppConfiguration
public class UserApiApplicationTests {

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void shouldGetUser() throws Exception {
        mockMvc.perform(get("/users/example.com/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-get-success",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("name").description("The user name"),
                                fieldWithPath("tld").description("The tld of the user"),
                                fieldWithPath("expirationDate").description("The date on which the user will expire"),
                                fieldWithPath("autorenewal").description("The flag that tells if the user will renew at the expiration date or not"))));
    }

    @Test
    public void shouldGetUserNotFound() throws Exception {
        mockMvc.perform(get("/users/notfounduser.com/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(document("user-get"));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        mockMvc.perform(put("/users/example.com/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(existingUserAsString())).andExpect(status().isOk())
                .andDo(document("user-put",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("name").description("The user name"),
                                fieldWithPath("tld").description("The tld of the user"),
                                fieldWithPath("expirationDate").description("The date on which the user will expire"),
                                fieldWithPath("autorenewal").description("The flag that tells if the user will renew at the expiration date or not"))));
    }

    @Test
    public void shouldUpdateUserNotFound() throws Exception {
        mockMvc.perform(put("/users/notfounduser.com/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(newUserAsString()))
                .andExpect(status().isNotFound())
                .andDo(document("user-put"));
    }

    @Test
    public void shouldUpdateUserBadRequest() throws Exception {
        mockMvc.perform(put("/users/example.com/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(newUserAsString()))
                .andExpect(status().isBadRequest())
                .andDo(document("user-put"));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        String content = newUserAsString();
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andDo(document("user-post"));
    }

    @Test
    public void shouldCreateUserBadRequest() throws Exception {
        String content = userWithNullUserNameAsString();
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest())
                .andDo(document("user-post"));
    }

    @Test
    public void shouldCreateUserConflict() throws Exception {
        String content = existingUserAsString();
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isConflict())
                .andDo(document("user-post"));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/example.nyc/")).andExpect(status().isNoContent())
                .andDo(document("user-delete"));
    }

    @Test
    public void shouldDeleteUserNotFound() throws Exception {
        mockMvc.perform(delete("/users/notfounduser.com/")).andExpect(status().isNotFound())
                .andDo(document("user-delete"));
    }

    private String userWithNullUserNameAsString() {
        return userAsString(null);
    }

    private String existingUserAsString() {
        return userAsString("example.com");
    }

    private String newUserAsString() {
        return userAsString("thisisanewuser.com");
    }

    private String userAsString(String userName) {
        try {
            User user = new User(userName, "com", "2016-04-01", false);
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to create user");
        }
    }
}
