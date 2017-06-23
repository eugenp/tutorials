package com.baeldung.beaninjectionautowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterMethodBean {
    SimpleBean simpleBean;

    // setter for SimpleBean injection
    @Autowired 
    public void setFirstBean(SimpleBean simpleBean) {
        this.simpleBean = simpleBean;
    }

    public String tukTukBean() {
        return simpleBean.ping();
    }
}
