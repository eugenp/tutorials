package com.baeldung.web3j.controllers;

import com.baeldung.web3j.constants.Constants;
import com.baeldung.web3j.services.Web3Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EthereumRestControllerUnitTest {

    private MockMvc mockMvc;

    private void constructAsyncTest(String endpoint) {
        try {
            MvcResult asyncResult = mockMvc
                    .perform(get(endpoint))
                    .andReturn();

            mockMvc.perform(asyncDispatch(asyncResult))
                    .andDo(print())
                    .andExpect(status().isOk());

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Mock
    private Web3Service web3Service;

    @InjectMocks
    private EthereumRestController ethereumRestController;

    @Before
    public void preTest() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ethereumRestController).build();
    }

    @Test
    public void accounts() {
        constructAsyncTest(Constants.API_ACCOUNTS);
    }

    @Test
    public void transactions() {
        constructAsyncTest(Constants.API_TRANSACTIONS);
    }

    @Test
    public void block() {
        constructAsyncTest(Constants.API_BLOCK);
    }

    @Test
    public void balance() {
        constructAsyncTest(Constants.API_BALANCE);
    }

    @After
    public void postTest() {
        mockMvc = null;
    }

}
