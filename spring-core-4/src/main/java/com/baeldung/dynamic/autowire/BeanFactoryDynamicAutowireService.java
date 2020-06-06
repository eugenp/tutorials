package com.baeldung.dynamic.autowire;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeanFactoryDynamicAutowireService {
    private static final String SERVICE_NAME_SUFFIX = "regionService";
    private final BeanFactory beanFactory;

    @Autowired
    public BeanFactoryDynamicAutowireService(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public boolean isServerActive(String isoCountryCode, int serverId) {
        RegionService service = beanFactory.getBean(getRegionServiceBeanName(isoCountryCode), RegionService.class);

        return service.isServerActive(serverId);
    }

    private String getRegionServiceBeanName(String isoCountryCode) {
        return isoCountryCode + SERVICE_NAME_SUFFIX;
    }

}
