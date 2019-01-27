package org.baeldung.sampleabstract;

import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractService {

    @Autowired
    private FooBean fooBean;

    private BarBean barBean;

    private FooBarBean fooBarBean;

    public AbstractService(FooBarBean fooBarBean) {

        this.fooBarBean = fooBarBean;
    }

    public FooBean getFooBean() {

        return fooBean;
    }

    public void setFooBean(FooBean fooBean) {

        this.fooBean = fooBean;
    }

    public BarBean getBarBean() {

        return barBean;
    }

    @Autowired
    public void setBarBean(BarBean barBean) {

        this.barBean = barBean;
    }

    public FooBarBean getFooBarBean() {

        return fooBarBean;
    }

    public void setFooBarBean(FooBarBean fooBarBean) {

        this.fooBarBean = fooBarBean;
    }

    public void afterInitialize() {

        System.out.println(fooBean.value());
        System.out.println(barBean.value());
        System.out.println(fooBarBean.value());
    }
}
