package com.baeldung.bitcoinj;

import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WalletKit {

    static Logger logger = LoggerFactory.getLogger(WalletKit.class);

    public static void main(String[] args) throws InsufficientMoneyException {

        NetworkParameters params = TestNet3Params.get();

        WalletAppKit kit = new WalletAppKit(params, new File("."), "baeldungkit") {
            @Override
            protected void onSetupCompleted() {
                logger.info("Wallet created and loaded successfully.");
                logger.info("Receive address: " + wallet().currentReceiveAddress());
                logger.info("Seed Phrase: " + wallet().getKeyChainSeed());
                logger.info("Balance: " + wallet().getBalance()
                    .toFriendlyString());
                logger.info("Public Key: " + wallet().findKeyFromAddress(wallet().currentReceiveAddress())
                    .getPublicKeyAsHex());
                logger.info("Private Key: " + wallet().findKeyFromAddress(wallet().currentReceiveAddress())
                    .getPrivateKeyAsHex());
                wallet().encrypt("password");

            }
        };

        kit.startAsync();
        kit.awaitRunning();
        kit.setAutoSave(true);

        kit.wallet()
            .addCoinsReceivedEventListener((wallet, tx, prevBalance, newBalance) -> {
                logger.info("-----> coins resceived: " + tx.getTxId());
                logger.info("received: " + tx.getValue(wallet));
            });
        String receiveAddress = "n1vb1YZXyMQxvEjkc53VULi5KTiRtcAA9G";
        Coin value = Coin.valueOf(200);
        final Coin amountToSend = value.subtract(Transaction.REFERENCE_DEFAULT_MIN_TX_FEE);
        final Wallet.SendResult sendResult = kit.wallet()
            .sendCoins(kit.peerGroup(), Address.fromString(params, receiveAddress), amountToSend);

        kit.wallet()
            .addCoinsSentEventListener((wallet, tx, prevBalance, newBalance) -> logger.info("new balance: " + newBalance.toFriendlyString()));

    }

}
