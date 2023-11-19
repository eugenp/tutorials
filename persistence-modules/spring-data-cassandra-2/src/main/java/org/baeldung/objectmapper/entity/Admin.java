package org.baeldung.objectmapper.entity;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.HierarchyScanStrategy;

@Entity
@CqlName("admin_profile")
@HierarchyScanStrategy(highestAncestor = User.class, includeHighestAncestor = true)
public class Admin extends User {
    private String role;
    private String department;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}

