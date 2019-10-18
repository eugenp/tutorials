package com.baeldung.infinispan;

import com.baeldung.infinispan.listener.CacheListener;
import com.baeldung.infinispan.repository.HelloWorldRepository;
import com.baeldung.infinispan.service.HelloWorldService;
import com.baeldung.infinispan.service.TransactionalService;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import java.util.function.Supplier;

@Ignore
public abstract class AbstractIntegrationTest {

    private DefaultCacheManager cacheManager;

    private HelloWorldRepository repository = new HelloWorldRepository();

    protected HelloWorldService helloWorldService;
    protected TransactionalService transactionalService;

    @Before
    public void setup() {
        CacheConfiguration configuration = new CacheConfiguration();
        CacheListener listener = new CacheListener();

        cacheManager = configuration.cacheManager();

        Cache<String, Integer> transactionalCache = configuration.transactionalCache(cacheManager, listener);

        Cache<String, String> simpleHelloWorldCache = configuration.simpleHelloWorldCache(cacheManager, listener);

        Cache<String, String> expiringHelloWorldCache = configuration.expiringHelloWorldCache(cacheManager, listener);

        Cache<String, String> evictingHelloWorldCache = configuration.evictingHelloWorldCache(cacheManager, listener);

        Cache<String, String> passivatingHelloWorldCache = configuration.passivatingHelloWorldCache(cacheManager, listener);

        this.helloWorldService = new HelloWorldService(repository, listener, simpleHelloWorldCache, expiringHelloWorldCache, evictingHelloWorldCache, passivatingHelloWorldCache);

        this.transactionalService = new TransactionalService(transactionalCache);
    }

    @After
    public void tearDown() {
        cacheManager.stop();
    }

    protected <T> long timeThis(Supplier<T> supplier) {
        long millis = System.currentTimeMillis();
        supplier.get();
        return System.currentTimeMillis() - millis;
    }
}
