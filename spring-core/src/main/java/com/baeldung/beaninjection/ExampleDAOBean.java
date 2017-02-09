package com.baeldung.beaninjection;

import java.util.List;

public class ExampleDAOBean implements IExampleDAO {

    private String propertyX;

    public ExampleDAOBean(String propertyX) {
        this.propertyX = propertyX;
    }

    public String getPropertyX() {
        return propertyX;
    }

    public void setPropertyX(String propertyX) {
        this.propertyX = propertyX;
    }

    @Override
    public List<SampleDomainObject> getDomainList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SampleDomainObject createNewDomain(SampleDomainObject inputDomain) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SampleDomainObject getSomeDomain() {
        // TODO Auto-generated method stub
        return new SampleDomainObject();
    }

}
