package com.gateway.config;

import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MaxSizeConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.annotation.PreDestroy;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

    private final Environment env;

    private final DiscoveryClient discoveryClient;

    private final ServerProperties serverProperties;

    public CacheConfiguration(Environment env, DiscoveryClient discoveryClient, ServerProperties serverProperties) {
        this.env = env;
        this.discoveryClient = discoveryClient;
        this.serverProperties = serverProperties;
    }

    @PreDestroy
    public void destroy() {
        log.info("Closing Cache Manager");
        Hazelcast.shutdownAll();
    }

    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        log.debug("Starting HazelcastCacheManager");
        CacheManager cacheManager = new com.hazelcast.spring.cache.HazelcastCacheManager(hazelcastInstance);
        return cacheManager;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(JHipsterProperties jHipsterProperties) {
        log.debug("Configuring Hazelcast");
        Config config = new Config();
        config.setInstanceName("gateway");
        // The serviceId is by default the application's name, see Spring Boot's eureka.instance.appname property
        String serviceId = discoveryClient.getLocalServiceInstance().getServiceId();
        log.debug("Configuring Hazelcast clustering for instanceId: {}", serviceId);

        // In development, everything goes through 127.0.0.1, with a different port
        if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)) {
            log.debug("Application is running with the \"dev\" profile, Hazelcast " +
                      "cluster will only work with localhost instances");

            System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
            config.getNetworkConfig().setPort(serverProperties.getPort() + 5701);
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
            config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
            for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
                String clusterMember = "127.0.0.1:" + (instance.getPort() + 5701);
                log.debug("Adding Hazelcast (dev) cluster member " + clusterMember);
                config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
            }
        } else { // Production configuration, one host per instance all using port 5701
            config.getNetworkConfig().setPort(5701);
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
            config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
            for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
                String clusterMember = instance.getHost() + ":5701";
                log.debug("Adding Hazelcast (prod) cluster member " + clusterMember);
                config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
            }
        }
        config.getMapConfigs().put("default", initializeDefaultMapConfig());
        config.getMapConfigs().put("com.gateway.domain.*", initializeDomainMapConfig(jHipsterProperties));
        return Hazelcast.newHazelcastInstance(config);
    }

    private MapConfig initializeDefaultMapConfig() {
        MapConfig mapConfig = new MapConfig();

    /*
        Number of backups. If 1 is set as the backup-count for example,
        then all entries of the map will be copied to another JVM for
        fail-safety. Valid numbers are 0 (no backup), 1, 2, 3.
     */
        mapConfig.setBackupCount(0);

    /*
        Valid values are:
        NONE (no eviction),
        LRU (Least Recently Used),
        LFU (Least Frequently Used).
        NONE is the default.
     */
        mapConfig.setEvictionPolicy(EvictionPolicy.LRU);

    /*
        Maximum size of the map. When max size is reached,
        map is evicted based on the policy defined.
        Any integer between 0 and Integer.MAX_VALUE. 0 means
        Integer.MAX_VALUE. Default is 0.
     */
        mapConfig.setMaxSizeConfig(new MaxSizeConfig(0, MaxSizeConfig.MaxSizePolicy.USED_HEAP_SIZE));

        return mapConfig;
    }

    private MapConfig initializeDomainMapConfig(JHipsterProperties jHipsterProperties) {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setTimeToLiveSeconds(jHipsterProperties.getCache().getHazelcast().getTimeToLiveSeconds());
        return mapConfig;
    }
}
