package org.baeldung.startup;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class InitializingBeanExampleBean implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(InitializingBeanExampleBean.class);

    @Autowired
    private Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("Env Default Profiles", Arrays.asList(environment.getDefaultProfiles()));
    }
}
