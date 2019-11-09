package com.baeldung.web3j;

import com.baeldung.web3j.contracts.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

public class Template {

    private Logger l = LoggerFactory.getLogger(Template.class);

    private void deployContract() throws Exception{

        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/<your_token>"));

        Credentials credentials =
            WalletUtils.loadCredentials(
               "<password>",
               "/path/to/<walletfile>");

        Greeting contract = Greeting.deploy(
            web3j, credentials,
            ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT,
            "Hello blockchain world!").send();

        String contractAddress = contract.getContractAddress();
        l.debug("Smart contract deployed to address "+ contractAddress);

        l.debug("Value stored in remote smart contract: "+ contract.greet().send());

        TransactionReceipt transactionReceipt = contract.setGreeting("Well hello again").send();

        l.debug("New value stored in remote smart contract: "+ contract.greet().send());
    }
}
