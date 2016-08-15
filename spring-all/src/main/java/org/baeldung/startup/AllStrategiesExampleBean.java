package org.baeldung.startup;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

public class AllStrategiesExampleBean implements InitializingBean {

    private static final Logger LOG = Logger.getLogger(AllStrategiesExampleBean.class);

    public AllStrategiesExampleBean() {
        LOG.info("Constructor");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("InitializingBean");
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("PostConstruct");
    }

    public void init() {
        LOG.info("init-method");
    }
}
