package com.baeldung.spring.cloud.client;

import com.baeldung.spring.cloud.model.Book;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.loadbalancer.support.ServiceInstanceListSuppliers;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.baeldung.spring.cloud.client.BookMocks.setupMockBooksResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.moreThan;
import static java.util.Arrays.asList;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertTrue;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@EnableFeignClients
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfig.class })
class LoadBalancerBooksClientIntegrationTest {

    @Autowired
    private WireMockServer mockBooksService;

    @Autowired
    private WireMockServer secondMockBooksService;

    @Autowired
    private BooksClient booksClient;

    @Autowired
    private LoadBalancerClientFactory clientFactory;

    @BeforeEach
    void setUp() throws IOException {
        setupMockBooksResponse(mockBooksService);
        setupMockBooksResponse(secondMockBooksService);

        String serviceId = "books-service";
        RoundRobinLoadBalancer loadBalancer = new RoundRobinLoadBalancer(ServiceInstanceListSuppliers
          .toProvider(serviceId, instance(serviceId, "localhost", 1030, false), instance(serviceId, "localhost", 1031, true)),
          serviceId, -1);
    }

    private static DefaultServiceInstance instance(String serviceId, String host, int port, boolean secure) {
        return new DefaultServiceInstance(serviceId, serviceId, host, port, secure);
    }

    @Test
    void whenGetBooks_thenRequestsAreLoadBalanced() {
        for (int k = 0; k < 10; k++) {
            booksClient.getBooks();
        }

        mockBooksService.verify(
          moreThan(0), getRequestedFor(WireMock.urlEqualTo("/books")));
        secondMockBooksService.verify(
          moreThan(0), getRequestedFor(WireMock.urlEqualTo("/books")));
    }

    @Test
    public void whenGetBooks_thenTheCorrectBooksShouldBeReturned() {
        assertTrue(booksClient.getBooks()
          .containsAll(asList(
            new Book("Dune", "Frank Herbert"),
            new Book("Foundation", "Isaac Asimov"))));
    }

    @Test
    void loadbalancerWorks() throws IOException {

        setupMockBooksResponse(mockBooksService);
        setupMockBooksResponse(secondMockBooksService);

        ReactiveLoadBalancer<ServiceInstance> reactiveLoadBalancer = this.clientFactory.getInstance("books-service",
          ReactiveLoadBalancer.class, ServiceInstance.class);

        then(reactiveLoadBalancer).isInstanceOf(RoundRobinLoadBalancer.class);
        then(reactiveLoadBalancer).isInstanceOf(ReactorLoadBalancer.class);
        ReactorLoadBalancer<ServiceInstance> loadBalancer = (ReactorLoadBalancer<ServiceInstance>) reactiveLoadBalancer;

        for (int k = 0; k < 10; k++) {
            booksClient.getBooks();
        }

        // order dependent on seedPosition -1 of RoundRobinLoadBalancer
        List<String> hosts = Arrays.asList("localhost", "localhost");

        assertLoadBalancer(loadBalancer, hosts);

        mockBooksService.verify(
          moreThan(0), getRequestedFor(WireMock.urlEqualTo("/books")));
        secondMockBooksService.verify(
          moreThan(0), getRequestedFor(WireMock.urlEqualTo("/books")));
    }

    private void assertLoadBalancer(ReactorLoadBalancer<ServiceInstance> loadBalancer, List<String> hosts) {
        for (String host : hosts) {
            Mono<Response<ServiceInstance>> source = loadBalancer.choose();
            StepVerifier.create(source).consumeNextWith(response -> {
                then(response).isNotNull();
                then(response.hasServer()).isTrue();

                ServiceInstance instance = response.getServer();
                then(instance).isNotNull();
                then(instance.getHost()).as("instance host is incorrect %s", host).isEqualTo(host);

                if (host.contains("secure")) {
                    then(instance.isSecure()).isTrue();
                }
                else {
                    then(instance.isSecure()).isFalse();
                }
            }).verifyComplete();
        }
    }
}
