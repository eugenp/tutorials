package com.baeldung.web3j.controllers;


import com.baeldung.web3j.config.AppConfig;
import com.baeldung.web3j.constants.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class EthereumRestControllerIntegrationTest {

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

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void preTest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
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