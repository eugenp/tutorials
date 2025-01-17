package com.baeldung.currency_converter.service;

import org.springframework.stereotype.Service;
import com.baeldung.currency_converter.dto.ExchangeRateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import java.time.Duration;

@Service
public class CurrencyConverterService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterService.class);

    @Value("${openexchangerates.url}")
    private String openApiUrl;

    @Value("${openexchangerates.app_id}")
    private String openApiAppId;

    @Value("${openexchangerates.base_currency}")
    public String baseCurrency;

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Double> redisTemplate;

    public CurrencyConverterService(RedisTemplate<String, Double> redisTemplate) {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
        this.redisTemplate = redisTemplate;
    }

    @Trace(metricName="CurrencyConversionCalc")
    public double getConvertedAmount(String targetCurrency, double amount) {
        String cacheKey = baseCurrency + "-" + targetCurrency;
                
        // Try to get rate from cache first
        Double cachedRate = redisTemplate.opsForValue().get(cacheKey);
        if (cachedRate != null) {
            logger.info("Cache hit for key: {}", cacheKey);
            return amount * cachedRate;
        } else {
            logger.info("Cache miss for key: {}, fetching from API", cacheKey);
            NewRelic.incrementCounter("Custom/CacheMisses");
        }
        
        // Original API call logic
        String url = String.format("%s/latest.json?app_id=%s", openApiUrl, openApiAppId);
        logger.info("Fetching exchange rates from {}", url);
        
        try {            
            Request request = new Request.Builder()
                .url(url)
                .build();

            long startTime = System.nanoTime();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("Unexpected response code: " + response);
                }

                long durationInMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                NewRelic.addCustomParameter("RatesAPI/ResponseTime", durationInMillis);


                String rawResponse = response.body().string();
                ExchangeRateResponse exchangeResponse = objectMapper.readValue(rawResponse, ExchangeRateResponse.class);

                logger.info("Response headers:");
                response.headers().toMultimap().forEach((k, v) -> logger.info("{}: {}", k, v));

                String eTagHeaderField = response.header("ETag");
                logger.info(eTagHeaderField);

                NewRelic.addCustomParameter(cacheKey, eTagHeaderField);

                if (exchangeResponse != null && exchangeResponse.getRates() != null) {
                    Map<String, Double> rates = exchangeResponse.getRates();
                    Double targetRate = rates.get(targetCurrency);

                    if (targetRate == null) {
                        logger.error("Target currency {} not found in rates", targetCurrency);
                        throw new IllegalArgumentException("Target currency not found in rates.");
                    }

                    // Cache the rate before returning
                    redisTemplate.opsForValue().set(cacheKey, targetRate, Duration.ofHours(24));
                    logger.info("Cached exchange rate for key: {}", cacheKey);

                    return amount * targetRate;
                }

                logger.error("Failed to get exchange rate response");
                throw new RuntimeException("Could not retrieve exchange rate.");
            }
        } catch (Exception e) {
            logger.error("Error converting currency: {}", e.getMessage(), e);
            NewRelic.noticeError(e);
            throw new RuntimeException("Error converting currency", e);
        }
    }
}
