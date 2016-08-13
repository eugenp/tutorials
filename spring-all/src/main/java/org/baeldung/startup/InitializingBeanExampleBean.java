package org.baeldung.startup;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;

@Scope(value = "prototype")
public class InitializingBeanExampleBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
