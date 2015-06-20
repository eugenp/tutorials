package org.baeldung.caching.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@CacheConfig("addressDemo")
public class CustomerDataService {

    @Autowired
    private CacheManager cacheManager;

    /**
     * The method returns the customer's address,
       only it doesn't find it the cache- addresses and directory.
     *
     * @param customer the customer
     * @return the address
     */
    @Cacheable("addresses", “directory”)
    public String getAddress1(Customer customer) {
        return customer.getAddress();
    }

    /**
     * The method returns the customer's address,
        but refreshes all the entries in the cache to load new ones.
     *
     * @param customer the customer
     * @return the address
     */
    @CacheEvict(value="addresses", allEntries=true)
    public String getAddress2(Customer customer) {
        return customer.getAddress();
    }

    /**
     * The method returns the customer's address,
        but not before selectively evicting the cache as per specified paramters.
     *
     * @param customer the customer
     * @return the address
     */
    @Caching(evict = { @CacheEvict("addresses"), @CacheEvict(value="directory", key="customer.name") })
    public String getAddress3(Customer customer) {
        return customer.getAddress();
    }

    /**
     * The method uses the class level cache to look up for entries.
     *
     * @param customer the customer
     * @return the address
     */
    @Cacheable // parameter not required as we have declared it using @CacheConfig
    public String getAddress4(Customer customer) {
        return customer.getAddress();
    }

    /**
     * The method selectively caches the results that meet the predefined criteria.
     *
     * @param customer the customer
     * @return the address
     */
    @CachePut(value="addresses", condition=”#customer.name=’Tom’”)
    @CachePut(value="addresses", unless=”#result.length>64”)
    public String getAddress5(Customer customer) {
        return customer.getAddress();
    }
}
