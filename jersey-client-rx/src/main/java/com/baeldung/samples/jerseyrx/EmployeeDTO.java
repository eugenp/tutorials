package com.baeldung.samples.jerseyrx;

import java.util.List;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.empIds);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmployeeDTO other = (EmployeeDTO) obj;
        if (!Objects.equals(this.empIds, other.empIds)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" + "empIds=" + empIds + '}';
    }
    
    

}
