package org.baeldung.copy.shallow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Employee implements Cloneable{
    private String employeeId;
    private String name;
    private Department department;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
