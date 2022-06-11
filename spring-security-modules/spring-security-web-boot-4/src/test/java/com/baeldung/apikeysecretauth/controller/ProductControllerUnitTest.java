package com.baeldung.apikeysecretauth.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.apikeysecretauth.repository.UserKeysRepository;
import com.baeldung.apikeysecretauth.repository.model.UserData;
import com.baeldung.apikeysecretauth.repository.model.UserKeysData;
import com.baeldung.apikeysecretauth.service.ProductService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserKeysRepository mockUserKeysRepo;
    @MockBean
    private ProductService mockProductService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenCredentialsNotProvided_thenUnauthorized() throws Exception {
        this.mockMvc.perform(get("/products"))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenUnknownAuthScheme_thenUnauthorized() throws Exception {
        this.mockMvc.perform(get("/products").header(HttpHeaders.AUTHORIZATION, "bearer abcd:efgh"))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

@Test
public void whenUnknownApiKey_thenUnauthorized() throws Exception {
    when(mockUserKeysRepo.findById(anyString())).thenReturn(Optional.empty());

    this.mockMvc.perform(get("/products").header(HttpHeaders.AUTHORIZATION, "api-key abcd:efgh"))
        .andDo(print())
        .andExpect(status().isUnauthorized())
        .andExpect(content().string(containsString("Invalid API KEY")));
}

    @Test
    public void whenApiSecretNotMatch_thenUnauthorized() throws Exception {
        UserKeysData userKeys = new UserKeysData();
        userKeys.setApiSecret("");
        when(mockUserKeysRepo.findById(anyString())).thenReturn(Optional.of(userKeys));

        this.mockMvc.perform(get("/products").header(HttpHeaders.AUTHORIZATION, "api-key bogusKey:bogusSecret"))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(containsString("Invalid API SECRET")));
    }

    @Test
    public void whenAuthenticatedRequest_thenSuccess() throws Exception {
        UserData user = new UserData();
        user.setId(1L);

        UserKeysData userKeys = new UserKeysData();
        userKeys.setApiKey("bogusKey");
        userKeys.setApiSecret(passwordEncoder.encode("bogusSecret"));
        userKeys.setAppUser(user);

        when(mockUserKeysRepo.findById(anyString())).thenReturn(Optional.of(userKeys));
        when(mockProductService.getAllProducts(any())).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/products").header(HttpHeaders.AUTHORIZATION, "api-key bogusKey:bogusSecret"))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());
    }
}
