package com.baeldung.hibernate.customtypes;

import com.baeldung.hibernate.pojo.Phone;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@TypeDef(name = "PhoneNumber",
        typeClass = PhoneNumberType.class,
        defaultForType = PhoneNumber.class)
@Entity
@Table(name = "OfficeEmployee")
public class OfficeEmployee {

    @Id
    @GeneratedValue
    private long id;

    @Column
    @Type(type = "LocalDateString")
    private LocalDate dateOfJoining;

    @Columns(columns = {@Column(name = "country_code"),
            @Column(name = "city_code"),
            @Column(name = "number")})
    private PhoneNumber employeeNumber;

    @Columns(columns = {@Column(name = "address_line_1"),
            @Column(name = "address_line_2"),
            @Column(name = "city"), @Column(name = "country"),
            @Column(name = "zip_code")})
    @Type(type = "com.baeldung.hibernate.customtypes.AddressType")
    private Address empAddress;

    @Type(type = "com.baeldung.hibernate.customtypes.SalaryType",
            parameters = {@Parameter(name = "currency", value = "USD")})
    @Columns(columns = {@Column(name = "amount"),
            @Column(name = "currency")})
    private Salary salary;

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public PhoneNumber getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(PhoneNumber employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Address getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(Address empAddress) {
        this.empAddress = empAddress;
    }
}
