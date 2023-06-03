package com.baeldung.hibernate.customtypes;

import org.hibernate.annotations.CompositeType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.usertype.UserTypeLegacyBridge;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "OfficeEmployee")
public class OfficeEmployee {

    @Id
    @GeneratedValue
    private long id;

    @Column
    @Type(
            value = UserTypeLegacyBridge.class,
            parameters = @Parameter(name = UserTypeLegacyBridge.TYPE_NAME_PARAM_KEY, value = "LocalDateString")
    )
    private LocalDate dateOfJoining;

    @AttributeOverrides({
            @AttributeOverride(name = "countryCode", column = @Column(name = "country_code")),
            @AttributeOverride(name = "cityCode", column = @Column(name = "city_code")),
            @AttributeOverride(name = "number", column = @Column(name = "number"))
    })
    @CompositeType(value = PhoneNumberType.class)
    private PhoneNumber employeeNumber;

    @CompositeType(value = com.baeldung.hibernate.customtypes.AddressType.class)
    @AttributeOverrides({
            @AttributeOverride(name = "addressLine1", column = @Column(name = "address_line_1")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "address_line_2")),
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "country", column = @Column(name = "country")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "zip_code"))
    })
    private Address empAddress;

    @Type(value = com.baeldung.hibernate.customtypes.SalaryType.class,
            parameters = {@Parameter(name = "currency", value = "USD")})
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
