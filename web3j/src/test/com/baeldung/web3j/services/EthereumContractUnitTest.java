package com.baeldung.web3j.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;

import static com.baeldung.web3j.constants.Constants.PLEASE_SUPPLY_REAL_DATA;

public class EthereumContractUnitTest {

    private Web3Service web3Service;

    @Before
    public void setup() {
        web3Service = new Web3Service();
    }

    @Test
    public void testContract() {
        CompletableFuture<String> result = web3Service.fromScratchContractExample();
        assert (result instanceof CompletableFuture);
    }

    @Test
    public void sendTx() {
        CompletableFuture<String> result = web3Service.sendTx();
        assert (result instanceof CompletableFuture);
    }

    @After
    public void cleanup() {
    }
}
