package com.baeldung.aware;

import org.springframework.beans.factory.BeanNameAware;

public class MyBeanName implements BeanNameAware {

    @Override
    public void setBeanName(String beanName) {
        System.out.println(beanName);
    }
}
