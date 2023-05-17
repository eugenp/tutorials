package com.baeldung.lombok.equalsandhashcode.exclude.fieldlevel;


import lombok.Getter;

@Getter
public class EmployeeDelomboked {
    private String name;
    private int id;
    private int age;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof com.baeldung.lombok.equalsandhashcode.exclude.fieldlevel.Employee)) return false;
        final com.baeldung.lombok.equalsandhashcode.exclude.fieldlevel.Employee other = (com.baeldung.lombok.equalsandhashcode.exclude.fieldlevel.Employee) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }
}

