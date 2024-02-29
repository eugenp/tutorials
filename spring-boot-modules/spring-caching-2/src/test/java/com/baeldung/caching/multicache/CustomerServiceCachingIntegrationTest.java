package com.baeldung.caching.multicache;

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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    void givenCustomerIsPresentInDb_whenFindCustomerById_thenCustomerReturnedFromDb_And_Cached() {
        Customer customer = new Customer("100", "test", "test@mail.com");
        given(customerRepository.getCustomerById("100"))
                .willReturn(customer);

        Customer customerCacheMiss = customerService.getCustomer("100");

        assertThat(customerCacheMiss).isEqualTo(customer);
        verify(customerRepository, times(1)).getCustomerById("100");
        assertThat(customerFromRedisCache("100")).isEqualTo(customer);
        assertThat(customerFromCaffeineCache("100")).isEqualTo(customer);
    }

    @Test
    void givenCustomerIsPresentInDb_whenFindCustomerById_CalledTwice_thenCustomerReturnedFromDb_And_Cached() {
        Customer customer = new Customer("101", "test", "test@mail.com");
        given(customerRepository.getCustomerById("101"))
                .willReturn(customer);

        Customer customerCacheMiss = customerService.getCustomer("101");
        Customer customerCacheHit = customerService.getCustomer("101");

        assertThat(customerCacheMiss).isEqualTo(customer);
        assertThat(customerCacheHit).isEqualTo(customer);

        verify(customerRepository, times(1)).getCustomerById("101");
        assertThat(customerFromRedisCache("101")).isEqualTo(customer);
        assertThat(customerFromCaffeineCache("101")).isEqualTo(customer);
    }

    @Test
    void givenCustomerIsPresentInDb_whenFindCustomerById_CalledThrice_thenCustomerReturnedFromDBFirst_ThenFromCache() throws InterruptedException {
        Customer customer = new Customer("102", "test", "test@mail.com");
        given(customerRepository.getCustomerById("102"))
                .willReturn(customer);

        Customer customerCacheMiss = customerService.getCustomer("102");
        Customer customerCacheHit = customerService.getCustomer("102");

        TimeUnit.SECONDS.sleep(4);

        assertThat(customerFromCaffeineCache("102")).isEqualTo(null);
        Customer customerCacheHitAgain = customerService.getCustomer("102");

        verify(customerRepository, times(1)).getCustomerById("102");
        assertThat(customerCacheMiss).isEqualTo(customer);
        assertThat(customerCacheHit).isEqualTo(customer);
        assertThat(customerCacheHitAgain).isEqualTo(customer);
        assertThat(customerFromRedisCache("102")).isEqualTo(customer);
        assertThat(customerFromCaffeineCache("102")).isEqualTo(customer);
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

        public EmbeddedRedisConfiguration() {
            this.redisServer = new RedisServer();
        }

        @PostConstruct
        public void startRedis() {
            redisServer.start();
        }

        @PreDestroy
        public void stopRedis() {
            this.redisServer.stop();
        }
    }
}