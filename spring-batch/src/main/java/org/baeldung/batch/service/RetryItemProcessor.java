package org.baeldung.batch.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.baeldung.batch.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.io.IOException;

public class RetryItemProcessor implements ItemProcessor<Transaction, Transaction> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryItemProcessor.class);

    @Override
    public Transaction process(Transaction transaction) throws IOException {
        LOGGER.info("Attempting to process user with id={}", transaction.getUserId());
        HttpResponse response = fetchMoreUserDetails(transaction.getUserId());
        //parse user's age and postCode from response and update transaction
        return transaction;
    }

    private HttpResponse fetchMoreUserDetails(int id) throws IOException {
        final RequestConfig config = RequestConfig.custom().setConnectTimeout(2 * 1000).build();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        final HttpGet request = new HttpGet("http://www.baeldung.com:81/user/" + id);
        return client.execute(request);
    }
}
