package com.baeldung.microservices.nacos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.listener.NamingChangeEvent;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ServiceDiscoveryLiveTest {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationLiveTest.class);

    @Test
    void whenRegisteringAnAddress_thenTheAddressIsRetrievable() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");

        NamingService namingService = NacosFactory.createNamingService(properties);

        namingService.registerInstance("BaeldungTest", "localhost", 8848);

        Instance baeldungTest = namingService.selectOneHealthyInstance("BaeldungTest");
        assertEquals("localhost", baeldungTest.getIp());
        assertEquals(8848, baeldungTest.getPort());

        Thread.sleep(10000);
    }

    @Test
    void whenRegisteringAnInstance_thenTheAddressIsRetrievable() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");

        NamingService namingService = NacosFactory.createNamingService(properties);

        Instance instance = new Instance();
        instance.setIp("localhost");
        instance.setPort(8848);
        instance.setHealthy(true);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Example", "value");
        instance.setMetadata(metadata);

        namingService.registerInstance("BaeldungTest", instance);
        List<Instance> allInstances = namingService.getAllInstances("BaeldungTest");
        assertEquals(1, allInstances.size());
        assertEquals("localhost", allInstances.get(0).getIp());
        assertEquals(8848, allInstances.get(0).getPort());

        Thread.sleep(10000);
    }

    @Test
    void whenReRegisteringAnInstance_thenTheAddressIsRetrievable() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");

        NamingService namingService = NacosFactory.createNamingService(properties);

        {
            Instance instance = new Instance();
            instance.setIp("localhost");
            instance.setPort(8848);
            instance.setEnabled(true);
            instance.setHealthy(true);

            Map<String, String> metadata = new HashMap<>();
            metadata.put("Example", "value");
            instance.setMetadata(metadata);

            namingService.registerInstance("BaeldungTest", instance);
            Thread.sleep(1000); // Enough time to update

            List<Instance> allInstances = namingService.selectInstances("BaeldungTest", true);
            assertEquals(1, allInstances.size());
            assertEquals("localhost", allInstances.get(0).getIp());
            assertEquals(8848, allInstances.get(0).getPort());
        }

        {
            Instance instance = new Instance();
            instance.setIp("localhost");
            instance.setPort(8848);
            instance.setEnabled(true);
            instance.setHealthy(false);

            Map<String, String> metadata = new HashMap<>();
            metadata.put("Example", "value");
            instance.setMetadata(metadata);

            namingService.registerInstance("BaeldungTest", instance);
            Thread.sleep(1000); // Enough time to update

            List<Instance> allInstances = namingService.selectInstances("BaeldungTest", true);
            assertEquals(0, allInstances.size());
        }

        Thread.sleep(10000);
    }

    @Test
    void whenDeregisteringAnAddress_thenTheAddressIsNotRetrievable() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");

        NamingService namingService = NacosFactory.createNamingService(properties);

        namingService.subscribe("BaeldungTest", (event) -> {
            NamingChangeEvent namingChangeEvent = (NamingChangeEvent) event;
            LOG.info("Added Instances: {}", namingChangeEvent.getAddedInstances());
            LOG.info("Removed Instances: {}", namingChangeEvent.getRemovedInstances());
            LOG.info("Modified Instances: {}", namingChangeEvent.getModifiedInstances());

        });
        Thread.sleep(1000);

        namingService.registerInstance("BaeldungTest", "localhost", 8848);
        Thread.sleep(1000);
        namingService.deregisterInstance("BaeldungTest", "localhost", 8848);
        Thread.sleep(1000);

        Thread.sleep(10000);
    }
}
