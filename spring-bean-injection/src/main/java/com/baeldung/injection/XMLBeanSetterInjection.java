package com.baeldung.injection;

import com.baeldung.bean.SpringAnnotatedBean;

public class XMLBeanSetterInjection {

    private SpringAnnotatedBean springAnnotatedBean;

    public SpringAnnotatedBean getSpringAnnotatedBean() {
        return springAnnotatedBean;
    }

    public void setSpringAnnotatedBean(SpringAnnotatedBean springAnnotatedBean) {
        this.springAnnotatedBean = springAnnotatedBean;
    }
}
