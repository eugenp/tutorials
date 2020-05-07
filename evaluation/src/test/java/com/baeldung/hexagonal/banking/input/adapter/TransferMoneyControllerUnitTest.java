package com.baeldung.hexagonal.banking.input.adapter;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.hexagonal.banking.input.port.TransferMoneyCommand;
import com.baeldung.hexagonal.banking.input.port.TransferMoneyPort;

@WebMvcTest(controllers = TransferMoneyController.class)
class TransferMoneyControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransferMoneyPort target;

    @Test
    void testSendMoney() throws Exception {

        mockMvc.perform(post("/accounts/transfer/{sourceAccountNumber}/{targetAccountNumber}/{amount}/{pin}", 41L, 42L, 500D, 1234).header("Content-Type", "application/json"))
            .andExpect(status().isOk());

        then(target).should()
            .transferMoney(eq(new TransferMoneyCommand(41L, 42L, BigDecimal.valueOf(500D), 1234)));
    }

}