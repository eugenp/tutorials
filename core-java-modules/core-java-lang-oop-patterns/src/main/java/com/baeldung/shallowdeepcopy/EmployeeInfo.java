package com.baeldung.shallowdeepcopy;

class EmployeeInfo {
    private String jobTitle;
    private Integer salary;

    public EmployeeInfo(String jobTitle, Integer salary) {
        this.jobTitle = jobTitle;
        this.salary = salary;
    }

    public EmployeeInfo(EmployeeInfo employeeInfo) {
        this.jobTitle = employeeInfo.jobTitle;
        this.salary = employeeInfo.salary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
