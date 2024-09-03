package com.baeldung.cloud.openfeign.completablefuturefeignclient;

import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

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
                .handle((res, ex) -> {
                    if (ex.getCause() instanceof FeignException) {
                        int status = ((FeignException) ex.getCause()).status();

                        if (status == 404) {
                            return null;
                        }
                    }
                    return res;
                })
                .orTimeout(2, TimeUnit.SECONDS);

        CompletableFuture<Void> reportFuture = CompletableFuture.runAsync(() -> reportClient.sendReport("Purchase Order Report"))
                .orTimeout(1, TimeUnit.SECONDS);

        CompletableFuture.allOf(paymentMethodsFuture, reportFuture)
                .join();

        return paymentMethodsFuture.get();
    }
}
