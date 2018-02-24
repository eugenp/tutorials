package com.baeldung.injection;

import com.baeldung.bean.SpringAnnotatedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;

@Component
public class SpringSetterBeanInjection {

    private SpringAnnotatedBean springAutowiredBean;

    private SpringAnnotatedBean springInjectBean;

    private SpringAnnotatedBean springResourceBean;

    public SpringAnnotatedBean getSpringAutowiredBean() {
        return springAutowiredBean;
    }

    @Autowired
    public void setSpringAutowiredBean(SpringAnnotatedBean springAutowiredBean) {
        this.springAutowiredBean = springAutowiredBean;
    }

    public SpringAnnotatedBean getSpringInjectBean() {
        return springInjectBean;
    }

    @Inject
    public void setSpringInjectBean(SpringAnnotatedBean springInjectBean) {
        this.springInjectBean = springInjectBean;
    }

    public SpringAnnotatedBean getSpringResourceBean() {
        return springResourceBean;
    }

    @Resource
    public void setSpringResourceBean(SpringAnnotatedBean springResourceBean) {
        this.springResourceBean = springResourceBean;
    }
}
