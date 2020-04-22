package com.baeldung.thymeleaf.currencies;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class CurrenciesControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCallExchangeWithSpanishLocale_ThenReturnCurrenciesTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/exchange")
                .header("Accept-Language", "es-ES")
                .param("amount", "10032.5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("10.032,50 €")))
                .andExpect(content().string(containsString("10,032.50")))
                .andExpect(content().string(containsString("10.032,50 €")));
    }

    @Test
    public void whenCallExchangeWithUSALocale_ThenReturnCurrenciesTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/exchange")
                .header("Accept-Language", "en-US")
                .param("amount", "10032.5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("$10,032.50")))
                .andExpect(content().string(containsString("10,032.50")))
                .andExpect(content().string(containsString("$10,032.50")));
    }

    @Test
    public void whenCallExchangeWithRomanianLocale_ThenReturnCurrenciesTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/exchange")
                .header("Accept-Language", "ro-RO")
                .param("amount", "10032.5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("10.032,50 RON")))
                .andExpect(content().string(containsString("10,032.50")))
                .andExpect(content().string(containsString("10.032,50 RON")));
    }

    @Test
    public void whenCallExchangeWithUSALocaleWithoutDecimal_ThenReturnCurrenciesTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/exchange")
                .header("Accept-Language", "en-US")
                .param("amount", "10032"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("$10,032")));    }

}
