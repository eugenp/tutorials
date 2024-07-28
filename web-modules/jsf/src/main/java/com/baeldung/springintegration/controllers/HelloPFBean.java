package com.baeldung.springintegration.controllers;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("helloPFBean")
@ViewScoped
public class HelloPFBean {

    private String firstName;
    private String lastName;

    private String componentSuite;

    private List<Technology> technologies;

    private String inputText;
    private String outputText;

    @PostConstruct
    public void init() {
        firstName = "Hello";
        lastName = "Primefaces";

        technologies = new ArrayList<Technology>();

        Technology technology1 = new Technology();
        technology1.setCurrentVersion("10");
        technology1.setName("Java");

        technologies.add(technology1);

        Technology technology2 = new Technology();
        technology2.setCurrentVersion("5.0");
        technology2.setName("Spring");

        technologies.add(technology2);
    }

    public void onBlurEvent() {
        outputText = inputText.toUpperCase();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getComponentSuite() {
        return componentSuite;
    }

    public void setComponentSuite(String componentSuite) {
        this.componentSuite = componentSuite;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public class Technology {
        private String name;
        private String currentVersion;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCurrentVersion() {
            return currentVersion;
        }

        public void setCurrentVersion(String currentVersion) {
            this.currentVersion = currentVersion;
        }

    }

}
