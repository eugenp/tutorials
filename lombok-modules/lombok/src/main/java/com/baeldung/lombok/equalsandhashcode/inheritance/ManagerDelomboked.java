package com.baeldung.lombok.equalsandhashcode.inheritance;

import lombok.Getter;

@Getter
public class ManagerDelomboked extends Employee {
    private String departmentName;
    private int uid;

    public ManagerDelomboked(String departmentName, int uid, String name, int id, int age) {
        super(name, id, age);
        this.departmentName = departmentName;
        this.uid = uid;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof com.baeldung.lombok.equalsandhashcode.inheritance.Manager)) return false;
        final com.baeldung.lombok.equalsandhashcode.inheritance.Manager other = (com.baeldung.lombok.equalsandhashcode.inheritance.Manager) o;
        if (!other.canEqual((Object) this)) return false;
        if (!super.equals(o)) return false;
        if (this.getUid() != other.getUid()) return false;
        final Object this$departmentName = this.getDepartmentName();
        final Object other$departmentName = other.getDepartmentName();
        if (this$departmentName == null ? other$departmentName != null : !this$departmentName.equals(other$departmentName)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        result = result * PRIME + this.getUid();
        final Object $departmentName = this.getDepartmentName();
        result = result * PRIME + ($departmentName == null ? 43 : $departmentName.hashCode());
        return result;
    }
}
