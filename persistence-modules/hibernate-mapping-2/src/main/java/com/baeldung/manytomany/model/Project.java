package com.baeldung.manytomany.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Project")
public class Project implements Serializable {

    @Id
    @Column(name = "project_id")
    @GeneratedValue
    private Long projectId;

    @Column(name = "title")
    private String title;

    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees = new HashSet<Employee>();
    
    public Project() {
        super();
    }

    public Project(String title) {
        this.title = title;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    
    
}
