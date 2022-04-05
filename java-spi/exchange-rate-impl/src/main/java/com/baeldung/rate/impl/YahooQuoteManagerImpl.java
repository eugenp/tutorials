package com.baeldung.rate.impl;

import com.baeldung.rate.api.Quote;
import com.baeldung.rate.api.QuoteManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public class YahooQuoteManagerImpl implements QuoteManager {

    static final String URL_PROVIDER = "https://query1.finance.yahoo.com/v7/finance/quote";
    OkHttpClient client = new OkHttpClient();

    @Override
    public List<Quote> getQuotes(String baseCurrency, LocalDate date) {

        StringBuilder sb = new StringBuilder();
        Currency.getAvailableCurrencies().forEach(currency -> {
            if (!baseCurrency.equals(currency.getCurrencyCode())) {
                sb.append(baseCurrency).append(currency.getCurrencyCode()).append("=X").append(",");
            }
        });

        String value = "";
        try {
            value = URLEncoder.encode(sb.toString().substring(0, sb.toString().length() - 1), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String queryString = String.format("%s=%s", "symbols", value);
        String response = doGetRequest(queryString);
        System.out.println(response);
        return map(response);
    }

    private List<Quote> map(String response) {
        QuoteResponseWrapper qrw = JsonbBuilder.create().fromJson(response, QuoteResponseWrapper.class);
        return qrw.getQuoteResponse().getResult();
    }

    String doGetRequest(String queryString) {
        String fullUrl = URL_PROVIDER + "?" + queryString;

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
