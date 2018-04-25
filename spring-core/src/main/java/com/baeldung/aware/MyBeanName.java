package com.baeldung.aware;

import org.springframework.beans.factory.BeanNameAware;

/**
 * Created by Gebruiker on 4/25/2018.
 */
public class MyBeanName implements BeanNameAware {

    @Override
    public void setBeanName(String beanName) {
        System.out.println(beanName);
    }
}
