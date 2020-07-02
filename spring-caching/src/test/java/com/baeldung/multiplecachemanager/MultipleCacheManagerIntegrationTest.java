package com.baeldung.multiplecachemanager;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.util.Assert;

import com.baeldung.multiplecachemanager.bo.OrderDetailBO;
import com.baeldung.multiplecachemanager.entity.Order;
import com.baeldung.multiplecachemanager.repository.OrderDetailRepository;

@SpringBootApplication
@SpringBootTest
public class MultipleCacheManagerIntegrationTest {

    @MockBean
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailBO orderDetailBO;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CacheManager alternateCacheManager;

    @Test
    public void givenCacheResolverIsConfigured_whenCallGetOrderDetail_thenDataShouldBeInCaffieneCacheManager() {
        Integer key = 30001;
        cacheManager.getCache("orders")
            .evict(key);
        Order order = new Order();
        order.setCustomerId(1001);
        order.setItemId(10001);
        order.setOrderId(30001);
        order.setQuantity(2);
        Mockito.when(orderDetailRepository.getOrderDetail(key))
            .thenReturn(order);
        orderDetailBO.getOrderDetail(key);
        org.springframework.cache.caffeine.CaffeineCache cache = (CaffeineCache) cacheManager.getCache("orders");
        Assert.notNull(cache.get(key)
            .get(), "caffieneCache should have had the data");
    }

    @Test
    public void givenCacheResolverIsConfigured_whenCallGetOrderPrice_thenDataShouldBeInAlternateCacheManager() {
        Integer key = 30001;
        alternateCacheManager.getCache("orderprice")
            .evict(key);
        Mockito.when(orderDetailRepository.getOrderPrice(key))
            .thenReturn(500.0);
        orderDetailBO.getOrderPrice(key);
        Cache cache = alternateCacheManager.getCache("orderprice");
        Assert.notNull(cache.get(key)
            .get(), "alternateCache should have had the data");
    }
}
