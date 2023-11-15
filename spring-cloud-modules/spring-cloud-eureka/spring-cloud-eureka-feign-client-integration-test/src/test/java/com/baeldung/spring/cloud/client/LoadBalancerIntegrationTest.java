package com.baeldung.spring.cloud.client;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.loadbalancer.support.ServiceInstanceListSuppliers;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.tomakehurst.wiremock.WireMockServer;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@EnableFeignClients
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = { TestConfig.class })
class LoadBalancerIntegrationTest {

    @Autowired
    private LoadBalancerClientFactory clientFactory;

    @Autowired
    private BooksClient booksClient;

    @Autowired
    private WireMockServer mockBooksService;

    @Autowired
    private WireMockServer secondMockBooksService;

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

    @Test
    void staticConfigurationWorks() {
        String serviceId = "test-book-service";
        RoundRobinLoadBalancer loadBalancer = new RoundRobinLoadBalancer(ServiceInstanceListSuppliers
          .toProvider(serviceId, instance(serviceId, "bookservice1", 1030, false), instance(serviceId, "bookservice2", 1031, false)),
          serviceId, -1);
        assertLoadBalancer(loadBalancer, Arrays.asList("bookservice1", "bookservice2"));
    }

    private static DefaultServiceInstance instance(String serviceId, String host, int port, boolean secure) {
        return new DefaultServiceInstance(serviceId, serviceId, host, port, secure);
    }

    @EnableAutoConfiguration
    @SpringBootConfiguration(proxyBeanMethods = false)
    @LoadBalancerClients({ @LoadBalancerClient(name = "books-service", configuration = MyBooksServiceConfig.class) })
    @EnableCaching
    protected static class Config {

    }

    protected static class MyBooksServiceConfig {

        @Bean
        public RoundRobinLoadBalancer roundRobinContextLoadBalancer(LoadBalancerClientFactory clientFactory,
          Environment env) {
            String serviceId = LoadBalancerClientFactory.getName(env);
            return new RoundRobinLoadBalancer(
              clientFactory.getLazyProvider(serviceId, ServiceInstanceListSupplier.class), serviceId, -1);
        }

    }

}