package com.baeldung.cloud.openfeign.completablefuturefeignclient;

import feign.FeignException;
import feign.RetryableException;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class PurchaseService {

    private final PaymentMethodClient paymentMethodClient;
    private final ReportClient reportClient;

    public PurchaseService(PaymentMethodClient paymentMethodClient, ReportClient reportClient) {
        this.paymentMethodClient = paymentMethodClient;
        this.reportClient = reportClient;
    }

    public String executePurchase(Purchase purchase) throws ExecutionException, InterruptedException {
        CompletableFuture<String> paymentMethodsFuture = CompletableFuture.supplyAsync(() -> paymentMethodClient.processPurchase(purchase.getSiteId()))
                .orTimeout(400, TimeUnit.MILLISECONDS)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof FeignException && ((FeignException) ex.getCause()).status() == 404) {
                        return "account_money";
                    }

                    if (ex.getCause() instanceof RetryableException) {
                        // handle REST timeout
                        throw new RuntimeException("REST call network timeout!");
                    }

                    if (ex instanceof TimeoutException) {
                        // handle thread timeout
                        throw new RuntimeException("Thread timeout!", ex);
                    }

                    throw new RuntimeException("Unrecoverable error!", ex);
                });

        CompletableFuture.runAsync(() -> reportClient.sendReport("Purchase Order Report"))
                .orTimeout(1, TimeUnit.SECONDS);

        return String.format("Purchase executed with payment method %s", paymentMethodsFuture.get());
    }
}
