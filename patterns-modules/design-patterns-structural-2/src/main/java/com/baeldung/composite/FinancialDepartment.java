package com.baeldung.composite;

import static com.baeldung.util.LoggerUtil.LOG;

/**
 * Created by Gebruiker on 5/1/2018.
 */
public class FinancialDepartment implements Department {

    private Integer id;
    private String name;

    public FinancialDepartment(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void printDepartmentName() {
        LOG.info(getClass().getSimpleName());
    }

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
}
