package com.baeldung.permitallanonymous;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SecuredEcommerceApplication.class)
@AutoConfigureMockMvc
public class SecureEcommerceApplicationUnitTest {
    @Autowired
    private MockMvc mockMvc;
    private static final Logger logger = LoggerFactory.getLogger(SecureEcommerceApplicationUnitTest.class);

    @WithAnonymousUser
    @Test
    public void givenAnonymousUser_whenAccessToUserRegisterPage_thenAllowAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/public/registerUser"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Register User"));
    }

    @WithMockUser(username = "spring", password = "secret")
    @Test
    public void givenAuthenticatedUser_whenAccessToUserRegisterPage_thenDenyAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/public/registerUser"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser(username = "spring", password = "secret")
    @Test
    public void givenAuthenticatedUser_whenAccessToProductLinePage_thenAllowAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/public/showProducts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("List Products"));
    }

    @WithAnonymousUser
    @Test
    public void givenAnonymousUser_whenAccessToProductLinePage_thenAllowAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/public/showProducts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("List Products"));
    }

    @WithMockUser(username = "spring", password = "secret")
    @Test
    public void givenAuthenticatedUser_whenAccessToCartPage_thenAllowAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/private/showCart"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Show Cart"));
    }

    @WithAnonymousUser
    @Test
    public void givenAnonymousUser_whenAccessToCartPage_thenDenyAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/private/showCart"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
