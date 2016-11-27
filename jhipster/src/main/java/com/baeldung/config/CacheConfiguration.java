package com.baeldung.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ehcache.InstrumentedEhcache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.PluralAttribute;
import java.util.Set;
import java.util.SortedSet;

@SuppressWarnings("unused") @Configuration @EnableCaching @AutoConfigureAfter(value = { MetricsConfiguration.class, DatabaseConfiguration.class }) public class CacheConfiguration {

    private final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

    @PersistenceContext private EntityManager entityManager;

    @Inject private MetricRegistry metricRegistry;

    private net.sf.ehcache.CacheManager cacheManager;

    @PreDestroy public void destroy() {
        log.info("Remove Cache Manager metrics");
        SortedSet<String> names = metricRegistry.getNames();
        names.forEach(metricRegistry::remove);
        log.info("Closing Cache Manager");
        cacheManager.shutdown();
    }

    @Bean public CacheManager cacheManager(JHipsterProperties jHipsterProperties) {
        log.debug("Starting Ehcache");
        cacheManager = net.sf.ehcache.CacheManager.create();
        cacheManager.getConfiguration().setMaxBytesLocalHeap(jHipsterProperties.getCache().getEhcache().getMaxBytesLocalHeap());
        log.debug("Registering Ehcache Metrics gauges");
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        for (EntityType<?> entity : entities) {
            String name = entity.getName();
            if (name == null || entity.getJavaType() != null) {
                name = entity.getJavaType().getName();
            }
            Assert.notNull(name, "entity cannot exist without an identifier");
            reconfigureCache(name, jHipsterProperties);
            for (PluralAttribute pluralAttribute : entity.getPluralAttributes()) {
                reconfigureCache(name + "." + pluralAttribute.getName(), jHipsterProperties);
            }
        }
        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    private void reconfigureCache(String name, JHipsterProperties jHipsterProperties) {
        net.sf.ehcache.Cache cache = cacheManager.getCache(name);
        if (cache != null) {
            cache.getCacheConfiguration().setTimeToLiveSeconds(jHipsterProperties.getCache().getTimeToLiveSeconds());
            net.sf.ehcache.Ehcache decoratedCache = InstrumentedEhcache.instrument(metricRegistry, cache);
            cacheManager.replaceCacheWithDecoratedCache(cache, decoratedCache);
        }
    }

}
