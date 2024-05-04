package com.baeldung.copytechniques.deepcopy;

import com.baeldung.copytechniques.deepcopy.Department;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Student implements Cloneable {
    private String name;
    private Department department;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
