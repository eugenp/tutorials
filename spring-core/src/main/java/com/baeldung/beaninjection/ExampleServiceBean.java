package com.baeldung.beaninjection;

import java.util.List;

public class ExampleServiceBean implements IExampleService {

    private IExampleDAO exampleDAO;
    private IAnotherSampleDAO anotherSampleDAO;

    public ExampleServiceBean(IExampleDAO exampleDAO) {
        this.exampleDAO = exampleDAO;
    }

    public void setAnotherSampleDAO(IAnotherSampleDAO anotherSampleDAO) {
        this.anotherSampleDAO = anotherSampleDAO;
    }

    // standard setters and getters

    public IAnotherSampleDAO getAnotherSampleDAO() {
        return anotherSampleDAO;
    }

    public void setExampleDAO(ExampleDAOBean exampleDAO) {
        this.exampleDAO = exampleDAO;
    }

    public IExampleDAO getExampleDAO() {
        return exampleDAO;
    }

    private String propertyX;

    public String getPropertyX() {
        return propertyX;
    }

    public void setPropertyX(String propertyX) {
        this.propertyX = propertyX;
    }

    public List<SampleDomainObject> serviceMethodX() {
        /*get domain list from DAO .. business logic on domain objects..return*/
        return exampleDAO.getDomainList();
    }

}
