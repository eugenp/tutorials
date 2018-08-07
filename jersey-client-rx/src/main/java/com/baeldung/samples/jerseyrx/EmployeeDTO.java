package com.baeldung.samples.jerseyrx;

import java.util.List;

/**
 *
 * @author SIGINT-X
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
