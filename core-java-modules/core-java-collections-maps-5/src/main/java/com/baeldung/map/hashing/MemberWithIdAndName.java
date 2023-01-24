package com.baeldung.map.hashing;

import java.util.Objects;

public class MemberWithIdAndName extends Member {
    public static final int PRIME = 31;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberWithObjects that = (MemberWithObjects) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = PRIME * result + (name == null ? 0  : name.hashCode());
        return result;
    }
}
