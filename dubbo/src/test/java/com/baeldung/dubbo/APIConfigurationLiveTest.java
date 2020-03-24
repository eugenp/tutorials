package com.baeldung.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.baeldung.dubbo.remote.GreetingsService;
import com.baeldung.dubbo.remote.GreetingsServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author aiet
 */
public class APIConfigurationLiveTest {

    @Before
    public void initProvider() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("demo-provider");
        application.setVersion("1.0");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("multicast://224.1.1.1:9090");

        ServiceConfig<GreetingsService> service = new ServiceConfig<>();
        service.setApplication(application);
        service.setRegistry(registryConfig);
        service.setInterface(GreetingsService.class);
        service.setRef(new GreetingsServiceImpl());

        service.export();
    }

    @Test
    public void givenProviderConsumer_whenSayHi_thenGotResponse() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("demo-consumer");
        application.setVersion("1.0");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("multicast://224.1.1.1:9090");

        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registryConfig);
        reference.setInterface(GreetingsService.class);

        GreetingsService greetingsService = reference.get();
        String hiMessage = greetingsService.sayHi("baeldung");

        assertEquals("hi, baeldung", hiMessage);
    }

}
