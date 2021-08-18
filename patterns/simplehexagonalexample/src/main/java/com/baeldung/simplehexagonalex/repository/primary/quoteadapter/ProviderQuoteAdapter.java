package com.baeldung.simplehexagonalex.repository.primary.quoteadapter;

import java.net.URI;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.baeldung.simplehexagonalex.domain.QuoteOfTheDay;
import com.baeldung.simplehexagonalex.domain.repository.QuoteOfTheDayFromProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("providerQuoteAdapter")
@Primary
public class ProviderQuoteAdapter implements QuoteOfTheDayFromProvider {

    @Autowired
    private Environment env;

    @Override
    public QuoteOfTheDay getQuote() {

        QuoteOfTheDay quoteOfTheDay = new QuoteOfTheDay();
        ProviderQuoteEnvelope providerQuote;
        try {
            providerQuote = getProviderQuote();
            quoteOfTheDay.setQuote(providerQuote.getContents()
                .getQuotes()
                .get(0)
                .getQuote());
            quoteOfTheDay.setProvider(providerQuote.getCopyright()
                .getUrl());
        } catch (Exception e) {
            quoteOfTheDay.setQuote("Unable to get the quote");
            quoteOfTheDay.setProvider("none");
        }
        return quoteOfTheDay;
    }

    private ProviderQuoteEnvelope getProviderQuote() throws Exception {

        HttpGet request = new HttpGet(URI.create(env.getProperty("theysayso.quote.provider.url")));
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(request);
        ProviderQuoteEnvelope providersQuote = new ObjectMapper().readValue(response.getEntity()
            .getContent(), ProviderQuoteEnvelope.class);
        return providersQuote;
    }
}
