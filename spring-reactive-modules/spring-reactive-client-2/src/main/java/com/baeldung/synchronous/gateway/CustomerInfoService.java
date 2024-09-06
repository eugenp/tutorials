package com.baeldung.synchronous.gateway;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.synchronous.system.billing.Billing;
import com.baeldung.synchronous.system.billing.BillingController;
import com.baeldung.synchronous.system.customer.Customer;
import com.baeldung.synchronous.system.customer.CustomerController;

import reactor.core.publisher.Mono;

@Service
public class CustomerInfoService {

    private WebClient webClient;

    public CustomerInfoService(ServerProperties serverProperties) {
        initializeWebClient(serverProperties.getPort());
    }

    protected void initializeWebClient(Integer port) {
        this.webClient = WebClient.create("http://localhost:" + port);
    }

    public CustomerInfo getCustomerInfo(Long customerId) {
        // enable to trigger the test failure (scenario where each method call is blocked)
        // return getCustomerInfoBlockEach(customerId);

        return getCustomerInfoBlockCombined(customerId);
    }

    private CustomerInfo getCustomerInfoBlockEach(Long customerId) {
        Customer customer = webClient.get()
            .uri(uriBuilder -> uriBuilder.path(CustomerController.PATH_CUSTOMER)
                .pathSegment(String.valueOf(customerId))
                .build())
            .retrieve()
            .onStatus(status -> status.is5xxServerError() || status.is4xxClientError(), response -> response.bodyToMono(String.class)
                .map(ApiGatewayException::new))
            .bodyToMono(Customer.class)
            .block();

        Billing billing = webClient.get()
            .uri(uriBuilder -> uriBuilder.path(BillingController.PATH_BILLING)
                .pathSegment(String.valueOf(customerId))
                .build())
            .retrieve()
            .onStatus(status -> status.is5xxServerError() || status.is4xxClientError(), response -> response.bodyToMono(String.class)
                .map(ApiGatewayException::new))
            .bodyToMono(Billing.class)
            .block();

        return new CustomerInfo(customer.getId(), customer.getName(), billing.getBalance());
    }

    private CustomerInfo getCustomerInfoBlockCombined(Long customerId) {
        Mono<Customer> customerMono = webClient.get()
            .uri(uriBuilder -> uriBuilder.path(CustomerController.PATH_CUSTOMER)
                .pathSegment(String.valueOf(customerId))
                .build())
            .retrieve()
            .onStatus(status -> status.is5xxServerError() || status.is4xxClientError(), response -> response.bodyToMono(String.class)
                .map(ApiGatewayException::new))
            .bodyToMono(Customer.class);

        Mono<Billing> billingMono = webClient.get()
            .uri(uriBuilder -> uriBuilder.path(BillingController.PATH_BILLING)
                .pathSegment(String.valueOf(customerId))
                .build())
            .retrieve()
            .onStatus(status -> status.is5xxServerError() || status.is4xxClientError(), response -> response.bodyToMono(String.class)
                .map(ApiGatewayException::new))
            .bodyToMono(Billing.class);

        return Mono.zip(customerMono, billingMono, (customer, billing) -> new CustomerInfo(customer.getId(), customer.getName(), billing.getBalance()))
            .block();
    }

}
