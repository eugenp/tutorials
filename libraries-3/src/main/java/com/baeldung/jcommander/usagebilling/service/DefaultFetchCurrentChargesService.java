package com.baeldung.jcommander.usagebilling.service;

import com.baeldung.jcommander.usagebilling.model.CurrentChargesRequest;
import com.baeldung.jcommander.usagebilling.model.CurrentChargesResponse;
import com.baeldung.jcommander.usagebilling.model.CurrentChargesResponse.LineItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.fill;
import static java.util.Collections.emptyList;
import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;

class DefaultFetchCurrentChargesService implements FetchCurrentChargesService {

    @Override
    public CurrentChargesResponse fetch(CurrentChargesRequest request) {
        List<String> subscriptions = request.getSubscriptionIds();

        if (subscriptions == null || subscriptions.isEmpty()) {
            System.out.println("Fetching ALL charges for customer: " + request.getCustomerId());
            subscriptions = mockSubscriptions();

        } else {
            System.out.println(format("Fetching charges for customer: %s and subscriptions: %s", request.getCustomerId(), subscriptions));
        }

        CurrentChargesResponse charges = mockCharges(request.getCustomerId(), subscriptions, request.isItemized());
        System.out.println("Fetched charges...");
        return charges;
    }

    private CurrentChargesResponse mockCharges(String customerId, List<String> subscriptions, boolean itemized) {
        List<LineItem> lineItems = mockLineItems(subscriptions);
        BigDecimal amountDue = lineItems
          .stream()
          .map(li -> li.getAmount())
          .reduce(new BigDecimal("0"), BigDecimal::add);

        return CurrentChargesResponse
          .builder()
          .customerId(customerId)
          .lineItems(itemized ? lineItems : emptyList())
          .amountDue(amountDue)
          .build();
    }

    private List<LineItem> mockLineItems(List<String> subscriptions) {
        return subscriptions
          .stream()
          .map(subscription -> LineItem.builder()
            .subscriptionId(subscription)
            .quantity(current().nextInt(20))
            .amount(new BigDecimal(current().nextDouble(1_000)))
            .build())
          .collect(toList());
    }

    private List<String> mockSubscriptions() {
        String[] subscriptions = new String[5];
        fill(subscriptions, UUID.randomUUID().toString());
        return asList(subscriptions);
    }
}
