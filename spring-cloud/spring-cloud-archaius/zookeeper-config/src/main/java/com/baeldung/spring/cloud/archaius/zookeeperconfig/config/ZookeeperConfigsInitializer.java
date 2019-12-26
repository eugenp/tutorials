package com.baeldung.spring.cloud.archaius.zookeeperconfig.config;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 
 * Ideally, we wouldn't need to initialize the zookeeper config values.
 * Here we do it to verify that configurations are being retrieved.
 *
 */
@Component
public class ZookeeperConfigsInitializer {

    private static final String CONFIG_BASE_NODE_PATH = "/config";
    private static final String APPLICATION_BASE_NODE_PATH = CONFIG_BASE_NODE_PATH + "/application";

    @Autowired
    CuratorFramework client;

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws Exception {
        String pathOne = APPLICATION_BASE_NODE_PATH + "/baeldung.archaius.properties.one";
        String valueOne = "one FROM:zookeeper";
        String pathThree = APPLICATION_BASE_NODE_PATH + "/baeldung.archaius.properties.three";
        String valueThree = "three FROM:zookeeper";
        createBaseNodes();
        setValue(pathOne, valueOne);
        setValue(pathThree, valueThree);
    }

    private void setValue(String path, String value) throws Exception {
        if (client.checkExists()
            .forPath(path) == null) {
            client.create()
                .forPath(path, value.getBytes());
        } else {
            client.setData()
                .forPath(path, value.getBytes());
        }
    }

    private void createBaseNodes() throws Exception {
        if (client.checkExists()
            .forPath(CONFIG_BASE_NODE_PATH) == null) {
            client.create()
                .forPath(CONFIG_BASE_NODE_PATH);
        }
        if (client.checkExists()
            .forPath(APPLICATION_BASE_NODE_PATH) == null) {
            client.create()
                .forPath(APPLICATION_BASE_NODE_PATH);
        }
    }
}
