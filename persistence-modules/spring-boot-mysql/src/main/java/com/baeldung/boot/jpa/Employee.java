package com.baeldung.boot.jpa;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "emp_name")
    private String empName;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "emp_doj")
    private LocalDate empDoj;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    public Integer getId() {
        return id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getEmpDoj() {
        return empDoj;
    }

    public void setEmpDoj(LocalDate empDoj) {
        this.empDoj = empDoj;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
