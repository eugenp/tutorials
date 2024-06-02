package com.baeldung.bitcoinj;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Utils;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class CreateWallet {

    Logger logger = LoggerFactory.getLogger(CreateWallet.class);

    NetworkParameters params = TestNet3Params.get();

    public void createWallet() throws IOException {
        Wallet wallet = Wallet.createDeterministic(params, Script.ScriptType.P2PKH);
        File walletFile = new File("baeldung.dat");
        wallet.saveToFile(walletFile);
    }

    public void createEncryptedWallet() throws IOException {
        Wallet wallet = Wallet.createDeterministic(params, Script.ScriptType.P2PKH);
        File walletFile = new File("baeldungencrypted.dat");
        wallet.encrypt("password");
        wallet.saveToFile(walletFile);
    }

    public Wallet loadEncryptedWallet() throws IOException, UnreadableWalletException {
        File walletFile = new File("baeldungencrypted.dat");
        Wallet wallet = Wallet.loadFromFile(walletFile);
        logger.info("Address: " + wallet.currentReceiveAddress().toString());
        logger.info("Seed Phrase: " + wallet.getKeyChainSeed().getMnemonicString());
        logger.info("Balance: " + wallet.getBalance().toFriendlyString());
        logger.info("Public Key: " + wallet.findKeyFromAddress(wallet.currentReceiveAddress()).getPublicKeyAsHex());
        return wallet;
    }

    public Wallet loadDecryptedWallet() throws IOException, UnreadableWalletException {
        File walletFile = new File("baeldungencrypted.dat");
        Wallet wallet = Wallet.loadFromFile(walletFile);
        wallet.decrypt("password");
        return wallet;
    }

    public Wallet loadWallet() throws IOException, UnreadableWalletException {
        File walletFile = new File("baeldung.dat");
        Wallet wallet = Wallet.loadFromFile(walletFile);
        logger.info("Address: " + wallet.currentReceiveAddress().toString());
        logger.info("Seed Phrase: " + wallet.getKeyChainSeed().getMnemonicString());
        logger.info("Balance: " + wallet.getBalance().toFriendlyString());
        logger.info("Public Key: " + wallet.findKeyFromAddress(wallet.currentReceiveAddress()).getPublicKeyAsHex());
        logger.info("Private Key: " + wallet.findKeyFromAddress(wallet.currentReceiveAddress()).getPrivateKeyAsHex());
        return wallet;
    }

    public Wallet loadUsingSeed(String seedWord) throws UnreadableWalletException {
        DeterministicSeed seed = new DeterministicSeed(seedWord, null, "", Utils.currentTimeSeconds());
        return Wallet.fromSeed(params, seed);
    }

    public void connectWalletToPeer() throws BlockStoreException, UnreadableWalletException, IOException {
        Wallet wallet = loadWallet();
        BlockStore blockStore = new MemoryBlockStore(params);
        BlockChain chain = new BlockChain(params, wallet, blockStore);
        PeerGroup peerGroup = new PeerGroup(params, chain);
        peerGroup.addPeerDiscovery(new DnsDiscovery(params));
        peerGroup.addWallet(wallet);

        wallet.addCoinsReceivedEventListener((Wallet w, Transaction tx, Coin prevBalance, Coin newBalance) -> {
            logger.info("Received tx for " + tx.getValueSentToMe(wallet));
            logger.info("New balance: " + newBalance.toFriendlyString());
        });

        peerGroup.start();
        peerGroup.downloadBlockChain();
        wallet.saveToFile(new File("baeldung.dat"));

        logger.info("Wallet balance: " + wallet.getBalance()
            .toFriendlyString());
    }

}
