package com.baeldung.caching.twolevelcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Caching(cacheable = {
        @Cacheable(cacheNames = "customerCache", cacheManager = "caffeineCacheManager"),
        @Cacheable(cacheNames = "customerCache", cacheManager = "redisCacheManager")
    })
    public Customer getCustomer(String id) {
        return customerRepository.findById(id)
            .orElseThrow(RuntimeException::new);
    }
}