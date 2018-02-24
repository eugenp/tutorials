package com.baeldung.injection;

import com.baeldung.bean.SpringAnnotatedBean;
import org.springframework.stereotype.Component;

@Component
public class SpringConstructorBeanInjection {

    public SpringAnnotatedBean springAnnotatedBean;

    public SpringConstructorBeanInjection(SpringAnnotatedBean springAnnotatedBean) {
        this.springAnnotatedBean = springAnnotatedBean;
    }

    public SpringAnnotatedBean getSpringAnnotatedBean() {
        return springAnnotatedBean;
    }
}
