package org.baeldung.thymeleaf.model;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 
 * Simple student POJO with two fields - id and name
 *
 */
public class Student implements Serializable {

    private static final long serialVersionUID = -8582553475226281591L;

    @NotNull(message = "Student ID is required.")
    @Min(value = 1000, message = "Student ID must be atleast 4 digits.")
    private Integer id;

    @NotNull(message = "Student Name is required.")
    private String name;

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
