package com.baeldung.lombok.equalsandhashcode.include.methodlevel;

import lombok.Getter;

@Getter
public class EmployeeDelomboked {
    private String name;
    private int id;
    private int age;

    public boolean hasOddId() {
        return id % 2 != 0;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Employee)) return false;
        final Employee other = (Employee) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        if (this.getAge() != other.getAge()) return false;
        if (this.hasOddId() != other.hasOddId()) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        result = result * PRIME + this.getAge();
        result = result * PRIME + (this.hasOddId() ? 79 : 97);
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }
}

