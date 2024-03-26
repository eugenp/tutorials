package com.baeldung.caching.twolevelcache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import redis.embedded.RedisServer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Import({ CacheConfig.class,CustomerService.class })
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = { CacheAutoConfiguration.class, RedisAutoConfiguration.class })
@EnableCaching
class CustomerServiceCachingIntegrationTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CacheManager redisCacheManager;

    @Autowired
    private CacheManager caffeineCacheManager;

    @Test
    void givenCustomerIsPresent_whenGetCustomerCalled_thenReturnCustomerAndCacheIt() {
        String CUSTOMER_ID = "100";
        Customer customer = new Customer(CUSTOMER_ID, "test", "test@mail.com");

        given(customerRepository.findById(CUSTOMER_ID)).willReturn(Optional.of(customer));

        Customer customerCacheMiss = customerService.getCustomer(CUSTOMER_ID);

        assertThat(customerCacheMiss).isEqualTo(customer);
        verify(customerRepository, times(1)).findById(CUSTOMER_ID);
        assertThat(customerFromCaffeineCache(CUSTOMER_ID)).isEqualTo(customer);
        assertThat(customerFromRedisCache(CUSTOMER_ID)).isEqualTo(customer);
    }

    @Test
    void givenCustomerIsPresent_whenGetCustomerCalledTwice_thenReturnCustomerAndCacheIt() {
        String CUSTOMER_ID = "101";
        Customer customer = new Customer(CUSTOMER_ID, "test", "test@mail.com");
        given(customerRepository.findById(CUSTOMER_ID)).willReturn(Optional.of(customer));

        Customer customerCacheMiss = customerService.getCustomer(CUSTOMER_ID);
        Customer customerCacheHit = customerService.getCustomer(CUSTOMER_ID);

        assertThat(customerCacheMiss).isEqualTo(customer);
        assertThat(customerCacheHit).isEqualTo(customer);
        verify(customerRepository, times(1)).findById(CUSTOMER_ID);
        assertThat(customerFromCaffeineCache(CUSTOMER_ID)).isEqualTo(customer);
        assertThat(customerFromRedisCache(CUSTOMER_ID)).isEqualTo(customer);
    }

    @Test
    void givenCustomerIsPresent_whenGetCustomerCalledTwiceAndFirstCacheExpired_thenReturnCustomerAndCacheIt() throws InterruptedException {
        String CUSTOMER_ID = "102";
        Customer customer = new Customer(CUSTOMER_ID, "test", "test@mail.com");
        given(customerRepository.findById(CUSTOMER_ID)).willReturn(Optional.of(customer));

        Customer customerCacheMiss = customerService.getCustomer(CUSTOMER_ID);
        TimeUnit.SECONDS.sleep(3);
        assertThat(customerFromCaffeineCache(CUSTOMER_ID)).isEqualTo(null);
        Customer customerCacheHit = customerService.getCustomer(CUSTOMER_ID);

        verify(customerRepository, times(1)).findById(CUSTOMER_ID);
        assertThat(customerCacheMiss).isEqualTo(customer);
        assertThat(customerCacheHit).isEqualTo(customer);
        assertThat(customerFromCaffeineCache(CUSTOMER_ID)).isEqualTo(customer);
        assertThat(customerFromRedisCache(CUSTOMER_ID)).isEqualTo(customer);
    }

    private Object customerFromRedisCache(String key) {
        return redisCacheManager.getCache("customerCache").get(key) != null ?
          redisCacheManager.getCache("customerCache").get(key).get() : null;
    }

    private Object customerFromCaffeineCache(String key) {
        return caffeineCacheManager.getCache("customerCache").get(key) != null ?
          caffeineCacheManager.getCache("customerCache").get(key).get() : null;
    }

    @TestConfiguration
    static class EmbeddedRedisConfiguration {

        private final RedisServer redisServer;

        public EmbeddedRedisConfiguration() throws IOException {
            this.redisServer = new RedisServer();
        }

        @PostConstruct
        public void startRedis() throws IOException {
            redisServer.start();
        }

        @PreDestroy
        public void stopRedis() throws IOException {
            this.redisServer.stop();
        }
    }
}