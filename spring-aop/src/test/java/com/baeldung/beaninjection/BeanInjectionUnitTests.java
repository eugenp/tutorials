package com.baeldung.beaninjection;

import com.baeldung.beaninjection.service.FileSystemStorageService;
import com.baeldung.beaninjection.service.NetworkStorageService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by smatt on 13/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BeanInjectionUnitTests {

    @Autowired
    NetworkStorageService networkStorageService;

    @Autowired
    FileSystemStorageService fileSystemStorageService;

    Logger logger = Logger.getLogger(BeanInjectionApplicationTests.class);


    @Test
    public void contextLoads() {
    }

    @Test
    public void givenAutowiredOnClassConstructor_whenInstantiatingAndCallingGetter_thenDependencyShouldResolve() {
        Assert.assertNotNull("FileSystemStorageService not autowired in test class", fileSystemStorageService);
        Assert.assertNotNull("StorageProperties not autowired in FileSystemStorageService", fileSystemStorageService.getStorageProperties());
        logger.info(fileSystemStorageService.toString());
    }

    @Test
    public void givenAutowiredOnClassSetter_whenInstantiatingAndCallingGetter_thenDependencyShouldResolve() {
        Assert.assertNotNull("NetworkStorageService not autowired in test class", networkStorageService);
        Assert.assertNotNull("StorageProperties not autowired in NetworkStorageService", networkStorageService.getStorageProperties());
        logger.info(networkStorageService.toString());
    }

}
