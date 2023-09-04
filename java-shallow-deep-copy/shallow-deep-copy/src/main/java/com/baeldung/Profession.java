package com.baeldung;

public class Profession {
    private String jobTitle;
    private Double salary;
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }


    public Profession(String jobTitle, Double salary){
        this.jobTitle = jobTitle;
        this.salary = salary;
    }
    // standard getters and setters

}
