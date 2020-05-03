package com.baeldung.dynamic.autowire;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeanFactoryDynamicAutowireService {
    private final BeanFactory beanFactory;

    @Autowired
    public BeanFactoryDynamicAutowireService(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public boolean isServerActive(String countryCode, int serverId) {
        RegionService service = beanFactory.getBean(countryCode, RegionService.class);

        return service.isServerActive(serverId);
    }

}
