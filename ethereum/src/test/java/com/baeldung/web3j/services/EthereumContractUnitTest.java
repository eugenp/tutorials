package com.baeldung.web3j.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EthereumContractUnitTest {

    private Web3Service web3Service;

    @Before
    public void setup() {
        web3Service = new Web3Service();
    }

    @Test
    public void testContract() {
        String result = web3Service.fromScratchContractExample();
        assert (result instanceof String);
    }

    @Test
    public void sendTx() {
        String result = web3Service.sendTx();
        assert (result instanceof String);
    }

    @After
    public void cleanup() {
    }
}
