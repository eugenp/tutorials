package com.baeldung.injection;

import com.baeldung.bean.SpringAnnotatedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;

@Component
public class SpringPropertyBeanInjection {

    @Autowired
    private SpringAnnotatedBean autowiredInjection;

    @Inject
    private SpringAnnotatedBean injectInjection;

    @Resource
    private SpringAnnotatedBean resourceInjection;

    public SpringAnnotatedBean getAutowiredInjection() {
        return autowiredInjection;
    }

    public SpringAnnotatedBean getInjectInjection() {
        return injectInjection;
    }

    public SpringAnnotatedBean getResourceInjection() {
        return resourceInjection;
    }
}
