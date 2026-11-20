package com.baeldung.microservices.nacos;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigQueryResult;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConfigurationLiveTest {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationLiveTest.class);

    @Test
    void whenGettingConfig_thenCorrectValueIsRetrieved() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");

        ConfigService configService = NacosFactory.createConfigService(properties);

        String config = configService.getConfig("com.baeldung.nacos.Example", "DEFAULT_GROUP", 1000);
        assertEquals("Some updated config.", config);
    }

    @Test
    void whenGettingConfigResult_thenCorrectValueAndTypeIsRetrieved() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");

        ConfigService configService = NacosFactory.createConfigService(properties);

        ConfigQueryResult config = configService.getConfigWithResult("com.baeldung.nacos.Example", "DEFAULT_GROUP", 1000);
        assertEquals("text", config.getConfigType());
        assertEquals("Some updated config.", config.getContent());
    }

    @Test
    void whenListeningToConfig_thenChangesAreReceived() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");

        ConfigService configService = NacosFactory.createConfigService(properties);

        configService.addListener("com.baeldung.nacos.Example", "DEFAULT_GROUP", new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                LOG.info("Received config info: {}", configInfo);
            }
        });

        Thread.currentThread().join();
    }

    @Test
    void whenGettingAndListeningToConfig_thenChangesAreReceived() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");

        ConfigService configService = NacosFactory.createConfigService(properties);

        String config = configService.getConfigAndSignListener("com.baeldung.nacos.Example", "DEFAULT_GROUP", 1000, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                LOG.info("Received config info: {}", configInfo);
            }
        });

        assertEquals("Some updated config.", config);

        Thread.currentThread().join();
    }
}
