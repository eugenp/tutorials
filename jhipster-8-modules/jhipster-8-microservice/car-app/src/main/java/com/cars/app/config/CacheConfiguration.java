package com.cars.app.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;

    private final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

    private final Environment env;

    private final ServerProperties serverProperties;

    private final DiscoveryClient discoveryClient;

    private Registration registration;

    public CacheConfiguration(Environment env, ServerProperties serverProperties, DiscoveryClient discoveryClient) {
        this.env = env;
        this.serverProperties = serverProperties;
        this.discoveryClient = discoveryClient;
    }

    @Autowired(required = false)
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    @PreDestroy
    public void destroy() {
        log.info("Closing Cache Manager");
        Hazelcast.shutdownAll();
    }

    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        log.debug("Starting HazelcastCacheManager");
        return new com.hazelcast.spring.cache.HazelcastCacheManager(hazelcastInstance);
    }

    @Bean
    public HazelcastInstance hazelcastInstance(JHipsterProperties jHipsterProperties) {
        log.debug("Configuring Hazelcast");
        HazelcastInstance hazelCastInstance = Hazelcast.getHazelcastInstanceByName("carsapp");
        if (hazelCastInstance != null) {
            log.debug("Hazelcast already initialized");
            return hazelCastInstance;
        }
        Config config = new Config();
        config.setInstanceName("carsapp");
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        if (this.registration == null) {
            log.warn("No discovery service is set up, Hazelcast cannot create a cluster.");
        } else {
            // The serviceId is by default the application's name,
            // see the "spring.application.name" standard Spring property
            String serviceId = registration.getServiceId();
            log.debug("Configuring Hazelcast clustering for instanceId: {}", serviceId);
            // In development, everything goes through 127.0.0.1, with a different port
            if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT))) {
                log.debug(
                    "Application is running with the \"dev\" profile, Hazelcast " + "cluster will only work with localhost instances"
                );

                config.getNetworkConfig().setPort(serverProperties.getPort() + 5701);
                config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
                for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
                    String clusterMember = "127.0.0.1:" + (instance.getPort() + 5701);
                    log.debug("Adding Hazelcast (dev) cluster member {}", clusterMember);
                    config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
                }
            } else { // Production configuration, one host per instance all using port 5701
                config.getNetworkConfig().setPort(5701);
                config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
                for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
                    String clusterMember = instance.getHost() + ":5701";
                    log.debug("Adding Hazelcast (prod) cluster member {}", clusterMember);
                    config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
                }
            }
        }
        config.setManagementCenterConfig(new ManagementCenterConfig());
        config.addMapConfig(initializeDefaultMapConfig(jHipsterProperties));
        config.addMapConfig(initializeDomainMapConfig(jHipsterProperties));
        return Hazelcast.newHazelcastInstance(config);
    }

    private MapConfig initializeDefaultMapConfig(JHipsterProperties jHipsterProperties) {
        MapConfig mapConfig = new MapConfig("default");

        /*
        Number of backups. If 1 is set as the backup-count for example,
        then all entries of the map will be copied to another JVM for
        fail-safety. Valid numbers are 0 (no backup), 1, 2, 3.
        */
        mapConfig.setBackupCount(jHipsterProperties.getCache().getHazelcast().getBackupCount());

        /*
        Valid values are:
        NONE (no eviction),
        LRU (Least Recently Used),
        LFU (Least Frequently Used).
        NONE is the default.
        */
        mapConfig.getEvictionConfig().setEvictionPolicy(EvictionPolicy.LRU);

        /*
        Maximum size of the map. When max size is reached,
        map is evicted based on the policy defined.
        Any integer between 0 and Integer.MAX_VALUE. 0 means
        Integer.MAX_VALUE. Default is 0.
        */
        mapConfig.getEvictionConfig().setMaxSizePolicy(MaxSizePolicy.USED_HEAP_SIZE);

        return mapConfig;
    }

    private MapConfig initializeDomainMapConfig(JHipsterProperties jHipsterProperties) {
        MapConfig mapConfig = new MapConfig("com.cars.app.domain.*");
        mapConfig.setTimeToLiveSeconds(jHipsterProperties.getCache().getHazelcast().getTimeToLiveSeconds());
        return mapConfig;
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
