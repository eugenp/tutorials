package com.baeldung.ignite.spring.dto;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {

    @QuerySqlField(index = true)
    private Integer id;
    @QuerySqlField(index = true)
    private String name;
    @QuerySqlField(index = true)
    private boolean isEmployed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmployed() {
        return isEmployed;
    }

    public void setEmployed(boolean employed) {
        isEmployed = employed;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isEmployed=" + isEmployed +
                '}';
    }
}
