package com.baeldung.injection;

import com.baeldung.bean.SpringAnnotatedBean;

public class XMLBeanConstructorInjection {

    private SpringAnnotatedBean springAnnotatedBean;

    public XMLBeanConstructorInjection(SpringAnnotatedBean springAnnotatedBean) {
        this.springAnnotatedBean = springAnnotatedBean;
    }

    public SpringAnnotatedBean getSpringAnnotatedBean() {
        return springAnnotatedBean;
    }
}
