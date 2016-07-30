package com.baeldung.springintegration.controllers;

import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

@ManagedBean(name = "ELBean")
@ViewScoped
public class ELSampleBean {

    private String firstName;
    private String lastName;
    private String pageDescription = "This page demos JSF EL Basics";
    private int pageCounter;
    private Random randomIntGen = new Random();

    @PostConstruct
    public void init() {
        pageCounter = randomIntGen.nextInt();
    }

    public void save() {

    }

    public void saveFirstName(String firstName) {
        this.firstName = firstName;
    }


    public void saveByELEvaluation() {
        firstName = (String) evaluateEL("#{firstName.value}", String.class);
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage theMessage = new FacesMessage("Name component Evaluated: " + firstName);
        theMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        ctx.addMessage(null, theMessage);

    }

    private Object evaluateEL(String elExpression, Class<?> clazz) {
        Object toReturn = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        Application app = ctx.getApplication();
        toReturn = app.evaluateExpressionGet(ctx, elExpression, clazz);

        return toReturn;

    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the pageDescription
     */
    public String getPageDescription() {
        return pageDescription;
    }

    /**
     * @param pageDescription the pageDescription to set
     */
    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

    /**
     * @return the pageCounter
     */
    public int getPageCounter() {
        return pageCounter;
    }

    /**
     * @param pageCounter the pageCounter to set
     */
    public void setPageCounter(int pageCounter) {
        this.pageCounter = pageCounter;
    }
}
