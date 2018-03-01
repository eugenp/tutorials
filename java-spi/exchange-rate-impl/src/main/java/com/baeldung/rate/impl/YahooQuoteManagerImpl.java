package com.baeldung.rate.impl;

import com.baeldung.rate.api.Quote;
import com.baeldung.rate.api.QuoteManager;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class YahooQuoteManagerImpl implements QuoteManager {

    static final String URL_PROVIDER = "https://query1.finance.yahoo.com/v7/finance/quote";
    OkHttpClient client = new OkHttpClient();

    @Override
    public List<Quote> getQuotes(String baseCurrency, LocalDate date) {
        List<Quote> quotes = new ArrayList<>();

        String response = doGetRequest();
        System.out.println(response);
        return map(response);
    }

    private List<Quote> map(String response) {
        return new ArrayList<>();
    }

    String doGetRequest() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL_PROVIDER).newBuilder();
        //urlBuilder.addQueryParameter("v", "1.0");
        urlBuilder.addQueryParameter("symbols", "madusd=x");
        urlBuilder.addQueryParameter("symbols", "USDEUR=x");

        String fullUrl = urlBuilder.build().toString();
        System.out.println(fullUrl);
        Request request = new Request.Builder()
                .url(fullUrl)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
