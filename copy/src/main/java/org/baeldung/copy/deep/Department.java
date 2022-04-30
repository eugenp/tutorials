package org.baeldung.copy.deep;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Department implements Cloneable {

    private String departmentId;
    private String departmentName;

    public Department(Department department) {
        this.departmentId = department.getDepartmentId();
        this.departmentName = department.getDepartmentName();
    }

    @Override
    protected Department clone() throws CloneNotSupportedException {
        return (Department) super.clone();
    }
}
