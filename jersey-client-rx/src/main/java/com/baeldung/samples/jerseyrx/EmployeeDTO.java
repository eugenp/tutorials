package com.baeldung.samples.jerseyrx;

import java.util.List;

/**
 *
 * @author baeldung
 */
public class EmployeeDTO {

    private List<Long> empIds;

    public List<Long> getEmpIds() {
        return empIds;
    }

    public void setEmpIds(List<Long> empIds) {
        this.empIds = empIds;
    }

}
