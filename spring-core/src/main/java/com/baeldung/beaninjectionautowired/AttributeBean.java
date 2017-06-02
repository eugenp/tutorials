package com.baeldung.beaninjectionautowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttributeBean {

    // attribute for SimpleBean injection
    @Autowired
    private SimpleBean simpleBean;

    public String tukTukBean() {
        return simpleBean.ping();
    }
}
