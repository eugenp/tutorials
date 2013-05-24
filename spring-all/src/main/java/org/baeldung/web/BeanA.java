package org.baeldung.web;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanA implements InitializingBean {

    @Autowired
    private IBeanC dependency;

    public BeanA() {
        super();
    }

    //

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println();
    }

}
