package com.baeldung.beaninjection;

import java.io.Serializable;

public class SampleDomainObject implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 449859763481296747L;

    private String domainPropX;
    private String domainPropY;

    public String getDomainPropX() {
        return domainPropX;
    }

    public void setDomainPropX(String domainPropX) {
        this.domainPropX = domainPropX;
    }

    public String getDomainPropY() {
        return domainPropY;
    }

    public void setDomainPropY(String domainPropY) {
        this.domainPropY = domainPropY;
    }

}
