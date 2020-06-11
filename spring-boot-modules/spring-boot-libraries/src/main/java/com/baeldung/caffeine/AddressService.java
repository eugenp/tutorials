package com.baeldung.caffeine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Service that uses caching.
 */
@Service
public class AddressService
{
    private final static Logger LOG = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private CacheManager cacheManager;

    @Cacheable(cacheNames = "addresses")
    public String getAddress(long customerId)
    {
        LOG.info("Method getAddress is invoked for customer {}", customerId);

        return "123 Main St";
    }

    public String getAddress2(long customerId)
    {
        if(cacheManager.getCache("addresses2").get(customerId) != null)
        {
            return cacheManager.getCache("addresses2").get(customerId).get().toString();
        }

        LOG.info("Method getAddress2 is invoked for customer {}", customerId);

        String address = "123 Main St";

        cacheManager.getCache("addresses2").put(customerId, address);

        return address;
    }
}
