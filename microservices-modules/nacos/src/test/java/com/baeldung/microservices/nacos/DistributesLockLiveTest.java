package com.baeldung.microservices.nacos;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.lock.LockService;
import com.alibaba.nacos.client.lock.core.NLock;
import org.junit.jupiter.api.Test;

public class DistributesLockLiveTest {
    @Test
    void whenLocking_thenTheLockIsObtained() throws Exception{
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");

        LockService lockService = NacosFactory.createLockService(properties);

        NLock lock = new NLock("Baeldung_lock", 5000L);
        assertTrue(lockService.lock(lock));
    }

    @Test
    void whenLockingTwice_thenTheLockIsNotObtained() throws Exception{
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");


        LockService lockService = NacosFactory.createLockService(properties);

        NLock lock = new NLock("Baeldung_lockTwice", 5000L);
        assertTrue(lockService.lock(lock));
        assertFalse(lockService.lock(lock));
    }

    @Test
    void whenUnlockingAndLocking_thenTheLockIsObtained() throws Exception{
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");


        LockService lockService = NacosFactory.createLockService(properties);

        NLock lock = new NLock("Baeldung_unlock", 5000L);
        assertTrue(lockService.lock(lock));
        lockService.unLock(lock);
        assertTrue(lockService.lock(lock));
    }

    @Test
    void whenTimingOutAndReLocking_thenTheLockIsObtained() throws Exception{
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");


        LockService lockService = NacosFactory.createLockService(properties);

        NLock lock = new NLock("Baeldung_timeout", 100L);
        assertTrue(lockService.lock(lock));
        Thread.sleep(200L);
        assertTrue(lockService.lock(lock));
    }
}
