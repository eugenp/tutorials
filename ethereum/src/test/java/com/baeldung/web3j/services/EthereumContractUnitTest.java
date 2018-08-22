package com.baeldung.web3j.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

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
