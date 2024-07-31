package com.baeldung.bitcoinj;

import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateWalletUnitTest {

    Logger logger = LoggerFactory.getLogger(CreateWalletUnitTest.class);

    @Test
    void givenCreateWalletInstance_whenCreateWalletCalled_thenNoExceptionThrown() {
        CreateWallet createWallet = new CreateWallet();
        assertDoesNotThrow(createWallet::createWallet);
    }

    @Test
    void givenCreateWalletInstance_whenCreateEncryptedWalletCalled_thenNoExceptionThrown() {
        CreateWallet createWallet = new CreateWallet();
        assertDoesNotThrow(createWallet::createEncryptedWallet);
    }

    @Test
    void givenCreateWalletInstance_whenLoadWalletCalled_thenNoExceptionThrown() {
        CreateWallet createWallet = new CreateWallet();
        assertDoesNotThrow(createWallet::loadWallet);
    }

    @Test
    void givenCreateWalletInstance_whenLoadWalletCalled_thenReturnTrue() throws UnreadableWalletException, IOException {
        CreateWallet createWallet = new CreateWallet();
        assertTrue(createWallet.loadEncryptedWallet()
            .isEncrypted());
    }

    @Test
    void givenCreateWalletInstance_whenLoadDecryptedWalletCalled_thenReturnFalse() throws UnreadableWalletException, IOException {
        CreateWallet createWallet = new CreateWallet();
        assertFalse(createWallet.loadDecryptedWallet()
            .isEncrypted());
    }

    @Test
    void givenCreateWalletInstanceAndSeed_whenLoadUsingSeedCalled_thenWalletAddressMatchesExpected() throws UnreadableWalletException {
        CreateWallet createWallet = new CreateWallet();
        Wallet wallet = createWallet.loadUsingSeed("sad puzzle wrestle despair remain husband oval weird library guess fee avoid");
        assertEquals("n1vb1YZXyMQxvEjkc53VULi5KTiRtcAA9G", wallet.currentReceiveAddress()
            .toString());
        logger.info("Wallet balance: " + wallet.getBalance()
            .toFriendlyString());
    }

}