package com.baeldung.azure.functions.http.entity;

public class Employee {
    private String name;
    private String department;
    private String sex;
    private String partitionKey;
    private String rowKey;

    public Employee(String name, String department, String sex, String partitionKey, String rowKey) {
        this.name = name;
        this.department = department;
        this.sex = sex;
        this.partitionKey = partitionKey;
        this.rowKey = rowKey;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
