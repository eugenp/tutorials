package com.baeldung.jpa.sqlresultsetmapping;

import jakarta.persistence.*;


@SqlResultSetMapping(
        name="EmployeeResult",
        entities={
                @EntityResult(
                        entityClass = com.baeldung.jpa.sqlresultsetmapping.Employee.class,
                        fields={@FieldResult(name="id",column="employeeNumber"),
                                 @FieldResult(name="name", column="name")}
                )
        }
)
@NamedNativeQuery(
        name="Employees",
        query="SELECT id as employeeNumber, name FROM EMPLOYEE",
        resultSetMapping = "EmployeeResult"
)
@Entity
public class Employee {

    @Id
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
